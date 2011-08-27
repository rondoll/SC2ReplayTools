package com.rondoll.sc2replayparser;
public class ReplayFile {
	/*
	 * com.mundi4.mpq.MpqFile does not extract the names of ATTRIBUTES and
	 * LISTFILE, instead the names of those both files will be null (the data is
	 * intact though). The reason for them still being in the list below is if
	 * com.mundi4.mpq.MpqFile is updated to handle those files, or if another
	 * api would be used instead that can handle them.
	 */
	
	public static final String ATTRIBUTES = "(attributes)";
	public static final String LISTFILE = "(listfile)";
	public static final String REPLAY_ATTRIBUTES_EVENTS = "replay.attributes.events";
	public static final String REPLAY_DETAILS = "replay.details";
	public static final String REPLAY_GAME_EVENTS = "replay.game.events";
	public static final String REPLAY_INIT_DATA = "replay.initData";
	public static final String REPLAY_LOAD_INFO = "replay.load.info";
	public static final String REPLAY_MESSAGE_EVENTS = "replay.message.events";
	public static final String REPLAY_SMARTCAM_EVENTS = "replay.smartcam.events";
	public static final String REPLAY_SYNC_EVENTS = "replay.sync.events";
}
