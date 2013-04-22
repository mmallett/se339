package edu.iastate.se339.text;

import java.io.UnsupportedEncodingException;

public class AsciiRepresentation extends AbstractRepresentation{

	public AsciiRepresentation(byte[] rawBytes){
		this.rawBytes = rawBytes;
		bitsPerChar = 8;
	}
	
	@Override
	public String toString(){
		String ret;
		try{
			ret = new String(rawBytes, "US-ASCII");
		}catch(UnsupportedEncodingException e){
			ret = e.toString();
		}
		return ret;
	}

}
