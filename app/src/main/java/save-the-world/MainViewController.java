package com.savetheworld;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXDrawer;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import javafx.scene.layout.VBox;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;

public class MainViewController implements Initializable {

	public enum ContainerViews {
		CONTACTS,
		MESSAGES,
		SETTINGS 
	}

    @FXML
    private JFXHamburger menuBtn;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private AnchorPane containerAnchorPane;

    @FXML
    private Label titleLabel;

    @FXML
    private AnchorPane headerAnchorPane;

    private HamburgerBackArrowBasicTransition transition;
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    	assert containerAnchorPane != null : "fx:id=\"containerAnchorPane\" was not injected: check your FXML file 'MainView.fxml'.";
        assert drawer != null : "fx:id=\"drawer\" was not injected: check your FXML file 'MainView.fxml'.";
        assert headerAnchorPane != null : "fx:id=\"headerAnchorPane\" was not injected: check your FXML file 'MainView.fxml'.";
        assert menuBtn != null : "fx:id=\"menuBtn\" was not injected: check your FXML file 'MainView.fxml'.";
        assert titleLabel != null : "fx:id=\"titleLabel\" was not injected: check your FXML file 'MainView.fxml'.";

        System.out.println("Initilized MainViewController...");
    	transition = new HamburgerBackArrowBasicTransition(menuBtn);
    	transition.setRate(-1);
    	
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/SidePanelContent.fxml"));
    	    VBox box = loader.load();
    	    drawer.setSidePane(box);
    	} catch (IOException ex) {
    	    Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
    	}
    }   

    @FXML
    void menuBtnAction(MouseEvent event) {
    	System.out.println("Menu Clicked...");
        toggleSideMenu();
    }

    void setContainerViewToView(ContainerViews view) {
    	switch(view) {
    		case CONTACTS:
    			try {
    				FXMLLoader loader = new FXMLLoader(getClass().getResource("/ContactsView.fxml"));
    	    		containerAnchorPane.getChildren().add(loader.load());
    	    	} catch (IOException ex) {
    	    		 Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
    	    	}
    			//containerAnchorPane.setStyle("-fx-background-color:#00FF00");
    			titleLabel.setText("Contacts");
    			break;
    		case MESSAGES:
    			containerAnchorPane.setStyle("-fx-background-color:#0000FF");
    			titleLabel.setText("Messages");
    			break;
    		case SETTINGS:
    			containerAnchorPane.setStyle("-fx-background-color:#FF0000");
    			titleLabel.setText("Settings");
    			break;
    	}

    	toggleSideMenu();
    }

    private void toggleSideMenu() {
	    transition.setRate(transition.getRate()*-1);
		transition.play();

		if(drawer.isShown())
		    drawer.close();
		else 
		    drawer.open();
    }

}
