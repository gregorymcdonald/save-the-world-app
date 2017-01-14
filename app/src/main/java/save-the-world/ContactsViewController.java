package com.savetheworld;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;
import javafx.beans.value.ObservableValue;
import javafx.beans.property.ReadOnlyObjectWrapper;
import java.util.ArrayList;


public class ContactsViewController implements ControlledScreen {

    private ScreensController controller;
    private ObservableList<ContactTableViewModel> data;

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
    private TableView<ContactTableViewModel> tableView;

    @FXML
    private TableColumn<ContactTableViewModel, String> firstNameCol;

    @FXML
    private TableColumn<ContactTableViewModel, String> lastNameCol;

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
        assert firstNameCol != null : "fx:id=\"firstNameCol\" was not injected: check your FXML file 'ContactsView.fxml'.";
        assert lastNameCol != null : "fx:id=\"lastNameCol\" was not injected: check your FXML file 'ContactsView.fxml'.";

        firstNameCol.setMinWidth(100);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<ContactTableViewModel, String>("firstName"));
 
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<ContactTableViewModel, String>("lastName"));
 
    
        // TableColumn<ContactTableViewModel, Number> indexColumn = new TableColumn<ContactTableViewModel, Number>("#");
        // indexColumn.setSortable(false);
        // indexColumn.setCellValueFactory(column-> new ReadOnlyObjectWrapper<Number>(tableView.getItems().indexOf(column.getValue())));
 
        ArrayList<Contact> contacts = Parser.parseFile();
        data = FXCollections.observableArrayList();

        for(Contact c: contacts) {
            ContactTableViewModel model = new ContactTableViewModel();
            model.firstName.set(c.getFirstName());
            model.lastName.set(c.getLastName());
            model.phoneNumber.set(c.getPhoneNumber());
            data.add(model);
        } 

        //System.out.println(data.size());
        //tableView.setItems(data);
    }

    public void setScreenParent(ScreensController screenParent) {
        controller = screenParent;
    }  
} 



