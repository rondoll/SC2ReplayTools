package com.rondoll.sc2replayparser;
import java.util.ArrayList;
import java.util.Date;

public class Replay {
	public String map;
	public ArrayList<Player> team1 = new ArrayList<Player>();
	public ArrayList<Player> team2 = new ArrayList<Player>();
	public Date date;

	public String toString() {
		String str = "==Team 1==";
		str += "\nOutcome: " + team1.get(0).outcome;
		for (Player player : team1) {
			str += "\n" + player.name + " (" + player.race + ")";
		}
		str += "\n==Team 2==";
		str += "\nOutcome: " + team2.get(0).outcome;
		for (Player player : team2) {
			str += "\n" + player.name + " (" + player.race + ")";
		}
		str += "\n==Map==\n" + map + "\n";
		str += "\n==Date==\n" + date + "\n";
		return str;
	}
}
