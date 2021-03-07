import Game from './Game';

let game = new Game();

// Level 1
game.addLevel(
	'With **regular expressions**, we can use . (*a dot*) as a wildcard symbolizing any character (**only one character**), so that if you write two dots it symbolizes a word with two 2 characters. Please enter a regexp to match the string above `74921132A` which is formed by 8 digits + 1 letter? If you succeed, the smiley above turns green and the `Correct` button appears. <br> <br> **ALLOWED** CHAR IN REGEXP = \"everything\" <br> <br> Search for the **shortest** regexp possible!',
	[['74921132A', []]],
	[true]
);

// Level 2
game.addLevel(
	'However, Level 1/15 was only an initial example and does not make much sense. Lets go with something more useful... If we look at your first character how could we select only the `moving` happy character (image above)? <br> <br> **ALLOWED** CHAR IN REGEXP = \"everything\" <br> <br> Search for the **shortest** regexp possible!',
	[
		['74921132A', []],
		['84921132A', ['happymouth', 'dance']]
	],
	[false, true]
);

// Level 3
game.addLevel(
	'Now we seek to select the two `moving` characters above. Remember to first look for the pattern of similarity with the candidates and the differences with the ones we want to discard! <br> <br> **ALLOWED** CHAR IN REGEXP = \"everything\" <br> <br> Search for the **shortest** regexp possible!',
	[
		['74921132A', ['dance']],
		['84921133C', ['sadmouth']],
		['84921132A', ['happymouth', 'dance']]
	],
	[true, false, true]
);

// Level 4
game.addLevel(
	'But using several dots is cumbersome and uncomfortable. From now on, we will try to avoid it. If we write 7+ it symbolizes the `7` repeated many times (1 or more times). Try to select the `moving` character... <br> <br> **NOT ALLOWED** CHAR IN REGEXP = `.` `\\`',
	[
		['74921132A', []],
		['84921133C', ['sadmouth']],
		['84921132A', ['happymouth']],
		['77777777A', ['black', 'brows', 'smileteeth', 'dance']]
	],
	[false, false, false, true],
	['nodot']
);

// Level 5
game.addLevel(
	'Now we are going to select all the `moving` characters except the one that is `scared`. Please remember: `+` indicates that the preceding character (which can also be part of a regular expression) is repeated 1 or more times. Also, the `*` indicates that the preceding character is repeated 0 or more times. Do not use multiple dots. <br> <br> **NOT ALLOWED** CHAR IN REGEXP = `...` `<` `\\` <br> **MAX length** = 3 CHARS',
	[
		['74921132A', ['dance']],
		['84921133C', ['angrymouth']],
		['84921132A', ['happymouth', 'dance']],
		['77777777A', ['black', 'brows', 'smileteeth', 'dance']]
	],
	[true, false, true, true],
	['<4', 'nodots']
);

// Level 6
game.addLevel(
	'By using `.` we allow any character (*be it number, letter, symbol...*), so we do not specify. Instead, we are interested in specifying a specific range of characters. Instead of `.` we will use `[0-9]` or `\\d` (*any digit from 0 to 9*) or `\\w` (*any word*) . <br> <br> Try now to select only light-skinned `moving` characters. <br> <br> **NOT ALLOWED** CHAR IN REGEXP = `.` <br> <br> Search for the **shortest** regexp possible!',
	[
		['74921132A', ['happymouth', 'dance']],
		['J-429112C', ['orc', 'fangs', 'angrymouth']],
		['84921133C', ['black', 'smile']],
		['84921132A', ['smile', 'dance']],
		['H-381234A', ['orc', 'brows', 'fangs', 'angrymouth']]
	],
	[true, false, false, true, false],
	['nodot']
);

// Level 7
game.addLevel(
	'Okay, lets make it a little more difficult... Now select the three characters that are not green orcs. You may have to create ranks `[0-9]` or `\\d` or `\\w` that are not just numbers.... Remember that points are no longer allowed! <br> <br> **NOT ALLOWED** CHAR IN REGEXP = `.`',
	[
		['74921132A', ['happymouth', 'dance']],
		['J-429112C', ['orc', 'fangs', 'angrymouth']],
		['84921133C', ['black', 'smile', 'dance']],
		['84921132A', ['smile', 'dance']],
		['H-381234A', ['orc', 'brows', 'fangs', 'angrymouth']]
	],
	[true, false, true, true, false],
	['nodot']
);

// Level 8
game.addLevel(
	'Lets modify the characters a bit. Now there are both lowercase and uppercase characters. Select the `moving` characters. If we want to include several ranges, we can do it like this: `[A-Za-z]`, although we can also include single specific characters: `[AHz]` (*A, H and z are accepted*) or exclude specific characters: `[^AHz]` (*A, H and z are not accepted*). <br> <br> **NOT ALLOWED** CHAR IN REGEXP = `.`',
	[
		['74921132c', ['happymouth', 'dance']],
		['83811128h', ['angrymouth', 'dance']],
		['84921133W', ['black', 'smile', 'dance']],
		['84921132A', ['smile', 'dance']],
		['89187373H', ['mouth']]
	],
	[true, true, true, true, false],
	['nodot']
);

// Level 9
game.addLevel(
	'Regular expressions can also be turned around; instead of selecting those that meet a criterion, select those that do not. Ranges that have a caret `^` inside the brackets (`[^A]`) symbolize that there is no such character or range. Note that orcs always have a hyphen in their DNI. **Note**: Inside the square brackets, if you want to indicate a hyphen, it must be explicitly in the last position, otherwise it will be confused with the interval to indicate ranges. <br> <br> **NOT ALLOWED** CHAR IN REGEXP = `.` `<` `\\` <br> **REQUIRED** CHAR IN REGEXP = `[` `]` `^` <br> **MAX length** = 5 CHARS',
	[
		['J-429112C', ['orc', 'smileteeth']],
		['28395728a', ['black', 'beard', 'smile', 'dance']],
		['83713611H', ['black', 'beard', 'angrymouth', 'dance']],
		['7-381234A', ['orc', 'fangs', 'angrymouth']],
		['L-382271c', ['orc', 'brows', 'fangs', 'angrymouth']]
	],
	[false, true, true, false, false],
	['hat', '<6']
);

// Level 10
game.addLevel(
	'The green orc DNI format is **Letter-Dash-6Numbers-Letter**, lets try to be more specific. Instead of using `[0-9]+` (*1 or more repetitions of a digit from 0 to 9*), we can use `[0-9]{2}` (*2 repetitions of a digit from 0 to 9*). With this we can specify the number of times a character is repeated. <br> <br> **NOT ALLOWED** CHAR IN REGEXP = `.` <br> **REQUIRED** CHAR IN REGEXP = `{` `}`',
	[
		['74921132A', ['happymouth']],
		['J-429112C', ['orc', 'fangs', 'angrymouth', 'dance']],
		['84921133C', ['black', 'smile']],
		['84921132A', ['smile']],
		['H-381234A', ['orc', 'brows', 'fangs', 'angrymouth', 'dance']]
	],
	[false, true, false, false, true],
	['nodot', 'curly']
);

// Level 11
game.addLevel(
	'Things start to get complicated. Older green orcs have a smaller DNI, since they had fewer digits to begin with. With `{2,4}` we can indicate that the preceding character must be **2 to 4 characters long**. You can also use `{2,}` to indicate **2 or more**. Select the `moving` characters. <br> <br> **NOT ALLOWED** CHAR IN REGEXP = `.` <br> **REQUIRED** CHAR IN REGEXP = `{` `}`',
	[
		['K-882132U', ['orc', 'smileteeth']],
		['J-44392C', ['oldorc', 'fangs', 'angrymouth', 'dance']],
		['H-728a', ['oldorc', 'smileteeth', 'dance']],
		['H-281711A', ['orc', 'brows', 'angrymouth']],
		['H-3812A', ['oldorc', 'brows', 'smileteeth', 'dance']]
	],
	[false, true, true, false, true],
	['nodot', 'curly']
);

// Level 12
game.addLevel(
	'Clowns have a special ID similar to that of green orcs. Instead of a **hyphen**, they have a **+** (*plus*) sign. In regular expressions we can make use of the symbol `|` to set alternatives. For example, `(A|B)` means **A** or **B**. **Warning**! Some characters (`+`, `*`, `(`, ...) must be preceded by a backslash (e.g. `\\+`) in order not to be interpreted as a regular expression. <br> <br> **NOT ALLOWED** CHAR IN REGEXP = `.` <br> **REQUIRED** CHAR IN REGEXP = `|` `{`',
	[
		['K+937212W', ['clown', 'smileteeth', 'dance']],
		['43244392T', ['angrymouth']],
		['H+421728a', ['clown', 'smileteeth', 'dance']],
		['H-837199R', ['orc', 'brows', 'angrymouth', 'dance']],
		['H+343421A', ['clown', 'brows', 'smileteeth', 'dance']]
	],
	[true, false, true, true, true],
	['nodot', 'curly', 'pipe']
);

// Level 13
game.addLevel(
	'In some cases we want to indicate that the preceding character is **optional**: it may or may not exist, and both options are correct. For this, we will use the symbol `?`. Ghosts have a very complex DNI system. When they have accomplished their task in *non-life*, they are assigned a letter in the **third position**. If they still have unfinished tasks, they lack it. <br> <br> **NOT ALLOWED** CHAR IN REGEXP = `.` `\\` <br> **REQUIRED** CHAR IN REGEXP = `?` `{`',
	[
		['28A84721D', ['ghost', 'sadmouth', 'dance']],
		['39H71283L', ['ghost', 'sadmouth', 'dance']],
		['84118321a', ['angrymouth']],
		['39N83726D', ['ghost', 'sadmouth', 'dance']],
		['1762312D', ['ghost', 'brows', 'sadmouth', 'dance']]
	],
	[true, true, false, true, true],
	['nodot', 'curly', 'question']
);

// Level 14
game.addLevel(
	'What if we wanted to select only the orcs posing as ghosts? We will know which ones they are because they have forged a DNI with fewer numbers because they can not count? <br> <br> **NOT ALLOWED** CHAR IN REGEXP = `.` `(` `)` <br> **REQUIRED** CHAR IN REGEXP = `{` `?` <br>',
	[
		['28A84721D', ['ghost', 'sadmouth']],
		['39783L', ['ghost', 'angrymouth', 'fangs', 'dance']],
		['84118321a', ['angrymouth']],
		['39N8D', ['ghost', 'brows', 'fangs', 'angrymouth', 'dance']],
		['1762312D', ['ghost', 'brows', 'sadmouth']]
	],
	[false, true, false, true, false],
	['nodot', 'curly', 'question']
);

// Level 15
game.addLevel(
	'Now we want to select only clowns and old orcs... You can use the `(\\+[0-9]|-[A-Z])` parentization to group fragments. Be very careful with this, since `CA|B` (*CA or B*) is not the same as `C(A|B)` (*CA or CB*). <br> <br> **NOT ALLOWED** CHAR IN REGEXP = `.` `*` <br> **REQUIRED** CHAR IN REGEXP = `{`',
	[
		['H+421728a', ['clown', 'smileteeth', 'dance']],
		['28A84721D', ['ghost', 'sadmouth']],
		['H-837199R', ['orc', 'brows', 'angrymouth']],
		['83713611H', ['black', 'beard', 'angrymouth']],
		['H-3812A', ['oldorc', 'brows', 'smileteeth', 'dance']]
	],
	[true, false, false, false, true],
	['nodot', 'curly']
);

game.enableDebug();
game.start();
