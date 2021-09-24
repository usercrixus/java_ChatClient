module org.openjfx.chatClient {
	requires javafx.base;
	requires transitive javafx.graphics;
	requires transitive javafx.fxml;
	requires transitive javafx.controls;
	requires java.xml;
	requires transitive java.rmi;
 
	opens org.openjfx.chatClient.chat;
	opens org.openjfx.chatClient.menu;
	opens org.openjfx.chatClient.beans;
	opens org.openjfx.chatClient.utilities;
    opens org.openjfx.chatClient to javafx.fxml;
    opens org.openjfx.chatClient.menu.windows;
    
    exports org.openjfx.chatClient;
    exports org.openjfx.chatClient.utilities;
    exports org.openjfx.chatClient.chat;
    exports org.openjfx.chat.remote;
}