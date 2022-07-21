package com.aaa;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class JavaSocketClient {

	public static void main(String[] args) {
		
		String host = "localhost";
		int port = 7001;
		try {
			Socket socket = new Socket(host, port);
			System.out.println("서버에 연결함 : " + host +", " + port);
			
			String output = "안녕!";
			ObjectOutputStream outstream = new ObjectOutputStream(socket.getOutputStream());
			outstream.writeObject(output);
			outstream.flush();
			System.out.println("서버로 보냄: " + output);
			
			ObjectInputStream instream = new ObjectInputStream(socket.getInputStream());
			String input = (String) instream.readObject();
			System.out.println("서버로부터 받은 데이터: " + input);
			
		} catch (Exception e) {
			e.printStackTrace(); // TODO: handle exception
		}

	}

}
