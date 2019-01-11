package Controllers;

import DatabaseConnection.dbConnection;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class ReportButtons {
    Connection connection;
    Stage reportButtonsStage;


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
        reportButtonsStage = new Stage();
        try {
            FXMLLoader reportButtonsLoader = new FXMLLoader();
            Pane reportButtonsRoot = (Pane) reportButtonsLoader.load(getClass().getResource("/Layouts/ReportButtons.fxml").openStream());

            ReportButtons classificationButtonsController = (ReportButtons) reportButtonsLoader.getController();
            Scene scene = new Scene(reportButtonsRoot);
            this.reportButtonsStage.setScene(scene);
            this.reportButtonsStage.setTitle("");
            this.reportButtonsStage.setResizable(false);
            this.reportButtonsStage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void close(){
        this.reportButtonsStage.close();
    }
}