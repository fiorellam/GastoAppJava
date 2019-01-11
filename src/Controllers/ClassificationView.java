package Controllers;

import DatabaseConnection.dbConnection;
import Model.Classification;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ClassificationView implements Initializable {
    Connection connection;
    Stage classificationViewStage;
    ArrayList<Classification> classification_list_classification;
    AddClassification addClassification;
//    ObservableList list = FXCollections.observableArrayList();

    @FXML private ListView<Classification> listView_classification;
    ObservableList<Classification> data;


    public void CheckConnection() {
        connection = dbConnection.getConnection();
        if(connection == null){
            System.out.println("Connection Not succesful");
            System.exit(1);
        }else {
            System.out.println("Connection Succesful");
        }
    }

    public void buildDataListView(){
        data = FXCollections.observableArrayList();
        connection = dbConnection.getConnection();
        Classification classification = null;
        classification_list_classification = new ArrayList<Classification>();
        try {
            String SQL = "select * from classification";
            ResultSet rs = connection.createStatement().executeQuery(SQL);
            while(rs.next()){
                classification = new Classification();
                classification.setClassification_id(rs.getInt("classification_id"));
                classification.setNamee(rs.getString("name"));
                classification.setLimitt(rs.getInt("limitt"));
                classification.setActual_limit(rs.getInt("actual_limit"));

                data.add(classification);
            }
            listView_classification.getItems().addAll(data);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error Building ComboBox Data");
        }
        if (connection == null) {
            System.exit(1);
            System.out.println("Connection failed");
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
    public void goToAddClassification(ActionEvent actionEvent) {
        addClassification = new AddClassification();
        addClassification.show();
    }

    public void close(){
        this.classificationViewStage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buildDataListView();
    }
}
