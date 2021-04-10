import struct
import sys
import requests
from math import floor
from prettytable import PrettyTable
from z3 import *

URL="https://ffe6b8f2-e574-4cbb-a99d-f8bef1a49528.idocker.vuln.land"

MASK = 0xFFFFFFFFFFFFFFFF

# ToDouble() changed with node 12.x.
USE_OLD_TODOUBLE = False

# 12 bits of mantissa et 29 missing bits
SKYMASK = MASK & (MASK << 52 - 9) & (MASK >> 12)
print("SKYMASK=" + str("{0:b}".format(SKYMASK)))

# Condition counter
global_i = 0


# xor_shift_128_plus algorithm
def xs128p(state0, state1):
    s1 = state0 & MASK
    s0 = state1 & MASK
    s1 ^= (s1 << 23) & MASK
    s1 ^= (s1 >> 17) & MASK
    s1 ^= s0 & MASK
    s1 ^= (s0 >> 26) & MASK
    state0 = state1 & MASK
    state1 = s1 & MASK
    generated = state0 & MASK

    return state0, state1, generated


# Symbolic execution of xs128p
def sym_xs128p(slvr, sym_state0, sym_state1, generated):
    EXPONENT = 0x3FF0000000000000
    MANTISSE = 0x000FFFFFFFFFFFFF

    global global_i
    global_i = global_i + 1

    s1 = sym_state0
    s0 = sym_state1
    s1 ^= s1 << 23
    s1 ^= LShR(s1, 17)
    s1 ^= s0
    s1 ^= LShR(s0, 26)
    sym_state0 = sym_state1
    sym_state1 = s1
    condition = Bool(f"c{global_i}")

    if USE_OLD_TODOUBLE:
        impl = Implies(condition, (sym_state0 + sym_state1) & SKYMASK == generated)
    else:
        impl = Implies(condition, LShR(sym_state0, 12) & SKYMASK == generated)

    slvr.add(impl)
    return sym_state0, sym_state1, [condition]


def reverse17(val):
    return val ^ (val >> 17) ^ (val >> 34) ^ (val >> 51)


def reverse23(val):
    return (val ^ (val << 23) ^ (val << 46)) & MASK


def xs128p_backward(state0, state1):
    prev_state1 = state0
    prev_state0 = state1 ^ (state0 >> 26)
    prev_state0 = prev_state0 ^ state0
    prev_state0 = reverse17(prev_state0)
    prev_state0 = reverse23(prev_state0)
    return prev_state0, prev_state1


def to_double(state0, state1):
    EXPONENT = 0x3FF0000000000000
    MANTISSE = 0x000FFFFFFFFFFFFF

    if USE_OLD_TODOUBLE:
        double_bits = ((state0 + state1) & MANTISSE) | EXPONENT
    else:
        double_bits = (state0 >> 12) | EXPONENT

    return struct.unpack("d", struct.pack("<Q", double_bits))[0] - 1


def predict_the_future(old_numbers):
    dubs = [float(arg) / 10_000 for arg in old_numbers]
    dubs.reverse()

    for dub in dubs:
        generated = struct.unpack("<Q", struct.pack("d", dub + 1))[0] & SKYMASK
        flooredDub = floor(dub * 10_000) / 10_000
        generatedFlooredDub = struct.unpack("<Q", struct.pack("d", flooredDub + 1))[0] & SKYMASK
        #print("dub = " + str(dub))
        #print("generatedDub        = " + str("{0:b}".format(generated)))
        #print("generatedFlooredDub = " + str("{0:b}".format(generatedFlooredDub)))

    # from the doubles, generate known piece of the original uint64
    generated = [struct.unpack("<Q", struct.pack("d", dub + 1))[0] & SKYMASK for dub in dubs]

    # setup symbolic state for xorshift128+
    ostate0, ostate1 = BitVecs("ostate0 ostate1", 64)
    sym_state0 = ostate0
    sym_state1 = ostate1
    slvr = Solver()
    conditions = []

    # run symbolic xorshift128+ algorithm for N iterations
    for val in generated:
        sym_state0, sym_state1, ret_conditions = sym_xs128p(slvr, sym_state0, sym_state1, val)
        conditions += ret_conditions

    if slvr.check(conditions) == sat:
        # get a solved state
        m = slvr.model()
        state0 = m[ostate0].as_long()
        state1 = m[ostate1].as_long()
        slvr.add(Or(ostate0 != m[ostate0], ostate1 != m[ostate1]))
        #if slvr.check(conditions) == sat:
        #    print("Multiple solutions found: add more inputs.")



        print(f"Solution found: state0 = {state0}, state1 = {state1}")

        output_table = PrettyTable(["next prediction"])
        # generate random numbers from recovered state
        for idx in range(7):
            state0, state1 = xs128p_backward(state0, state1)
            hacked_rand = to_double(state0, state1)

            output_table.add_row(
                [
                    floor(hacked_rand * 10_000)
                ]
            )

        print(output_table)
    else:
        print("No possible solution. Try other inputs or a different version...")

def getSomeDubs():
    dubs = []
    # learning phase
    for i in range(2):
        print("====== ROUND " + str(i))
        response = requests.post(URL + '/make_guess', json={"guess": [1, 1, 1, 1, 1, 1]})
        content = response.content
        values = str(content).split("[", 1)[1].split("]", 1)[0].split(",", 6)
        dubs.extend(values)
        print("New values: " + str(values))
        print("dubs now: " + str(dubs))
        predict_the_future(dubs)

    response = requests.post(URL + '/make_guess', json={"guess": [1, 1, 1, 1, 1, 1]})

def hacky():
    state0 = 5395385508706694617
    state1 = 13392189448733659106
    print(f"Solution found: state0 = {state0}, state1 = {state1}")

    output_table = PrettyTable(["next prediction"])
    # generate random numbers from recovered state
    for idx in range(12):
        state0, state1 = xs128p_backward(state0, state1)
        hacked_rand = to_double(state0, state1)

        output_table.add_row(
            [
                floor(hacked_rand * 10_000)
            ]
        )

    print(output_table)

hacky()
#getSomeDubs()
