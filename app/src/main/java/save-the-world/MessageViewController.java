package com.savetheworld;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;

public class MessageViewController implements ControlledScreen {

    private ScreensController controller;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane containerAnchorPane;

    @FXML
    private JFXListView<Label> chatList;

    @FXML
    private TextArea messageField;

    @FXML
    private JFXButton sendBtn;

    @FXML
    private ScrollPane chatScrollView;

    @FXML
    void sendBtnAction(MouseEvent event) {

    }

    @FXML
    void initialize() {
        assert containerAnchorPane != null : "fx:id=\"containerAnchorPane\" was not injected: check your FXML file 'MessageView.fxml'.";
        assert chatList != null : "fx:id=\"chatList\" was not injected: check your FXML file 'MessageView.fxml'.";
        assert messageField != null : "fx:id=\"messageField\" was not injected: check your FXML file 'MessageView.fxml'.";
        assert sendBtn != null : "fx:id=\"sendBtn\" was not injected: check your FXML file 'MessageView.fxml'.";
        assert chatScrollView != null : "fx:id=\"chatScrollView\" was not injected: check your FXML file 'MessageView.fxml'.";

        for(int i = 0 ; i < 10 ; i++) chatList.getItems().add(new Label("Chat " + i));

        System.out.println("Initilized MessageViewController...");
    }

    public void setScreenParent(ScreensController screenParent) {
        controller = screenParent;
    }
}
