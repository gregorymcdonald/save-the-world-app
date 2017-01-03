package com.savetheworld;
 
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUI extends Application {

    public static MainViewController mainView;
    
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

    // private void layoutContactsTableView(Scene scene) {

    //     TableView <Contact> table = new TableView <Contact>(FXCollections.observableArrayList(Parser.parseFile()));
       
    //     final Label label = new Label("Contacts");
    //     label.setFont(new Font("Arial", 20));

    //     table.setEditable(true);

    //     TableColumn firstNameCol = new TableColumn("First Name");
    //     firstNameCol.setMinWidth(100);
    //     firstNameCol.setCellValueFactory(new PropertyValueFactory <Contact, String> ("firstName"));

    //     TableColumn lastNameCol = new TableColumn("Last Name");
    //     lastNameCol.setMinWidth(100);
    //     lastNameCol.setCellValueFactory(new PropertyValueFactory <Contact, String> ("lastName"));
        
    //     TableColumn emailCol = new TableColumn("Phone Number");
    //     emailCol.setMinWidth(200);
    //     emailCol.setCellValueFactory(new PropertyValueFactory <Contact, String> ("phoneNumber"));
        
    //     table.getColumns().addAll(firstNameCol, lastNameCol, emailCol);
        
    
    //     final VBox vbox = new VBox();
    //     vbox.setSpacing(5);
    //     vbox.setPadding(new Insets(10, 0, 0, 10));
    //     vbox.getChildren().addAll(label, table);

    //     ((Group) scene.getRoot()).getChildren().addAll(vbox);
    // }

}
