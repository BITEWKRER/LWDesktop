package SideBar;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
//import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import Weather.ToPinyin;

public class Setting extends JFrame {
	/**
	 * setting
	 */
	private static final long serialVersionUID = -5023253596732373497L;
	public static int lists = 0; // 0新歌榜 1 热歌榜 2 原创榜 3电音榜
	public static int pattren = 0; // 样式
	public static String Hlocation = "chengdu";
	public static Boolean auto_start = false; // 默认关闭
	private static int start = 0;
	private static Font font = new Font("微软雅黑", Font.PLAIN, 18);
	public static boolean isConnect = false;
	public static boolean isloadok = false;
	private JComboBox<String> jpcomboBox = new JComboBox<String>(); // 样式下拉
	private JComboBox<String> jLcomboBox = new JComboBox<String>(); // 榜单下拉
	private Mwindow mwindow;

	public static void initSetting() {

		try {
			isConnect = NetworkState.isConnect();
			pattren = Integer.parseInt(Configs.readConfig().substring(0, 1));
			lists = Integer.parseInt(Configs.readConfig().substring(2, 3));
			start = Integer.parseInt(Configs.readConfig().substring(4, 5));
			if (Configs.readConfig().substring(6).length() > 1) {
				Hlocation = Configs.readConfig().substring(6);
			}

		} catch (Exception e) {
			System.out.println("无配置文件！");
		}
	}

	public Setting(Mwindow w) {

		mwindow = w;
		this.setLayout(null);
		JLabel plable = new JLabel("样式选择：");
		plable.setFont(font);
		plable.setBounds(30, 20, 100, 30);
		this.add(plable);

		jpcomboBox.setBounds(130, 20, 100, 30);
		jpcomboBox.addItem("一");
		jpcomboBox.addItem("二");
		jpcomboBox.addItem("三");
		jpcomboBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getItem() == "一") {
					pattren = 0;
				} else if (e.getItem() == "二") {
					pattren = 1;
				} else if (e.getItem() == "三") {
					pattren = 2;
				}
			}
		});
		jpcomboBox.setSelectedIndex(pattren);

		JLabel listLable = new JLabel("榜单选择：");
		listLable.setFont(font);
		listLable.setBounds(30, 60, 100, 30);
		this.add(listLable);

		jLcomboBox.setBounds(130, 60, 100, 30);
		jLcomboBox.addItem("新歌榜");
		jLcomboBox.addItem("热歌榜");
		jLcomboBox.addItem("电音榜");
		jLcomboBox.addItem("歌单收录榜");
		jLcomboBox.addItem("抖音热歌榜");
		jLcomboBox.addItem("影视原声榜");
		jLcomboBox.addItem("分享榜");
		this.add(jLcomboBox);
		jLcomboBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getItem() == "新歌榜") {
					lists = 0;
				} else if (e.getItem() == "热歌榜") {
					lists = 1;
				} else if (e.getItem() == "电音榜") {
					lists = 2;
				} else if (e.getItem() == "歌单收录榜") {
					lists = 3;
				} else if (e.getItem() == "抖音热歌榜") {
					lists = 4;
				} else if (e.getItem() == "影视原声榜") {
					lists = 5;
				} else if (e.getItem() == "分享榜") {
					lists = 6;
				}
			}
		});
		jLcomboBox.setSelectedIndex(lists);

		JLabel loLabel = new JLabel("天气地区:");
		loLabel.setFont(font);
		loLabel.setBounds(30, 100, 100, 30);
		this.add(loLabel);
		JTextArea loJTextArea = new JTextArea();
		loJTextArea.setFont(font);
		loJTextArea.setLineWrap(true);
		loJTextArea.setLayout(null);
		loJTextArea.setBounds(130, 100, 100, 30);
		this.add(loJTextArea);

		// JCheckBox sBox = new JCheckBox("开机自启");
		// sBox.setBounds(125, 140, 200, 30);
		// sBox.setFont(font);
		// this.add(sBox);
		// sBox.setSelected(Setting.auto_start);

		JLabel hint = new JLabel();
		hint.setFont(font);
		hint.setBounds(20, 360, 250, 30);
		this.add(hint);
		JButton confirm = new JButton("确认");
		confirm.setBounds(30, 140, 100, 30);
		confirm.setFont(font);
		confirm.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// if (sBox.isSelected()) {
				// Setting.auto_start = true;
				// start = 1;
				// }
				mwindow.player.setviewmodel(pattren);
				if (loJTextArea.getText().length() > 1) {
					Hlocation = loJTextArea.getText();
					Hlocation = ToPinyin.toPinyin(Hlocation);

				}
				Configs.writeConfig(String.valueOf(pattren + "," + lists + "," + start + "," + Hlocation));
				dispose();
			}
		});
		this.add(confirm);

		JButton Cancel = new JButton("取消");
		Cancel.setBounds(140, 140, 100, 30);
		Cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		this.add(Cancel);
		this.add(jpcomboBox);
		this.setTitle("设置");
		this.setSize(300, 240);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setVisible(true);
	}
}
