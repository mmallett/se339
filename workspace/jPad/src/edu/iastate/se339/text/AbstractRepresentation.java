package edu.iastate.se339.text;

public abstract class AbstractRepresentation {
	
	protected byte[] rawBytes;
	protected int bitsPerChar;
	
	@Override
	public abstract String toString();

}
