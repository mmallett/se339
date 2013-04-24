package edu.iastate.se339.gui;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTabbedPane;

/**
 * Extension of JTabbedPane that builds close icon funcitonality into the class
 * through the MouseListener interface
 * @author mrmallet
 *
 */
class TabbedPane extends JTabbedPane implements MouseListener{

	private JPadFrame xtext;
	
	/**
	 * Create a new MyTabbedPane with itself attached as a mouselistener
	 * @param xtext
	 */
	public TabbedPane(JPadFrame xtext){
		super();
		this.addMouseListener(this);
		this.xtext = xtext;
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
				xtext.closeTab(i);
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

