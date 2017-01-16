package com.savetheworld;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;
import javafx.beans.value.ObservableValue;
import javafx.beans.property.ReadOnlyObjectWrapper;
import java.util.List;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialog.DialogTransition;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TablePosition;

public class ContactsViewController implements ControlledScreen {

    private ScreensController controller;
    private ObservableList<ContactTableViewModel> data;
    private ObservableList<TablePosition> selectedCells = FXCollections.observableArrayList();
    private Database db = Database.getInstance();

    @FXML
    private StackPane stackPane;

    @FXML
    private AnchorPane containerAnchorPane;

    @FXML
    private JFXButton newMassMessageBtn;

    @FXML
    private JFXButton clearSelectedBtn;

    @FXML
    private TableView<ContactTableViewModel> tableView;

    @FXML
    private TableColumn<ContactTableViewModel, String> firstNameCol;

    @FXML
    private TableColumn<ContactTableViewModel, String> lastNameCol;

    @FXML
    void newMassMessageHandler(MouseEvent event) {
    
        JFXDialogLayout content = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.TOP);

        TextArea textArea = new TextArea();
        JFXButton sendButton = new JFXButton("Send");
        JFXButton cancelButton = new JFXButton("Cancel");

        sendButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){

                String textAreaContent = textArea.getText();
                sendMassMessage(textAreaContent);
                dialog.close();
            }
        });

        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                dialog.close();
            }
        });

        content.setHeading(new Text("New Mass Message"));
        content.setBody(textArea);
        content.setActions(cancelButton, sendButton);
        dialog.show();
    }

    @FXML
    void clearSelectedHandler(MouseEvent event) {
       clearSelectedCells();
    }

    @FXML
    void initialize() {
        assert containerAnchorPane != null : "fx:id=\"containerAnchorPane\" was not injected: check your FXML file 'ContactsView.fxml'.";
        assert newMassMessageBtn != null : "fx:id=\"newContactBtn\" was not injected: check your FXML file 'ContactsView.fxml'.";
        assert clearSelectedBtn != null : "fx:id=\"downloadIFCBtn\" was not injected: check your FXML file 'ContactsView.fxml'.";
        assert tableView != null : "fx:id=\"tableView\" was not injected: check your FXML file 'ContactsView.fxml'.";
        assert firstNameCol != null : "fx:id=\"firstNameCol\" was not injected: check your FXML file 'ContactsView.fxml'.";
        assert lastNameCol != null : "fx:id=\"lastNameCol\" was not injected: check your FXML file 'ContactsView.fxml'.";
        assert stackPane != null : "fx:id=\"stackPane\" was not injected: check your FXML file 'ContactsView.fxml'.";

        firstNameCol.setMinWidth(100);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<ContactTableViewModel, String>("firstName"));
 
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<ContactTableViewModel, String>("lastName"));
 
    
        TableColumn<ContactTableViewModel, Number> indexColumn = new TableColumn<ContactTableViewModel, Number>("#");
        indexColumn.setSortable(false);
        indexColumn.setCellValueFactory(column-> new ReadOnlyObjectWrapper<Number>(tableView.getItems().indexOf(column.getValue())));
 
        data = FXCollections.observableArrayList();
        tableView.setItems(data);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView.setEditable(false);
        tableView.getColumns().add(indexColumn);

        tableView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                selectedCells.add(
                    tableView.getSelectionModel().getSelectedCells().get(
                        tableView.getSelectionModel().getSelectedCells().size()-1
                    )
                );
                for (TablePosition tp : selectedCells){
                    tableView.getSelectionModel().select(tp.getRow(), tp.getTableColumn());
                }
            }
        });

    }

    /* Called when the view appears on the screen */
    public void viewDidLoad() {
        db.pull();
        data.removeAll(data);
        populateContactsTable();
    }

    public void setScreenParent(ScreensController screenParent) {
        controller = screenParent;
    }

    public void sendMassMessage(String message) {
               
        ObservableList<ContactTableViewModel> selectedContacts = tableView.getSelectionModel().getSelectedItems();
        Database db = Database.getInstance();
        Messenger messenger = Messenger.getInstance();

        //loop over all the selected contacts and send each one a message
        for(ContactTableViewModel model : selectedContacts) {

            String constructedMessage = MessageBuilder.buildMessage(message, model);
            String contactsPhoneNumber = model.phoneNumber.get();
            String TWILIO_PHONE_NUMBER = "+15162102347";

            //check to see if there is already a conversation with this contact
            ConversationRecord record = db.getConversation(contactsPhoneNumber, TWILIO_PHONE_NUMBER);

            //if there was no record create a new one
            if(record == null)
                record = new ConversationRecord(TWILIO_PHONE_NUMBER, contactsPhoneNumber, null);

            //send the message to the contact
            MessageRecord sentMessage = messenger.sendSMS(contactsPhoneNumber, constructedMessage);

            //if the message was successfully sent save the conversation to the db and push
            if(sentMessage != null) {
                record.addMessage(sentMessage);
                db.saveConversation(record);
            }else{
                //notify user about failed message attempt 
            }

        }

        //clear the selected cells
        clearSelectedCells();
    }

    public void clearSelectedCells() {
        selectedCells.clear();
        tableView.getSelectionModel().clearSelection();
    }

    private void populateContactsTable() {
        List<ContactRecord> contacts = db.getAllContacts();
        for(ContactRecord c: contacts) {
            ContactTableViewModel model = new ContactTableViewModel();
            model.firstName.set(c.getFirstName());
            model.lastName.set(c.getLastName());
            model.phoneNumber.set(c.getPhoneNumber());
            data.add(model);
        }
    }


    //TEST FOR TABLE VIEW
    public void populateTestTable() {
        ContactTableViewModel greg = new ContactTableViewModel();
        greg.firstName.set("Greg");
        greg.lastName.set("McDondald");
        greg.phoneNumber.set("14692379287");

        ContactTableViewModel adam = new ContactTableViewModel();
        adam.firstName.set("Adam");
        adam.lastName.set("Estrin");
        adam.phoneNumber.set("5163533154");

        ContactTableViewModel kieran = new ContactTableViewModel();
        kieran.firstName.set("Kieran");
        kieran.lastName.set("Vanderslice");
        kieran.phoneNumber.set("17136208645");

        data.add(greg);
        data.add(adam);
        data.add(kieran);
    }


} 