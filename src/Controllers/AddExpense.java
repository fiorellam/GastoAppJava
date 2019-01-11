package Controllers;

import DatabaseConnection.dbConnection;
import Model.Classification;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
//import org.controlsfx.control.Notifications;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class AddExpense extends Application implements Initializable {
    Connection connection;

    @FXML private TextField tf_concept;
    @FXML private TextField tf_amount;
    @FXML private ComboBox<Classification> c_box_classification;
    ArrayList<String> list_classification_string;
    ArrayList<Classification> classification_list_classification;
    ObservableList<Classification> data;


    Configuration config;
    ReportButtons reportButtons;
    ClassificationView classificationView;
    Stage primarStage;
//    ClassificationButtons classificationButtons;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primarStage = primaryStage;

        CheckConnection();
        Parent root = (Parent) FXMLLoader.load(getClass().getResource("../Layouts/AddExpense.fxml"));

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("GastoApp");

        primaryStage.show();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buildData();
        System.out.println(data.sorted());
    }

    public void CheckConnection() {
        connection = dbConnection.getConnection();
        if(connection == null){
            System.out.println("Connection Not succesful");
            System.exit(1);
        }else {
            System.out.println("Connection Succesful");
        }
    }
    //combobox sql connection
    public void buildData() {
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
//                System.out.println("ID "+  classification.getClassification_id() + classification.getNamee() + "  "+ classification.getLimitt() + "  " +classification.getActual_limit() );
//                data.add(rs.getString("name"));
                data.add(classification);
                System.out.println(rs.getInt("classification_id"));
            }
            c_box_classification.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error Building ComboBox Data");
        }
        if (connection == null) {
            System.exit(1);
            System.out.println("Connection failed");
        }
    }

    public static void main(String[] args){
        launch(args);
    }
    public String setDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        String fecha = dateFormat.format(date);
        return fecha;
    }

    public void save(MouseEvent mouseEvent) {
        String classification_name_string = String.valueOf(c_box_classification.getSelectionModel().getSelectedItem());
        String concept_string = tf_concept.getText();
        String amount_string = tf_amount.getText();

        Classification s = c_box_classification.getSelectionModel().getSelectedItem();
        int selectedid = s.getClassification_id();
        String selectedname = s.getNamee();
//        System.out.println(concept_string + "  " + amount_string + " " + classification_name_string);
        System.out.println("SELECTED ID" + selectedid + "  " +selectedname + " " + classification_name_string);

        if(!classification_name_string.equals("") && !concept_string.equals("") && !amount_string.equals("")){
            String date = setDate();
            double amount_double = Double.parseDouble(amount_string);
            String sqlInsert = "INSERT INTO expense (concept_name, amount, dateb, classification_id) VALUES (?,?,?,?)";
            try{
                Connection connect = dbConnection.getConnection();
                PreparedStatement sqlStatement = connect.prepareStatement(sqlInsert);
                sqlStatement.setString(1,concept_string);
                sqlStatement.setDouble(2,amount_double);
                sqlStatement.setString(3,date );
                sqlStatement.setInt(4,selectedid);
                sqlStatement.execute();
                connect.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

    }

    public void goToReportButtons(ActionEvent actionEvent) {
         reportButtons = new ReportButtons();
         reportButtons.show();
    }

    public void goToClassificationView(ActionEvent actionEvent) {
        classificationView = new ClassificationView();
        classificationView.show();
    }
    public void goToConfiguration(ActionEvent actionEvent) {
        config = new Configuration();
        config.show();
    }
}
