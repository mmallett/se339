package edu.iastate.se339.gui;

import java.awt.Font;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import edu.iastate.se339.io.BinaryFile;
import edu.iastate.se339.text.AbstractRepresentation;
import edu.iastate.se339.text.AsciiRepresentation;
import edu.iastate.se339.text.BinaryRepresentation;
import edu.iastate.se339.text.HexRepresentation;
import edu.iastate.se339.text.NoOpDecorator;
import edu.iastate.se339.text.WordLengthDecorator;
import edu.iastate.se339.text.WordTokenizerDecorator;

public class EditorPane extends JPanel{
	
	private BinaryFile file;
	private LinkedList<AbstractRepresentation> decoratorStack;
	
	private JTextArea text;
	
	public EditorPane(String title, String filePath) throws IOException{
		setLayout(new GridLayout(1,1));
		
		text = new JTextArea();
		
		file = new BinaryFile(filePath);
		decoratorStack = new LinkedList<AbstractRepresentation>();
		AsciiRepresentation def = new AsciiRepresentation(file.read());
		decoratorStack.push(def);
		
		initializeTextArea();
		text.setText(decoratorStack.peek().toString());
		
		add(new JScrollPane(text));
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

	public void customizeClicked() {
		CustomizeFrame cust = new CustomizeFrame("Customize", this);
		Thread t = new Thread(cust);
		t.start();
	}

	public void buildNewStack(String base, String wordLength, String wordsPerLine) {
		byte[] bytes = decoratorStack.peek().getRawBytes();
		decoratorStack.clear();
		/*if(cust.getSelectedBase().equals("ASCII")){
			decoratorStack.push(new AsciiRepresentation(bytes));
		}
		else{
			if(cust.getSelectedBase().equals("HEX")){
				decoratorStack.push(new HexRepresentation(bytes));
			}
			else{
				decoratorStack.push(new BinaryRepresentation(bytes));
			}
			decoratorStack.push(
					new WordLengthDecorator(
						decoratorStack.peek(),
						Integer.parseInt(cust.getSelectedLength())));
			decoratorStack.push(
					new WordTokenizerDecorator(
						decoratorStack.peek(),
						"",
						Integer.parseInt(cust.getSelectedLineLength())));
		}*/
		if(base.equals("ASCII")){
			decoratorStack.push(new AsciiRepresentation(bytes));
		}
		else if(base.equals("HEX")){
			decoratorStack.push(new HexRepresentation(bytes));
		}
		else{
			decoratorStack.push(new BinaryRepresentation(bytes));
		}
		
		if(Integer.parseInt(wordLength) == -1){
			decoratorStack.push(new NoOpDecorator(decoratorStack.peek()));
		}
		else{
			decoratorStack.push(new WordLengthDecorator(decoratorStack.peek(), Integer.parseInt(wordLength)));
		}
		
		if(Integer.parseInt(wordsPerLine) == -1){
			decoratorStack.push(new NoOpDecorator(decoratorStack.peek()));
		}
		else{
			decoratorStack.push(new WordTokenizerDecorator(decoratorStack.peek(), " ", Integer.parseInt(wordsPerLine)));
		}
		
		text.setText(decoratorStack.peek().toString());
		
	}
}
