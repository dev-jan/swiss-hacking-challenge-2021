# Pathfinder

## Task
Reverse-Engineering of massive callgraphs

You are part of an elite force protecting the intergalatic federal council.
Some colleagues have gone missing while exfiltrating an important asset out of the enemies territory.
Equiped with their last coordenates you mount your spaceship and head out, only to be confronted with a massive asteroid field.
Are you able to evate the deadly rocks and find the right path to safety?
The lifes of your co-workers are in your hands!

Please download the challenge binary here: pathfinder.zip

Important Flag Information: Please wrap your flag in shc2021{} and make sure to not have any whitespace when submitting.
Acknowledgment: This challenge has been created by xorkiwi from Swiss Hacking Challenge (https://twitter.com/xorkiwi)

# Solution

The binary is a standard linux ELF binary:
```
$ file pathfinder
pathfinder: ELF 32-bit LSB executable, Intel 80386, version 1 (SYSV), dynamically linked, interpreter /lib/ld-linux.so.2, for GNU/Linux 2.6.32, BuildID[sha1]=922d41c7e9195c7f3bdbadbb0d68a8bf638b40a3, stripped
```

So let's open it in Ghidra and decompile it. The main function ask the user for a password
and than loops over every char of the given password (which is max 8 chars given by the scanf format string).
Then given string gets kind of scramble with a function and then compared with the hardcoded string "CYHSZZBU". If
they match, we won, otherwise "You died." is displayed.

The "encodePasswordChar" function (that is at least how i named it) does the following:
```
char encodePasswordChar(char character,int indexOfCharacter) {
  int tmp;
  if ((0x40 < _character) && (_character < 0x5b)) {
    tmp = _character + -0x41 + indexOfCharacter * 0x1f;
    return (char)tmp + (char)(tmp / 0x1a) * -0x1a + 'A';
  }
  puts("You died.");
                    /* WARNING: Subroutine does not return */
  exit(1);
}
```

I tried to reverse the math behind the operations. But this not worked, I think because of the overflows happened
in the char fields, WolframAlpha uses much bigger fields. So to solve it, I just rewrote the encode function and
tried all uppercase letters (as only uppercase letters are valid chars). Than I just take the first valid solution
and it worked! The used C program is attached as "solver.c".

```
$ ./pathfinder
Enter the password: OFJPRMJX
You reached your destination.
A massive abandoned spacestation appears in front of you
```

So the flag is shc2021{OFJPRMJX}
