package CutScreen;

import java.awt.AWTException;
import java.awt.EventQueue;

public class ScreenShotTest {

	public ScreenShotTest() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					ScreenShotWindow ssw = new ScreenShotWindow();
					ssw.setVisible(true);
				} catch (AWTException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
