package com.rondoll.sc2replayrenamer;

import java.io.File;

import com.rondoll.sc2replayparser.Parser;
import com.rondoll.sc2replayparser.Replay;

public class SC2ReplayRenamer {
	//TODO: option to keep original file or to delete it
	//TODO: source folder/file and target folder
	public static void main(String[] args) {
		Parser parser = new Parser();
		RenamingRule renamingRule = new RenamingRule();
		renamingRule.setPrioritizedPlayers(new String[] {"zoidberg", "grymme", "apanloco"});
		renamingRule.setRule(RuleConstant.OUTCOME_TEAM_1 + " " + RuleConstant.PLAYERS_AND_RACES_TEAM_1 + " vs " + RuleConstant.RACES_TEAM_2 + " on " + RuleConstant.MAP);
//		renamingRule.setRule(RuleConstant.PLAYERS_TEAM_1 + " " + RuleConstant.RACES_TEAM_1 + "v" + RuleConstant.RACES_TEAM_2);
		renamingRule.setPlayersDivider(" ");
		renamingRule.setRacesDivider("");
		renamingRule.setShortRaceName(true);
		
		File[] files = new File("C:\\Users\\Henrik\\Documents\\StarCraft II\\Accounts\\1367582\\2-S2-1-844568\\Replays\\Multiplayer").listFiles();
		for (File file : files) {
			if (!file.isDirectory() && file.getName().toLowerCase().endsWith(".sc2replay")) {
				try {
					Replay replay = parser.getReplay(file);
					System.out.println(renamingRule.getNewFileName(replay));
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}				
			}
		}
	}
}
