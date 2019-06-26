package SideBar;

import java.awt.Color;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JWindow;

import CutScreen.ScreenShotTest;
import Weather.Weather;
import jmp123.gui.Player;
import trapockage.HistoryGui;
import trapockage.TralationGui;
import xiamiDecode.XiamiProcess;

public class Mwindow extends JWindow implements Runnable, MouseListener,
		MouseMotionListener {

	/**
	 * main_window
	 */
	private static final long serialVersionUID = -2801915174060259456L;
	private int width;
	private boolean MouseEnt = true;
	private Mbutton mbp;
	private ArrayList<Mbutton> mbs = new ArrayList<Mbutton>();// 爬歌相关
	private ArrayList<Mbutton> mbT = new ArrayList<Mbutton>();// 标题相关
	private ArrayList<Mbutton> mba = new ArrayList<Mbutton>();// 功能相关
	public Player player = new Player();
	private Weather weather = new Weather();
	private Thread weatherthread = new Thread(weather);
	private ShowText loadStext;
	private ShowText[] sText = new ShowText[10];
	private rectpanel Rpanelp, Rpanelt, Rpanelw, Rpanels;

	private Color tittleColor = new Color(153, 204, 255, 125);
	private Color btnColor = new Color(153, 204, 204, 125);
	private Color txtColor = new Color(255,255,255,125);
	private Color clickedColor = new Color(255, 255, 204, 125);
	private Color wColor = new Color(250,250,250);
	
	public Mwindow(int x, int y, int width, int height) {

		this.width = width;
		Init();

		this.setLayout(null);
		this.setLocation(x, y);
		this.setSize(width, height);
		this.setBackground(new Color(0, 0, 0, 125));
		this.setAlwaysOnTop(true);

		this.addMouseMotionListener(this);
		this.addMouseListener(this);

		this.setVisible(true);
		this.drag();
	}

	private void Init() {
		/// 频谱列表
		Rpanelp = new rectpanel(3, 3, this.width - 7, 100);
		this.add(Rpanelp);
		mbp = new Mbutton(9, 37, this.width - 18, 30, false, txtColor);
		mbp.settitle("拖拽文件进入播放歌曲");
		this.add(mbp);

		mbT.add(new Mbutton(9, 6, this.width - 18, 28, false,tittleColor) );
		mbT.get(0).seticon("img/music.png");
		mbT.get(0).settitle("可视化音乐播放");
		this.add(mbT.get(0));

		mba.add(new Mbutton(9, 69, 120, 30, true,btnColor ));
		mba.get(0).settitle("播放/暂停");
		mba.get(0).SetSt(clickedColor);
		this.add(mba.get(0));
		mba.add(new Mbutton(131, 69, 120, 30, true, btnColor));
		mba.get(1).settitle("隐藏/显示");
		mba.get(1).SetSt(clickedColor);
		this.add(mba.get(1));
		// 榜单列表
		Rpanels = new rectpanel(3, 102, this.width - 7, 392);
		this.add(Rpanels);
		mbT.add(new Mbutton(9, 106, this.width - 18, 28, false, tittleColor));
		mbT.get(1).seticon("img/Leaderboard.png");
		mbT.get(1).settitle("今日榜单");
		this.add(mbT.get(1));

		for (int i = 0; i < 10; i++) {
			mbs.add(new Mbutton(9, 136 + i * 32, this.width - 18, 30, false, txtColor));
			mbs.get(i).SetSt(clickedColor);
			mbs.get(i).settitle("空");
			this.add(mbs.get(i));
		}

		mba.add(new Mbutton(9, 460, 120, 30, true, btnColor));
		mba.get(2).settitle("更新榜单");
		mba.get(2).SetSt(clickedColor);
		this.add(mba.get(2));

		mba.add(new Mbutton(131, 460, 120, 30, true, btnColor));
		mba.get(3).settitle("下载榜单");
		mba.get(3).SetSt(clickedColor);
		this.add(mba.get(3));

		// 翻译功能
		Rpanelt = new rectpanel(3, 496, this.width - 7, 74);
		this.add(Rpanelt);
		mbT.add(new Mbutton(9, 500, this.width - 18, 28, false, tittleColor));
		mbT.get(2).seticon("img/Translation.png");
		mbT.get(2).settitle("即时翻译");
		this.add(mbT.get(2));

		mba.add(new Mbutton(9, 534, 120, 30, true, btnColor));
		mba.get(4).settitle("快速翻译");
		mba.get(4).SetSt(clickedColor);
		this.add(mba.get(4));

		mba.add(new Mbutton(132, 534, 120, 30, true, btnColor));
		mba.get(5).settitle("历史记录");
		mba.get(5).SetSt(clickedColor);
		this.add(mba.get(5));

		// 天气
		Rpanelw = new rectpanel(3, 574, this.width - 7, 280);
		this.add(Rpanelw);
		mbT.add(new Mbutton(9, 578, this.width - 18, 30, false, tittleColor));
		mbT.get(3).seticon("img/Weather.png");
		mbT.get(3).settitle("即时天气");
		this.add(mbT.get(3));

		if (Setting.isConnect) {
			for (int i = weather.info.length - 1; i > 0; i--) {
				sText[i] = new ShowText(9, 594 + 24 * i, this.width - 18, 24 * i + 1, " ",wColor);
				this.add(sText[i]);
				}
				loadStext = new ShowText(9+54, 594+26, this.width - 18, 24  + 1, "正在加载中...",wColor);
				this.add(loadStext);
				UpdateMusic();
		}
		weatherthread.start();
		mba.add(new Mbutton(9, 816, 242, 30, true, btnColor));
		mba.get(6).settitle("更新天气");
		mba.get(6).SetSt(clickedColor);
		this.add(mba.get(6));

//		// 截图工具
		Rpanelw = new rectpanel(3, 856, this.width - 7, 74);
		this.add(Rpanelw);

		mbT.add(new Mbutton(9, 860, 242, 30, false, tittleColor));
		mbT.get(4).seticon("img/cut.png");
		mbT.get(4).settitle("快速截图");
		this.add(mbT.get(4));
		
		mba.add(new Mbutton(9, 894, 242, 30, true, btnColor));
		mba.get(7).settitle("截图");
		mba.get(7).SetSt(clickedColor);
		this.add(mba.get(7));

		mba.add(new Mbutton(9, 1000, 120, 30, true, btnColor));
		mba.get(8).settitle("设置");
		mba.get(8).SetSt(clickedColor);
		this.add(mba.get(8));

		mba.add(new Mbutton(132, 1000, 120, 30, true, btnColor));
		mba.get(9).settitle("退出");
		mba.get(9).SetSt(clickedColor);
		this.add(mba.get(9));
		
		Setting.isloadok = true;
	}

	public void drag() {
		new DropTarget(mbp, DnDConstants.ACTION_COPY_OR_MOVE,
				new DropTargetAdapter() {
					@Override
					public void drop(DropTargetDropEvent dtde) {
						try {
							// 如果拖入的文件格式受支持
							if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
								// 接收拖拽来的数据
								dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
								@SuppressWarnings("unchecked")
								List<File> list = (List<File>) (dtde
										.getTransferable()
										.getTransferData(DataFlavor.javaFileListFlavor));

								for (File file : list) {
									player.getlistThread().append(
											file.getAbsolutePath());
									mbp.settitle(file.getAbsolutePath());
								}
								// 指示拖拽操作已完成
								dtde.dropComplete(true);
							} else {
								// 拒绝拖拽来的数据
								dtde.rejectDrop();
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
	}

	@Override
	public void run() {

		while (true) {
			if (Setting.isConnect) {
				if (weather.complete == true && weather.read == false) {
					loadStext.setVisible(false);
					for (int i = weather.info.length - 1; i > 0; i--) {
						sText[i].settext(weather.info[i]);
					}
					weather.read = false;
				} else {
					loadStext.setVisible(true);
				}
			}

			if (MouseEnt) {
				if (this.getLocation().getX() > (1920 - (width - 5))) {
					this.setLocation((int) (this.getLocation().getX() - 15), 0);
				}
			} else {
				if (this.getLocation().getX() < (1920 - 1)) {
					this.setLocation((int) (this.getLocation().getX() + 15), 0);
				}
			}
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

		if (e.getButton() == MouseEvent.BUTTON3) {
			MouseEnt = false;
		}
		if (e.getButton() == MouseEvent.BUTTON1) {

			if (mba.get(0).PointIn(e.getX(), e.getY())) {
				// 播放/暂停
				player.getlistThread().pause();
			} else if (mba.get(1).PointIn(e.getX(), e.getY())) {
				// 显示频谱/隐藏
				player.setVisible(!player.isVisible());
			} else if (mba.get(2).PointIn(e.getX(), e.getY())) {
				// 更新歌单
				if (Setting.isConnect) {
					UpdateMusic();
				}
			} else if (mba.get(3).PointIn(e.getX(), e.getY())) {
				// 下载歌单
				new Thread(new Runnable() {
					@Override
					public void run() {
						if (Setting.isConnect) {
							new XiamiProcess(2);
						}
					}
				}).start();
			} else if (mba.get(4).PointIn(e.getX(), e.getY())) {
				// 快速翻译呼出窗口
				if (Setting.isConnect) {
					try {
						new TralationGui();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			} else if (mba.get(5).PointIn(e.getX(), e.getY())) {
				// 历史纪录呼出窗口
				new HistoryGui();
			} else if (mba.get(6).PointIn(e.getX(), e.getY())) {
				if (Setting.isConnect) {
					// //更新天气
					weather.ReRead();
				}
			} else if (mba.get(7).PointIn(e.getX(), e.getY())) {

				new ScreenShotTest();
			} else if (mba.get(8).PointIn(e.getX(), e.getY())) {
				new Setting(this);
			} else if (mba.get(9).PointIn(e.getX(), e.getY())) {
				System.exit(0);
			}
		}
	}

	public void UpdateMusic() {
		UpMusicThread Callback = new UpMusicThread();
		Callback.setMbs(mbs);
		Callback.start();
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

		MouseEnt = true;
	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {

		for (int i = 0; i < mba.size(); i++) {
			mba.get(i).PointIn(e.getX(), e.getY());
		}
		for (int i = 0; i < mbs.size(); i++) {
			mbs.get(i).PointIn(e.getX(), e.getY());
		}
	}

	public class UpMusicThread extends Thread {
		String mNames[] = new String[10];
		ArrayList<Mbutton> mbs = new ArrayList<Mbutton>();

		public void setMbs(ArrayList<Mbutton> mbs) {
			this.mbs = mbs;
		}

		@Override
		public void run() {
			// 爬取歌单
			super.run();
			XiamiProcess xProcess = new XiamiProcess(1);
			for (int i = 0; i < 10; i++) {
				mNames[i] = xProcess.Names[i];
				mbs.get(i).settitle(mNames[i]);
			}
		}
	}

}
