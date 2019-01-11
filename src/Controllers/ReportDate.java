package Controllers;

import DatabaseConnection.dbConnection;
import Model.Expense;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
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

public class ReportDate implements Initializable {

    Connection connection;
    Stage reportDateStage;


    @FXML ListView listView_date;
    @FXML Label lbl_total_date;
    @FXML DatePicker date1;
    @FXML DatePicker date2;

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
        reportDateStage = new Stage();
        try {
            FXMLLoader reportDateLoader = new FXMLLoader();
            Pane reportDateRoot = (Pane) reportDateLoader.load(getClass().getResource("/Layouts/ReportDate.fxml").openStream());

            ReportDate classificationButtonsController = (ReportDate) reportDateLoader.getController();
            Scene scene = new Scene(reportDateRoot);
            this.reportDateStage.setScene(scene);
            this.reportDateStage.setTitle("");
            this.reportDateStage.setResizable(false);
            this.reportDateStage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void close(){
        this.reportDateStage.close();
    }

    public void searchByDate(ActionEvent actionEvent) {
        try{
            String date1_string = date1.getValue().toString();
            String date2_string = date2.getValue().toString();

            if(!date1_string.equals("") && !date2_string.equals("")){
                consultExpenseListByDate(date1_string, date2_string);
                double amount = getTotal(date1_string, date2_string);
                lbl_total_date.setText(String.valueOf(amount));
                System.out.println("FECHA 1 " + date1_string + "FECHA 2 " + date2_string);
            }

        }catch (Exception ex){
            System.out.print("que pasion");
        }

    }

    public void consultExpenseListByDate(String date1, String date2){
        ArrayList<Expense> expense_list_expense;
        ObservableList<Expense> data_expense;
        data_expense = FXCollections.observableArrayList();
        connection = dbConnection.getConnection();
        Expense expense = null;
        expense_list_expense = new ArrayList<>();

        try {
            String SQL = "select * from expense where dateb between " + "'"+date1+ "' AND " + "'"+date2+"'" ;
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
            listView_date.getItems().addAll(data_expense);
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

    public double getTotal(String date1, String date2){
        double amount=0;
        String query = "select sum(amount) from expense where dateb between " + "'"+date1 + "' AND " + "'"+date2+"'";
        try{
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        date1.setEditable(false);
        date2.setEditable(false);
    }
}
