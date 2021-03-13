# CrackMe Native

## Task
This app holds a secret inside. The app is armored with "Anti-Hooking" protection.

Objective: The password is somewhere hidden in this app. Extract it. The password is the flag.
Hint: If you need a hint, please ask Cyrill. Send him an e-mail to cyrill.brunschwiler@compass-security.com
Acknowledgement: This challenge has been developed by myonium@gmail.com

# Solution

First decompile the Java app with JADX. After a bit of digging, it seems like the class LoginViewModel.java does the job. It uses XOR with a given byte array before giving the user string to a native function in the "libnative-lib.so" binary via JNI. So I extracted the SO binary and run Ghidra over it. There is an interesting function "Java_org_bfe_crackmenative_ui_LoginViewModel_checkPw", that seems to do the job. The first part of the function is just to check if "frida" or "Xposed" is running, the interesting part comes after the check:

```
if (!someValidityCheck) {
  givenPasswordPTR = (**(code **)(*param_1 + 0x2ec))(param_1,param_3,0);
  pwChunk1 = &UINT_00010b80;
  pwChunk2 = &UINT_00010b4c;
  pwChunk3 = &UINT_000109e4;
  counter = -0x6c;
  do {
    resultPassword = userPassword;
    if ((*pwChunk3 ^ *(uint *)(givenPasswordPTR + 0x6c + counter) ^ *pwChunk2) != *pwChunk1)
    break;
    pwChunk1 = pwChunk1 + 1;
    pwChunk2 = pwChunk2 + -1;
    pwChunk3 = pwChunk3 + 1;
    counter = counter + 4;
    resultPassword = param_3;
  } while (counter != 0);
}
```

The decompilation is not perfect, but shows the principal: It compares the user input with some hardcoded integers. The integers (pwChunk* named in my decompilation) can be extracted from the library. Since XOR can be reverted in this example, I wrote a simple C program that uses the defined byte chunks (defined as int arrays) and invert the XOR by XOR the pwChunk1 once again with all the other chunks and the int array from the Java class. This outputs the flag (solve.c is used to get this output):

Flag: HL{J4v4.nativ3.d0.n0t.c4r3}
