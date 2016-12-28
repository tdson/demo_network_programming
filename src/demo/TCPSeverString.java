package demo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

public class TCPSeverString {

	public static void main(String[] args) throws Exception {
		// String str = "Mot nGay DeP TRoi";
		// System.out.println(convertStr(str));

		// Tạo server socket để nhận lại socket từ Client
		ServerSocket server = new ServerSocket(8811);
		// Thông báo Server sẵng sàng.
		System.out.println("Server is started!");

		// Lắng nghe yêu cầu từ Client qua cổng 8811
		while (true) {
			// Tạo luồng con để chạy cho mỗi client.
			new ThreadSocket(server.accept()).start();
		}
	}
}

/*
 * Mỗi đối tượng được tạo ra từ ThreadSocket có nhiệm vụ xử lý yêu cầu cho mỗi
 * Client tương ứng.
 */
class ThreadSocket extends Thread {
	Socket socket = null;

	// Hàm khởi tạo.
	public ThreadSocket(Socket server) {
		this.socket = server;
	}

	// Phương thức run() được gọi tự động khi phương thức start() được gọi
	// Sever thực hiện yêu cầu từ Client
	@Override
	public void run() {
		// Tạo 2 luồng nhập - xuất
		try {
			DataOutputStream out = new DataOutputStream(
					socket.getOutputStream());
			DataInputStream in = new DataInputStream(socket.getInputStream());
			
			
			while(true) {
				String orgStr = in.readUTF();
				// System.out.println(orgStr + "\n" + convertStr(orgStr));
				if(orgStr.equals("exit"))
					break;
				// Xử lý chuỗi và đưa kết quả ra luồng xuất để đẩy về Client
				out.writeUTF(convertStr(orgStr));
			}
			in.close();
			out.close();
			socket.close();
			
		} catch (IOException e) {
			System.out.println("Exception: " + e.getMessage());
		}
	}

	// Phương thức chuyển chữ hoa thành chữ thường và ngược lại.
	private static String convertStr(String orgStr) {
		char c = 0;
		String result = "";
		for (int i = 0; i < orgStr.length(); i++) {
			c = orgStr.charAt(i);
			if (c >= 'a' && c <= 'z') {
				c = (char) (orgStr.charAt(i) - 32);
			} else if (c >= 'A' && c <= 'Z') {
				c = (char) (orgStr.charAt(i) + 32);
			}
			result += String.valueOf(c);
		}
		return result;
	}
}