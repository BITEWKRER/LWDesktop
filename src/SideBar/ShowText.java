package SideBar;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class ShowText extends JPanel {

	/**
	 * 
	 */
	private Color mColor;
	private static final long serialVersionUID = 4426776594314060032L;
	private String text;
	private Font font = new Font("微软雅黑", Font.PLAIN, 18);

	public ShowText(int x, int y, int w, int h, String text, Color C) {
		this.text = text;
		this.mColor = C;
		this.setLayout(null);
		setOpaque(false);
		this.setBounds(x, y, w, h);
	}
	public void settext(String s) {
		this.text=s;
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setFont(font);
		g.setColor(mColor);
		if(text!=null){
			g.drawString(text, 0, 18);
		}
	}
}
