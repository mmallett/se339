package edu.iastate.se339.gui;

import java.awt.Font;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import edu.iastate.se339.io.BinaryFile;
import edu.iastate.se339.text.AbstractRepresentation;
import edu.iastate.se339.text.AsciiRepresentation;

public class EditorPane extends JScrollPane{
	
	private BinaryFile file;
	private LinkedList<AbstractRepresentation> decoratorStack;
	
	private JTextArea text;
	
	public EditorPane(String title, String filePath) throws IOException{
		
		text = new JTextArea();
		
		file = new BinaryFile(filePath);
		decoratorStack = new LinkedList<AbstractRepresentation>();
		AsciiRepresentation def = new AsciiRepresentation(file.read());
		decoratorStack.push(def);
		
		initializeTextArea();
		text.setText(decoratorStack.peek().toString());
		
		this.add(text);
	}
	
	
	private int size = 15;
	private String face = "Consolas";
	private int fontType = Font.BOLD;
	
	private void initializeTextArea(){
		text.setLineWrap(true);
		//text.addMouseListener(this);
		text.setEditable(true);
		text.setFont(new Font(face, fontType, size));
		//text.getDocument().addDocumentListener(this);
		text.setTabSize(3);
	}
}
