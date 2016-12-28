package lanChat.ClientSever;

import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;

public class Windows extends JFrame {
	private JButton btSend;
	private JTextArea content;
	private JTextField input;
	private JPanel p;

	public Windows(String title) {
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
	
	public JTextArea getContent() {
		return content;
	}

	public void setContent(JTextArea content) {
		this.content = content;
	}

	public JTextField getInput() {
		return input;
	}

	public void setInput(JTextField input) {
		this.input = input;
	}

	public void setWindowsTitle(String name) {
		this.setTitle(name);
	}
	
	public static void main(String[] args) {
		new Windows("Sơn Trần");
	}
}
