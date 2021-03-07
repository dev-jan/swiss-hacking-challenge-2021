# CrackMe Simple

## Task
Objective: The password is somewhere hidden in this app. Extract it. The password is the flag.

Hint: APK Reverse Engineering

Acknowledgement: This challenge has been developed by myonium@gmail.com

## Solution

First step is to decompile the given APK. A simple and fast solution for this is the JADX decompiler.
Instead of installing it and doing it locally, I just used http://www.javadecompilers.com/apk to decompile the
apk quickly.

After browsing the java source files, 2 files are looking interesting: LoginViewModel.java and AESUtil.java. In
the LoginViewModel.java is the validation check for the password, which seems to have a AES encrypted field with
the password and compares the given userimport with the decrypted saved password:

```
private static byte[] exs = {-28, 73, 79, 78, 113, 73, 101, 98, 115, 6, 27, -35, 111, -55, -114, -11, -29, 0, -73, 91, 115, -24, -4, -94, -59, 43, -57, 112, 11, -54, -115, 2};
// ...
public void login(String str) {
    try {
        String str2 = new String(AESUtil.decrypt(exs));
        if (str.equals(str2)) {
            this.loginResult.setValue(new LoginResult(new LoggedInUser(str2, "Well done you did it.")));
            return;
        }
        // ...
```

The AESUtil class contains the logic to decrypt the bytearray. Since no library classes and functions are used
in the AESUtils class, a decrypt program is simple and able to output the string. I quickly done this with the repl.it online IDE,
after executing the solution.java, the following flag was printed:

```
HL{R3v3rsing.FUN}
```
