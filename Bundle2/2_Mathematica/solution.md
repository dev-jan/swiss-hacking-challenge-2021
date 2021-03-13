# Mathematica

## Task
Are you good at math? For example linear algebra?

I neither, but I know about theorem solvers and SMT solvers, they'll do that gaussian elimination and more for me. ;)

Goal: Get the docker running, and solve the easy challenge! :)
Acknowledgment

This challenge has been created by muffinx from Swiss Hacking Challenge.
https://twitter.com/_muffinx

# Solution

For this kind of mathematical systems, z3 can be a good source to solve them quickly. So I just added all constraints into z3 an let it do the hard work. The used python script is attached.

One possible solution:
```
$ python3 solver.py
sat
[a = 400025,
 b = 31200000000000003,
 c = 62396000000621956,
 d = 40000001781]
```

Flag: shc2021{n1c3_sMt_s0lv3r_m0v3z}
