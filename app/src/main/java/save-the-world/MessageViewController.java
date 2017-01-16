package com.savetheworld;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.ListView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;

import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;
import java.util.List;

public class MessageViewController implements ControlledScreen {

    private ScreensController controller;
    private ConversationRecord selectedRecord = null;
    private static Database db = Database.getInstance();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane containerAnchorPane;

    @FXML
    private ListView<String> listView;

    @FXML
    private TextArea messageField;

    @FXML
    private JFXButton sendBtn;

    @FXML
    private ListView<String> conversationList;

    @FXML
    void sendBtnAction(MouseEvent event) {
        sendMessage();
    }

    @FXML
    void initialize() {
        assert containerAnchorPane != null : "fx:id=\"containerAnchorPane\" was not injected: check your FXML file 'MessageView.fxml'.";
        assert listView != null : "fx:id=\"listView\" was not injected: check your FXML file 'MessageView.fxml'.";
        assert messageField != null : "fx:id=\"messageField\" was not injected: check your FXML file 'MessageView.fxml'.";
        assert sendBtn != null : "fx:id=\"sendBtn\" was not injected: check your FXML file 'MessageView.fxml'.";
        assert conversationList != null : "fx:id=\"conversationList\" was not injected: check your FXML file 'MessageView.fxml'.";

        System.out.println("Initilized MessageViewController...");

        loadConversationList();

        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println("ListView selection changed from oldValue = " 
                        + oldValue + " to newValue = " + newValue);
                handleListCellClicked(newValue);
            }
        });
    }

    public void viewDidLoad() {
        db.pull();
    }

    public void handleListCellClicked(String cellValue) {
        this.selectedRecord = Database.getInstance().getConversation(cellValue, "+15162102347");
        loadConversation();
    }

    public void loadConversation() {

        if(this.selectedRecord == null)
            return;

        db.pull();

        conversationList.getItems().clear();
        List<MessageRecord> messages = selectedRecord.getMessages();
        for(MessageRecord m : messages) {
            displayMessage(m);
        }
    }

    public void displayMessage(MessageRecord m) {
        conversationList.getItems().add(m.from + ": " + m.body);
    }

    public void sendMessage() {

        //grab the message body from teh text field
        String messageBody = messageField.getText();
        System.out.println(messageBody);

        //Send actual message to selected 
        Messenger messenger = Messenger.getInstance();
        MessageRecord newMessage = messenger.sendSMS(selectedRecord.participant2, messageBody);

        //Uppon successfully sending message save message to the ConversationRecord, save the local Database and then push 
        if (newMessage != null) {
            Database db = Database.getInstance();
            selectedRecord.addMessage(newMessage);
            db.saveConversation(selectedRecord);
            displayMessage(newMessage); 
        } else{
            //notify the user there was some sore of error sending message
        }
       
        messageField.clear();
    }

    public void loadConversationList() {

        List<ConversationRecord> conversations = db.getAllConversations();

        for(ConversationRecord c : conversations) {
            listView.getItems().add(c.participant2);
        }
    }

    public void setScreenParent(ScreensController screenParent) {
        controller = screenParent;
    }
}
