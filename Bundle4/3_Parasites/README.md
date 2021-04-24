# Parasites

## Task

Lost in a dangerous search and rescue mission you are stranded on an abandoned space station, deep in a foreign galaxy.
You skillfully maneuvered your spacecraft through the dangerous asteroid field, making split-second decisions and flying with impecable precision.
Searching through the remains of what once was a blooming civilization you suddently receive a nearby signal.
You pave your way through debris getting closer and closer to your destination, unknowing that there is an invisible threat lingering in the air...

An eerie atmosphere awaits you as you find your former colleagues sitting in a foreign looking control room.
As you start to investigate the bodies you realize they've been dead for months!
Just as you ask yourself what the source of the emergency signal was, the doors lock and you see hundreds of microscopic parasitic robots burst from the bodies.
Shocked you get cover behind a chair, noticing a terminal labeled with "Nanobots Control Terminal".
Your only chance in survial lies in reverse engineering the protection fast enough so you can disable the bot and escape from this hellhole.

Please download the challenge binary here: parasites.zip

Please wrap your flag in shc2021{} and make sure to not have any whitespace when submitting.

Acknowledgment: This challenge has been created by xorkiwi from Swiss Hacking Challenge (https://twitter.com/xorkiwi)

## Solution

After analysing the binary, the initial "unlock" string to start the binary was simply to
discover by using ghidra. The command "./parasites_fixed -sUp3R_S33333Cret_uNloCK_sTring" will
start the unlock process.

Now some more "unlock keys" are required. As I found out, the real checks runs in a
forked child process that is supervised by the parent process via ptrace(). The child
processes the input with a "dispatch" function, that only contains an invalid asm instruction.
This way, the parent process, that is waiting for a signal, wakes up and handles the
exception and also sets the return value. The return value is a simple XOR with the input and
the hardcoded value 0x4fb30a91. The child checks if the returnvalue is equal to some
hardcoded value. And this is done multiple times. So lets calculate all needed values
by simply XOR the hardcoded values from every stage (found via Cutter in the binary):

```
>> 0x4fb30a91 ^ 0x4babd7ac
68738365
>>> 0x4fb30a91 ^ 0x4c49c202
66766995
>>> 0x4fb30a91 ^ 0x4abfde42
84726995
>>> 0x4fb30a91 ^ 0x4b0333c6
78657879
>>> 0x4fb30a91 ^ 0x4b113b74
77738469
>>> 0x4fb30a91 ^ 0x4ab20669
83954936
```

So lets enter the values and receive the flag:
```
~~~WeLcOmE t0' b0t-Os 2.4.1~~~
\<¦PlE4se EntEr t_he FIrsT AUtheNtIcaT1on: 68738365
DISA
.::.::.PlE4se EntEr AUtheNtIcaT1on PaRT 2: 66766995
BLE_
PlE4se EntEr AUtheNtIcaT1on PaRT 3: 84726995
THE_
^3----ç*PlE4se EntEr AUtheNtIcaT1on PaRT 4: 78657879
NANO
---------PlE4se EntEr AUtheNtIcaT1on PaRT 5: 77738469
MITE
*~+*^*PlE4se EntEr F1NAl AUtheNtIcaT1on:83954936
S_1$
UnLocKed B0tS cOntroL SySTem!
[*] D1sAble AutonOmous DeFen5e Bot5 and opEN d0oRS

UnLock1ng DooRs...iN PROgrEs
UNLocKing doOrs...SucCessFUL!
DiSarm1ng BoTS...in ProgreS
disArming bOts...succEssFul!
YoU aRe fREE to LeAve HuMan
```

Flag: shc2021{DISABLE_THE_NANOMITES_1$}
