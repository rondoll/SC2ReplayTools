package com.acke.sc2stats;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StatsRules {

	public void winsTotal(StatsCounters sc, String replay, String user) {
		Pattern p = Pattern.compile("\\bWinner ");
		Matcher m = p.matcher(replay);
		if (m.find()) {
			sc.setTotalWins(sc.getTotalWins() + 1);
			System.out.println(sc.getTotalWins());
		}
	}

	public void winsTotal1v1(StatsCounters sc, String replay, String user) {
		Pattern p = Pattern.compile("Winner " + user + "\\([ZTP]\\)"+" ");
		Matcher m = p.matcher(replay);
		if (m.find()) {
			sc.setTotal1v1Wins(sc.getTotal1v1Wins() + 1);
			sc.setTotal1v1Replays(sc.getTotal1v1Replays() + 1);
			System.out.println("Total wins 1v1s: " + sc.getTotal1v1Wins());
			System.out.println("Total 1v1s: " + sc.getTotal1v1Replays());
		}

		// count losses as well
		Pattern p2 = Pattern.compile("Loser " + user + "\\([ZTP]\\)");
		Matcher m2 = p2.matcher(replay);
		if (m2.find()) {
			sc.setTotal1v1Replays(sc.getTotal1v1Replays() + 1);

			System.out.println("Total 1v1s: " + sc.getTotal1v1Replays());
		}
	}

	public void statMatchup(StatsCounters sc, String replay, String user) {
		
		if (replay.startsWith("Winner")) {
			System.out.println("Winner " + user + "(Z) vs ");
			if (replay.startsWith("Winner " + user + "(Z) vs T")) {
				sc.setZvt(sc.getZvt()+1);
				sc.setTotalZvt(sc.getTotalZvt()+1);
			} else if (replay.startsWith("Winner " + user + "(Z) vs P")) {
				sc.setZvp(sc.getZvp()+1);
				sc.setTotalZvp(sc.getTotalZvp()+1);
			} else if (replay.startsWith("Winner " + user + "(Z) vs Z")) {
				sc.setZvz(sc.getZvz()+1);
				sc.setTotalZvz(sc.getTotalZvz()+1);
			} else if (replay.startsWith("Winner " + user + "(T) vs Z")) {
				sc.setTvz(sc.getTvz()+1);
				sc.setTotalTvz(sc.getTotalTvz()+1);
			} else if (replay.startsWith("Winner " + user + "(T) vs P")) {
				sc.setTvp(sc.getTvp()+1);
				sc.setTotalTvp(sc.getTotalTvp()+1);
			} else if (replay.startsWith("Winner " + user + "(T) vs T")) {
				sc.setTvt(sc.getTvt()+1);
				sc.setTotalTvt(sc.getTotalTvt()+1);
			} else if (replay.startsWith("Winner " + user + "(P) vs Z")) {
				sc.setPvz(sc.getPvz()+1);
				sc.setTotalPvz(sc.getTotalPvz()+1);
			} else if (replay.startsWith("Winner " + user + "(P) vs T")) {
				sc.setPvt(sc.getPvt()+1);
				sc.setTotalPvt(sc.getTotalPvt()+1);
			} else if (replay.startsWith("Winner " + user + "(P) vs P")) {
				sc.setPvp(sc.getPvp()+1);
				sc.setTotalPvp(sc.getTotalPvp()+1);
			} else {
				System.out.println("invalid matchup");
			}

		}else if (replay.startsWith("Loser")){
			if (replay.startsWith("Loser " + user + "(Z) vs T")) {
				sc.setTotalZvt(sc.getTotalZvt()+1);
			} else if (replay.startsWith("Loser " + user + "(Z) vs P")) {
				sc.setTotalZvp(sc.getTotalZvp()+1);
			} else if (replay.startsWith("Loser " + user + "(Z) vs Z")) {
				sc.setTotalZvz(sc.getTotalZvz()+1);
			} else if (replay.startsWith("Loser " + user + "(T) vs Z")) {
				sc.setTotalTvz(sc.getTotalTvz()+1);
			} else if (replay.startsWith("Loser " + user + "(T) vs P")) {
				sc.setTotalTvp(sc.getTotalTvp()+1);
			} else if (replay.startsWith("Loser " + user + "(T) vs T")) {
				sc.setTotalTvt(sc.getTotalTvt()+1);
			} else if (replay.startsWith("Loser " + user + "(P) vs Z")) {
				sc.setTotalPvz(sc.getTotalPvz()+1);
			} else if (replay.startsWith("Loser " + user + "(P) vs T")) {
				sc.setTotalPvt(sc.getTotalPvt()+1);
			} else if (replay.startsWith("Loser " + user + "(P) vs P")) {
				sc.setTotalPvp(sc.getTotalPvp()+1);
			} else {
				System.out.println("invalid matchup");
			}
		}
	}
}
