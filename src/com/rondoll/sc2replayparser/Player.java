package com.rondoll.sc2replayparser;

public class Player {
	public String name;
	public String race;

	/*
	 * Regarding outcome:
	 * 
	 * 0 == Unknown (if more than one on each team, and the "creator" of the replay leaves before
	 * one of the other players on the same team leaves, then the outcome is unknown, although
 	 * if a player leaves before win, it is pretty good chance that it is a loss if it is not due 
	 * to disconnect)
	 * 
	 * 1 == Winner
	 * 
	 * 2 == Loser
	 */
	public String outcome;
	
	public Player(String name, String race, String outcome) {
		this.name = name;
		this.race = race;
		this.outcome = outcome;
	}
}
