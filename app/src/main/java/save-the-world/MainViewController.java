package com.savetheworld;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.SubScene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Group;

public class MainViewController {

    private ScreensController mainController;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private SubScene mainScene;

    @FXML
    private AnchorPane headerAnchorPane;

    @FXML
    void handleContactsBtn(MouseEvent event) {
         mainController.setScreen(GUI.CONTACTS_SCREEN);
    }

    @FXML
    void handleMessagesBtn(MouseEvent event) {
         mainController.setScreen(GUI.MESSAGE_SCREEN);
    }

    @FXML
    void initialize() {
        assert mainScene != null : "fx:id=\"mainScene\" was not injected: check your FXML file 'MainView.fxml'.";
        assert headerAnchorPane != null : "fx:id=\"headerAnchorPane\" was not injected: check your FXML file 'MainView.fxml'.";

        mainController = new ScreensController(this);
  
        mainController.loadScreen(GUI.CONTACTS_SCREEN, GUI.CONTACTS_SCREEN_FXML);
        mainController.loadScreen(GUI.MESSAGE_SCREEN, GUI.MESSAGE_SCREEN_FXML);
        mainController.setScreen(GUI.CONTACTS_SCREEN);
        
        Group root = new Group();
        root.getChildren().addAll(mainController);
        mainScene.setRoot(root);

        System.out.println("Initilized MainViewController...");
    }
}


