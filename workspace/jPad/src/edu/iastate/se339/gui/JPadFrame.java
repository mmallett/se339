package edu.iastate.se339.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

public class JPadFrame extends JFrame implements ActionListener, 
                                                   DocumentListener,  
                                                   MouseListener                        
{
	
	/********************** 
	 *  Member Attributes
	 *********************/
	// main text area
	//private JTextArea text;
	private List<JTextArea> textList;
	// keeps track of whether the text has changed in text area
	//private boolean documentChanged = false;
	private List<Boolean> changedList;
	
	private List<String> openFiles;
	private static final String NEW_FILE = "NEW_FILE";
	
	// main text area holder: a scrolling pane
	//private JScrollPane textPane;
	
	private TabbedPane tabbedPane;
	
	// menubar with menus
	private JMenuBar menubar = new JMenuBar();
	private JMenu fileMenu = new JMenu("File");
	private JMenu editMenu = new JMenu("Edit");
	private JMenu viewMenu = new JMenu("View");
	
	// file menu items
	private JMenuItem xnew = new JMenuItem("New", 'N');
	private JMenuItem open = new JMenuItem("Open", 'O');
	private JMenuItem exit = new JMenuItem("eXit", 'X');
	private JMenuItem save = new JMenuItem("Save As", 'S');
	
	private JMenuItem customize = new JMenuItem("Customize", 'C');
	

	
	/**********************************
	 * AbstractAction Declarations
	 * - function handlers are separately defined
	 **********************************/
	// abstract action: to make sure the popup menu and menuitems work identically
	private AbstractAction replace = new AbstractAction("Replace") 
	{
		public void actionPerformed(ActionEvent event)
		{
			replaceHandler();
		}
	};
	
	private AbstractAction replaceAll = new AbstractAction("ReplaceAll") 
	{
		public void actionPerformed(ActionEvent event)
		{
			replaceAllHandler();
		}
	};
	
	private AbstractAction find = new AbstractAction("Find") 
	{
		public void actionPerformed(ActionEvent event)
		{
			findHandler();
		}
	};
	
	/*private AbstractAction customize = new AbstractAction("Customize"){
		public void actionPerformed(ActionEvent event){
			customizeHandler();
		}
	}*/
	
	// creating the main 
	public static void main(String[] args) 
	{
		final JPadFrame frame = new JPadFrame("jPad");
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e)
			{
				frame.exitHandler();
				System.exit(0);
			}
		});
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);		
	}
	
	
	/**************
	 * Constructor
	 *************/
	JPadFrame(String title)
	{	
		super(title);
		setSize(500,500);
		
		/*****************************************************************************************
		 *  1. create the text area 
		 *  2. register the mouse listener: to enable popup whenever right-mouse button clicked
		 *  3. register the document listener: any change should be caught
		 *         For items 2 and 3 look inside settingTextAreaProperty()
		 *  4. put text area in a scrollpane
		 *  5. put the scrollpane in the frames content pane
		 ****************************************************************************************/
		tabbedPane = new TabbedPane(this);
		textList = new ArrayList<JTextArea>();
		getContentPane().add(tabbedPane);
		
		// set the documentChanged to false to indicate initially nothing has changed in the text area
		changedList = new ArrayList<Boolean>();
		//changedList.add(false);
		
		openFiles = new ArrayList<String>();
		//openFiles.add(NEW_FILE);
		//tabbedPane.setIconAt(0, new CloseTabIcon(null));
		setTitle("jPad");
		
		/*****************************************
		 *  create all the menus and the menubar
		 *****************************************/
		createFileMenu();
		createEditMenu();
		createViewMenu();
		createMenuBar();
		setJMenuBar(menubar);
	}
	
	
	
	/******************************************************************
	 * Creating the file menu: the registration of ActionListener 
	 *****************************************************************/
	public void createFileMenu()
	{
		fileMenu.add(xnew);
		fileMenu.add(open);
		fileMenu.add(save);
		fileMenu.add(exit);
		xnew.addActionListener(this);
		open.addActionListener(this);
		save.addActionListener(this);	
		exit.addActionListener(this);		
	}
	
	/***************************************************************
	 * creating the edit menu: note the usage of abstract action class
	 * replace, replaceAll and find 
	 ***************************************************************/
	public void createEditMenu()
	{
		editMenu.add(replace);	
		editMenu.add(replaceAll);
		editMenu.add(find);
		//editMenu.add(optionSubMenu);
	}
	
	public void createViewMenu(){
		viewMenu.add(customize);
		customize.addActionListener(this);
	}


	/******************************
	 *  finally create the menu bar
	 ******************************/
	public void createMenuBar()
	{
		menubar.add(fileMenu);
		menubar.add(editMenu);
		menubar.add(viewMenu);
	}
	
	/****************************************************************************
	 * This class implements ActionListener interface: based on where the 
	 * event has been generated, different action handlers are invoked
	 * For example: if the new file menu item is clicked, xnewHandler is invoked.
	 ****************************************************************************/
	public void actionPerformed(ActionEvent e) 
	{
		Object source = e.getSource();
		if (source == xnew) xnewHandler();     
		else if (source == open) openHandler();
		else if (source == save) saveHandler();
		else if (source == exit) exitHandler();
		else if (source.equals(customize)) customizeHandler();
	}

	/************************************
	 *  Function to create new text area
	 ***********************************/
	public void xnewHandler()
	{	
		//newTab("New");
		//openFiles.add(NEW_FILE);
		tabbedPane.newTab();
	}
    
    
	/*******************************************************************************************
	 * Function to open new files
	 *  1. usage of file chooser starting from user home
	 *  2. usage of file filter so that any file with "text" or "txt" extensions can be opened.
     *****************************************************************************************/
	public void openHandler()
	{
		
		/*int returnVal;
		JFileChooser fchooser = new JFileChooser(System.getProperty("user.home"));
		
		returnVal = fchooser.showOpenDialog(JPadFrame.this);
		
		// a file to be opened is selected
		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
	       File file = fchooser.getSelectedFile();
	       if (file == null || !file.exists())
	       {
	    	   JOptionPane.showMessageDialog(this, "File " + file.getName() + " not found!"); 
	    	   return;
	       }
	       
	       // writing function is invoked via this helper
	       openHandlerHelper(file);
	    }
	    else { System.out.println("User decided not to open a file"); }*/
		tabbedPane.newTab();
	}

//	// Another function: simple but invoked once: future use
//	public void openHandlerHelper(File file)
//	{
//		/*MyFileFiltermmallett filter = new MyFileFiltermmallett();
//		if(filter.accept(file)){*/
//			for(int i = 0; i< openFiles.size(); i++){
//				if(openFiles.get(i).equals(file.getName())){
//					tabbedPane.setSelectedIndex(i);
//					return;
//				}
//			}
//			String title = file.getName();
//			//setTitle(title);
//			//newTab(title);
//			//tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), title);
//		    
//		    // file reading function is a helper to read file-data one line at a time
//		    fileReading(file);
//		  
//		    // need to set up the documentChanged to false; otherwise it will remain true incorrectly.
//		    //documentChanged = false;
//		    changedList.set(tabbedPane.getSelectedIndex(),false);
//		    openFiles.add(file.getName());
//		}
	
//	// file reading helper
//	public void fileReading(File file)
//	{
//		try 
//	       {
//	    	   BufferedReader reader = new BufferedReader(new FileReader(file));
//	    	   String line = null;
//	    	   int i = tabbedPane.getSelectedIndex();
//	    	   while ((line = reader.readLine()) != null)
//	    	   {
//	    		   textList.get(i).append(line);
//	    		   textList.get(i).append("\n");
//	    	   }
//	    	   reader.close();
//	       } catch (IOException excep)   { excep.printStackTrace(); }
//	}
	
	
	/****************************
	 * Saving files
	 * 
	 *****************************/

	public void saveHandler()
	{
		JFileChooser fchooser = new JFileChooser(System.getProperty("user.home"));
		
		int returnVal = fchooser.showSaveDialog(JPadFrame.this);

		// Some file has been selected 
	    if (returnVal == JFileChooser.APPROVE_OPTION)
	    {
	       File file = fchooser.getSelectedFile();
	
	       boolean newFile = true;
	       if (file.exists()) 
	       {
	    	   newFile = false;
	    	   // If file already exists, ask before replacing it.
	    	   returnVal = JOptionPane.showConfirmDialog(null, "Replace existing file?", "Confirmation Window", returnVal);
	    	   if (returnVal != JOptionPane.YES_OPTION) return;
	       }
	       try 
	       {
	    	   int i = tabbedPane.getSelectedIndex();
	    	   PrintWriter out = new PrintWriter(new FileWriter(file));
	    	   String contents = textList.get(i).getText();
	    	   out.print(contents);
	    	   openFiles.set(i, file.getName());
	    	   tabbedPane.setTitleAt(i, file.getName());
	    	   changedList.set(i, false);
	    	   if (out.checkError())
	    		   throw new IOException("Error while writing to file.");
	    	   out.close();	
	       } catch (IOException excep) {excep.printStackTrace();}
	    }
	    else 
	    {
	       // save is cancelled
	    }			
	}
	
	/***************************
	 * Exiting and cleaning up 
	***************************/
	
	public void exitHandler()
	{	
		// Option to save is only provided when the current text document has been updated
		/*for(int i = 0; i < tabbedPane.getComponentCount(); i++){
			if (changedList.get(i))
			{
				// open the dialog asking whether the user wants to save the file or not
				int returnVal = JOptionPane.showConfirmDialog(this, "Want to save the file in editor:" + 
						tabbedPane.getTitleAt(i), null, JOptionPane.YES_NO_OPTION);
				if (returnVal == JOptionPane.YES_OPTION)
				{
					tabbedPane.setSelectedIndex(i);
					saveHandler();
				}
			}
		}*/
		System.exit(0);
	}
	
	private void customizeHandler() {
		tabbedPane.customize();
	}
	
	
	/**************************
     * Editing operations 
     ***********************/
	public void replaceHandler()
	{
		String to = null;
		String from = null;
		
		// check whether any text is selected or not
		int i = tabbedPane.getSelectedIndex();
		JTextArea text = textList.get(i);
		from = text.getSelectedText();
		
		if (from == null)
			// not selected: open dialog 
			from = JOptionPane.showInputDialog(this,"Provide the string to replace?");
		else 
			// otherwise set the caret to the start of the selected text
			text.setCaretPosition(text.getSelectionStart());
		
		if (from == null) return;
		
		// this is to make sure the string to replace exists in the text area
		int caretPosition = text.getCaretPosition();
		int fromIndex = find2Handler(from, caretPosition);
		if (fromIndex == -1)
			return;
	
		// Reuse the fromIndex: set it from the beginning of the text
		fromIndex = fromIndex + caretPosition;
		
		// Now its time to get the replace-to string
		to = JOptionPane.showInputDialog(this, "What do you want to replace with?");
		if (to == null)
			return;
		else
			text.replaceRange(to, fromIndex, fromIndex+from.length());
	}
	
	public void replaceAllHandler()
	{		
		String to = null;
		String from = null;
		
		JTextArea text = textList.get(tabbedPane.getSelectedIndex());
		from = text.getSelectedText();
		if (from == null)
			from = JOptionPane.showInputDialog(this,"Provide the string to replace");
		else text.setCaretPosition(text.getSelectionStart());
		if (from == null) return;

		// this is for making sure that the string to replace exists in text area
		int caretPosition = text.getCaretPosition();
		int fromIndex = find2Handler(from, caretPosition);
		if (fromIndex == -1)
			return;
		
		to = JOptionPane.showInputDialog(this, "What do you want to replace with?");
		if (to == null)
			return;
		else
		{
			int textLength = text.getText().length();
			while (fromIndex != -1)
			{
				fromIndex = fromIndex + caretPosition;
				if (fromIndex >= 0 && from.length() > 0)
				{
					text.replaceRange(to, fromIndex, fromIndex+from.length());
					text.setCaretPosition(fromIndex+to.length());
					textLength = text.getText().length();
					caretPosition = text.getCaretPosition();
					try {
						fromIndex = text.getText(caretPosition, textLength - caretPosition).indexOf(from);
					} catch (BadLocationException excep) {excep.printStackTrace();}
				}
			}
		}
	}
	
	/*
	 * finding the string - once again use the find2Handler
	 */
	public void findHandler()
	{
		String search = null;
		
		JTextArea text = textList.get(tabbedPane.getSelectedIndex());
		String in = text.getText();
		
		search = JOptionPane.showInputDialog(null, "Enter the search string\n", "Find", JOptionPane.PLAIN_MESSAGE);
		if (search == null) return;
		
		else
		{
			int caretPosition = text.getCaretPosition();
			int fromIndex = find2Handler(search, caretPosition);
			if (fromIndex == -1)
				return;
			text.select(caretPosition+fromIndex, caretPosition+fromIndex+search.length()); 
		}
	}
	
	/*
	 * Level two find handling used by replace, replaceAll and find handers
	 */
	
	public int find2Handler(String search, int caretPosition)
	{
		JTextArea text = textList.get(tabbedPane.getSelectedIndex());
		int textLength = text.getText().length();
		int fromIndex = -1;
		try {
			fromIndex = text.getText(caretPosition, textLength - caretPosition).indexOf(search);
		} catch (BadLocationException excep) {excep.printStackTrace(); }
		
		if (fromIndex == -1)
			JOptionPane.showMessageDialog(this, "Word not found");
		return fromIndex;
	}
	
	/**
	 * Invoked whenever a new tab is created
	 * Initializes the tab in the xtext data structures
	 * @param title the title of the new tab
	 */
	/*protected void newTab(String title)
	{
		textList.add(new JTextArea());
		settingTextAreaProperty(textList.get(textList.size()-1));
		changedList.add(false);
		tabbedPane.add(title,new JScrollPane(textList.get(textList.size()-1)));
		tabbedPane.setSelectedIndex(tabbedPane.getComponentCount()-1);
		tabbedPane.setIconAt(tabbedPane.getComponentCount()-1, new CloseTabIcon(null));
	}*/
	
	/**
	 * Invoked whenever a tab is closed (by clicking its close icon)
	 * Saves changed state, then removes the tab from the xtext data structures
	 * @param index tab index to be closed
	 */
	protected void closeTab(int index){
		if(changedList.get(index)){
			int returnVal = JOptionPane.showConfirmDialog(this, "Want to save changes made to file:" + tabbedPane.getTitleAt(index), null, JOptionPane.YES_NO_OPTION);
			if (returnVal == JOptionPane.YES_OPTION)
				saveHandler();
		}
		textList.remove(index);
		changedList.remove(index);
		openFiles.remove(index);
		tabbedPane.remove(index);
	}


	/*
	 * All the methods are implemented: We are left with the listener interfaces
	 */
	
	
	/****************************************************************************************
	 * Implementing the MouseListener interface: This is for right mouse button click event,
	 * when the popup is shown
	 ****************************************************************************************/
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	
	public void mousePressed(MouseEvent e) 
	{
		// reuse the abstract action 
		if (e.isPopupTrigger())
		{
			JPopupMenu popup = new JPopupMenu();
			popup.add(replace);
			popup.add(replaceAll);
			popup.add(find);
			popup.show(e.getComponent(), e.getX(), e.getY());
		}
	}
	
	/*****************************************************************************************
	 * Implementing the DocumentListener: this is required to keep track of text area updates
	 *****************************************************************************************/
	public void insertUpdate(DocumentEvent e) 
	{
		changedList.set(tabbedPane.getSelectedIndex(), true);
	}


	public void removeUpdate(DocumentEvent e) 
	{
		changedList.set(tabbedPane.getSelectedIndex(), true);
	}


	public void changedUpdate(DocumentEvent e) 
	{	
		// nothing should go in here because this is nothing to do with the text in the text area 
	}
	
	
}