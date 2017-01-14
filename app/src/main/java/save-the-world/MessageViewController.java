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

        populateConversationList();

        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println("ListView selection changed from oldValue = " 
                        + oldValue + " to newValue = " + newValue);
                handleListCellClicked(newValue);
            }
        });
    }

    public void handleListCellClicked(String cellValue) {
        this.selectedRecord = Database.getInstance().getConversation(cellValue, "+15162102347");
        loadConversation();
    }

    public void loadConversation() {

        if(this.selectedRecord == null)
            return;

        conversationList.getItems().clear();
        List<MessageRecord> messages = selectedRecord.getMessages();
        for(MessageRecord m : messages) {
            displayMessage(m);
        }
    }

    public void displayMessage(MessageRecord m) {
        conversationList.getItems().add("From: " + m.to + ", to: " + m.from  + ", body: " + m.body);
    }

    public void sendMessage() {
        String messageBody = messageField.getText();
        MessageRecord newMessage = new MessageRecord(selectedRecord.participant2, selectedRecord.participant1, messageBody);
        selectedRecord.addMessage(newMessage);
        displayMessage(newMessage);
        messageField.clear();
    }

    public void populateConversationList() {
        List<ConversationRecord> conversations = Database.getInstance().getAllConversations();

        for(ConversationRecord c : conversations) {
            listView.getItems().add(c.participant2);
        }
    }

    public void setScreenParent(ScreensController screenParent) {
        controller = screenParent;
    }
}
