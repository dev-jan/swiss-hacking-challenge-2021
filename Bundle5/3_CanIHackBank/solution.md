# Can I Hack Bank?

## Task
In this challenge, you will try to find and exploit a vulnerability in a web application.

While many people only dream of big money, you now have the opportunity to earn it. Go ahead and prove that you can hack the bank!

Goal: Prove that you can hack the bank and log in as admin.

Acknowledgment: This challenge has been created by tttttx2 and pyth0n33.

## Solution

After creating some accounts and tried some simple passwords and sql injections (which not worked),
I tried to register a new account with the "admin" username. This throwed an error message, but
the password was still overridden. With this trick, it was possible to change the password of the
admin account. But there is more: A 2FA code is also needed.

After creating a new seperate account, I found out that there is an endpoint that can show 2FA
secrets for a given username: https://7a08e87b-fe52-41f8-b253-338e723e42fa.idocker.vuln.land/getCodeAgain.php?name=admin

There is a validation of the user, which the page also explains:
```
Only members that pass the filter (cn="$username") in dn ou=admins,dc=my-domain,dc=com can access other users login tokens. You are a member of ou=user,dc=my-domain,dc=com
```

From this hint it is clear, that the page uses some kind of LDAP to store the user. So lets use
a simple LDAP injection to pass this check: Lets assume the username is not correctly escaped and
use the username "*admin" to create an account. This works! So lets visit the getCodeAgain.php Page again and
pass the username "admin" => And voila the 2FA Secret was returned! Lets use that to login
as admin with our previously changed password and use a service like https://totp.danhersam.com/ to
create a valid 2FA token from the secret.

The page shows then shows the flag:
shc2021{HereIsTheCashMoneyyyyyy}
