package com.acke.sc2stats;


public class SC2Stats {
	public static void main(String[] args) {
		String user = "grymme";
		String source = "/Users/knutfunkel/Library/Application Support/Blizzard/StarCraft II/Accounts/113386094/2-S2-1-877626/Replays/Multiplayer";
		MakeStats makeStats = new MakeStats();
		makeStats.makeStats(user, source);
	}


}
