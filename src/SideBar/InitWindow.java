package SideBar;

import java.io.IOException;

import javax.swing.UIManager;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;


public class InitWindow {

	public static void main(String[] args) throws IOException {
//		BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
//		try {
//			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		UIManager.put("RootPane.setupButtonVisible", false);
//		BeautyEyeLNFHelper.translucencyAtFrameInactive = false;
//		UIManager.put("TabbedPane.tabAreaInsets", new javax.swing.plaf.InsetsUIResource(3, 20, 2, 20));
		
		Loading l=new Loading();
		Setting.initSetting();
		l.lp.exit=true;
		l.dispose();
		
		Mwindow w = new Mwindow(1920 - 1, 0, 260, 1080);
		Thread t = new Thread(w);
		t.start();
	}
}