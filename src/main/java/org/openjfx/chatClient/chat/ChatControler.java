package org.openjfx.chatClient.chat;

import org.openjfx.chatClient.socket.SocketClient;
import org.openjfx.chatClient.socket.SocketMsgObserver;
import org.openjfx.chatClient.socket.SocketStatusObserver;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ChatControler implements SocketMsgObserver, SocketStatusObserver {

	SocketClient clientSocket;

	@FXML
	private TextArea textArea;
	@FXML
	private TextField textFieldServerIp;
	@FXML
	private TextField textFieldServerPort;
    @FXML
    private TextField textFieldPseudo;
	@FXML
	private TextField textFieldSend;
	@FXML
	private Button buttonConnect;
	@FXML
	private Button buttonDisconnect;
	@FXML
	private Button buttonSend;


	@FXML
	public void initialize() {
		updateStatus(0);
	}

	/**
	 * on connect button clicked, connect to the server
	 * @param event
	 */
	@FXML
	void connect(ActionEvent event) {
		clientSocket = new SocketClient(textFieldServerIp.getText(), textFieldServerPort.getText(), textFieldPseudo.getText());
		clientSocket.subscribeMsg(this);
		clientSocket.subscribeStatus(this);
		clientSocket.connect();
	}
	
	/**
	 * on disconnect button clicked, close the server ; release the port
	 */
	@FXML
	public void disconnect() {
		if(clientSocket != null) clientSocket.disconnect();
	}

	/**
	 * send msg to the server when enter pressed in textfield
	 * @param event
	 */
	@FXML
	void sendOnEnter(KeyEvent event) {
		if(event.getCode() == KeyCode.ENTER) {
			sendMsg(); 		
		}
	}
	/**
	 * send msg to the server when button send's clicked
	 * @param event
	 */
	@FXML
	void sendOnClick(ActionEvent event) {
		sendMsg(); 		
	}

	/**
	 * send msg to the server
	 */
	void sendMsg() {
		String valueString = textFieldSend.getText();
		clientSocket.sendMsg(valueString);
		textFieldSend.clear();
	}
	
	/**
	 * Manage button status (enable/disable)
	 * 1 = connected
	 * 0 = not connected
	 */
	@Override
	public void updateStatus(int sigValue) {
		if(sigValue == 1) {
			buttonSend.setDisable(false);
			buttonDisconnect.setDisable(false);
			buttonConnect.setDisable(true);			
		}else {
			buttonSend.setDisable(true);
			buttonDisconnect.setDisable(true);
			buttonConnect.setDisable(false);	
		}
	}

	/**
	 *  Add msg to the text area flow
	 */
	@Override
	public void updateMsg(String msg) {
		textArea.appendText(msg);
	}
	
	//-----------Getter-Setter---------

	public TextField getTextFieldServerPort() {
		return textFieldServerPort;
	}

	public void setTextFieldServerPort(TextField textFieldServerPort) {
		this.textFieldServerPort = textFieldServerPort;
	}

	public TextField getTextFieldServerIp() {
		return textFieldServerIp;
	}

	public void setTextFieldServerIp(TextField textFieldServerIp) {
		this.textFieldServerIp = textFieldServerIp;
	}
}

