package com.rondoll.sc2replayparser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import com.mundi4.mpq.MpqEntry;
import com.mundi4.mpq.MpqFile;


public class DataExtractor {
	String PREFIX = "sc2replaytempfile";
	
	public byte[] getData(File file, String name) {
		byte[] data = null;
		
		try {
			byte[] bytes = getBytes(file);
			
			byte[] mpqData = getMpqData(bytes);
			File tempFile = createTempFile();
			write(tempFile, mpqData);
			
			MpqFile mpqFile = new MpqFile(tempFile);
			Iterator<MpqEntry> iter = mpqFile.iterator();
            while (iter.hasNext()) {
                MpqEntry entry = iter.next();
                if (name.equals(entry.getName())) {
                	data = new byte[(int) entry.getSize()];
                	InputStream inputStream = mpqFile.getInputStream(entry);
                	read(inputStream, data);
                	break;
                }
            }
    		tempFile.delete();
            mpqFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
	private byte[] getMpqData(byte[] data) {
		//The first 1024 bytes is the header
		byte[] mpqData = new byte[data.length - 1024];
		for (int i = 1024 ; i < data.length ; i++) {
			mpqData[i - 1024] = data[i];
		}
				
		return mpqData;
	}
	
	private void write(File file, byte[] data) throws IOException {
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		fileOutputStream.write(data);
		fileOutputStream.close();
	}
	
	private void read(InputStream inputStream, byte[] data) throws IOException {
        inputStream.read(data);
        inputStream.close();
	}
	
	private File createTempFile() throws IOException {
		File tempFile = null;
		
		
		for (int i = 0 ; i < 10000 ; i++) {
			tempFile = new File(System.getProperty("java.io.tmpdir") + File.separator + PREFIX + i);
			if(!tempFile.exists()) {
				break;
			}
		}
 
		return tempFile;
	}
	
	private byte[] getBytes(File file) throws IOException {
		byte[] data = new byte[(int)file.length()];
	    InputStream inputStream = new FileInputStream(file);
	    inputStream.read(data);
	    inputStream.close();
	    return data;
	}
}
