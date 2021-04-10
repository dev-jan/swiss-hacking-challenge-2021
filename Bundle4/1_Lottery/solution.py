"""A hack for skychat's roll command.
Based on https://github.com/TACIXAT/XorShift128Plus.
Works with Node.JS 10+. v10 has a different ToDouble() from v12+,
which is way slower to crack with this method.
Usage example :
    # 6 inputs should be enough
    $ python hack_rand.py 40400861 26920257 57439447 608722397 24329015 447108618
    +---------------------+-----------+-------+------+
    |        float        |   image   | guess | roll |
    +---------------------+-----------+-------+------+
    | 0.06023399323812351 |  60233993 |   60  |  7   |
    |  0.3098338752246381 | 309833875 |  309  |  0   |
    |  0.7580949674167554 | 758094967 |  758  |  4   |
    |  0.9574229085595414 | 957422908 |  957  |  6   |
    |  0.3330787878068444 | 333078787 |  333  |  0   |
    | 0.16923076429504458 | 169230764 |  169  |  8   |
    |  0.8990241997823878 | 899024199 |  899  |  5   |
    |  0.3945494482569667 | 394549448 |  394  |  0   |
    | 0.13085277487802682 | 130852774 |  130  |  8   |
    |  0.5188443365850164 | 518844336 |  518  |  2   |
    |  0.7920761627187354 | 792076162 |  792  |  4   |
    | 0.02022102998018438 |  20221029 |   20  |  7   |
    |  0.9006849720528429 | 900684972 |  900  |  6   |
    | 0.11078071588793281 | 110780715 |  110  |  8   |
    |  0.299550141383361  | 299550141 |  299  |  9   |
    +---------------------+-----------+-------+------+
Explanation :
You can extract an important number of bits from Math.random() :
https://github.com/skychatorg/skychat/blob/2094c1b3476731c9be3a0ba26f349ac0c76324e9/app/server/skychat/Server.ts#L111
From there, we use z3 to get the seed of Node.JS's PRNG.
Once we have the seed, we just need to generate random numbers with
XorShift128's inverse function (because node buffers random numbers then
reads then in inverse order), and you have the same results as the server. :)
"""
import struct
import sys
from math import floor
from z3 import *


MASK = 0xFFFFFFFFFFFFFFFF

# ToDouble() changed with node 12.x.
USE_OLD_TODOUBLE = True

# 12 bits of mantissa et 29 missing bits
SKYMASK = MASK & (MASK << 33) & (MASK >> 12)

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
    dubs = [int(arg) / 10_000 for arg in old_numbers]
    print(dubs)
    dubs.reverse()

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
        if slvr.check(conditions) == sat:
            print("Multiple solutions found: add more inputs.")

        print(f"Solution found: state0 = {state0}, state1 = {state1}")

        rands = []
        # generate random numbers from recovered state
        for idx in range(6):
            state0, state1 = xs128p_backward(state0, state1)
            hacked_rand = to_double(state0, state1)

            rands.append(floor(hacked_rand * 10_000))

        print(*rands, sep='\t')
    else:
        print("No possible solution. Try other inputs or a different version...")

predict_the_future(sys.argv[1:])
