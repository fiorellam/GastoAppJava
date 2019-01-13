package Controllers;

import DatabaseConnection.dbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EditClassification implements Initializable {
    Connection connection;
    Stage editClassificationStage = new Stage();

    @FXML
    private TextField tf_general_limit;
    @FXML TextField tf_classification_name;
    @FXML TextField tf_classification_limit;
    String name;
    double limit;

//    public EditClassification(Integer classification_id, Integer limitt, String namee) {
//        System.out.println("holaaaaa");
//    }


    public void CheckConnection() {
        connection = dbConnection.getConnection();
        if(connection == null){
            System.out.println("Connection Not succesful");
            System.exit(1);
        }else {
            System.out.println("Connection Succesful");
        }
    }


    public void save(ActionEvent actionEvent) {

    }

    public void show() {
        try {
            FXMLLoader editClassificationLoader = new FXMLLoader();
            Pane editClassificationRoot = (Pane) editClassificationLoader.load(getClass().getResource("/Layouts/EditClassification.fxml").openStream());

            EditClassification editClassificationController = (EditClassification) editClassificationLoader.getController();
            Scene scene = new Scene(editClassificationRoot);
            this.editClassificationStage.setScene(scene);
            this.editClassificationStage.setTitle("Configuration");
            this.editClassificationStage.setResizable(false);
            this.editClassificationStage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void close(){
        this.editClassificationStage.close();
    }

    public void sendInfo(Integer classification_id, double limitt, String namee) {
        this.name = namee;
        this.limit = limitt;

    }
    public void buildInfo(){
        tf_classification_limit.setText(String.valueOf(this.limit));
        tf_classification_name.setText(String.valueOf(this.name));

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buildInfo();
        System.out.println(this.name);
        System.out.println(this.limit);
//
    }
}
