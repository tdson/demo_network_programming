package chatroom_udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Enumeration;
import java.util.Hashtable;

public class ConnectionThread extends Thread{
	DatagramSocket server;
	int connectionNumber;//tổng số client kết nối đến server
	DatagramPacket sendPacket;
	DatagramPacket receivePacket;
	byte[] sendData;
	byte[] receiveData;
	private static Hashtable<Integer,InetAddress> allConnections = new Hashtable<Integer,InetAddress>();
	InetAddress address;
	int port;
	public ConnectionThread(DatagramSocket server,DatagramPacket receivePacket) {
		this.connectionNumber = allConnections.size();
		this.server= server;
		this.receivePacket = receivePacket;
	}
	public void run(){
		try {
			receiveData = new byte[1024];
			receiveData = receivePacket.getData();
			String message = new String(receiveData).trim();
			address = receivePacket.getAddress();
			port = receivePacket.getPort();
			//kiểm tra xem cổng port của gói tin, tức là client đã kết nối trước đây chưa
			//nếu chưa thì thêm vào danh sách
			//danh sách này, để server gửi tin nhắn đến từng client
			if(!allConnections.containsKey(port)){
				allConnections.put(port,address);
			}
			messageAllSockets(message);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//gửi tin nhắn đến tất cả client có kết nối đến server
	public void messageAllSockets(String message) throws IOException{
		//đồng bộ hóa để khi đang gửi tin nhắn này đi, thì tin nhắn sau ko được
		//chen ngang vào quá trình này
		synchronized (allConnections) {
			Enumeration<Integer> allPort;
			allPort = allConnections.keys();
			//duyệt hết danh sách tất cả các port
			//gửi tin nhắn đến từng client với port và address trogn danh sách
			while(allPort.hasMoreElements()){
				port = (Integer) allPort.nextElement();
				address = allConnections.get(port);
				sendData = new byte[1024];
				sendData = (message).getBytes();
				sendPacket = new DatagramPacket(sendData, sendData.length, address, port);
				server.send(sendPacket);
			}
		}
	}
}