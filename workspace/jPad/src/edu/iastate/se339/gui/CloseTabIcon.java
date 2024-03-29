package edu.iastate.se339.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.Icon;

/**
 * Given in spec
 * Creates a close button icon that can be placed on a MyTabbedPane tab
 * @author mrmallet
 *
 */
class CloseTabIcon implements Icon {
	private int x_pos;
	private int y_pos;
	private int width;
	private int height;
	private Icon fileIcon;
	
	/*
	 * Called as new CloseTabIcon(null) in my code
	 * @param fileIcon
	 */
	public CloseTabIcon(Icon fileIcon) {
		this.fileIcon=fileIcon;
		width=16;
		height=16;
	}
			
	public void paintIcon(Component c, Graphics g, int x, int y) {
		this.x_pos=x;
		this.y_pos=y;
		Color col=g.getColor();
		g.setColor(Color.black);
		int y_p=y+2;
		g.drawLine(x+1, y_p, x+12, y_p);
		g.drawLine(x+1, y_p+13, x+12, y_p+13);
		g.drawLine(x, y_p+1, x, y_p+12);
		g.drawLine(x+13, y_p+1, x+13, y_p+12);
		g.drawLine(x+3, y_p+3, x+10, y_p+10);
		g.drawLine(x+3, y_p+4, x+9, y_p+10);
		g.drawLine(x+4, y_p+3, x+10, y_p+9);
		g.drawLine(x+10, y_p+3, x+3, y_p+10);
		g.drawLine(x+10, y_p+4, x+4, y_p+10);
		g.drawLine(x+9, y_p+3, x+3, y_p+9);
		g.setColor(col);
		if (fileIcon != null) {
			fileIcon.paintIcon(c, g, x+width, y_p);
		}
	}
		
	public int getIconWidth() {
		return width + (fileIcon != null? fileIcon.getIconWidth() : 0);
	}
	
	public int getIconHeight() {
		return height;
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x_pos, y_pos, width, height);
	}
}