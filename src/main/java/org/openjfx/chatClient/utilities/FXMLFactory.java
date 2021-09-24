package org.openjfx.chatClient.utilities;

import java.io.IOException;

import org.openjfx.chatClient.Main;
import org.openjfx.chatClient.chat.ChatControler;
import org.openjfx.chatClient.menu.MenuBarControler;
import org.openjfx.chatClient.menu.windows.ClientListControler;
import org.openjfx.chatClient.menu.windows.ServerManagerControler;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FXMLFactory {
	
	ChatControler chatControler;
	
	public FXMLFactory() {}
	
	public ChatControler createChat() throws IOException {
		FXMLLoader loader = loadFXML("Chat");
		Main.root.setCenter(loader.load());
		
		chatControler = loader.getController();
		
		return chatControler;
	}
	
	public void createMenuBar() throws IOException {
		FXMLLoader loader = loadFXML("menu/MenuBar");
		loader.setController(new MenuBarControler());
		Main.root.setTop(loader.load());
	}
	
    public void createServerManagerWindow() throws IOException {
		FXMLLoader loader = loadFXML("menu/windows/ServerManager");
		loader.setController(new ServerManagerControler());
		Parent root = loader.load();
		Scene scene = new Scene(root);
		Stage windowsManagerServer = new Stage();
		
		windowsManagerServer.setScene(scene);
		windowsManagerServer.initModality(Modality.WINDOW_MODAL);
		windowsManagerServer.initOwner(chatControler.getTextFieldServerIp().getScene().getWindow());
		windowsManagerServer.setTitle("Ip Manager");
		
		windowsManagerServer.show();
    }
    
    public void createClientListWindow() throws IOException {
		FXMLLoader loader = loadFXML("menu/windows/ClientList");
		loader.setController(new ClientListControler());
		Parent root = loader.load();
		Scene scene = new Scene(root);
		Stage windowsClientList = new Stage();
		
		windowsClientList.setScene(scene);
		windowsClientList.initModality(Modality.WINDOW_MODAL);
		windowsClientList.initOwner(chatControler.getTextFieldServerIp().getScene().getWindow());
		windowsClientList.setTitle("Ip Manager");
		
		windowsClientList.show();
    }
    
	public void createAboutWindow() {
		Alert popUp = new Alert(AlertType.INFORMATION);
		popUp.setGraphic(null);
		popUp.setResizable(false);
		popUp.setTitle("About");
		popUp.setHeaderText(null);
		popUp.setContentText("Develloped by By Supra (2021/01)");
		popUp.show();
	}
    
    
	public static FXMLLoader loadFXML(String fxml) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
		return fxmlLoader;
	}

	public ChatControler getChatControler() {
		return chatControler;
	}

	
}
