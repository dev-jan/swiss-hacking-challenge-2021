# Android App: Geo IP Restricted Login

## Task
You must login into the app to get the flag. But be aware, the login is restricted to people located in Zermatt (CH) only. As the app trust all people in Zermatt, a 4 digit PIN will do.

This Android app GeoLogin.apk is compatible with Android SDK version 26 and above.

Acknowledgemnt: This challenge has been developed by myonium (myonium@gmail.com)


## Solution

For the start, let's decompile the APK with a random online decompiler and check the
result. The only interesting file is the LoginViewModel.java, but this file was not
correctly decompiled, some parts are still Java Bytecode (maybe this was intended?). But it
is enough to get the logic of the app:

1. It requests https://ipinfo.io/json for the country and region of the IP of the
   requester.
2. A hash is generated in the format md5("<COUNTRY-CODE>.<REGION>.<PIN>")
3. https://ja3er.com/img/$HASH is requested and checked, if the URL does not return an error

As I checked the domain ja3er.com, I first thought that the endpoint with the hash
is checking, if the client is really a Android client. In the time I installed Android
Studio to try that out, I quickly hacked a python script that tries all possible PINs for
the country "CH" and the Region "Zermatt". This was quicker than installing Android Studio
and it brings up the correct PIN: 2021 :)

```
$ python3 solver.py
[...]
CH.Zermatt.2016 => https://ja3er.com/img/f1b45b084acebb3c75044f94ef82b80bHTTP Error 404: Not Found
CH.Zermatt.2017 => https://ja3er.com/img/648f452530ffef1d0695703a7298a527HTTP Error 404: Not Found
CH.Zermatt.2018 => https://ja3er.com/img/0eb7eadfb668178936395815583bec40HTTP Error 404: Not Found
CH.Zermatt.2019 => https://ja3er.com/img/e7f2ed8da17a947c40df3d359d7c44c1HTTP Error 404: Not Found
CH.Zermatt.2020 => https://ja3er.com/img/b96b2c4809f587139cdc7543e0c1ffd9HTTP Error 404: Not Found
!!!!!!!!!! CH.Zermatt.2021 => https://ja3er.com/img/d3cfe142d34095520f6b776e864278ab => 200
```

So the URL would be: https://ja3er.com/img/d3cfe142d34095520f6b776e864278ab
which outputs the flag:
SCS21{R3wire-Your-Br@in-Cr@ck-The-Ch@lleng3s}
