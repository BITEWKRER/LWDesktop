package SideBar;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;
import javax.swing.JWindow;

public class Loading extends JWindow{
	
	/**
	 * 
	 */
	public loadingpanel lp=new loadingpanel();
	private static final long serialVersionUID = 1L;

	public Loading() {
		// TODO Auto-generated constructor stub
		setSize(200,200);
		setLocationRelativeTo(null);
		setBackground(new Color(0,0,0,0));
		setLayout(null);
		
		Thread t=new Thread(lp);
		t.start();
		this.add(lp);
		setVisible(true);
	}
	
	public class loadingpanel extends JPanel implements Runnable
	{
		/**
		 * 
		 */
		private double angle=0;
		public boolean exit=false;
		private static final long serialVersionUID = 1L;

		public loadingpanel() {
			// TODO Auto-generated constructor stub
			setLayout(null);
			setBounds(0,0,200,200);
			setOpaque(false);
			setAlwaysOnTop(true);
			
		}
		@Override
		protected void paintComponent(Graphics g) {
			// TODO Auto-generated method stub
			super.paintComponent(g);

			((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // 抗锯齿
			g.setColor(new Color(226,219,190,230));
			g.fillOval(0, 0, 200, 200);
			g.setColor(new Color(163,163,128,240));
			g.fillOval(
					(int)(90+80*Math.sin(angle)),
					(int)( 90+80*Math.cos(angle)),
					24, 24);
			g.setColor(Color.black);
			g.setFont(new Font("楷体", Font.PLAIN, 18));
			g.drawString("Loading...", 68, 100);
		}
		@Override
		public void run() {

			while(!exit)
			{
				angle+=Math.PI/96;
				repaint();
				try {
					Thread.sleep(8);
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
			}
			dispose();
		}
	}
}
