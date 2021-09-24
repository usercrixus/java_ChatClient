package org.openjfx.chatClient.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import org.openjfx.chatClient.utilities.RMIManager;

import javafx.fxml.FXML;


public class SocketClient implements SocketObservable {

	Socket socketClient;
	BufferedReader in;
	PrintWriter out;
	
	String ip;
	int port;
	String pseudo;
	
	ArrayList<SocketMsgObserver> msgObservers;
	ArrayList<SocketStatusObserver> statusObservers;

	public SocketClient(String ip, String port, String pseudo) {
		msgObservers = new ArrayList<>();
		statusObservers = new ArrayList<>();

		this.ip = ip;
		
		try {
			this.port = Integer.parseInt(port);
			if(this.port < 1024 || this.port > 65535) {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException  e) {
			this.port = 50000;
		}
		
		if(pseudo == "") pseudo = "Anonymous";
		this.pseudo = pseudo;
	}

	public void connect() {

		// start the connection
		try {
			socketClient = new Socket(ip, port);
		} catch (Exception e) {
			notifyMsg("We can't find this server\n");
		}

		try {
			if (socketClient != null && !socketClient.isClosed()) {
				out = new PrintWriter(socketClient.getOutputStream()); //sender flow
				in = new BufferedReader(new InputStreamReader(socketClient.getInputStream())); //receiver flow
				Thread getMessageThread = new Thread( ()->{
					getMsg();
				});
				getMessageThread.start();
				sendMsg(this.pseudo);
				
				RMIManager.createRMI(ip);
				
				notifyStatus(1);
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Close the server ; release port
	 * @throws IOException
	 */
	@FXML
	public void disconnect() {
		if(socketClient != null && !socketClient.isClosed()) {
			try {
				socketClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * display msg from the server
	 */
	void getMsg() {
		try {
			String msg = in.readLine();
			while(msg!=null && !socketClient.isClosed()){
				notifyMsg(msg+"\n");
				msg = in.readLine();
			}
			// if server disconnect
			notifyMsg("Server lost...\n");
		} catch (IOException e) {
			// if client disconnect
			notifyMsg("You are disconnected...\n");
		}
		socketClient = null;
		notifyStatus(0);
	}

	/**
	 * send msg to the server
	 */
	public void sendMsg(String msg) {
		out.println(msg);
		out.flush();    		
	}

	@Override
	public void subscribeStatus(SocketStatusObserver observer) {
		statusObservers.add(observer);
	}

	@Override
	public void unsubscribeStatus(SocketStatusObserver observer) {
		statusObservers.remove(observer);
	}

	@Override
	public void subscribeMsg(SocketMsgObserver observer) {
		msgObservers.add(observer);
	}

	@Override
	public void unsubscribeMsg(SocketMsgObserver observer) {
		msgObservers.remove(observer);
	}

	@Override
	public void notifyStatus(int sigValue) {
		for (SocketStatusObserver socketStatusObserver : statusObservers) {
			socketStatusObserver.updateStatus(sigValue);
		}
	}

	@Override
	public void notifyMsg(String msg) {
		for (SocketMsgObserver socketMsgObserver : msgObservers) {
			socketMsgObserver.updateMsg(msg);
		}
	}


}
