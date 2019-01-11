package Controllers;

import DatabaseConnection.dbConnection;
import Model.Expense;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
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

public class ReportGeneral implements Initializable {

    Connection connection;
    Stage reportGeneralStage;
    ArrayList<Expense> expense_list_expense;
    ObservableList<Expense> data_expense;

    @FXML private ListView listView_general_report;
    @FXML private Label lbl_total;

    public void CheckConnection() {
        connection = dbConnection.getConnection();
        if(connection == null){
            System.out.println("Connection Not succesful");
            System.exit(1);
        }else {
            System.out.println("Connection Succesful");
        }
    }

    public void buildDataGeneralReport(){
        data_expense = FXCollections.observableArrayList();
        connection = dbConnection.getConnection();
        Expense expense = null;
        expense_list_expense = new ArrayList<>();

        try {
            String SQL = "select * from expense";
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
            listView_general_report.getItems().addAll(data_expense);
            double amount = getTotal();
            lbl_total.setText(String.valueOf(amount));
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
        reportGeneralStage = new Stage();
        try {
            FXMLLoader reportGeneralLoader = new FXMLLoader();
            Pane reportGeneralRoot = (Pane) reportGeneralLoader.load(getClass().getResource("/Layouts/ReportGeneral.fxml").openStream());

            ReportGeneral classificationButtonsController = (ReportGeneral) reportGeneralLoader.getController();
            Scene scene = new Scene(reportGeneralRoot);
            this.reportGeneralStage.setScene(scene);
            this.reportGeneralStage.setTitle("");
            this.reportGeneralStage.setResizable(false);
            this.reportGeneralStage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public double getTotal(){
        double amount=0;
        String query = "select sum(amount) from expense";
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
    public void close(){
        this.reportGeneralStage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buildDataGeneralReport();
    }
}
