# Self Service (medium - 200 Points)

## Task
This is a strange self service handing out certificates. Take a look!

Goal: Submit a X509 Certificate Chain that satisfies all the requirements

Code: app.py

Acknowledgement: This challenge has been developed by norelect from Swiss Hacking Challenge

## Solution

Normally, a certificate is protected from modification by the signature. But for
CAs, this is a bit special, since they are the root of trust. For this reason, its
possible to simply alter the given CA cert and then re-sign it with another key.
Since the key stays the same, the signed client cert stays valid and the expire-date
of the CA can be altered this way, without knowing the private Key of the CA.

The new CA is generated using the attached solve.py file. After uploading the
modified CA and the original client.pem, the following flag is shown:

aa21a327-0f97-421c-b37b-8f18bd027221