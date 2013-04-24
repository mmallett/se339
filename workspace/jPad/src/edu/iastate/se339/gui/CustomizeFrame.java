package edu.iastate.se339.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class CustomizeFrame extends JFrame implements ActionListener, Runnable{
	
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
	
	private JComboBox<String> textList;
	private JComboBox<String> lengthList;
	private JComboBox<String> lineList;
	private JButton ok;
	private JButton cancel;
	
	private EditorPane editorPane;
	
	CustomizeFrame(String title, EditorPane editorPane){
		super(title);
		setSize(350,150);
		setLayout(new GridLayout(4,2));
		
		this.editorPane = editorPane;
		
		getContentPane().add(new JLabel("Text Base"), BorderLayout.CENTER);
		textList = new JComboBox<String>(TEXT_BASES);
		textList.setSelectedIndex(0);
		getContentPane().add(textList, BorderLayout.CENTER);
		
		getContentPane().add(new JLabel("Word Length (bits)"), BorderLayout.CENTER);
		lengthList = new JComboBox<String>(WORD_LENGTH);
		lengthList.setSelectedIndex(0);
		getContentPane().add(lengthList, BorderLayout.CENTER);
		
		getContentPane().add(new JLabel("Words per line"), BorderLayout.CENTER);
		lineList = new JComboBox<String>(WORDS_PER_LINE);
		lineList.setSelectedIndex(0);
		getContentPane().add(lineList, BorderLayout.CENTER);
		
		cancel = new JButton("Cancel");
		cancel.addActionListener(this);
		getContentPane().add(cancel, BorderLayout.CENTER);
		
		ok = new JButton("Ok");
		ok.addActionListener(this);
		getContentPane().add(ok, BorderLayout.CENTER);
	}
	
	public String getSelectedBase(){
		return textList.getSelectedItem().toString();
	}
	
	public String getSelectedLength(){
		if(getSelectedBase().equals("ASCII")){
			return "-1";
		}
		return lengthList.getSelectedItem().toString();
	}
	
	public String getSelectedLineLength(){
		if(getSelectedBase().equals("ASCII")){
			return "-1";
		}
		return lineList.getSelectedItem().toString();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(ok)){
			System.out.println("DIS SHIT GIT CLICKED MON");
			editorPane.buildNewStack(getSelectedBase(),getSelectedLength(),getSelectedLineLength());
			setVisible(false);
		}
		else if(e.getSource().equals(cancel)){
			setVisible(false);
		}
	}

	@Override
	public void run() {
		setVisible(true);
	}
}
