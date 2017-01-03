package com.savetheworld;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.JFXTreeTableColumn;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class ContactsViewController implements ControlledScreen {


	private ScreensController controller;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane containerAnchorPane;

    @FXML
    private JFXButton newContactBtn;

    @FXML
    private JFXButton downloadIFCBtn;

    @FXML
    private JFXTreeTableView <Contact> tableView;

    @FXML
    void addNewContactAction(MouseEvent event) {

    }

    @FXML
    void downloadIFCAction(MouseEvent event) {
    	
    }

    @FXML
    void initialize() {
        assert containerAnchorPane != null : "fx:id=\"containerAnchorPane\" was not injected: check your FXML file 'ContactsView.fxml'.";
        assert newContactBtn != null : "fx:id=\"newContactBtn\" was not injected: check your FXML file 'ContactsView.fxml'.";
        assert downloadIFCBtn != null : "fx:id=\"downloadIFCBtn\" was not injected: check your FXML file 'ContactsView.fxml'.";
        assert tableView != null : "fx:id=\"tableView\" was not injected: check your FXML file 'ContactsView.fxml'.";

        JFXTreeTableColumn <Contact, String> firstName = new JFXTreeTableColumn<>("First Name");
    	firstName.setPrefWidth(150);
    	firstName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Contact, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Contact, String> param) {
                return param.getValue().getValue().firstName;
            }
        });

        JFXTreeTableColumn <Contact, String> lastName = new JFXTreeTableColumn<>("Last Name");
    	lastName.setPrefWidth(150);
    	lastName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Contact, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Contact, String> param) {
                return param.getValue().getValue().lastName;
            }
        });

        JFXTreeTableColumn <Contact, String> phoneNumber = new JFXTreeTableColumn<>("Phone Number");
    	phoneNumber.setPrefWidth(150);
    	phoneNumber.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Contact, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Contact, String> param) {
                return param.getValue().getValue().phoneNumber;
            }
        });

        ObservableList <Contact> contacts = FXCollections.observableArrayList(Parser.parseFile());

        final TreeItem <Contact> root = new RecursiveTreeItem <Contact>(contacts, RecursiveTreeObject::getChildren);
        tableView.getColumns().setAll(firstName, lastName, phoneNumber);
        tableView.setRoot(root);
        tableView.setShowRoot(false);

        System.out.println("Initialized ContactsViewController...");
    }

    public void setScreenParent(ScreensController screenParent) {
    	controller = screenParent;
    }
}
