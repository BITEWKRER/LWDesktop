package CutScreen;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.JWindow;

class ToolsWindow extends JWindow {
	/**
	 * 操作窗口
	 */
	private static final long serialVersionUID = 1L;
	private ScreenShotWindow parent;

	public ToolsWindow(ScreenShotWindow parent, int x, int y) {
		this.parent = parent;
		this.init();
		this.setLocation(x, y);
		this.pack();
		this.setVisible(true);
		this.setAlwaysOnTop(true);
	}

	private void init() {

		this.setLayout(new BorderLayout());
		JToolBar toolBar = new JToolBar("快速截图");

//保存按钮
		JButton saveButton = new JButton(new ImageIcon("img/save.png"));
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					parent.saveImage();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		toolBar.add(saveButton);
// 完成按钮
		JButton completebtn = new JButton(new ImageIcon("img/cut1.png"));
		completebtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				parent.setClipboardImage();
				parent.dispose();
				dispose();
			}
		});
		toolBar.add(completebtn);
//关闭按钮
		JButton closeButton = new JButton(new ImageIcon("img/exit.png"));
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				parent.dispose();
				dispose();
			}
		});
		toolBar.add(closeButton);
		this.add(toolBar, BorderLayout.NORTH);
		this.setAlwaysOnTop(true);
	}

}