package com.acke.sc2stats;

import java.io.File;

import com.rondoll.sc2replayparser.Parser;
import com.rondoll.sc2replayparser.Replay;
import com.rondoll.sc2replayrenamer.RenamingRule;
import com.rondoll.sc2replayrenamer.RuleConstant;

public class MakeStats {
	
	public void makeStats(String user, String sourceFolder) {
		
		File source = new File(sourceFolder);
		File[] files = source.listFiles();
		Parser parser = new Parser();
		RenamingRule renamingRule = new RenamingRule();
		StatsRules statsRules = new StatsRules();
		StatsCounters statsCounters = new StatsCounters();
		renamingRule.setPrioritizedPlayers(new String[] { "zoidberg", "grymme",
				"apanloco" });
		renamingRule.setRule(RuleConstant.OUTCOME_TEAM_1 + " "
				+ RuleConstant.PLAYERS_AND_RACES_TEAM_1 + " vs "
				+ RuleConstant.RACES_TEAM_2);
		renamingRule.setPlayersDivider(" ");
		renamingRule.setRacesDivider("");
		renamingRule.setShortRaceName(true);

		RenamingRule excludeComputerPlayerRule = new RenamingRule();
		excludeComputerPlayerRule.setRule(RuleConstant.OUTCOME_TEAM_1 + " "
				+ RuleConstant.PLAYERS_AND_RACES_TEAM_1 + " vs "
				+ RuleConstant.PLAYERS_AND_RACES_TEAM_2);
		excludeComputerPlayerRule.setPlayersDivider(" ");
		excludeComputerPlayerRule.setRacesDivider("");
		excludeComputerPlayerRule.setShortRaceName(true);

		for (File file : files) {
			if (!file.isDirectory()
					&& file.getName().toLowerCase().endsWith(".sc2replay")) {
				try {
					Replay replay = parser.getReplay(file);
					System.out.println(excludeComputerPlayerRule.getNewFileName(replay));
					if (!excludeComputerPlayerRule.getNewFileName(replay)
							.contains("Player 1")) {
						statsRules.winsTotal1v1(statsCounters,
								renamingRule.getNewFileName(replay), user);

						statsRules.statMatchup(statsCounters,
								renamingRule.getNewFileName(replay), user);
					}

				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}
		// System.out.println("Total Wins: " + statsCounters.getTotalWins());
		System.out.println("Total 1v1s played: "
				+ statsCounters.getTotal1v1Replays());
		System.out.println("Total wins 1v1s: "
				+ statsCounters.getTotal1v1Wins());
		System.out.println("Zvp: " + (double) statsCounters.getZvp()
				/ (double) statsCounters.getTotalZvp());
		System.out.println("Zvt: " + (double) statsCounters.getZvt()
				/ (double) statsCounters.getTotalZvt());
		System.out.println("Zvz: " + (double) statsCounters.getZvz()
				/ (double) statsCounters.getTotalZvz());
		System.out.println("Tvp: " + (double) statsCounters.getTvp()
				/ (double) statsCounters.getTotalTvp());
		System.out.println("Tvp: " + (double) statsCounters.getTvz()
				/ (double) statsCounters.getTotalTvz());
		System.out.println("Tvt: " + (double) statsCounters.getTvt()
				/ (double) statsCounters.getTotalTvt());
		System.out.println("Pvz: " + (double) statsCounters.getPvz()
				/ (double) statsCounters.getTotalPvz());
		System.out.println("Pvt: " + (double) statsCounters.getPvt()
				/ (double) statsCounters.getTotalPvt());
		System.out.println("Pvp: " + (double) statsCounters.getPvp()
				/ (double) statsCounters.getTotalPvp());
	}
}
