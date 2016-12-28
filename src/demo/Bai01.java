package demo;

import java.io.*;
import java.net.*;

// Class trang tram
class Bai01 {
	public static void main(String[] args) throws Exception {
		int portS = 2812;
		InetAddress ipS = InetAddress.getByName("localhost");
		Socket server = new Socket(ipS, portS);
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader br = new BufferedReader(new InputStreamReader(server.getInputStream()));
		PrintWriter pw = new PrintWriter(server.getOutputStream());
		System.out.print("Nhap mot so bat ky: ");
		double so = Double.parseDouble(in.readLine());
		String str = String.valueOf(so);
		pw.write(str + "\n");
		pw.flush();
		String kqS = br.readLine();
		double kqD = Double.parseDouble(kqS);
		System.out.println("Lap phuong so = " + kqD);
		pw.close();
		br.close();
		in.close();
		server.close();
	}
}

// Class Server
class Server01 {
	public static void main(String[] args) throws Exception {
		int port = 2812;
		ServerSocket server = new ServerSocket(port);
		Socket client = server.accept();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
		PrintWriter pw = new PrintWriter(client.getOutputStream());
		String str = br.readLine();
		double so = Double.parseDouble(str);
		double kqD = Math.pow(so, 3);
		String kqS = String.valueOf(kqD);
		pw.write(kqS + "\n");
		pw.flush();
		pw.close();
		br.close();
		client.close();
		server.close();
	}
}