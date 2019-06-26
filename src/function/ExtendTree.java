package function;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import common.WavePanel;

public class ExtendTree extends WavePanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long pre = System.currentTimeMillis();;
	private long cur;
	private int sequence = 1;
	private int x1, y1, x2, y2;
	private double angel = 2;
	private int r = 10;
	private int ragap = 5;
	private int ragap1 = 15;

	public ExtendTree() {

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		x1 = getWidth() / 2;
		y1 = getHeight() / 2;
		x2 = getWidth() / 2;
		y2 = getHeight() / 2;

		switch (sequence) {
		case 1:
			((Graphics2D) g).setStroke(new BasicStroke(4));
			g.setColor(new Color(0, 255, 204));
			g.drawOval(x1 - r, y2 - r, r * 2, r * 2);

			break;
		case 2:
			((Graphics2D) g).setStroke(new BasicStroke(8));
			g.setColor(new Color(204, 0, 0));
			g.drawLine(x1, y1 - r - 4, x1, y2 - r - 50);
			g.drawLine(x1 - r - 4, y1, x2 - r - 50, y2);
			g.drawLine(x1 + r + 4, y1, x2 + r + 50, y2);
			g.drawLine(x1, y1 + r + 4, x2, y2 + r + 50);

			break;
		case 3:
			((Graphics2D) g).setStroke(new BasicStroke(5));
			g.setColor(new Color(255, 228, 181));
			g.drawLine(x1, y2 - r - 50, x2 + 50, y2 - 100);
			g.drawLine(x1, y2 - r - 50, x2 - 50, y2 - 100);

			g.drawLine(x2 - r - 50, y2, x2 - r - 100, y2 - 50);
			g.drawLine(x2 - r - 50, y2, x2 - r - 100, y2 + 50);

			g.drawLine(x2 + r + 50, y2, x2 + r + 100, y2 - 50);
			g.drawLine(x2 + r + 50, y2, x2 + r + 100, y2 + 50);

			g.drawLine(x2, y2 + r + 50, x2 - 50, y2 + 100);
			g.drawLine(x2, y2 + r + 50, x2 + 50, y2 + 100);
			break;
		case 4:
			((Graphics2D) g).setStroke(new BasicStroke(6));
			g.setColor(new Color(255, 204, 51));

			g.drawOval(x2 + 50 - r, y2 - 100 - r, r * 2, r * 2);
			g.drawOval(x2 - 50 - r, y2 - 100 - r, r * 2, r * 2);

			g.drawOval(x2 - r - 100 - r, y2 - 50 - r, r * 2, r * 2);
			g.drawOval(x2 - r - 100 - r, y2 + 50 - r, r * 2, r * 2);

			g.drawOval(x2 + r + 100 - r, y2 - 50 - r, r * 2, r * 2);
			g.drawOval(x2 + r + 100 - r, y2 + 50 - r, r * 2, r * 2);

			g.drawOval(x2 - 50 - r, y2 + 100 - r, r * 2, r * 2);
			g.drawOval(x2 + 50 - r, y2 + 100 - r, r * 2, r * 2);
			break;
		case 5:
			((Graphics2D) g).setStroke(new BasicStroke(5));
			g.setColor(new Color(204, 255, 102));
			Circle1(g, x2 + 50, y2 - 100);
			Circle1(g, x2 - 50, y2 - 100);
			Circle1(g, x2 - r - 100, y2 - 50);
			Circle1(g, x2 - r - 100, y2 + 50);
			Circle1(g, x2 + r + 100, y2 - 50);
			Circle1(g, x2 + r + 100, y2 + 50);
			Circle1(g, x2 - 50, y2 + 100);
			Circle1(g, x2 + 50, y2 + 100);
			break;

		default:
			break;
		}
		sequence++;
		ragap++;
		ragap1 += 5;
		if (sequence == 6) {
			try {
				Thread.sleep(500);
				cur = System.currentTimeMillis();
				pre = cur;
				repaint();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			sequence = 1;
		}
		if (ragap == 55) {
			ragap1 = 15;
			ragap = 5;
		}

		cur = System.currentTimeMillis();

		if (cur - pre >= 100000) { // only purpose：get into thread sleep

		} else {
			try {
				Thread.sleep(1000);
				cur = System.currentTimeMillis();
				pre = cur;
				repaint();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void Circle1(Graphics g, int x, int y) {
		((Graphics2D) g).setStroke(new BasicStroke(3)); // stroke
		g.drawOval(x - r, y - r, r * 2, r * 2);// 椭圆
		g.drawOval(x - (r + ragap1), y - (r + ragap1), (r + ragap1) * 2, (r + ragap1) * 2);// 椭圆

	}

	public void ovel(Graphics g, int x, int y) {

		((Graphics2D) g).setStroke(new BasicStroke(2)); // stroke
		g.drawOval(x - r, y - r, r * 2, r * 2);// 椭圆

		((Graphics2D) g).setStroke(new BasicStroke(4)); // stroke
		g.drawOval(x - (r + ragap + 2), y - (r + ragap + 2), (r + ragap + 2) * 2, (r + ragap + 2) * 2);// 椭圆

	}

	public void dir(int flag, int x2, int y2) {

		switch (flag) {
		case 1: // right up
			this.x2 = (int) (x2 + y2 * Math.cos(Math.PI / angel));
			this.y2 = (int) (y2 - y2 * Math.sin(Math.PI / angel));
			break;
		case 2: // right done
			this.x2 = (int) (x2 + y2 * Math.cos(Math.PI / angel));
			this.y2 = (int) (y2 + y2 * Math.sin(Math.PI / angel));
			break;
		case 3: // left done
			this.x2 = (int) (x2 - y2 * Math.cos(Math.PI / angel));
			this.y2 = (int) (y2 + y2 * Math.sin(Math.PI / angel));
			break;
		case 4: // left up
			this.x2 = (int) (x2 - y2 * Math.cos(Math.PI / angel));
			this.y2 = (int) (y2 - y2 * Math.sin(Math.PI / angel));
			break;
		default:
			break;
		}
	}
}
