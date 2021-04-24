# ReadEEPROM by tttttx2

## Task

This challenge provides an arduino simulator. I hid the flag on the EEPROM of the arduino. Your job is to write a quick sketch to extract it.

The web interface (which is just for your convenience, not intended to be exploited) provides you with an easy way to flash your firmware and gives you read access to the UART interface of the arduino.

Goal: Write firmware for ATMega328p to extract the content of the builtin EEPROM. Upload your compiled firmware and get the flag.

Hint:
- Think I told you it's arduino.
- Don't overwrite the bootloader...

## Solution

First, lets download the Arduino IDE to create Arduino binaries. After playing around with the IDE and
some simple example tools, I found out that there is a library already included that can
read EEPROM storage. Also there is a builtin command to writes bytes to serial. Combining this,
a program that reads the whole EEPROM and output it to the serial is easy. The output then
contains the flag, so I uploaded a binary created from the attached ino file, which outputs the flag
as ASCII numbers. Converting them back to characters, the flag is shown:

HL{d85fba6c-8cbb-46d6-b3c3-6e12af06c8fa}
