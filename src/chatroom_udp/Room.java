package chatroom_udp;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Thanh
 */
public class Room extends JFrame implements Runnable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextArea taMessage, taAllMessage;
    JList<String> listUser=new JList<String>();
    String user;
    int port;
    private DatagramSocket socket;
	private byte[] sendData;
	private byte[] receiveData;
	private DatagramPacket sendPacket;
	private DatagramPacket receivePacket;
	private InetAddress IPAddress;
	
    public Room(String user, String ip, int port){
        super("Chat Room UDP:");
        try {
        	this.user = user;
            this.IPAddress = InetAddress.getByName(ip);
            this.port = port;
            socket = new DatagramSocket();
            createUI();
			sendData = new byte[1024];
			sendData = (this.user+" đã tham gia phòng chat").getBytes();
			//thông báo với bên server rằng tôi đã tham gia phòng
			sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, this.port);
			socket.send(sendPacket);
			//bắt đầu 1 luồng công việc nhận dữ liệu từ server
			new Thread(this).start();
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
    }
    
    public void createUI(){
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel p0= new JPanel();
        JLabel lbTitle = new JLabel("----"+ user+" : "+IPAddress+" ---");
        p0.add(lbTitle);
        add(p0,"North");
        JPanel p1= new JPanel();
        p1.setLayout(new BorderLayout());
        taAllMessage = new JTextArea();
        taAllMessage.setWrapStyleWord(true);
        taAllMessage.setLineWrap(true);
        taAllMessage.setEditable(false);
        JScrollPane spAllMessage = new JScrollPane(taAllMessage);
        spAllMessage.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        p1.add(spAllMessage);
        add(p1);
        JPanel p2= new JPanel();
        p2.setLayout(new BorderLayout());
        taMessage = new JTextArea(3,35);
        taMessage.setWrapStyleWord(true);
        taMessage.setLineWrap(true);
        JScrollPane spMessage = new JScrollPane(taMessage);
        p2.add(spMessage);
        JButton btnSend = new JButton("Send");
        btnSend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {//gửi tin nhắn lên server để server gửi đến các client khác
					sendData = new byte[1024];
					sendData = (user+":"+ taMessage.getText()).getBytes();
					taMessage.setText("");
					sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
					socket.send(sendPacket);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
        p2.add(btnSend, "East");
        add(p2, "South");
        setSize(350, 300);
        setLocation(400, 100);
        setVisible(true);
    }

	@Override
	public void run() {
		try {
			//luôn sẵn sàng nhận tin nhắn từ server gửi về
			while (true) {
				receiveData = new byte[1024];
				receivePacket = new DatagramPacket(receiveData, receiveData.length);
				socket.receive(receivePacket);
				receiveData = receivePacket.getData();
				String message = new String(receiveData).trim();
				taAllMessage.append(message + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
