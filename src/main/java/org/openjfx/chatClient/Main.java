package org.openjfx.chatClient;

import org.openjfx.chatClient.chat.ChatControler;
import org.openjfx.chatClient.utilities.FXMLFactory;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Main extends Application {

	public static BorderPane root;
	public static FXMLFactory booter;

	@Override
	public void start(Stage primaryStage) {
		try {

			root = new BorderPane();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());

			booter = new FXMLFactory();
			booter.createMenuBar();
			ChatControler controler = booter.createChat();

			primaryStage.setScene(scene);
			primaryStage.setOnHidden(e->{
				controler.disconnect();
				Platform.exit();
			});
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
