package edu.iastate.se339.gui;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

/**
 * Extension of JTabbedPane that builds close icon funcitonality into the class
 * through the MouseListener interface
 * @author mrmallet
 *
 */
class TabbedPane extends JTabbedPane implements MouseListener{

	private JPadFrame jpad;
	
	private List<EditorPane> tabs;
	
	/**
	 * Create a new MyTabbedPane with itself attached as a mouselistener
	 * @param xtext
	 */
	public TabbedPane(JPadFrame xtext){
		super();
		this.addMouseListener(this);
		this.jpad = xtext;
		tabs = new ArrayList<EditorPane>();
	}
	
	public void newTab(){

		JFileChooser fchooser = new JFileChooser(System.getProperty("user.home"));
		
		int ret = fchooser.showOpenDialog(this);
		if(ret == JFileChooser.APPROVE_OPTION){
			
			File file = fchooser.getSelectedFile();
			if(file == null || !file.exists()){
				JOptionPane.showMessageDialog(this, "File " + file.getName() + " not found!");
				return;
			}
		
			try{
				EditorPane newTab = new EditorPane(file.getName(), file.getPath());
				tabs.add(newTab);
				addTab(file.getName(), new CloseTabIcon(null), newTab, file.getPath());
			} catch(IOException e){
				JOptionPane.showMessageDialog(this, e.toString());
			}
		}
	}
	
	public void closeTab(int index){
		tabs.remove(index);
		remove(index);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 * If the close icon was clicked, closes the corresponding tab. closeTab() handles all of
	 * the complexities of closing a tab.
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		for(int i = 0; i < this.getComponentCount(); i++){
			CloseTabIcon icon = (CloseTabIcon) this.getIconAt(i);
			Rectangle rec = icon.getBounds();
			if(rec.contains(arg0.getPoint())){
				closeTab(i);
			}
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}

