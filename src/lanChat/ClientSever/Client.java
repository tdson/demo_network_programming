package lanChat.ClientSever;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

import javax.swing.*;
import javax.swing.text.*;

public class Client extends JFrame implements ActionListener {
	private JButton btSend;
	private JPanel p;
	private static JTextArea content;
	private static JTextField input;
	private final static int PORT = 2222;
	private static Socket socket;
	private static String ip = "localhost";
	private static PrintWriter send;
	private static String name = null;
	private static String str = ""; // the content of 2 Text Area
	private static String tmp = "";

	// Constructor
	public Client(String title) {
		super(title);
		content = new JTextArea();
		content.setBackground(Color.decode("#ACEBC7"));
		// Scroll the textArea
		JScrollPane sp = new JScrollPane(content);
		// Scroll JScrollPane to bottom
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

		// Listen events
		btSend.addActionListener(this);
		input.addActionListener(this);

		add(p);
		setSize(520, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(input) || e.getSource().equals(btSend)) {
			try {
				// Create a stream to send message
				send = new PrintWriter(socket.getOutputStream(), true);
				// Send the message attach name
				send.println(name + ": " + input.getText());
				// Update the text just sent into temp variable
				tmp += name + input.getText() + "\n";
				
				// Reset the input textfield
				input.setText(null);
				input.requestFocus();
				
				// display the test on the textarea content
				content.setText(tmp);
				// Update the UI
				content.setVisible(false);
				content.setVisible(true);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		try {
			// Enter the server's ip
			ip = JOptionPane.showInputDialog("Nhập IP máy chủ: ", "localhost");
			do {
				name = JOptionPane.showInputDialog(null,
						"Vui lòng cho biết tên của bạn");
			} while (name == null);
			System.out.println(name + " logged in.");

			new Server("Chào bạn " + name);

			// Create a socket, connects to server
			socket = new Socket(ip, PORT);
			BufferedReader recv = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			
			/*
			// check if there is any text to receive
			while ((str = recv.readLine()) != null) {
				// update the tmp variable
				tmp += str + "\n";
				// display the test on the textarea content
				content.setText(tmp);
				// Update the UI
				content.setVisible(false);
				content.setVisible(true);
			}
			*/
			
			while ((str = recv.readLine()) != null) { // Kiểm tra xem có tin để nhận hay không?
				
				tmp += str + "\n"; // Cộng chuỗi tin chat vào biến tạm
				content.setText(tmp); // Đưa nội dung lên giao diện
				content.setVisible(false); // Cập nhật lại giao diện
				content.setVisible(true); // Cập nhật lại giao diện
			}
			
		} catch (Exception e) {
			System.out.println("Server is now offline.");
			e.printStackTrace();
		}
	}
}
