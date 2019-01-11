package Controllers;

import DatabaseConnection.dbConnection;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Configuration  {
    Connection connection;
    Stage configurationStage = new Stage();

    @FXML private TextField tf_general_limit;

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
        CheckConnection();
        String general_limit_string = tf_general_limit.getText();
        int general_limit_int = Integer.parseInt(general_limit_string);
        System.out.println(general_limit_string);
//        if(!classification_name_string.equals("") && !concept_string.equals("") && !amount_string.equals("")){
//            String date = setDate();
//            TODO: Insertar nuevo limite
        String sqlInsert = "INSERT INTO general_configuration (general_limit) VALUES (?)";
        try{
            Connection connect = dbConnection.getConnection();
            PreparedStatement sqlStatement = connect.prepareStatement(sqlInsert);
            sqlStatement.setInt(1,general_limit_int);

            sqlStatement.execute();
            connect.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        }

//        Notifications

//    }


    public void show() {
        try {
            FXMLLoader configurationLoader = new FXMLLoader();
            Pane configurationRoot = (Pane) configurationLoader.load(getClass().getResource("/Layouts/Configuration.fxml").openStream());

            Configuration configurationController = (Configuration) configurationLoader.getController();
            Scene scene = new Scene(configurationRoot);
            this.configurationStage.setScene(scene);
            this.configurationStage.setTitle("Configuration");
            this.configurationStage.setResizable(false);
            this.configurationStage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void close(){
        this.configurationStage.close();
    }
}
