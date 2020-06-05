package com.kqds.util.sys.export;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class Swing extends JPanel {

	private static final String LABEL_KAIPIAO = "转开票系统";
	private static final String LABEL_KQDS = "转真实系统";
	private static final String LABEL_CQ = "重启系统";

	private Action actionKaipiao;
	private Action actionKqds;
	private Action actionCq;

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception e) {
					e.printStackTrace();
				}
				JFrame test = new JFrame("系统设置");
				test.setContentPane(new Swing());
				test.setSize(600, 300);
				// test.pack();
				test.setResizable(false);
				test.setLocationRelativeTo(null);
				test.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				test.setVisible(true);
			}
		});
	}

	Swing() {

		super(new BorderLayout(5, 5));
		assert SwingUtilities.isEventDispatchThread();

		actionKaipiao = new AbstractAction(LABEL_KAIPIAO) {
			@Override
			public void actionPerformed(ActionEvent e) {
				String strcmd = "cmd /c start  C:\\tkp.bat";
				run_cmd(strcmd);
			}
		};

		actionKqds = new AbstractAction(LABEL_KQDS) {
			@Override
			public void actionPerformed(ActionEvent e) {
				String strcmd = "cmd /c start  C:\\zreal.bat";
				run_cmd(strcmd);
			}
		};
		actionCq = new AbstractAction(LABEL_CQ) {
			@Override
			public void actionPerformed(ActionEvent e) {
				String strcmd = "cmd /c start  C:\\cq.bat";
				run_cmd(strcmd);
			}
		};
		add(layoutControl());
		setAlignmentY(CENTER_ALIGNMENT);
		setAlignmentX(CENTER_ALIGNMENT);
		setBorder(BorderFactory.createEmptyBorder(100, 5, 5, 5));
	}

	private JComponent layoutControl() {
		JComponent result = new JPanel();
		JButton jb1 = new JButton(actionKaipiao);
		jb1.setPreferredSize(new Dimension(150, 50));
		result.add(jb1);

		JButton jb2 = new JButton(actionKqds);
		jb2.setPreferredSize(new Dimension(150, 50));
		result.add(jb2);

		JButton jb3 = new JButton(actionCq);
		jb3.setPreferredSize(new Dimension(150, 50));
		result.add(jb3);
		result.setAlignmentY(CENTER_ALIGNMENT);
		result.setAlignmentX(CENTER_ALIGNMENT);
		return result;
	}

	public void run_cmd(String strcmd) {
		Runtime rt = Runtime.getRuntime(); // Runtime.getRuntime()杩斿洖褰撳墠搴旂敤绋嬪簭鐨凴untime瀵硅薄
		Process ps = null; // Process鍙互鎺у埗璇ュ瓙杩涚▼鐨勬墽琛屾垨鑾峰彇璇ュ瓙杩涚▼鐨勪俊鎭�
		try {
			ps = rt.exec(strcmd); // 璇ュ璞＄殑exec()鏂规硶鎸囩ずJava铏氭嫙鏈哄垱寤轰竴涓瓙杩涚▼鎵ц鎸囧畾鐨勫彲鎵ц绋嬪簭锛屽苟杩斿洖涓庤瀛愯繘绋嬪搴旂殑Process瀵硅薄瀹炰緥銆�
			ps.waitFor(); // 绛夊緟瀛愯繘绋嬪畬鎴愬啀寰�笅鎵ц銆�
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int i = ps.exitValue(); // 鎺ユ敹鎵ц瀹屾瘯鐨勮繑鍥炲�
		if (i == 0) {
			// System.out.println("鎵ц瀹屾垚");
			// JOptionPane.showMessageDialog(null, "鎵ц瀹屾垚", "淇℃伅鎻愮ず",
			// JOptionPane.PLAIN_MESSAGE);
		} else {
			// System.out.println("鎵ц澶辫触");
			// JOptionPane.showMessageDialog(null, "鎵ц澶辫触", "淇℃伅鎻愮ず",
			// JOptionPane.PLAIN_MESSAGE);
		}

		ps.destroy(); // 閿�瘉瀛愯繘绋�
		ps = null;
	}
}
