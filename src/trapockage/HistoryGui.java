package trapockage;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class HistoryGui extends JFrame {
	/**
	 * History
	 */
	private static final long serialVersionUID = -7579989860963930980L;
	private JButton CleanBtn = new JButton("�����ʷ��¼");
	private String History;

	public HistoryGui() {
		this.setLayout(null);
		History = (String) TraHistory.readTxt();
		JTextArea ShowHistory = new JTextArea();
		ShowHistory.setLineWrap(true);
		ShowHistory.setEditable(false);
		ShowHistory.setLayout(null);
		ShowHistory.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		JScrollPane inputjsp = new JScrollPane(ShowHistory);
		inputjsp.setBounds(0, 0, 670, 770);
		inputjsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		ShowHistory.setText(History);
		CleanBtn.setBounds(680, 50, 100, 30);
		CleanBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				TraHistory.CleanTxt();
				ShowHistory.setText("�����ʷ��¼�ɹ�,������������Ч��");
			}
		});
		this.add(CleanBtn);
		this.add(inputjsp);
		this.setTitle("��ʷ��¼");
		this.setSize(800, 800);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}
}