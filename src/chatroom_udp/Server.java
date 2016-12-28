package chatroom_udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import javax.swing.JOptionPane;


public class Server{
	DatagramSocket server;
	DatagramPacket receivePacket;
	byte[] receiveData;
	public Server(int port) {
		listen(port);
	}
	public void listen(int port){
		try {
			System.out.println(port);
			server = new DatagramSocket(port);
			while(true){
				receiveData = new byte[1024];
				receivePacket = new DatagramPacket(receiveData, receiveData.length);
				server.receive(receivePacket);
				ConnectionThread connectionThread = new ConnectionThread(server,receivePacket);
				connectionThread.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		int port = Integer.parseInt(JOptionPane.showInputDialog("Nhập port máy chủ: ","1221"));
		new Server(port);
	}
}