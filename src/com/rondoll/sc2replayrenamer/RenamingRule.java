package com.rondoll.sc2replayrenamer;

import java.util.ArrayList;
import java.text.SimpleDateFormat;

import com.rondoll.sc2replayparser.Player;
import com.rondoll.sc2replayparser.Replay;
import static com.rondoll.sc2replayrenamer.RuleConstant.*;

public class RenamingRule implements IRenamingRule {
	
	private Replay replay;
	
	private boolean shortRaceName = true;
	private String player;
	private String race;
	private String playerAndRace;
	private String racesDivider = "";
	private String playersDivider = "";
	private String playersAndRacesDivider = "";
	private String rule;
	private String[] prioritizedPlayers;
	private String[] validConstants = new String[] { PLAYER_NAME, RACE_NAME,
			RACES_TEAM_1, RACES_TEAM_2, RACES_ALL, PLAYERS_TEAM_1,
			PLAYERS_TEAM_2, PLAYERS_ALL, PLAYERS_AND_RACES_TEAM_1,
			PLAYERS_AND_RACES_TEAM_2, PLAYERS_AND_RACES_ALL, OUTCOME_TEAM_1,
			OUTCOME_TEAM_2, MAP, DATE };
	
	public RenamingRule() {
		player = PLAYER_NAME;
		race = RACE_NAME;
		playerAndRace = PLAYER_NAME + "(" + RACE_NAME + ")";		
		rule = PLAYERS_AND_RACES_TEAM_1 + " vs " + PLAYERS_AND_RACES_TEAM_2 + " on " + MAP;
	}
	
	public String getNewFileName(Replay replay) throws Exception {
		this.replay = replay;
		String[] ruleArray = split(rule);
		
		prioritize();
		
		String newFileName = "";
		for (String str : ruleArray ) {
			checkIfValidConstant(str, validConstants);
			newFileName += getPartOfNewFileName(str);
		}
		
		return newFileName;
	}
	
	/*
	 * First player in the list has highest priority. As soon as a player with
	 * priority is found in the replay, that player and all other team members
	 * become team 1. Useful for when you want to rename files to always start with
	 * your own team so that it is easy to see who you played with. 
	 *  
	 */
	public void setPrioritizedPlayers(String... players) {
		prioritizedPlayers = players;
	}
	
	/*
	 * Valid strings: PLAYER_NAME and any string not containing '#'
	 */
	public void setPlayer(String player) throws Exception {
		checkIfValidConstant(player, new String[]{PLAYER_NAME});
		this.player = player;
	}

	/*
	 * Valid strings: RACE_NAME and any string not containing '#'
	 */
	public void setRace(String race) throws Exception {
		checkIfValidConstant(race, new String[]{RACE_NAME});
		this.race = race;
	}
	
	/*
	 * Valid strings: PLAYER_NAME, RACE_NAME and any string not containing '#'
	 */
	public void setPlayerAndRace(String playerAndRace) throws Exception {
		checkIfValidConstant(playerAndRace, new String[]{PLAYER_NAME, RACE_NAME});
		this.playerAndRace = playerAndRace;
	}

	/*
	 * Valid strings: Any
	 */
	public void setPlayersDivider(String playersDivider) {
		this.playersDivider = playersDivider;
	}

	/*
	 * Valid strings: Any
	 */
	public void setRacesDivider(String racesDivider) {
		this.racesDivider = racesDivider;
	}
	
	/*
	 * Valid strings: Any
	 */
	public void setPlayersAndRacesDivider(String playersAndRacesDivider) {
		this.playersAndRacesDivider = playersAndRacesDivider;
	}
	
	public void setRule(String rule) {
		this.rule = rule;
	}

	public void setShortRaceName(boolean shortRaceName) {
		this.shortRaceName = shortRaceName;
	}
	
	private String getRace(String race) {
		if (shortRaceName) {
			return race.charAt(0) + "";
		} else {
			return race;
		}
	}
	
	private void prioritize() {		
		if (prioritizedPlayers == null) {
			return;
		}
		
		for (String prioritizedPlayer : prioritizedPlayers) {
			for (Player player : replay.team1) {
				if (prioritizedPlayer.equals(player.name)) {
					return;
				}
			}
			for (Player player : replay.team2) {
				if (prioritizedPlayer.equals(player.name)) {
					ArrayList<Player> tempTeam = replay.team1;
					replay.team1 = replay.team2;
					replay.team2 = tempTeam;
					return;
				}
			}
		}
	}
	
	private void checkIfValidConstant(String rule, String[] validConstantArray) throws Exception {
		String[] invalidConstantArray = getInvalidConstants(rule, validConstantArray);
		if (invalidConstantArray.length == 0) {
			return;
		}
		
		throw new RuleException("Invalid constant/s used: " + getStringFromArray(invalidConstantArray) + ". Only valid constant/s: " + getStringFromArray(validConstantArray));
	}
	
	private String[] getInvalidConstants(String rule, String[] validConstants) throws Exception {
		String[] ruleArray = split(rule);
		ArrayList<String> invalidRules = new ArrayList<String>();
		
		for (String rulePart : ruleArray) {
			for (int i = 0 ; i < validConstants.length ; i++) {
				if (!rulePart.startsWith("#")) {
					break;
				} else if (rulePart.equals(validConstants[i])) {
					break;
				} else if (i == validConstants.length - 1) {
					invalidRules.add(rulePart);
				}
			}
		}
		
		return (String[])invalidRules.toArray(new String[invalidRules.size()]);
	}
	
	private void checkIfValidRule(String rule) throws Exception {
		if (rule.replaceAll("#[^#]*#", "").contains("#")) {
			throw new Exception("Only constants may contain '#' in a rule");
		}
	}
	
	private String getStringFromArray(String[] stringArray) {
		String fullString = "";
		for (String str : stringArray) {
			fullString += str + " ";
		}
		return fullString.trim();
	}
	
	private String[] split(String str) throws Exception {
		checkIfValidRule(str);
		
		ArrayList<String> arrayList = new ArrayList<String>();
		int endIndex;
		for (int i = 0 ; i < str.length() ; i++) {
			if (str.charAt(i) == '#') {
				//if command
				endIndex = str.indexOf("#", i + 1) + 1;
				//toUpperCase() to make the command case-insensitive as all constants in RuleConstant are uppercase
				arrayList.add(str.substring(i, endIndex).toUpperCase());
				i = endIndex - 1;
			} else {
				//else string
				endIndex = str.indexOf("#", i); 
				if (endIndex == -1) {
					endIndex = str.length();
				}
				arrayList.add(str.substring(i, endIndex));
				i = endIndex - 1;
			}
		}
		
		return arrayList.toArray(new String[arrayList.size()]);
	}
	
	private String getPlayers(String constant) throws Exception {
		String playersTeam = "";
		ArrayList<String> list = new ArrayList<String>();
		
		if (PLAYERS_TEAM_1.equals(constant) || PLAYERS_ALL.equals(constant)) {
			for (Player p : replay.team1) {
				list.add(p.name);
			}
		}
		
		if (PLAYERS_TEAM_2.equals(constant) || PLAYERS_ALL.equals(constant)) {
			for (Player p : replay.team2) {
				list.add(p.name);
			}
		}
		
		String[] ruleArray = split(player);
		for (int i = 0 ; i < list.size() ; i++) {
			for (int j = 0 ; j < ruleArray.length ; j++) {
				if (PLAYER_NAME.equals(ruleArray[j])) {
					playersTeam += list.get(i);
				} else {
					playersTeam += ruleArray[j];
				}
				
				if (i < list.size() - 1 && j == ruleArray.length - 1) {
					playersTeam += playersDivider;
				}
			}
		}
		
		return playersTeam;
	}
	
	private String getRaces(String constant) throws Exception {
		String racesTeam = "";
		ArrayList<String> list = new ArrayList<String>();
		
		if (RACES_TEAM_1.equals(constant) || RACES_ALL.equals(constant)) {
			for (Player p : replay.team1) {
				list.add(getRace(p.race));
			} 
		}
		
		if (RACES_TEAM_2.equals(constant) || RACES_ALL.equals(constant)) {
			for (Player p : replay.team2) {
				list.add(getRace(p.race));
			} 
		}
		
		String[] ruleArray = split(race);
		for (int i = 0 ; i < list.size() ; i++) {
			for (int j = 0 ; j < ruleArray.length ; j++) {
				if (RACE_NAME.equals(ruleArray[j])) {
					racesTeam += list.get(i);
				} else {
					racesTeam += ruleArray[j];
				}
				
				if (i < list.size() - 1 && j == ruleArray.length - 1) {
					racesTeam += racesDivider;
				}
			}
		}
		
		return racesTeam;
	}
	
	private String getPlayersAndRaces(String constant) throws Exception {
		String playersAndRacesTeam = "";
		ArrayList<String> playerList = new ArrayList<String>();
		ArrayList<String> raceList = new ArrayList<String>();
		
		if (PLAYERS_AND_RACES_TEAM_1.equals(constant) || PLAYERS_AND_RACES_ALL.equals(constant)) {
			for (Player p : replay.team1) {
				playerList.add(p.name);
				raceList.add(getRace(p.race));
			}
		}
		
		if (PLAYERS_AND_RACES_TEAM_2.equals(constant) || PLAYERS_AND_RACES_ALL.equals(constant)) {
			for (Player p : replay.team2) {
				playerList.add(p.name);
				raceList.add(getRace(p.race));
			}
		}
		
		String[] ruleArray = split(playerAndRace);
		for (int i = 0 ; i < playerList.size() ; i++) { // Could also have chosen raceList since they have the same size
			for (int j = 0 ; j < ruleArray.length ; j++) {
				if (PLAYER_NAME.equals(ruleArray[j])) {
					playersAndRacesTeam += playerList.get(i);
				} else if (RACE_NAME.equals(ruleArray[j])) {
					playersAndRacesTeam += raceList.get(i);
				} else {
					playersAndRacesTeam += ruleArray[j];
				}
				
				//might need updating
				//i < list.size() - 1 && j == ruleArray.length - 1
				if (j == ruleArray.length - 1 && i < playerList.size() - 1) {
					playersAndRacesTeam += playersAndRacesDivider;
				}
			}
		}
		
		return playersAndRacesTeam;
	}
	
	private String getPartOfNewFileName(String str) throws Exception {
		String returnString = "";
		if (str.charAt(0) != '#') {
			return str;
		}
		
		if (PLAYERS_TEAM_1.equals(str) || PLAYERS_TEAM_2.equals(str) || PLAYERS_ALL.equals(str)) {
			returnString += getPlayers(str);
		} else if (RACES_TEAM_1.equals(str) || RACES_TEAM_2.equals(str) || RACES_ALL.equals(str)) {
			returnString += getRaces(str);
		} else if (PLAYERS_AND_RACES_TEAM_1.equals(str) || PLAYERS_AND_RACES_TEAM_2.equals(str) || PLAYERS_AND_RACES_ALL.equals(str)) {
			returnString += getPlayersAndRaces(str);
		} else if (MAP.equals(str)) {
			return replay.map;
		} else if (DATE.equals(str) && replay.date != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			return formatter.format(replay.date);
		} else if (OUTCOME_TEAM_1.equals(str)) {
			return replay.team1.get(0).outcome;
		} else if (OUTCOME_TEAM_2.equals(str)) {
			return replay.team2.get(0).outcome;
		}
		
		return returnString;
	}
}