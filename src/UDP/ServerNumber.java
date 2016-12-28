package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ServerNumber {
	public static void main(String[] args) {
		
		try {
			int port = 2016;
			byte[] data = new byte[1024];
			DatagramSocket socket = new DatagramSocket(port);
			DatagramPacket packet = new DatagramPacket(data, data.length);
			socket.receive(packet);
			String st = new String(packet.getData(), 0, packet.getLength());
			System.out.println("Thong tin nhan duoc tu Client: " + st);
			
			
		} catch (SocketException e) {
			e.printStackTrace();
			System.out.println("Server is not ready...");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Could not receive packet");
		}
		
	}
}
