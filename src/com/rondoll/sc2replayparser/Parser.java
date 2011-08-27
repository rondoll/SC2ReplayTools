package com.rondoll.sc2replayparser;

import java.io.File;
import java.util.ArrayList;

public class Parser {
	
	private int offset;
	private byte[] data;
	private ArrayList<String> byteStrings;
	private ArrayList<Byte> outcome;
	
	private static String TERRAN = "Terran";
	private static String PROTOSS = "Protoss";
	private static String ZERG = "Zerg";
	
	private static String WINNER = "Winner";
	private static String LOSER = "Loser";
	private static String UNKNOWN = "Unknown";
	
	//Throws exception since there are conditions that can arise when
	//playing on special maps vs the computer (Aiur Chef for example)
	//that this parser cannot handle
	public Replay getReplay(File file) throws Exception {
		offset = 0;
		data = null;
		byteStrings = new ArrayList<String>();
		outcome = new ArrayList<Byte>();
		
		Replay replay = new Replay();
		data = new DataExtractor().getData(file, ReplayFile.REPLAY_DETAILS);
		
		while (offset < data.length) {
			byte type = data[offset];
			parse(type);
			offset += 1;
		}
		
		int numberOfPlayers = data[6] >> 1;
		
		String outcomeString = null;
		outcomeString = getOutcomeString(outcomeString);		
		for (int i = 0 ; i < numberOfPlayers ; i += 2) {
			replay.team1.add(new Player(byteStrings.get(i), byteStrings.get(i + 1), outcomeString));
		}
		outcomeString = getOutcomeString(outcomeString);
		for (int i = numberOfPlayers ; i < numberOfPlayers * 2 ; i += 2) {
			replay.team2.add(new Player(byteStrings.get(i), byteStrings.get(i + 1), outcomeString));
		}
		
		replay.map = byteStrings.get(numberOfPlayers * 2);
		
		checkForErrors(file, replay);
		
		return replay;
	}
	
	private String getOutcomeString(String currentOutcomeString) {
		String outcomeString = null;
		if (currentOutcomeString == null) {
			if (outcome.get(2) >> 1 == 0) {
				outcomeString = UNKNOWN;
			} else if (outcome.get(2) >> 1 == 1) {
				outcomeString = WINNER;
			} else {
				outcomeString = LOSER;
			}
		} else if (currentOutcomeString.equals(UNKNOWN)) {
			outcomeString = UNKNOWN;
		} else if (currentOutcomeString.equals(WINNER)) {
			outcomeString = LOSER;
		} else {
			outcomeString = WINNER;
		}
		return outcomeString;
	}
	
	private void checkForErrors(File file, Replay replay) throws Exception {
		checkForErrors(file, replay.team1);
		checkForErrors(file, replay.team2);
	}

	//Right now only error checked for is if the races are not correct.
	//Could possibly add more checks.
	private void checkForErrors(File file, ArrayList<Player> team) throws Exception {
		for (Player player : team) {
			if (!TERRAN.equals(player.race) && !PROTOSS.equals(player.race) && !ZERG.equals(player.race)) {
				throw new Exception("Unable to parse '" + file.getName() + "'");
			}
		}
	}
	
	private void parseKeyValueObject() {
		offset += 1;
		int numberOfKeyValuePairs = data[offset] >> 1;
		
		for (int i = 0 ; i < numberOfKeyValuePairs ; i++) {
			//+2 since after an element in the array it is followed by a key integer and then the type
			offset += 2;
			byte type = data[offset];
			parse(type);
		}
	}

	private void parseArrayObject() {
		offset += 3;
		int numberOfElements = data[offset] >> 1;
		
		for (int i = 0 ; i < numberOfElements ; i++) {
			offset += 1;
			byte type = data[offset];
			parse(type);
		}
	}
	
	//The first strings in a replay file will always be:
	//player 1 name
	//player 1 race
	//player 2 name
	//player 2 race
	//player 3 name
	//player 3 race
	//etc
	//map name
	//followed by strings this parser is not interested in 
	private void parseByteString() {
		offset += 1;
		int numberOfBytes = data[offset] >> 1;
		
		byte[] byteString = getData(data, offset + 1, numberOfBytes);
		byteStrings.add(new String(byteString));
		
		outcome.add(data[offset - 5]);

		offset += numberOfBytes;
	}
	
	private void parseSingleByteInteger() {
		offset += 1;
		// TODO: this does nothing.
		int singleByteInteger = data[offset] >> 1;
	}	
	
	private void parseFourByteInteger() {
		offset += 1;
		byte[] fourByteIntegerBytes = getData(data, offset, 4);
		// TODO: this does nothing.
		int fourByteInteger = byteArrayToInt(fourByteIntegerBytes) >> 1;
		//Only +3 and not +4 since we already did the +1 above
		offset += 3;
	}
	
	//TODO: Calculate the integer. Read spec at 
	private void parseVariableLengthFormatInteger() {
		int bitmask = 128;
		while(true) {
			offset += 1;
			byte b = data[offset];
			if ((b & bitmask) == 0) {
				break;
			}
		}
	}
	
	private void parse(byte type) {
		switch(type) {
			case 2:
			parseByteString();
			break;
			
			case 4:
			parseArrayObject();
			break;
			
			case 5:
			parseKeyValueObject();
			break; 
			
			case 6:
			parseSingleByteInteger();
			break;
			
			case 7:
			parseFourByteInteger();
			break;
			
			case 9:
			parseVariableLengthFormatInteger();
			break;
		}
	}
	
	private int byteArrayToInt(byte[] b) {
        int value = 0;
        for (int i = 0; i < 4; i++) {
            int shift = (4 - 1 - i) * 8;
            value += (b[i] & 0x000000FF) << shift;
        }
        return value;
    }
		
	private byte[] getData(byte[] bytes, int offset, int length) {
		byte[] data = new byte[length];
		for (int i = offset ; i < offset + length ; i++) {
			data[i - offset] = bytes[i];
		}
		return data;
	}
}
