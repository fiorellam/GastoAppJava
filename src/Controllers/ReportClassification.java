package Controllers;

import DatabaseConnection.dbConnection;
import Model.Classification;
import Model.Expense;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReportClassification implements Initializable {

    Connection connection;
    Stage reportClassificationStage;
    @FXML ListView listView_classification;
    @FXML Label lbl_total_classification;
    @FXML ComboBox<Classification> combo_class;
    ArrayList<Classification> classification_list_classification;
    ObservableList<Classification> data;

    ObservableList<Expense> data_expense = FXCollections.observableArrayList();


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
        reportClassificationStage = new Stage();
        try {
            FXMLLoader reportClassificationLoader = new FXMLLoader();
            Pane reportDateRoot = (Pane) reportClassificationLoader.load(getClass().getResource("/Layouts/ReportClassification.fxml").openStream());

            ReportClassification classificationButtonsController = (ReportClassification) reportClassificationLoader.getController();
            Scene scene = new Scene(reportDateRoot);
            this.reportClassificationStage.setScene(scene);
            this.reportClassificationStage.setTitle("");
            this.reportClassificationStage.setResizable(false);
            this.reportClassificationStage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    //combobox sql connection
    public void buildData(){
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
            combo_class.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error Building ComboBox Data");
        }
        if (connection == null) {
            System.exit(1);
            System.out.println("Connection failed");
        }
    }

    public void close(){
        this.reportClassificationStage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buildData();
    }

    public void searchByClassification(ActionEvent actionEvent) {
        Classification s = combo_class.getSelectionModel().getSelectedItem();
        String classification_name = String.valueOf(combo_class.getSelectionModel().getSelectedItem());
        int selectedid = s.getClassification_id();
        String selectedname = s.getNamee();
        try{
//            String date2_string = date2.getValue().toString();
            if(!classification_name.equals("")){
                consultExpenseListByClassification(selectedid);
                lbl_total_classification.setText(String.valueOf(getTotal(selectedid)));
            }

        }catch (Exception ex){
            System.out.print("que pasion");
        }

    }

    public void consultExpenseListByClassification(int classification_id){
        ArrayList<Expense> expense_list_expense;
        connection = dbConnection.getConnection();
        Expense expense = null;
        expense_list_expense = new ArrayList<>();

        try {
            String SQL = "select * from expense where classification_id="+ classification_id ;
            ResultSet rs = connection.createStatement().executeQuery(SQL);
            while(rs.next()){
                expense = new Expense();
                expense.set_id(rs.getInt("expense_id"));
                expense.set_concept(rs.getString("concept_name"));
                expense.set_amount(rs.getDouble("amount"));
                expense.set_date(rs.getString("dateb"));
                expense.set_classification(rs.getInt("classification_id"));

                data_expense.add(expense);
            }
            listView_classification.getItems().addAll(data_expense);
//            double amount = getTotal();
//            lbl_total.setText(String.valueOf(amount));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error Building ComboBox Data");
        }
        if (connection == null) {
            System.exit(1);
            System.out.println("Connection failed");
        }
    }

    public double getTotal(int classification_id){
        double amount=0;
        try{
        String query = "select sum(amount) from expense where classification_id =" + classification_id;

            connection = dbConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                amount= rs.getDouble(1);
            }
//            System.out.println(String.valueOf(amount));
        }catch (SQLException e){
            System.err.println(e);
        }
        return amount;
    }

    public void deleteListView(ActionEvent actionEvent) {
//        listView_classification.refresh();
        data_expense.removeAll();
        this.listView_classification.getItems().addAll(data_expense);
    }
}
