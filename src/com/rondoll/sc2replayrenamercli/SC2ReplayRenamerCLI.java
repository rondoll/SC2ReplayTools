package com.rondoll.sc2replayrenamercli;

import java.io.File;

import com.rondoll.sc2replayrenamer.Renamer;
import com.rondoll.sc2replayrenamer.RenamingRule;
import static com.rondoll.sc2replayrenamer.RuleConstant.*;

import com.acke.sc2stats.MakeStats;
import com.beust.jcommander.JCommander;

public class SC2ReplayRenamerCLI {

	public static void main(String[] args) {

		Parameters parameters = new Parameters();
		JCommander jc = new JCommander(parameters, args);
		
		if (parameters.source.size() > 1 && parameters.source.get(0).toString().startsWith("stats")) {
			try {
				MakeStats makeStats = new MakeStats();
				makeStats.makeStats(parameters.source.get(2), parameters.source.get(1));
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

			return;
		}

		if (parameters.help != null || parameters.source == null
				|| parameters.source.size() != 1) {
			jc.usage();
			return;
		}

		try {
			File source = new File(parameters.source.get(0));
			File target = null;
			if (parameters.target != null) {
				target = new File(parameters.target);
			}

			RenamingRule renamingRule = new RenamingRule();
			renamingRule.setRule(parameters.rule);
			renamingRule.setPlayer(parameters.player);
			renamingRule.setRace(parameters.race);
			renamingRule.setPlayerAndRace(parameters.playerAndRace);
			if (parameters.playersDivider != null) {
				renamingRule.setPlayersDivider(parameters.playersDivider);
			}
			if (parameters.racesDivider != null) {
				renamingRule.setRacesDivider(parameters.racesDivider);
			}
			if (parameters.playersAndRacesDivider != null) {
				renamingRule
						.setPlayersAndRacesDivider(parameters.playersAndRacesDivider);
			}
			renamingRule.setShortRaceName(parameters.longRaceNames == null);
			if (parameters.prioritizedPlayers != null) {
				renamingRule
						.setPrioritizedPlayers(parameters.prioritizedPlayers
								.split(" "));
			}

			if (target == null) {
				if (source.isFile()) {
					target = source.getParentFile();
				} else {
					target = source;
				}
			}

			Renamer renamer = new Renamer(source, target, renamingRule,
					(parameters.delete == null));
			renamer.rename();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
}
