package SideBar;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class rectpanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4509956541185434839L;
	private int w, h;

	public rectpanel(int x, int y, int width, int height) {
		this.w = width;
		this.h = height;
		this.setBounds(x, y, width, height);
		this.setLayout(null);
		this.setOpaque(false);
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.setColor(Color.white);
		g.drawRect(0, 0, w - 1, h - 1);
	}
}
