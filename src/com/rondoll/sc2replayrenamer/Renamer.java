package com.rondoll.sc2replayrenamer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import com.rondoll.sc2replayparser.Parser;
import com.rondoll.sc2replayparser.Replay;

public class Renamer {
	
	private File sourceFile;
	private File targetFolder;
	private IRenamingRule renamingRule;
	private boolean keepOriginal = true;
	
	//If a file, then renames the file
	//If a folder, then renames all sc2replays in the folder 
	public Renamer(File sourceFile, File targetFolder, IRenamingRule renamingRule, boolean keepOriginal) throws Exception {
		if (!sourceFile.exists()) {
			throw new Exception("File '" + sourceFile.getAbsolutePath() + "' does not exist");
		}
		if (!targetFolder.exists() && !targetFolder.createNewFile()) {
			throw new Exception("Could not create folder '" + targetFolder.getAbsolutePath());
		}
		
		this.sourceFile = sourceFile;
		this.targetFolder = targetFolder;
		this.renamingRule = renamingRule;
		this.keepOriginal = keepOriginal;
	}
	
	public void rename() throws Exception {
		if (sourceFile.isFile()) {
			renameFile(sourceFile);
		} else {
			File[] files = sourceFile.listFiles();
			for (File file : files) {
				renameFile(file);
			}
		}
	}
	
	private void renameFile(File file) throws Exception {
		if (file.isDirectory() || !file.getName().toLowerCase().endsWith(".sc2replay")) {
			return;
		}
		
		Parser parser = new Parser();
		String newFileName = renamingRule.getNewFileName(parser.getReplay(file));
		//suffix in upper case since that is how the replays are named on my computer
		File newFile = new File(targetFolder.getAbsolutePath() + File.separator + newFileName + ".SC2REPLAY");
		
		int count = 1;
		while (newFile.exists()) {
			newFile = new File(targetFolder.getAbsolutePath() + File.separator + newFileName + " (" + count + ").SC2REPLAY");
			count++;
		}
		
		write(file, newFile);
		
		if (!keepOriginal && !file.delete()) {
			throw new Exception ("Could not delete original file: '" + file.getAbsolutePath() + "'");
		}
	}
	
	private void write(File input, File output) throws Exception {
		FileOutputStream fileOutputStream = new FileOutputStream(output);
		FileInputStream fileInputStream = new FileInputStream(input);
		byte[] outputData = new byte[(int)input.length()];
		fileInputStream.read(outputData);
		fileOutputStream.write(outputData);
		fileInputStream.close();
		fileOutputStream.close();
	}
}
