package edu.iastate.se339.io;

import java.io.IOException;

public class BinaryTest {
	
	public static void main(String args[]){
		
		BinaryFile datFile = new BinaryFile("C:/Users/Matt/Documents/GitHub/cpre426/prog2/psuedo.txt");
		try {
			for(byte b : datFile.read()){
				System.out.print(b);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
