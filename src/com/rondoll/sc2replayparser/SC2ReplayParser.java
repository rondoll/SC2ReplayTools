package com.rondoll.sc2replayparser;
import java.io.File;


public class SC2ReplayParser {

	//The replay spec used was taken from the below site
	//https://github.com/GraylinKim/sc2reader/wiki
	
	//The library used for extracting files from mpq files was taken from the below site
	//http://code.google.com/p/mpqlib/
	
	public static void main(String[] args) {
		File[] files = new File("C:\\Users\\Henrik\\Documents\\StarCraft II\\Accounts\\1367582\\2-S2-1-844568\\Replays\\Multiplayer").listFiles();
		for (File file : files) {
			if (!file.isDirectory() && file.getName().toLowerCase().endsWith(".sc2replay")) {
				try {
					Replay replay = new Parser().getReplay(file);
					System.out.println(replay);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					System.out.println();
				}
			}
		}
	}
}

