package com.rondoll.sc2replayrenamercli;

import java.io.File;

import com.rondoll.sc2replayrenamer.Renamer;
import com.rondoll.sc2replayrenamer.RenamingRule;
import static com.rondoll.sc2replayrenamer.RuleConstant.*;
import static com.rondoll.sc2replayrenamercli.ArgumentConstantUnix.*;

public class SC2ReplayRenamerUnixCLI {

	private static String WRONG_ARGUMENTS = "Wrong argument/s, see help section below for usage:";
	
	public static void main(String[] args) {
		if (args.length == 0 || "help".equals(args[0]) || "?".equals(args[0])) {
			showHelp();
			return;
		}
		
		try {
			File source = null;
			File target = null;
			RenamingRule renamingRule = new RenamingRule();
			boolean keepOriginal = true;
			for (String str : args) {
				if (!str.contains("-")) {
					System.out.println(str);
					System.out.println(WRONG_ARGUMENTS + "\n");
					showHelp();
					return;
				}
				
				if (str.startsWith(RULE)) {
					renamingRule.setRule(str.replace(RULE, ""));
				} else if (str.startsWith(SOURCE)) {
					source = new File(str.replace(SOURCE, ""));
				} else if (str.startsWith(TARGET)) {
					target = new File(str.replace(TARGET, ""));
				} else if (str.startsWith(KEEP_ORIGINAL) && str.replace(KEEP_ORIGINAL, "").toLowerCase().equals("false")) {
					keepOriginal = false;
				} else if (str.startsWith(KEEP_ORIGINAL) && str.replace(KEEP_ORIGINAL, "").toLowerCase().equals("true")) {
					keepOriginal = true;
				} else if (str.startsWith(PLAYER)) {
					renamingRule.setPlayer(str.replace(PLAYER, ""));
				} else if (str.startsWith(RACE)) {
					renamingRule.setRace(str.replace(RACE, ""));
				} else if (str.startsWith(PLAYER_AND_RACE)) {
					renamingRule.setPlayerAndRace(str.replace(PLAYER_AND_RACE, ""));
				} else if (str.startsWith(PLAYERS_DIVIDER)) {
					renamingRule.setPlayersDivider(str.replace(PLAYERS_DIVIDER, ""));
				} else if (str.startsWith(RACES_DIVIDER)) {
					renamingRule.setRacesDivider(str.replace(RACES_DIVIDER, ""));
				} else if (str.startsWith(PLAYERS_AND_RACES_DIVIDER)) {
					renamingRule.setPlayersAndRacesDivider(str.replace(PLAYERS_AND_RACES_DIVIDER, ""));
				} else if (str.startsWith(SHORT_RACE_NAME) && "false".equals(str.replace(SHORT_RACE_NAME, "").toLowerCase())) {
					//only checks for false since short race name is true by default
					renamingRule.setShortRaceName(false);
				} else if (str.startsWith(PRIORITIZED_PLAYERS)) {
					renamingRule.setPrioritizedPlayers(str.replace(PRIORITIZED_PLAYERS, "").split(" "));
				} else {
					System.out.println(WRONG_ARGUMENTS + "\n ERROR IN STATEMENT: " + str + "\n" );
					showHelp();
					return;
				}
			}
			
			if (source == null) {
				System.out.println("No source file/folder specified. Use " + SOURCE + "<file/folder>. Example: " + SOURCE + "\"d:\\sc2replays\"");
				return; 
			} else if (target == null) {
				if (source.isFile()) {
					target = source.getParentFile();
				} else {
					target = source;
				}
			}
			
			Renamer renamer = new Renamer(source, target, renamingRule, keepOriginal);
			renamer.rename();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void showUnixHelp() {
		
	}
	
	public static void showHelp() {
		System.out.println("== HELP ==");
		System.out.println("Arguments:");
		System.out.println(String.format("%s<file/folder> [%s<folder>] [%s<boolean>] [%s<boolean>] [%s<prioritized players>] [%s<rule>] [%s<player>] [%s<race>] [%s<player and race>] [%s<players divider>] [%s<races divider>] [%s<players and races divider>]", SOURCE, TARGET, KEEP_ORIGINAL, SHORT_RACE_NAME, PRIORITIZED_PLAYERS, RULE, PLAYER, RACE, PLAYER_AND_RACE, PLAYERS_DIVIDER, RACES_DIVIDER, PLAYERS_AND_RACES_DIVIDER));
		System.out.println();
		System.out.println("Whenever a value contains spaces, it needs to be surrounded with quotes.");
		System.out.println("If a new file name is generated which already exists in the target folder, then a number will be appended to the new file name.");
		System.out.println("Outcome (specified in <rule>) is either Winner, Loser or Unknown. The outcome Uknown arises for replays in which there is more than one player on each team and the main player (the one who saved the replay) leaves before at least one of the other players on the same team leaves or disconnects. It is possible that the team of the main player still wins and that is why the outcome is Unknown.");
		System.out.println();
		System.out.println(SOURCE + "<file/folder>");
		System.out.println("Source file to rename, or source folder containing files to rename.");
		System.out.println();
		System.out.println(TARGET + "<folder>");
		System.out.println("Target folder to copy files to.");
		System.out.println("If source is a file, then by default the folder that the source file is located in.");
		System.out.println("If source is a folder, then by default the same folder as the source folder.");
		System.out.println();
		System.out.println(KEEP_ORIGINAL + "<boolean>");
		System.out.println("Option to keep original file or to delete it.");
		System.out.println("Acceptable values: TRUE or FALSE");
		System.out.println("By default set to: TRUE");
		System.out.println();
		System.out.println(SHORT_RACE_NAME + "<boolean>");
		System.out.println("Option to use short race name (T, P or Z) or not (Terran, Protoss or Zerg).");
		System.out.println("Acceptable values: TRUE or FALSE");
		System.out.println("By default set to: TRUE");
		System.out.println();
		System.out.println(PRIORITIZED_PLAYERS + "<prioritized players>");
		System.out.println("List of players to make sure that the most prioritized player is always in team 1.");  
		System.out.println("Acceptable values: Any strings. Separate players with spaces.");
		System.out.println();
		System.out.println(RULE + "<rule>");
		System.out.println("Rule used for renaming files.");
		System.out.println(String.format("Acceptable values: %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s and any string not containing '#'.", PLAYERS_TEAM_1, PLAYERS_TEAM_2, PLAYERS_ALL, RACES_TEAM_1, RACES_TEAM_2, RACES_ALL, PLAYERS_AND_RACES_TEAM_1, PLAYERS_AND_RACES_TEAM_2, PLAYERS_AND_RACES_ALL, OUTCOME_TEAM_1, OUTCOME_TEAM_2, MAP));
		System.out.println(String.format("By default set to: %s vs %s on %s", PLAYERS_AND_RACES_TEAM_1, PLAYERS_AND_RACES_TEAM_2, MAP));
		System.out.println();
		System.out.println(PLAYER + "<player>");
		System.out.println(String.format("Acceptable values: %s and any strings not containing '#'.", PLAYER_NAME));
		System.out.println(String.format("Used by %s, %s and %s.", PLAYERS_TEAM_1, PLAYERS_TEAM_2, PLAYERS_ALL));
		System.out.println(String.format("By default set to: %s", PLAYER_NAME));
		System.out.println();
		System.out.println(RACE + "<race>");
		System.out.println(String.format("Acceptable values: %s and any strings not containing '#'.", RACE_NAME));
		System.out.println(String.format("Used by %s, %s and %s", RACES_TEAM_1, RACES_TEAM_2, RACES_ALL));
		System.out.println(String.format("By default set to: %s", RACE_NAME));
		System.out.println();
		System.out.println(PLAYER_AND_RACE + "<player and race>");
		System.out.println(String.format("Acceptable values: %s, %s and any strings not containing '#'.", PLAYER_NAME, RACE_NAME));
		System.out.println(String.format("Used by %s, %s and %s", PLAYERS_AND_RACES_TEAM_1, PLAYERS_AND_RACES_TEAM_2, PLAYERS_AND_RACES_ALL));
		System.out.println(String.format("By default set to: %s(%s)", PLAYER_NAME, RACE_NAME));
		System.out.println();
		System.out.println(PLAYERS_DIVIDER + "<players divider>");
		System.out.println(String.format("Divider used between players in %s, %s and %s.", PLAYERS_TEAM_1, PLAYERS_TEAM_2, PLAYERS_ALL)); 
		System.out.println("Acceptable values for <players divider>: Any strings.");
		System.out.println();
		System.out.println(RACES_DIVIDER + "<races divider>");
		System.out.println(String.format("Divider used between races in %s, %s and %s.", RACES_TEAM_1, RACES_TEAM_2, RACES_ALL));
		System.out.println("Acceptable values for <races divider>: Any strings.");
		System.out.println();
		System.out.println(PLAYERS_AND_RACES_DIVIDER + "<players and races divider>");
		System.out.println(String.format("Divider used between players and races in %s, %s and %s.", PLAYERS_AND_RACES_TEAM_1, PLAYERS_AND_RACES_TEAM_2, PLAYERS_AND_RACES_ALL));
		System.out.println("Acceptable values for <players and races divider>: Any strings");
		System.out.println();
		System.out.println(String.format("Example 1: %sd:\\sc2replays", SOURCE));
		System.out.println("The above would copy all .sc2replay files from the folder to the same folder with default settings");
		System.out.println("Example output: \"player1(Z)player2(Z)player3(T) vs zoidberg(T)grymme(P)apanloco(Z) on High Orbit.sc2replay\""); 
		System.out.println();
		System.out.println(String.format("Example 2: %s\"d:\\sc2replays\\High Orbit (20).sc2replay\" %s\"d:\\renamed files\" %sfalse %sfalse %s\"zoidberg apanloco grymme\" %s\"%s as %s\" %s(%s) %s\", \" %s_ %s\"%s! %s against %s on %s\"", SOURCE, TARGET, KEEP_ORIGINAL, SHORT_RACE_NAME, PRIORITIZED_PLAYERS, PLAYER_AND_RACE, PLAYER_NAME, RACE_NAME, RACE, RACE_NAME, PLAYERS_AND_RACES_DIVIDER, RACES_DIVIDER, RULE, OUTCOME_TEAM_1, PLAYERS_AND_RACES_TEAM_1, RACES_TEAM_2, MAP));
		System.out.println("The above would copy the sc2replay file from the source folder to the target folder with the specified settings");
		System.out.println("Example output: \"Winner! zoidberg as Terran, grymme as Protoss, apanloco as Zerg against (Zerg)_(Zerg)_(Terran) on High Orbit.sc2replay\"");
	}
}
