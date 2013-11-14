SC2ReplayTools
===============

ABOUT
-----

A cross-platform collection of Starcraft II Replay manipulation tools.

Current development status
--------------------------

The status of this project is early development.

Developed by Henrik Neckfors @ Purple Scout AB

HowTo
-----

To get usage instructions execute this in a terminal (after you have created a jar):
java -jar SC2ReplayTools-#.jar -help

Example 1: "d:\sc2replays"
The above would copy all .sc2replay files from the folder to the same folder with default settings.
Example output: "player1(Z)player2(Z)player3(T) vs zoidberg(T)grymme(P)apanloco(Z) on High Orbit.sc2replay"

Example 2: "d:\sc2replays\High Orbit (20).sc2replay" -t "d:\renamed files" -lr -pp "zoidberg apanloco grymme" -pr "#PLAYER_NAME# as #RACE_NAME#" -ra (#RACE_NAME#) -prd ", " -rd _ -r "#OUTCOME_TEAM_1#! #PLAYERS_AND_RACES_TEAM_1# against #RACES_TEAM_2# on #MAP#"
The above would move and rename the sc2replay file from the source folder to the target folder with the specified settings
Example output: "Winner! zoidberg as Terran, grymme as Protoss, apanloco as Zerg against (Zerg)_(Zerg)_(Terran) on High Orbit.sc2replay"

Contributors
------------

Thanks to acke and apanloco for contributing both code and ideas to this project!
