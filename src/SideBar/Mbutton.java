package SideBar;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Mbutton extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7449473685132643648L;
	private int x, y, width, height;
	private String status = "普通", title = "";
	private boolean bton;
	private Color st, sf;
	private ImageIcon icon = null;
	private Font f = new Font("宋体", Font.PLAIN, 12);

	public Mbutton(int x, int y, int width, int height, boolean bton, Color sf) {

		this.sf = sf;
		this.bton = bton;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.setLayout(null);
		this.setBounds(this.x, this.y, this.width, this.height);
		this.setOpaque(false);

	}

	public void seticon(String path) {
		this.icon = new ImageIcon(path);
		repaint();
	}

	public void SetSt(Color st) {
		this.st = st;
	}

	public void settitle(String s) {
		this.title = s;
		repaint();
	}

	public void setbton(boolean t) {
		this.bton = t;
	}

	public boolean PointIn(int px, int py) {
		if (bton == false) {
			return false;
		}

		if (px > x && px < (x + width) && py > y && py < (y + height)) {
			this.status = "划过";
			repaint();
			return true;
		}
		repaint();
		this.status = "普通";

		return false;
	}

	public void setstatus(String s) {
		this.status = s;
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		if (status == "普通") {
			g.setColor(this.sf);
		} else if (status == "划过") {
			g.setColor(this.st);
		}
		g.fillRect(1, 1, this.width - 2, this.height - 2);
		g.setColor(new Color(255, 255, 255));

		g.setFont(f);
		if (this.icon != null) {
			g.drawImage(this.icon.getImage(), 2, 2, 25, 25, this);
			g.drawString(this.title, 25 + 5, this.height / 2 + 3);
		} else {
			g.drawString(this.title, 5, this.height / 2 + 3);
		}
		if (this.title != "") {
			g.drawRect(0, 0, this.width - 1, this.height - 1);
		}

	}

}
