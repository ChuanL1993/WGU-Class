/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.DataPresent;
import model.Division;
import model.User;
import utils.DBConnection;
import utils.DBQuery;

/**
 * FXML Controller class for modify customer page. This class implement
 * necessary text field, label and buttons and table view that used on the
 * modifyAppointment page. It includes DateTimeFormatter for implement need.
 *
 * The future idea of this page is to add a feature to add city section and auto
 * fill the zip code
 *
 * @author Chuan
 */
public class ModifyCustomerController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TextField custIdTxt;

    @FXML
    private TextField phoneTxt;

    @FXML
    private TextField addressTxt;

    @FXML
    private ComboBox<Division> stateCBBox;

    @FXML
    private TextField nameTxt;

    @FXML
    private ComboBox<Country> countryCBBox;

    @FXML
    private TextField postalTxt;

    @FXML
    private Label warningLbl;

    @FXML
    private ComboBox<User> userCBBox;

    @FXML
    void onActionState(ActionEvent event) {
        Division d = stateCBBox.getSelectionModel().getSelectedItem();
        for (Country c : countryCBBox.getItems()) {
            if (d.getCountryId() == c.getCountryId()) {
                countryCBBox.setValue(c);
                break;
            }
        }

    }

    @FXML
    void onActionSave(ActionEvent event) throws SQLException, IOException {
        int custId = 0;
        int division = 0;
        String name = null, phone = null, address = null, postal = null, userName = null, warning = "Exception:\n";
        if (!(custIdTxt.getText()).isEmpty()) {
            boolean isGood = true;
            try {
                Integer.parseInt(custIdTxt.getText());
            } catch (NumberFormatException e) {
                isGood = false;
                warning += "ID is not valid!\n";
            }
            if (isGood) {
                custId = Integer.parseInt(custIdTxt.getText());
            }
        }

        if (!(nameTxt.getText()).isEmpty()) {
            name = nameTxt.getText();
        } else {
            warning += "Name cannot be empty!\n";
        }

        if (!(phoneTxt.getText()).isEmpty()) {
            phone = phoneTxt.getText();
        } else {
            warning += "Phone cannot be empty!\n";
        }

        if (!(addressTxt.getText()).isEmpty()) {
            address = addressTxt.getText();
        } else {
            warning += "Address cannot be empty!\n";
        }

        if (!(postalTxt.getText()).isEmpty()) {
            postal = postalTxt.getText();
        } else {
            warning += "Postal Code cannot be empty!\n";
        }

        if (stateCBBox.getValue() != null) {
            division = stateCBBox.getValue().getDivisionId();
        } else {
            warning += "please select a contact!\n";
        }

        if (userCBBox.getValue() != null) {
            //userId = userCBBox.getValue().getUserId();
            userName = userCBBox.getValue().getUserName();
        } else {
            warning += "please select an user!\n";
        }

        if ((warning == "Exception:\n")) {
            Connection conn = DBConnection.getConnection();
            String insertStmt = "update customers "
                    + "set Customer_Name=?,Address =?,Postal_Code =?,Phone =?,"
                    + "Last_Updated_By=? , Last_Update =CURRENT_TIMESTAMP, "
                    + "Division_ID=? where Customer_ID= ?;";
            DBQuery.setPreparedStatement(conn, insertStmt);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postal);
            ps.setString(4, phone);
            ps.setString(5, userName);
            ps.setInt(6, division);
            ps.setInt(7, custId);

            ps.execute();

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Customer created");
            Optional<ButtonType> result = alert.showAndWait();

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        } else {
            warningLbl.setText(warning);
        }
    }

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * This method grab the data and populate the correct text field and combo
     * box. 
     * use for loop to choose correct country and division match the customer
     * information
     *
     * @param c the customer that need to be modify
     */
    public void setField(Customer c) {
        custIdTxt.setText(String.valueOf(c.getCustomerId()));
        nameTxt.setText(c.getCustomerName());
        phoneTxt.setText(c.getPhone());
        addressTxt.setText(c.getAddress());
        postalTxt.setText(c.getPostalCode());
        for (Division d : stateCBBox.getItems()) {
            if (d.getDivisionId() == c.getDivisionId()) {
                stateCBBox.setValue(d);
                for (Country cou : countryCBBox.getItems()) {
                    if (cou.getCountryId() == d.getCountryId()) {
                        countryCBBox.setValue(cou);
                    }
                    break;
                }
                break;
            }
        }
    }

    /**
     * Initializes the controller class. This method get called first when the
     * login page is loaded This method also popular the combo box, when the
     * page is loaded, correct information is shown.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        if (!(DataPresent.getAllCountries().isEmpty())) {
            DataPresent.getAllCountries().clear();
        }

        if (!(DataPresent.getAllDivisions().isEmpty())) {
            DataPresent.getAllDivisions().clear();
        }
        if (!(DataPresent.getAllUsers().isEmpty())) {
            DataPresent.getAllUsers().clear();
        }
        // TODO
        Connection conn = DBConnection.getConnection();
        Statement stmt = DBQuery.getStatemnt();

        //Retire data from data base for country combo box 
        String pullContry = "select * from countries;";
        try {
            stmt.execute(pullContry);
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                DataPresent.addAllCountries(new Country(rs.getInt("Country_ID"), rs.getString("Country")));
            }
            rs.close();
        } catch (SQLException ex) {
            System.out.println(ex.getStackTrace());
        }

        //Methods to get the id for customer
        String getCustId = "Select max(Customer_ID) from customers;";

        try {
            stmt.execute(getCustId);
            ResultSet rs = stmt.getResultSet();
            if (rs.next()) {
                custIdTxt.setText(String.valueOf(rs.getInt("max(Customer_ID)") + 1));
            } else {
                custIdTxt.setText("1");
            }
            rs.close();
        } catch (SQLException ex) {
            System.out.println(ex.getStackTrace());
        }

        //Retrive data for state combo box
        String pullDivision = "select * from first_level_divisions;";
        try {
            stmt.execute(pullDivision);
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                DataPresent.addAllDivisions(new Division(rs.getInt("Division_ID"), rs.getString("Division"), rs.getInt("COUNTRY_ID")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getStackTrace());
        }

        //Method below retrive data for user ComboBox
        String pullUser = "Select * from users";
        try {
            stmt.execute(pullUser);
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                int id = rs.getInt("User_ID");
                String name = rs.getString("User_Name");
                String ps = rs.getString("Password");

                DataPresent.addAllUsers(new User(id, name, ps));
            }
            rs.close();

        } catch (SQLException ex) {
            System.out.println(ex.getStackTrace());
        }

        //Set up country combo box
        countryCBBox.setItems(DataPresent.getAllCountries());
        //countryCBBox.setPromptText("Select a country...");

        //Set up state combo box
        stateCBBox.setItems(DataPresent.getAllDivisions());
        stateCBBox.setPromptText("Select a state...");

        userCBBox.setPromptText("Selecr an user...");
        userCBBox.setItems(DataPresent.getAllUsers());

    }

}
