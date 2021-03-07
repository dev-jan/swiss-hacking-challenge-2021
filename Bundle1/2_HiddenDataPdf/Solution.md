# Hidden Data In PDF File

## Task
The Swiss Hacking Challenge 2021 flyer flyer-shc2021_flag.pdf contains some hidden data.
Goal

- Analyze the given PDF file
- find the flag

PDF File: flyer-shc2021_flag.pdf

This challenge has been developed by myonium from Swiss Hacking Challenge (myonium@gmail.com)

## Solution

First, lets analyze the pdf with the "file" tool, just to check if something is special with the
file. Seems like a regular pdf. So let's run binwalk on the file so see if there are some embedded
files in the pdf that can be extracted. It seems like there are some zlib compressed data in the
pdf:

```
$ binwalk 1b3760fb-fefb-434f-84b9-b02746dea0c4.pdf

DECIMAL       HEXADECIMAL     DESCRIPTION
--------------------------------------------------------------------------------
0             0x0             PDF document, version: "1.3"
496           0x1F0           Zlib compressed data, default compression
1001          0x3E9           Zlib compressed data, default compression
1256          0x4E8           Zlib compressed data, default compression
1510          0x5E6           Zlib compressed data, default compression
1698          0x6A2           Zlib compressed data, default compression
1885          0x75D           Zlib compressed data, default compression
2148          0x864           Zlib compressed data, default compression
2402          0x962           Zlib compressed data, default compression
2657          0xA61           Zlib compressed data, default compression
3009          0xBC1           Zlib compressed data, default compression
10273         0x2821          Zlib compressed data, default compression
12899         0x3263          Zlib compressed data, default compression
14812         0x39DC          Zlib compressed data, default compression
19111         0x4AA7          Zlib compressed data, default compression
25097         0x6209          Zlib compressed data, default compression
30441         0x76E9          Zlib compressed data, default compression
1514665       0x171CA9        Zlib compressed data, default compression
1534176       0x1768E0        Zlib compressed data, default compression
1535207       0x176CE7        Zlib compressed data, default compression
1539349       0x177D15        Zlib compressed data, default compression
1574627       0x1806E3        Zlib compressed data, default compression
1577990       0x181406        Zlib compressed data, default compression
1580670       0x181E7E        Zlib compressed data, default compression

```

So lets extract all of them an take a look:

```
binwalk --dd='.*' 1b3760fb-fefb-434f-84b9-b02746dea0c4.pdf
```

After exploring the files a bit, I found the flag at the end of some large binary
file:

```
$ cat _1b3760fb-fefb-434f-84b9-b02746dea0c4.pdf.extracted/171CA9
[...]
������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������
SCS21{W3lc0me.t0.0ur.W0rlD}
```
