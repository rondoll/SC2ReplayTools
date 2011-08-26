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
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(file);
			fileOutputStream.write(data);
		} finally {
			fileOutputStream.close();
		}
	}
	
	private void read(InputStream inputStream, byte[] data) throws IOException {
        if (inputStream.read(data) == -1) {
                throw new IOException("end of stream reached");
        }
        inputStream.close();
	}
	
	private File createTempFile() throws IOException {
		File tempFile = File.createTempFile(PREFIX, null);
		return tempFile;
	}
	
	private byte[] getBytes(File file) throws IOException {
		byte[] data = new byte[(int)file.length()];
	    InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
			inputStream.read(data);
	    } finally {
			inputStream.close();
		}
	    return data;
	}
}
