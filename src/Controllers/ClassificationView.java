package Controllers;

import DatabaseConnection.dbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class ClassificationView {
    Connection connection;
    Stage classificationViewStage;


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
        classificationViewStage = new Stage();
        try {
            FXMLLoader classificationViewLoader = new FXMLLoader();
            Pane classificationViewRoot = (Pane) classificationViewLoader.load(getClass().getResource("/Layouts/ClassificationView.fxml").openStream());

            ClassificationView classificationButtonsController = (ClassificationView) classificationViewLoader.getController();
            Scene scene = new Scene(classificationViewRoot);
            this.classificationViewStage.setScene(scene);
            this.classificationViewStage.setTitle("");
            this.classificationViewStage.setResizable(false);
            this.classificationViewStage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void close(){
        this.classificationViewStage.close();
    }
}
