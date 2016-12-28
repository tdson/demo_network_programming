package chatClientServer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

public class Client extends JFrame implements ActionListener {

	// content of the conversation
	static JTextArea content;
	// String of content
	static String str = "";
	// temp string to display on text area
	static String temp = "";
	static JButton btSend;
	static JTextField input;
	static String name;
	static Socket socket;
	static PrintWriter send;

	public static void main(String[] args) {

		try {
			// Enter the server's ip
			String ip = JOptionPane.showInputDialog("Nhập IP máy chủ: ",
					"localhost");
			do {
				name = JOptionPane.showInputDialog(null,
						"Vui lòng cho biết tên của bạn");
			} while (name == null);
			System.out.println(name + " logged in.");

			new Client("Chào bạn " + name);
			socket = new Socket(ip, 2222);
			BufferedReader recv = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			// check if there is any message, then receive it
			while ((str = recv.readLine()) != null) {
				// add the message to temp variable
				temp += str + "\n";
				// Put the message to text area
				content.setText(temp);
				// update the text area
				content.setVisible(false);
				content.setVisible(true);
			}
		} catch (Exception e) {
			System.out.println("Server is now offline. You cannot send the message.");
			// e.printStackTrace();
		}
	}

	public Client(String title) {
		super(title);

		Font f = new Font("Tahoma", Font.BOLD, 18);

		content = new JTextArea();
		content.setFont(f);
		content.setBackground(Color.decode("#ACEBC7"));
		content.setEditable(false);

		// Scroll the textArea
		JScrollPane sp = new JScrollPane(content);
		// Scroll JScrollPane to bottom
		DefaultCaret caret = (DefaultCaret) content.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		btSend = new JButton("Gửi");
		input = new JTextField(30);
		// input.setFont(f);

		add(sp, BorderLayout.CENTER);
		add(input, BorderLayout.PAGE_END);

		input.addActionListener(this);

		setSize(500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(input)) {
			try {
				send = new PrintWriter(socket.getOutputStream(), true);
				// Send the message attach nickname
				send.println(name + ": " + input.getText());
				// Add message string to temp variable
				temp += name + ": " + input.getText() + "\n";
				// reset the input text field
				input.setText("");
				// Focus on the input text field
				input.requestFocus();
				// Put the message to the text area
				content.setText(temp);
				// Update the UI
				content.setVisible(false);
				content.setVisible(true);

			} catch (Exception E) {
				System.out.println("Server is now offline. You cannot send the message.");
				// E.printStackTrace();
			}
		}

	}

}