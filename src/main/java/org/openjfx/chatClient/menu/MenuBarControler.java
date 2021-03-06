package org.openjfx.chatClient.menu;


import java.io.IOException;
import org.openjfx.chatClient.Main;
import javafx.fxml.FXML;

public class MenuBarControler {

	public MenuBarControler(){ }

	@FXML
	public void initialize() {
		// void
	}

	/**
	 * Manage server list ip/port. Work with ipManager.fxml and ipManager.java
	 * @throws IOException
	 */
	@FXML
	public void manageServer() throws IOException {
		Main.booter.createServerManagerWindow();
	}


	@FXML
	void listClient() throws IOException {
		Main.booter.createClientListWindow();
	}

	/**
	 * About windows
	 */
	@FXML
	public void about() {
		Main.booter.createAboutWindow();
	}

	/**
	 * close the app
	 * @param event
	 */
	@FXML
	public void quit() {
		Main.root.getScene().getWindow().hide();
	}

}
