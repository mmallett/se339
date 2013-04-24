package edu.iastate.se339.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class CustomizeFrame extends JFrame implements ActionListener{
	
	public static final int RESULT_OK = 1234;
	public static final int RESULT_CANCEL = 4321;
	public static final int RESULT_INVALID = 0;
	private int result;
	
	public static final String[] TEXT_BASES ={
		"ASCII",
		"HEX",
		"BINARY"
	};
	
	public static final String[] WORD_LENGTH ={
		"4","8","16","32","64"
	};
	
	public static final String[] WORDS_PER_LINE ={
		"1","2","3","4"
	};
	
	JComboBox<String> textList;
	JComboBox<String> lengthList;
	JComboBox<String> lineList;
	JButton ok;
	JButton cancel;
	
	CustomizeFrame(String title){
		super(title);
		setSize(500,500);
		
		getContentPane().add(new JLabel("Text Base"));
		textList = new JComboBox<String>(TEXT_BASES);
		textList.setSelectedIndex(0);
		getContentPane().add(textList);
		
		getContentPane().add(new JLabel("Word Length (bits)"));
		lengthList = new JComboBox<String>(WORD_LENGTH);
		lengthList.setSelectedIndex(0);
		getContentPane().add(lengthList);
		
		getContentPane().add(new JLabel("Words per line"));
		lineList = new JComboBox<String>(WORDS_PER_LINE);
		lineList.setSelectedIndex(0);
		getContentPane().add(lineList);
		
		ok = new JButton("Ok");
		ok.addActionListener(this);
		
		cancel = new JButton("Cancel");
		cancel.addActionListener(this);
		
		result = RESULT_INVALID;
	}
	
	public String getSelectedBase(){
		return textList.getSelectedItem().toString();
	}
	
	public String getSelectedLength(){
		return lengthList.getSelectedItem().toString();
	}
	
	public String getSelectedLineLength(){
		return lineList.getSelectedItem().toString();
	}
	
	public int getResult(){
		return result;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(ok)){
			result = RESULT_OK;
			setVisible(false);
		}
		else if(e.getSource().equals(cancel)){
			result = RESULT_CANCEL;
			setVisible(false);
		}
	}
}
