package function;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import common.WavePanel;

public class Bar extends WavePanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long pre = System.currentTimeMillis();;
	private long cur;
	private int max_line = 20;

	public Bar() {
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g = (Graphics2D) g;
		int w = getWidth();
		int h = getHeight();
		int gap = w / max_line;

		Random random = new Random();

		g.setColor(new Color(30, 144, 255));

		for (int j = 0; j < max_line; j++) {

			int x = random.nextInt(w / 2);
			int y = random.nextInt((h / 2));

			for (int i = 0; i < w; i += gap) {
				g.setColor(new Color(255, 215, 0));
				g.fillRect(j * gap * 2, i * gap, gap, (x + y) / 2);

				g.setColor(new Color(255, 64, 64));
				g.fillRect(j * gap, i * gap, gap, (x + y) / 4);
			}
		}
		cur = System.currentTimeMillis();

		if (cur - pre >= 100000) { // only purpose：get into thread sleep

		} else {
			try {
				Thread.sleep(120);
				cur = System.currentTimeMillis();
				pre = cur;
				repaint();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
