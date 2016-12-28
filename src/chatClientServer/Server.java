package chatClientServer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import javax.swing.text.AbstractDocument.Content;

public class Server extends JFrame implements ActionListener {

	static JTextArea content; // content of the conversation
	static JButton btSend;
	static JTextField input;
	static String name;
	static String str = ""; // String of content
	static String temp = ""; // temp string to display on text area
	static ServerSocket server;
	// Mảng gồm các socket các client đã kết nối với server
	static PrintWriter send;
	static Socket socket;
	static ArrayList<Socket> al = new ArrayList<Socket>();

	public static void main(String[] args) throws IOException {

		do {
			name = JOptionPane.showInputDialog(null,
					"Vui lòng cho biết tên của bạn");
		} while (name == null);

		Server s = new Server("Chào bạn " + name);
		System.out.println("Server is ready...");
		System.out.println(name + " logged in.");
		
		server = new ServerSocket(2222);

		while (true) {
			try {
				ServerThread st = new ServerThread(server.accept(), s);
				socket = st.getSocket();
				al.add(socket);
				st.start();
	
				/*
				while (true) {
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
				}
				*/
	
			} catch (Exception e) {
				System.out
						.println("Client is now offline. You cannot send the message.");
				// e.printStackTrace();
			}
		}
	}

	public Server(String title) {
		super(title);

		Font f = new Font("Tahoma", Font.BOLD, 18);

		content = new JTextArea();
		content.setFont(f);
		content.setBackground(Color.decode("#2980B9"));
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
				String text = input.getText();
				
				for(int i=0; i<al.size(); i++) {
					
					send = new PrintWriter(al.get(i).getOutputStream(), true);
					// Send the message attach nickname
					send.println(name + ": " + text);
					// Add message string to temp variable
					temp += name + ": " + text + "\n";
					// reset the input text field
					input.setText("");
					// Focus on the input text field
					input.requestFocus();
					// Put the message to the text area
					content.setText(temp);
					// Update the UI
					content.setVisible(false);
					content.setVisible(true);
				}

			} catch (Exception E) {
				System.out
						.println("Client is now offline. You cannot send the message.");
				// E.printStackTrace();
			}
		}
	}

	public void putOnMessage(String str) {
		temp += str;
		content.setText(str);
		// update the text area
		content.setVisible(false);
		content.setVisible(true);
	}
}