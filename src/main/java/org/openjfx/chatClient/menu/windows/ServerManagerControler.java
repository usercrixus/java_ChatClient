package org.openjfx.chatClient.menu.windows;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import org.openjfx.chatClient.Main;
import org.openjfx.chatClient.beans.DataServerList;
import org.openjfx.chatClient.utilities.EditCell;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;

public class ServerManagerControler {

	public ServerManagerControler() {}

	@FXML
	private TableView<DataServerList> tabViewIpList;
	@FXML
	private TableColumn<DataServerList, String> serverIp;
	@FXML
	private TableColumn<DataServerList, String> serverName;
	@FXML
	private TableColumn<DataServerList, String> serverPort;
	@FXML
	private TextField searchBar;
	@FXML
	private TextField addPort;
	@FXML
	private TextField addName;
	@FXML
	private TextField addIp;
	@FXML
	private Button buttonDelete;
	@FXML
	private Button buttonLoad;
	@FXML
	private Button buttonCancel;

	private FilteredList<DataServerList> serverListModel;

	private ArrayList<DataServerList> bufferArrayList;


	@SuppressWarnings("unchecked")
	@FXML
	public void initialize() throws FileNotFoundException, ClassNotFoundException, IOException {

		//get ip list
		try(ObjectInputStream input =  new ObjectInputStream(new FileInputStream("ipList.txt"))) {
			bufferArrayList = (ArrayList<DataServerList>)input.readObject();
			serverListModel = new FilteredList<DataServerList>(FXCollections.observableList(bufferArrayList), p->true);
			tabViewIpList.setItems(serverListModel);
		}catch (FileNotFoundException e) {
			bufferArrayList = new ArrayList<DataServerList>();
			serverListModel = new FilteredList<DataServerList>(FXCollections.observableList(bufferArrayList), p->true);
			save();
			tabViewIpList.setItems(serverListModel);
		}catch (Exception e) {
			e.printStackTrace();
		}

		// set table column
		serverIp.setCellValueFactory(new PropertyValueFactory<>("Ip"));
		serverPort.setCellValueFactory(new PropertyValueFactory<>("Port"));
		serverName.setCellValueFactory(new PropertyValueFactory<>("Name"));

		// tabler user experience code
		serverIp.setCellFactory(column -> EditCell.createStringEditCell() );
		serverIp.setOnEditCommit( (CellEditEvent<DataServerList, String> event) -> {
			((DataServerList) event.getTableView().getItems().get(event.getTablePosition().getRow())).setIp(event.getNewValue());
		});

		serverName.setCellFactory(column -> EditCell.createStringEditCell());
		serverName.setOnEditCommit( (CellEditEvent<DataServerList, String> event) -> {
			((DataServerList) event.getTableView().getItems().get(event.getTablePosition().getRow())).setName(event.getNewValue());
		});

		serverPort.setCellFactory(column -> EditCell.createStringEditCell());
		serverPort.setOnEditCommit( (CellEditEvent<DataServerList, String> event) -> {
			((DataServerList) event.getTableView().getItems().get(event.getTablePosition().getRow())).setPort(event.getNewValue());
		});

		tabViewIpList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<DataServerList>() {
			@Override
			public void changed(ObservableValue<? extends DataServerList> observable, DataServerList oldValue,
					DataServerList newValue) {
				if (newValue != null)
				{
					buttonDelete.setDisable(false);
					buttonLoad.setDisable(false);
				}else {
					buttonDelete.setDisable(true);
					buttonLoad.setDisable(true);
				}

			}
		});
	}

	/**
	 * set filter predicate
	 */
	@FXML
	void search() {
		serverListModel.setPredicate( (i) -> {
			if(i.getName().contains(searchBar.getText()) ) return true;
			return false;	
		});
	}

	/**
	 * Add one server ip/port to the list and save the list txt file
	 * @param event
	 */
	@SuppressWarnings("unchecked")
	@FXML
	void addToServerList(ActionEvent event) {
		((ObservableList<DataServerList>)serverListModel.getSource()).add(new DataServerList(addIp.getText(), addPort.getText(), addName.getText()));
		addIp.clear();
		addPort.clear();
		addName.clear();
		try {
			save();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Remove one server ip/port from the list and save the list txt file
	 * @param event
	 */
	@SuppressWarnings("unchecked")
	@FXML
	void removeOfServerList(ActionEvent event) {
		((ObservableList<DataServerList>)serverListModel.getSource()).remove( tabViewIpList.getSelectionModel().getSelectedIndex() );
		try {
			save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Save (record) server list on txt file
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	void save() throws FileNotFoundException, IOException {
		ObjectOutputStream outPut =  new ObjectOutputStream(new FileOutputStream("ipList.txt"));
		outPut.writeObject(new ArrayList<DataServerList>(bufferArrayList));
		outPut.close();
	}

	/**
	 * load selected line ip/port in BootLoaderControler field
	 */
	@FXML
	void load() {
		int index = tabViewIpList.getSelectionModel().getSelectedIndex();
		Main.booter.getChatControler().getTextFieldServerIp().setText(serverListModel.get(index).getIp());
		Main.booter.getChatControler().getTextFieldServerPort().setText(serverListModel.get(index).getPort());
		buttonLoad.getScene().getWindow().hide();
	}

	/**
	 * Close the windows
	 */
	@FXML
	void cancel() {
		buttonLoad.getScene().getWindow().hide();
	}
}
