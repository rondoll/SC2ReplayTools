package com.rondoll.sc2replayrenamer;

import com.rondoll.sc2replayparser.Replay;

public interface IRenamingRule {
	
	public String getNewFileName(Replay replay) throws Exception;
	
}
