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
import java.util.ArrayList;

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

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane containerAnchorPane;

    @FXML
    private StackPane stackPane;

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

                if(textArea.getText() != null && textArea.getText().trim().isEmpty()){
                    String textAreaContent = textArea.getText();
                    sendMassMessage(textAreaContent);
                }

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
        selectedCells.clear();
        tableView.getSelectionModel().clearSelection();
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
 
    
        // TableColumn<ContactTableViewModel, Number> indexColumn = new TableColumn<ContactTableViewModel, Number>("#");
        // indexColumn.setSortable(false);
        // indexColumn.setCellValueFactory(column-> new ReadOnlyObjectWrapper<Number>(tableView.getItems().indexOf(column.getValue())));
 
        //Ideally will replace witha a call to the Database to retreive all contacts that will have their status set 
        ArrayList<Contact> contacts = Parser.parseFile();
        data = FXCollections.observableArrayList();

        for(Contact c: contacts) {
            ContactTableViewModel model = new ContactTableViewModel();
            model.firstName.set(c.getFirstName());
            model.lastName.set(c.getLastName());
            model.phoneNumber.set(c.getPhoneNumber());
            data.add(model);
        } 

        System.out.println(data.size());
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView.setItems(data);

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

    public void setScreenParent(ScreensController screenParent) {
        controller = screenParent;
    }

    public void sendMassMessage(String message) {
        
        ObservableList<ContactTableViewModel> selectedContacts = tableView.getSelectionModel().getSelectedItems();
        Database db = Database.getInstance();
        Messenger messenger = Messenger.getInstance();

        //loop over all the selected contacts and send each one a message
        for(ContactTableViewModel model : selectedContacts) {

            String contactsPhoneNumber = model.phoneNumber.get();
            String TWILIO_PHONE_NUMBER = "+15162102347";

            //check to see if there is already a conversation with this contact
            ConversationRecord record = db.getConversation(contactsPhoneNumber, TWILIO_PHONE_NUMBER);

            //if there was no record create a new one
            if(record == null)
                record = new ConversationRecord(TWILIO_PHONE_NUMBER, contactsPhoneNumber, null);

            //send the message to the contact
            MessageRecord sentMessage = messenger.sendSMS(contactsPhoneNumber, message);

            //if the message was successfully sent save the conversation to the db and push
            if(sentMessage != null) {
                record.addMessage(sentMessage);
                db.saveConversation(record);
                db.push();
            }else{
                //notify user about failed message attempt 
            }

            System.out.println(model.phoneNumber.get());
        }

        //clear the selected cells
        clearSelectedCells();
    }

    public void clearSelectedCells() {
        selectedCells.clear();
        tableView.getSelectionModel().clearSelection();
    }  
} 




