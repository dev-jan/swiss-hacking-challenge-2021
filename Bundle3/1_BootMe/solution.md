# BootMe by tttttx2

## Task
No special tricks this time. No password needed for root, no fancy encryption, nothing. Just boot me. Easy as that. This is purely intended to teach you how to boot such systems and setup necessary network access.

Goal: You'll find the flag on the luci login page (http, port 80) of the router.

Hint 1: Openwrt malta

Acknowledgment: This challenge has been developed by tttttx2 (https://twitter.com/tttttx2_)

# Solution

The provided ELF binary seems like a MIPS binary:
```
$ file BootMe_openwrt-malta-le-vmlinux-initramfs.elf
BootMe_openwrt-malta-le-vmlinux-initramfs.elf: ELF 32-bit LSB executable, MIPS, MIPS32 rel2 version 1 (SYSV), statically linked, stripped
```

Since I do not have a MIPS system, lets use QEMU to start this. From the filename and the hint, it looks like the binary is an openWRT image, so a whole linux with everything is in it. With a little bit of help from google, I found the correct command to boot the image in QEMU and forwart the HTTP port to it to extract the flag:

```
sudo qemu-system-mipsel -kernel BootMe_openwrt-malta-le-vmlinux-initramfs.elf -nographic -m 256 -nic hostfwd=tcp::1122-:80
```

The first interface is always the "WAN" interface, so openWRT blocks access to the http port on this interface. This can be fixed by allowing the port 80 on the eth0 (WAN) interface:

```
uci add firewall rule &&
uci set firewall.@rule[-1].src='wan' &&
uci set firewall.@rule[-1].target='ACCEPT' &&
uci set firewall.@rule[-1].proto='tcp' &&
uci set firewall.@rule[-1].dest_port='80' &&
uci commit firewall &&
/etc/init.d/firewall restart
```

After that, lets browse to the page http://localhost:1122/ and enjoy the flag: hl{YAY_I_WAS_BOOTED}
