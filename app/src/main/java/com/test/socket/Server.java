package com.test.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {
	private final int tcpport = 5678;
	private final int udpport = 5679;
	ServerSocket tcpServerSocket = null;
	/*
	 * BufferedReader tcpInput = null; PrintWriter tcpOutput = null; String tcpMsg =
	 * null;
	 */
	//LinkedBlockingQueue socketQueue;
	
	public static void main(String args[]){
		Server server = new Server();
		//server.tcpServer();
		server.udpServer();
	}
	
	private void tcpServer(){
		//socketQueue = new LinkedBlockingQueue<Socket>();
		try {
			tcpServerSocket = new ServerSocket(tcpport);
			while(true) {
				Socket tcpSocket = tcpServerSocket.accept();
				System.out.println("connected from" + tcpSocket.getPort());
				startTalk(tcpSocket);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void startTalk(final Socket tcpSocket) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				BufferedReader stringInput = null;
				PrintWriter stringWriter = null;
				InputStream inputStream = null;
				OutputStream outputStream = null;
				String tcpMsg = null;
				try {
					inputStream = tcpSocket.getInputStream();
					outputStream = tcpSocket.getOutputStream();
					stringInput = new BufferedReader(new InputStreamReader(inputStream));
					stringWriter = new PrintWriter(tcpSocket.getOutputStream());
					while(true) {
						//tcpMsg = stringInput.readLine();
						byte b[] = new byte[1024];
						inputStream.read(b);
						tcpMsg = new String(b);
						if(null != tcpMsg) {
							System.out.println(tcpMsg);
							//stringWriter.println("reveived");
							//stringWriter.flush();
							outputStream.write("received".getBytes());
							outputStream.flush();
							if(tcpMsg.equals("bye")) {
								break;
							}
							tcpMsg = null;
						}
					}
					}catch(Exception e) {}
					try {
						if(null != inputStream)
						inputStream.close();
						if(null != outputStream)
						outputStream.close();
						if(null != stringInput)
						stringInput.close();
						if(null != stringWriter)
						stringWriter.close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			
		}).start();
	}
	
	private void udpServer(){
		DatagramSocket datagramSocket = null;
		DatagramPacket datagramPacket = null;
		DatagramPacket rspDatagramPacket = null;
		try {
			datagramSocket = new DatagramSocket(udpport);
			while(true) {
				System.out.println("preparing");
				byte req[] = new byte[1024];
				datagramPacket = new DatagramPacket(req, req.length);
				datagramSocket.receive(datagramPacket);
				System.out.println("req = " + new String(req));
				byte rsp[] = "received".getBytes();
				rspDatagramPacket = new DatagramPacket(rsp, rsp.length, 
						datagramPacket.getAddress(), datagramPacket.getPort());
				datagramSocket.send(rspDatagramPacket);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(null != datagramSocket)
				datagramSocket.close();
		}catch(Exception e) {
		
	}
	}
}
