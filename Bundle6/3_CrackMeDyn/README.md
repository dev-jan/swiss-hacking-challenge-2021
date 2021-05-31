# CrackMe Dyn (hard - 300 Points)

## Task
Many malware types are dynamically loading the malicious code in a second stage. This way the malicious code stays undetected from static analysis.

This demo app is protected with a basic "anti-root" protection and loads dynamically code.

Objective: The password is somewhere hidden in this app. Extract it. The correct 6 digit PIN will provide the flag.

Hint: APK Reverse Engineering
Acknowledgement: This challenge has been developed by myonium@gmail.com

## Solution

After decompiling the APK with a random online decompiler, a quick look on the
source code shows where the magic happens: In the LoginViewModel.java, a File
gets encrypted (and deleted after) and then dynamically loaded using the
Dalvic class loader. The file is contained in the APK, so let's copy the AESUtil
class with all needed keys (they are embedded in the class) and decrypt the
file. With the AESUtil class copied, it's possible to encrypt the file in a simple
java program:
```
public static void main(String[] args) throws Exception {
    File encryptedFile = new File("elib.enc");
    File decryptedFile = File.createTempFile("code", "tmp");
    System.out.println("Output decrypted DexFile: " + decryptedFile.getAbsolutePath());
    AESUtils.writeDecryptedFile(new FileInputStream(encryptedFile), decryptedFile);
}
```

As the outcomming file is a DEX file, let's use dex2jar to convert it to
a normal Java binary:

```
$ d2j-dex2jar.sh -o output.jar elib.dex
```

Using for example JD-GUI, the source can be viewed. After looking at the source
for a bit, it become clear that the PIN has 6 numbers. As bruteforcing this is quite
easy, let's just try every possibility. But before doing that, the Thread.sleep()
call was also removed because I don't want to wait so long :)

Result using the attached (modified) HLLoginCheck.java:
```
FOUND!!!! 777737
Result: HL{C@ll.you.A.M4ster}
```
