package Controllers;

import DatabaseConnection.dbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class AddClassification {
    @FXML private TextField tf_limit;
    @FXML private TextField tf_classification;

    Connection connection;
    Stage addClassificationStage;

    int actual_limit;


    public void CheckConnection() {
        connection = dbConnection.getConnection();
        if(connection == null){
            System.out.println("Connection Not succesful");
            System.exit(1);
        }else {
            System.out.println("Connection Succesful");
        }
    }
    public void show() {
        CheckConnection();
        addClassificationStage = new Stage();
        try {
            FXMLLoader addClassificationLoader = new FXMLLoader();
            Pane addClassificationRoot = (Pane) addClassificationLoader.load(getClass().getResource("/Layouts/AddClassification.fxml").openStream());

            AddClassification classificationButtonsController = (AddClassification) addClassificationLoader.getController();
            Scene scene = new Scene(addClassificationRoot);
            this.addClassificationStage.setScene(scene);
            this.addClassificationStage.setTitle("");
            this.addClassificationStage.setResizable(false);
            this.addClassificationStage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void close(){
        this.addClassificationStage.close();
    }

    public void saveClassification(ActionEvent actionEvent) {
        String classification_name_string = tf_classification.getText();
        String classification_limit_string = tf_limit.getText();

        if(!classification_name_string.equals("") && !classification_limit_string.equals("")){
            int classification_limit_int = Integer.parseInt(classification_limit_string);
            String sqlInsert = "INSERT INTO classification (name, limitt, actual_limit) VALUES (?,?,?)";
            try{
                System.out.println("nombreclasificacion: "+ classification_name_string+ "limite " + classification_limit_int);
                Connection connect = dbConnection.getConnection();
                PreparedStatement sqlStatement = connect.prepareStatement(sqlInsert);
                sqlStatement.setString(1,classification_name_string);
                sqlStatement.setInt(2,classification_limit_int);
                sqlStatement.setInt(3, actual_limit);
                sqlStatement.execute();
                connect.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
