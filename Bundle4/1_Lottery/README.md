# The Lottery (medium - 200 Points)

## Task

Can you win the lottery?

## Solution

As we all (should) know, Math.Random() is not a really secure random generator
in most languages, including Javascript. For stuff like a lottery, normally a
secure random generator should be used instead.

So I found a script, that predicts random values produces by a XorShift128+
Pseudo-Random generator (this one is used in the V8 engine by node, see https://github.com/v8/v8/blob/master/src/base/utils/random-number-generator.cc#L99).
I had to modify it quite a bit and it ended up quite hacky, but I worked :D

```
$ python3 sol.py
====== ROUND 2
New values: ['2997', '677', '4062', '6333', '7162', '7259']
dubs now: ['8689', '6758', '2935', '5066', '4769', '6858', '6878', '6488', '668', '4460', '7037', '9483', '2997', '677', '4062', '6333', '7162', '7259']
Solution found: state0 = 5395385508706694617, state1 = 13392189448733659106
+-----------------+
| next prediction |
+-----------------+
|       3374      |
|       4845      |
|       7263      |
|       2728      |
|       7783      |
|       1836      |
+-----------------+
More values?
====== ROUND 3
New values: ['2924', '3374', '4845', '7263', '2728', '7783']
dubs now: ['8689', '6758', '2935', '5066', '4769', '6858', '6878', '6488', '668', '4460', '7037', '9483', '2997', '677', '4062', '6333', '7162', '7259', '2924', '3374', '4845', '7263', '2728', '7783']

```

At this point I realized I worked and quickly wrote a method to predict some more values for the
found states. And it worked and got me the following flag:

Congratulations, here is your price: 72b6e625-fa5c-4d14-9865-f0958e6fa981
