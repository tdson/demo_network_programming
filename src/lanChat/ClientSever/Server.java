package lanChat.ClientSever;

import java.awt.Color;

import javax.swing.*;
import javax.swing.text.DefaultCaret;

public class Server extends JFrame {
	private JButton btSend;
	private JTextArea content;
	private JTextField input;
	private JPanel p;

	public Server(String title) {
		super(title);
		content = new JTextArea();
		content.setBackground(Color.decode("#ACEBC7"));
		JScrollPane sp = new JScrollPane(content);
		DefaultCaret caret = (DefaultCaret) content.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		content.setEditable(false);
		content.setBounds(5, 10, 493, 305);

		btSend = new JButton("Gửi");
		btSend.setBounds(432, 325, 65, 30);
		input = new JTextField();
		input.setBounds(5, 325, 420, 30);
		p = new JPanel();
		p.setLayout(null);
		p.add(content);
		p.add(input);
		p.add(btSend);

		add(p);
		setSize(520, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	}

	public static void main(String[] args) {
		try {
			// Nhận ip máy chủ
			String ip = JOptionPane.showInputDialog("Nhập IP máy chủ: ",
					"localhost");
			String name = null;
			do {
				name = JOptionPane.showInputDialog(null,
						"Vui lòng cho biết tên của bạn");
				System.out.println(name);
			} while (name == null);
			System.out.println(name);

			new Server("Chào bạn " + name);
			
		} catch (Exception e) {
			System.out.println("Không tìm thấy máy chủ!");
			e.printStackTrace();
		}
	}
}
