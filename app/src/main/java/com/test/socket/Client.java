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
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client {
	private final String localhost = "127.0.0.1";
	private final int tcpport = 5678;
	private final int udpport = 5679;
	
	
	public static void main(String args[]){
		Client client = new Client();
		//client.tcpClient();
		client.udpClient();
	}
	
	private void tcpClient(){
		Socket socket = null;
		BufferedReader consle = null,stringInput = null;
		InputStream inputStream = null;
		OutputStream outputStream = null;
		PrintWriter stringWriter = null;
		try {
			socket = new Socket(localhost, tcpport);
			if(socket.isConnected()){
				inputStream = socket.getInputStream();
				outputStream = socket.getOutputStream();
				consle = new BufferedReader(new InputStreamReader(System.in));
				stringInput = new BufferedReader(new InputStreamReader(inputStream));
				stringWriter = new PrintWriter(socket.getOutputStream());
				String clientMsg = null;
				while(true){
					clientMsg = consle.readLine();
					if(null != clientMsg) {
						//stringWriter.println(clientMsg);
						//stringWriter.flush();
						//System.out.println("rsp=" + stringInput.readLine());
						outputStream.write(clientMsg.getBytes());
						outputStream.flush();
						byte b[] = new byte[1024];
						inputStream.read(b);
						System.out.println("rsp=" + new String(b));
						if(clientMsg.equals("bye")) {
							break;
						}
						clientMsg = null;
					}
				}
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(null != consle)
			consle.close();
			if(null != inputStream)
			inputStream.close();
			if(null != outputStream)
			outputStream.close();
			if(null != stringInput)
			stringInput.close();
			if(null != stringWriter)
			stringWriter.close();
			if(null != socket)
			socket.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void udpClient(){
		DatagramSocket datagramSocket = null;
		DatagramPacket datagramPacket = null;
		BufferedReader consle = null;
		String clientMsg = null;
		try {
			datagramSocket = new DatagramSocket();
			consle = new BufferedReader(new InputStreamReader(System.in));
			while(true) {
				clientMsg = consle.readLine();
				if(null != clientMsg) {
					byte req[] = clientMsg.getBytes();
					datagramPacket = new DatagramPacket(req, req.length, 
							InetAddress.getByName(localhost), udpport);
					datagramSocket.send(datagramPacket);
					byte rsp[] = new byte[1024];
					datagramPacket = new DatagramPacket(rsp, rsp.length);
					datagramSocket.receive(datagramPacket);
					System.out.println("rsp=" + new String(rsp));
					if(clientMsg.equals("bye")) {
						break;
					}
					clientMsg = null;
				}
			}
		}catch(Exception e) {
			
		}
		try {
			if(null != datagramSocket)
				datagramSocket.close();
		}catch(Exception e) {}
	}
}
