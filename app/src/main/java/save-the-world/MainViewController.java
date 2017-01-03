package com.savetheworld;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Group;

import java.io.IOException;
import javafx.scene.layout.VBox;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.fxml.FXMLLoader;

public class MainViewController {

	private HamburgerBackArrowBasicTransition transition;
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
    private JFXHamburger menuBtn;

    @FXML
    private Label titleLabel;

    @FXML
    private JFXDrawer drawer;

    @FXML
    void menuBtnAction(MouseEvent event) {
    	System.out.println("Menu Clicked...");
        toggleSideMenu();
    }

    @FXML
    void initialize() {
        assert mainScene != null : "fx:id=\"mainScene\" was not injected: check your FXML file 'MainView.fxml'.";
        assert headerAnchorPane != null : "fx:id=\"headerAnchorPane\" was not injected: check your FXML file 'MainView.fxml'.";
        assert menuBtn != null : "fx:id=\"menuBtn\" was not injected: check your FXML file 'MainView.fxml'.";
        assert titleLabel != null : "fx:id=\"titleLabel\" was not injected: check your FXML file 'MainView.fxml'.";
        assert drawer != null : "fx:id=\"drawer\" was not injected: check your FXML file 'MainView.fxml'.";

        titleLabel.setText("Save The World");
    	transition = new HamburgerBackArrowBasicTransition(menuBtn);
    	transition.setRate(-1);
    	mainController = new ScreensController(this);

    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource(GUI.SIDE_MENU_SCREEN_FXML));
    	    VBox box = loader.load();
    	    drawer.setSidePane(box);
    	    ControlledScreen screenController = ((ControlledScreen) loader.getController());
            screenController.setScreenParent(mainController);
    	} catch (IOException ex) {
    	    Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
    	}

    	
    	mainController.loadScreen(GUI.CONTACTS_SCREEN, GUI.CONTACTS_SCREEN_FXML);
    	mainController.loadScreen(GUI.MESSAGE_SCREEN, GUI.MESSAGE_SCREEN_FXML);
    	mainController.setScreen(GUI.CONTACTS_SCREEN);
    	
    	Group root = new Group();
    	root.getChildren().addAll(mainController);
    	mainScene.setRoot(root);

    	System.out.println("Initilized MainViewController...");
    }

    public void toggleSideMenu() {
	    transition.setRate(transition.getRate()*-1);
		transition.play();

		if(drawer.isShown())
		    drawer.close();
		else 
		    drawer.open();
    }

}

