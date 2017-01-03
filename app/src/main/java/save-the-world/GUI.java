package com.savetheworld;
 
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUI extends Application {

    public static MainViewController mainView;

    public static final String MAIN_SCREEN = "MainView";
    public static final String MAIN_SCREEN_FXML = "/MainView.fxml";
    public static final String SIDE_MENU_SCREEN = "SideMenuView";
    public static final String SIDE_MENU_SCREEN_FXML = "/SidePanelContent.fxml";
    public static final String CONTACTS_SCREEN = "ContactsView";
    public static final String CONTACTS_SCREEN_FXML = "/ContactsView.fxml";
    public static final String MESSAGE_SCREEN = "MessageView";
    public static final String MESSAGE_SCREEN_FXML = "/MessageView.fxml";

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        primaryStage.setTitle("Save The World");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainView.fxml"));
        Parent root = loader.load();
        mainView = (MainViewController) loader.getController();
        
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
