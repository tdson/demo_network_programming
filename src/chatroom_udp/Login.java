package chatroom_udp;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Login extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextField tfUser;
	public Login(String title) {
		super(title);
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		JPanel p4 = new JPanel();
		//JPanel p5 = new JPanel();
		JLabel lbTitle = new JLabel("Chat Room UDP");
		lbTitle.setFont(new Font(lbTitle.getName(), Font.PLAIN, 40));
		JLabel lbUser = new JLabel("User");
		tfUser = new JTextField(20);
		JButton btnLogin = new JButton("Login");
		JButton btnReset = new JButton("Reset");
		JButton btnExit = new JButton("Exit");
		btnLogin.addActionListener(this);
		btnReset.addActionListener(this);
		btnExit.addActionListener(this);
		//JLabel lbAuthor = new JLabel("Author: Ngô Viết Thành");
		
		p1.add(lbTitle);
		p2.add(lbUser);
		p2.add(tfUser);
		p4.add(btnLogin);
		p4.add(btnReset);
		p4.add(btnExit);
		//p5.add(lbAuthor);
		add(p1);
		add(p2);
		add(p3);
		add(p4);
		//add(p5);
		setSize(350, 300);
		setLocation(200, 50);
		setLayout(new GridLayout(5, 1));
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String btn=e.getActionCommand();
		if(btn.equals("Login")){
			String user = tfUser.getText();
			String ip = JOptionPane.showInputDialog("Nhập vào IP máy chủ", "localhost");
			int port = Integer.parseInt(JOptionPane.showInputDialog("Nhập vào port máy chủ", "1221"));
			new Room(user,ip,port);
			dispose();
		}
		if(btn.equals("Reset")){
			tfUser.setText("");
		}
		if(btn.equals("Exit")){
			System.exit(0);
		}
	}
	public static void main(String[] args) {
		new Login("Đăng nhập");
	}
}