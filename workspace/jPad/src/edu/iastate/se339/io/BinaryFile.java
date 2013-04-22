package edu.iastate.se339.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class BinaryFile extends File{

	public BinaryFile(String arg0) {
		super(arg0);
	}
	
	public byte[] read() throws IOException{
		byte[] bytes = new byte[(int) this.length()+1];
		FileInputStream in = new FileInputStream(this);
		int i = 0;
		while((bytes[i++] = (byte) in.read()) != -1);
		in.close();
		return bytes;
	}

}
