package common;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JWindow;

public class mMouseHandler extends MouseAdapter {

	private JWindow mywindow;
	private int xx, yy;
	private boolean mousestatu = false;

	public mMouseHandler(JWindow jWindow) {
		this.mywindow = jWindow;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// mouse quit window
		super.mouseExited(e);

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// when mouse into window
		super.mouseEntered(e);

	}

	public void mousePressed(MouseEvent e) {

		super.mousePressed(e);
		if (e.getButton() == MouseEvent.BUTTON1) {
			xx = e.getX();
			yy = e.getY();
			mousestatu = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		super.mouseReleased(e);
		mousestatu = false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		super.mouseDragged(e);
		if (mousestatu) {

			int x = e.getX();
			int y = e.getY();

			int top = (int) mywindow.getLocation().getX();
			int left = (int) mywindow.getLocation().getY();
			mywindow.setLocation(top + x - xx, left + y - yy);
		}
	}
}