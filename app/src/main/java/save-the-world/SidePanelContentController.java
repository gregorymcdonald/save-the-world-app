package com.savetheworld;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;


public class SidePanelContentController implements Initializable {

    @FXML
    private JFXButton b1;
    @FXML
    private JFXButton b2;
    @FXML
    private JFXButton b3;
    @FXML
    private JFXButton exit;

    private MainViewController mainView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {}    

    @FXML
    private void displayView(ActionEvent event) {
        JFXButton btn = (JFXButton) event.getSource();
        System.out.println(btn.getText());

        switch(btn.getText())
        {
            case "Contacts":
                GUI.mainView.setContainerViewToView(MainViewController.ContainerViews.CONTACTS);
                break;
            case "Messages":
                GUI.mainView.setContainerViewToView(MainViewController.ContainerViews.MESSAGES);
                break;
            case "Settings":
                GUI.mainView.setContainerViewToView(MainViewController.ContainerViews.SETTINGS);
                break;
        }
    }

    @FXML
    private void exit(ActionEvent event) {
        System.exit(0);
    }  
}
