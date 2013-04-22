package edu.iastate.se339.text;

public class HexRepresentation extends AbstractRepresentation{
	
	public HexRepresentation(byte[] rawBytes){
		this.rawBytes = rawBytes;
		bitsPerChar = 4;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(byte b : rawBytes){
			sb.append(Integer.toHexString((b >> 4) & 0x0F).toUpperCase()); //not sure if treated as unsigned, playing safe
			sb.append(Integer.toHexString(b & 0x0F).toUpperCase());
		}
		return sb.toString();
	}

}
