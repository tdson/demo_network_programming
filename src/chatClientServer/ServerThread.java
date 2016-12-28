package chatClientServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerThread extends Thread {
	private Socket socket;
	private Server s;
	private static String str = "";
	private static String temp = "";
	private static String content;

	public ServerThread(Socket socket, Server s) {
		this.socket = socket;
		this.s = s;
	}

	@Override
	public void run() {
		while (true) {
			BufferedReader recv;
			try {
				recv = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));

				// check if there is any message, then receive it
				while ((str = recv.readLine()) != null) {
					// add the message to temp variable
					temp += str + "\n";
					// Put the message to text area
					s.putOnMessage(temp);
				}
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
	}

	public void sendMesage() {
		
	}
	
	public Socket getSocket() {
		return socket;
	}
}
