package com.aaa;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class JavaSocketServer {
	
	
	public static void main(String[] args) {
		try {
			int port = 7001;
			ServerSocket server = new ServerSocket(port);
			System.out.println("서버 실행됨: " + port);
			
			while(true) {
				Socket socket = server.accept();
				
				ObjectInputStream instream = new ObjectInputStream(socket.getInputStream());
				String input = (String) instream.readObject();
				System.out.println("클라이언트로부터 받은 데이터 : " + input);
				
				ObjectOutputStream outstream = new ObjectOutputStream(socket.getOutputStream());
				outstream.writeObject(input + " from server.");
				outstream.flush();
				System.out.println("클라이언트로 보냈음.");
				
				instream.close();
				outstream.close();
				socket.close();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
