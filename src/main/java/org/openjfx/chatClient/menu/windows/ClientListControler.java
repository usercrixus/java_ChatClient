package org.openjfx.chatClient.menu.windows;

import java.rmi.RemoteException;
import java.util.ArrayList;
import org.openjfx.chat.remote.RemoteOptions;
import org.openjfx.chat.remote.clientDataRemote;
import org.openjfx.chatClient.utilities.RMIManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ClientListControler {

	@FXML
	private VBox clientListVBox;

	public ClientListControler() {}

	@FXML
	void initialize() {
		RemoteOptions rmiEndPointOptions = (RemoteOptions) RMIManager.rmi.getRemoteObject("RMIEndPoint");
		try {
			ArrayList<clientDataRemote> clientList = rmiEndPointOptions.getClient();
			if(clientList.size() > 0) {
				clientListVBox.getChildren().clear();
				for (clientDataRemote clientDataRemote : clientList) {
					clientListVBox.getChildren().add(new Label(clientDataRemote.getIp()+" "+clientDataRemote.getPseudo()));
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		clientListVBox.getScene().getWindow().hide();
	}

}
