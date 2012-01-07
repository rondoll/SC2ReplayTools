
package com.rondoll.sc2replayrenamercli;

import java.util.List;
import java.util.ArrayList;
import com.beust.jcommander.Parameter;

public class Parameters {
	@Parameter(description = "renamer|stats <source file or directory> \n" +
			"\n" +
			"To print player statistics add <user>\n" +
			"<main class> stats <source file or directory> <user> " +
			"\n\n" +
			"To use the renamer use these paramerters: \n ", arity = 3)
	
	public List<String> source = new ArrayList<String>();

	@Parameter(names = {"-h", "--help"}, description = "Show help.")
	public Boolean help;
	
	@Parameter(names = {"-t"}, description = "Target folder to copy files to. If source is a file, then by default the folder that the source file is located in. If source is a folder, then by default the same folder as the source folder.")
	public String target;

	@Parameter(names = {"-d"}, description = "Delete original file instead of keeping it.")
	public Boolean delete;

	@Parameter(names = {"-lr"}, description = "Use long race name instead of short.")
	public Boolean longRaceNames;

	@Parameter(names = {"-pp"}, description = "List of players to make sure that the most prioritized player is always in team 1.")
	public String prioritizedPlayers;

	@Parameter(names = {"-r"}, description = "Rule used for renaming files. Acceptable values: #PLAYERS_TEAM_1#, #PLAYERS_TEAM_2#, #PLAYERS_ALL#, #RACES_TEAM_1#, #RACES_TEAM_2#, #RACES_ALL#, #PLAYERS_AND_RACES_TEAM_1#, #PLAYERS_AND_RACES_TEAM_2#, #PLAYERS_AND_RACES_ALL#, #OUTCOME_TEAM_1#, #OUTCOME_TEAM_2#, #MAP# and any string not containing '#'.")
	public String rule = "#DATE# #PLAYERS_AND_RACES_TEAM_1# vs #PLAYERS_AND_RACES_TEAM_2# on #MAP#";

	@Parameter(names = {"-p"}, description = "Used by #PLAYERS_TEAM_1#, #PLAYERS_TEAM_2# and #PLAYERS_ALL#.")
	public String player = "#PLAYER_NAME#";

	@Parameter(names = {"-ra"}, description = "Used by #RACES_TEAM_1#, #RACES_TEAM_2# and #RACES_ALL#. Acceptable values: #RACE_NAME# and any strings not containing '#'.")
	public String race = "#RACE_NAME#";
	
	@Parameter(names = {"-pr"}, description = "Used by #PLAYERS_AND_RACES_TEAM_1#, #PLAYERS_AND_RACES_TEAM_2# and #PLAYERS_AND_RACES_ALL#.")
	public String playerAndRace = "#PLAYER_NAME#(#RACE_NAME#)";

	@Parameter(names = {"-pd"}, description = "Divider used between players in #PLAYERS_TEAM_1#, #PLAYERS_TEAM_2# and #PLAYERS_ALL#. Can be set to any string.")
	public String playersDivider;

	@Parameter(names = {"-rd"}, description = "Divider used between races in #RACES_TEAM_1#, #RACES_TEAM_2# and #RACES_ALL#. Can be set to any string.")
	public String racesDivider;

	@Parameter(names = {"-prd"}, description = "Divider used between players and races in #PLAYERS_AND_RACES_TEAM_1#, #PLAYERS_AND_RACES_TEAM_2# and #PLAYERS_AND_RACES_ALL#. Can be set to any string.")
	public String playersAndRacesDivider;
}
