package demo;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.*;

public class TCPClientString extends JFrame {

	// Các thành phần trên frame
	private JButton btSend;
	private JTextField tfInput;

	public TCPClientString() {
		tfInput = new JTextField(24);
		btSend = new JButton("Send");
		btSend.addActionListener(new ActionListener() {
			// Gửi dữ liệu lên server
			@Override
			public void actionPerformed(ActionEvent e) {
				// Action Performed here
				send();
			}
		});

		// Xử lý sự kiện nhấn Enter
		tfInput.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				// System.out.println(e.getKeyCode());
				if (e.getKeyCode() == 10) {
					send();
				}
			}
		});

		add(tfInput, BorderLayout.WEST);
		add(btSend, BorderLayout.EAST);
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

	}

	public void send() {
		try {
			Socket socket = new Socket("localhost", 8811);
			// while(true) {
			DataInputStream in = new DataInputStream(socket.getInputStream());
			DataOutputStream out = new DataOutputStream(
					socket.getOutputStream());
			out.writeUTF(tfInput.getText());
			tfInput.setText(in.readUTF());
			in.close();
			out.close();
			socket.close();
			// }

		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		new TCPClientString();

		// Socket s = new Socket("localhost", 8811);
		// DataInputStream in = new DataInputStream(s.getInputStream());
		// DataOutputStream out = new DataOutputStream(s.getOutputStream());
		// out.writeUTF("Chuoi ThO");
		// System.out.println(in.readUTF());
	}
}
