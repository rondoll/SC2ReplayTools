package com.rondoll.sc2replayrenamer;

import java.util.Comparator;

import com.rondoll.sc2replayparser.Player;

public class PlayerComparator implements Comparator {

	private String[] prioritizedPlayers;
	
	public PlayerComparator(String[] prioritized) {
		this.prioritizedPlayers = prioritized;
	}
	
	@Override
	public int compare(Object player1, Object player2) {
		int p1 = 999;
		int p2 = 999;
		for (int i = 0 ; i < prioritizedPlayers.length ; i++) {
			if (prioritizedPlayers[i].equals(((Player)player1).name)) {
				p1 = i;
			}
			if (prioritizedPlayers[i].equals(((Player)player2).name)) {
				p2 = i;
			}
		}
		
		if (p1 > p2) {
			return 1;
		} else if (p1 < p2) {
			return -1;
		}
		return 0;
	}

}
