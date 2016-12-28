package UDP;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ClientNumber {
	public static void main(String[] args) {
		try {
			DatagramSocket socket = new DatagramSocket();
			DatagramPacket packet;
			// Mảng byte để chứa dữ liệu sau khi nhập
			byte[] data;
			InetAddress ipS = InetAddress.getByName("localhost");
			int portS = 2016;
			System.out.println("Client is ready...");
			// Nhập số nguyên
			BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Nhap mot so nguyen: ");
			data = keyboard.readLine().getBytes();
			// Đưa dữ liệu vào packet
			packet = new DatagramPacket(data, data.length, ipS, portS);
			// Đẩy dữ liệu lên Server.
			socket.send(packet);
			System.out.println("All data were sent to client.");
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
