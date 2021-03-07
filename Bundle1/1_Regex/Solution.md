# Regexp Challenge

## Task
A regular expression (shortened as regex or regexp; also referred to as rational expression) is a sequence of characters that define a search pattern. It is a very important search technique and is used in almost any software.
Goal

    Solve the 15 regexp challenges

Details

Sometimes you are not allowed to use all characters in your regexp search.
Start

Please run the application from RESOURCES and enjoy

## Solution

### Level 1
Smile 1: 74921132A
With regular expressions, we can use . (a dot) as a wildcard symbolizing any character (only one character), so that if you write two dots it symbolizes a word with two 2 characters. Please enter a regexp to match the string above 74921132A which is formed by 8 digits + 1 letter? If you succeed, the smiley above turns green and the Correct button appears.
ALLOWED CHAR IN REGEXP = "everything"
Search for the shortest regexp possible!

=> .*

### Real solution
The first Level is very easy, just insert ".*" to solve it. After viewing the Network Tab of the Browser Debugger, I noticed no requests are made. This hints, that the check is done locally. After reading the typescript code, I found some interesting functions like like the "endGame()" function in the Game.ts file. This function contains a data variable, which is base64 encoded and will be displayed if all levels are solve.

So this should be a shortcut, instead of solving all level, just decode the base64 string in the data variable and the flag will be shown:
window.atob('RmxhZyA9IEhMe1JlZ0V4cC1UeWMwMG4tOTEyMzR9');
=> Flag = HL{RegExp-Tyc00n-91234}
