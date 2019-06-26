package function;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import common.WavePanel;

public class LineWaveBar extends WavePanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9219460194340594494L;
	private int max_line = 19;
	private long pre = System.currentTimeMillis();;
	private long cur;
	private int r = 5;
	private int x, y;

	public LineWaveBar() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					repaint();
					Thread.sleep(100);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		int w = getWidth();
		int h = getHeight();
		int gap = (w / max_line);

		Random random = new Random();

		for (int j = 1; j < max_line; j++) {

			int rand = (random.nextInt(3));
			if (rand == 1) {
				x = random.nextInt((w / 2) - 20);
				y = random.nextInt((h / 2) - 20);
			} else if (rand == 2) {
				x = random.nextInt(100);
				y = random.nextInt(500);
			} else if (rand == 3) {
				x = random.nextInt(200);
				y = random.nextInt(150);
			}

			for (int i = 1; i < w; i += gap) {
				((Graphics2D) g).setStroke(new BasicStroke(2));

				g.setColor(new Color(255, 185, 15));
				g.drawLine(j * gap, h / 2, j * gap, (y + x) / 5);

				((Graphics2D) g).setStroke(new BasicStroke(5));
				g.setColor(new Color(255, 51, 102));
				g.drawOval((j * gap) - r, ((y + x) / 5) - r, r * 2, r * 2);

				((Graphics2D) g).setStroke(new BasicStroke(2));
				g.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);

			}

		}
		cur = System.currentTimeMillis();

		if (cur - pre >= 100000) { // only purpose：get into thread sleep

		} else {
			try {
				Thread.sleep(100);
				cur = System.currentTimeMillis();
				pre = cur;
				repaint();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
