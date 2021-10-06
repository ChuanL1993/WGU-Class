/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import model.Contact;
import model.DataPresent;
import model.User;
import java.sql.Connection;
import java.sql.SQLException;
import utils.DBConnection;
import utils.DBQuery;
import java.sql.Statement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.PreparedStatement;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

/**
 * FXML Controller class for new appointment page. This class implement
 * necessary text field, label and buttons and table view that used on the
 * newAppointment page. It includes DateTimeFormatter for implement need.
 *
 * The future idea of this page is to add a timezone list for user to choose
 * from.
 *
 * @author heych
 */
public class NewAppointmentController implements Initializable {

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    Stage stage;
    Parent scene;
    @FXML
    private Label warnLbl;

    @FXML
    private ComboBox<Contact> contactCBBox;

    @FXML
    private ComboBox<User> userCBBox;

    @FXML
    private ComboBox<LocalTime> startCBBox;

    @FXML
    private ComboBox<LocalTime> endCBBox;

    @FXML
    private TextField idTxt;

    @FXML
    private ComboBox<Integer> custIdCBBox;

    @FXML
    private TextField typeTxt;

    @FXML
    private TextField locationTxt;

    @FXML
    private TextField titleTxt;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField descriptionTxt;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button saveBtn;

    @FXML
    private Label localETLbl;

    @FXML
    private Label localSTLbl;

    //Lambda expression to conver an date and time object from EST to a localdatetime object.
    //This override the method in ESTtoLDT interface with its own body detail.
    ESTtoLDT c = (d, t) -> ZonedDateTime.of(d, t, ZoneId.of("America/New_York")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();

    @FXML
    void onActionSetST(ActionEvent event) {
        if (datePicker.getValue() != null) {
            //Lambda is used
            localSTLbl.setText(c.conver(datePicker.getValue(), startCBBox.getValue()).toString());
        }
        //localSTLbl.setText(ZonedDateTime.of(datePicker.getValue(), startCBBox.getValue(), ZoneId.of("America/New_York")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime().toString());

    }

    @FXML
    void onActionSetET(ActionEvent event) {
        if (datePicker.getValue() != null) {
            //Lambda is used.
            localETLbl.setText(c.conver(datePicker.getValue(), endCBBox.getValue()).toString());
            //localETLbl.setText(ZonedDateTime.of(datePicker.getValue(), endCBBox.getValue(), ZoneId.of("America/New_York")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime().toString());
        }

    }

    @FXML
    void onActionSave(ActionEvent event) throws SQLException, IOException {
        int aptId = 0, custId = 0, contactId = 0, userId = 0;
        String title = null, type = null, description = null, location = null, contactName = null, userName = null;
        LocalDate date = null;
        LocalTime start = null, end = null;
        String startDT = null;
        String endDT = null;
        String warning = "Exception:\n";
        if (!(idTxt.getText()).isEmpty()) {
            boolean isGood = true;
            try {
                Integer.parseInt(idTxt.getText());
            } catch (NumberFormatException e) {
                isGood = false;
                warning += "ID is not valid!\n";
            }
            if (isGood) {
                aptId = Integer.parseInt(idTxt.getText());
            }

        }

        if (!(titleTxt.getText()).isEmpty()) {
            title = titleTxt.getText();
        } else {
            warning += "Title cannot be empty!\n";
        }

        if (!(typeTxt.getText()).isEmpty()) {
            type = typeTxt.getText();
        } else {
            warning += "Type cannot be empty!\n";
        }

        if (!(descriptionTxt.getText()).isEmpty()) {
            description = descriptionTxt.getText();
        } else {
            warning += "Description cannot be empty!\n";
        }

        if (!(locationTxt.getText()).isEmpty()) {
            location = locationTxt.getText();
        } else {
            warning += "Location cannot be empty!\n";
        }

        if (custIdCBBox.getValue() != null) {
            custId = custIdCBBox.getValue();
        } else {
            warning += "Please select a customerID!\n";
        }

        if (datePicker.getValue() != null) {
            date = datePicker.getValue();
        } else {
            warning += "Please select a date!\n";
        }

        if (startCBBox.getValue() != null) {
            start = startCBBox.getValue();
        } else {
            warning += "Please select a start time!\n";
        }

        if (endCBBox.getValue() != null) {
            end = endCBBox.getValue();
        } else {
            warning += "Please select an end time!\n";
        }
        if (end != null && start != null) {
            if (end.isBefore(start)) {
                warning += "Please adjust right start and end time!\n";
            }
        }

        if (contactCBBox.getValue() != null) {
            contactId = contactCBBox.getValue().getContactId();
            contactName = contactCBBox.getValue().getContactName();
        } else {
            warning += "please select a contact!\n";
        }

        if (userCBBox.getValue() != null) {
            userId = userCBBox.getValue().getUserId();
            userName = userCBBox.getValue().getUserName();
        } else {
            warning += "please select an user!\n";
        }

        if (datePicker.getValue() != null && startCBBox.getValue() != null && endCBBox.getValue() != null && custIdCBBox.getValue() != null) {
            LocalDateTime inST = ZonedDateTime.of(datePicker.getValue(), startCBBox.getValue(), ZoneId.of("America/New_York")).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
            LocalDateTime inET = ZonedDateTime.of(datePicker.getValue(), endCBBox.getValue(), ZoneId.of("America/New_York")).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();

            String id = (custIdCBBox.getValue()).toString();
            Connection con = DBConnection.getConnection();
            Statement stmt = DBQuery.getStatemnt();
            String pullTime = "select Start, End from appointments where Customer_ID=" + id + ";";
            stmt.execute(pullTime);
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                LocalDateTime tempST = LocalDateTime.parse(rs.getString("Start"), dtf);
                LocalDateTime tempET = LocalDateTime.parse(rs.getString("End"), dtf);

                if ((inST.isAfter(tempST.minusSeconds(1)) && inST.isBefore(tempET.plusSeconds(1)))
                        || (inET.isAfter(tempST.minusSeconds(1)) && inET.isBefore(tempET.plusSeconds(1)))
                        || (tempST.isAfter(inST.minusSeconds(1)) && tempST.isBefore(inET.plusSeconds(1)))
                        || (tempET.isAfter(inST.minusSeconds(1)) && tempET.isBefore(inET.plusSeconds(1)))) {

                    warning += "This customer has existing appointment at selected period of time, please make an adjustment!\n";

                }
            }
            rs.close();
        }

        if (date != null && start != null && end
                != null) {
            LocalDateTime inST = ZonedDateTime.of(datePicker.getValue(), startCBBox.getValue(), ZoneId.of("America/New_York")).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
            LocalDateTime inET = ZonedDateTime.of(datePicker.getValue(), endCBBox.getValue(), ZoneId.of("America/New_York")).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
            startDT = dtf.format(inST);
            endDT = dtf.format(inET);
        }

        if ((warning == "Exception:\n")) {

            Connection conn = DBConnection.getConnection();

            String insertStmt = "insert into appointments "
                    + "(Appointment_ID, Title, Description, Location, Type, Start, End, "
                    + "Create_Date, Created_By, Last_Updated_By, Customer_ID, User_ID, Contact_ID)"
                    + " value(?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?, ?, ?, ?, ?)";
            DBQuery.setPreparedStatement(conn, insertStmt);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setInt(1, aptId);
            ps.setString(2, title);
            ps.setString(3, description);
            ps.setString(4, location);
            ps.setString(5, type);
            ps.setString(6, startDT);
            ps.setString(7, endDT);
            ps.setString(8, userName);
            ps.setString(9, userName);
            ps.setInt(10, custId);
            ps.setInt(11, userId);
            ps.setInt(12, contactId);

            ps.execute();

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Appointment created");
            Optional<ButtonType> result = alert.showAndWait();

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        } else {
            warnLbl.setText(warning);

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
     * Initializes the controller class. This method get called first when the
     * login page is loaded This method also popular the combo box, when the
     * page is loaded, correct information is shown.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        if (!(DataPresent.getAllContacts().isEmpty())) {
            DataPresent.getAllContacts().clear();
        }

        if (!(DataPresent.getAllUsers().isEmpty())) {
            DataPresent.getAllUsers().clear();
        }

        Connection conn = DBConnection.getConnection();

        Statement stmt = DBQuery.getStatemnt();

        //Methods below retive data for contact ComboBox
        String pullContact = "Select * from contacts;";
        try {
            stmt.execute(pullContact);
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                int id = rs.getInt("Contact_ID");
                String name = rs.getString("Contact_Name");
                String email = rs.getString("Email");

                DataPresent.addAllContacs(new Contact(id, name, email));
            }
            rs.close();

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

        // method below retrive max id from appointment id from database and incease it by 1 and set value to id textfield
        String getId = "Select max(Appointment_ID) from appointments;";
        try {
            stmt.execute(getId);
            ResultSet rs = stmt.getResultSet();
            if (rs.next()) {
                idTxt.setText(String.valueOf(rs.getInt("max(Appointment_ID)") + 1));
            } else {
                idTxt.setText("1");
            }

            rs.close();
        } catch (SQLException ex) {
            System.out.println(ex.getStackTrace());
        }

        // retrive customerID from database to let user to choose from.
        String getCustId = "Select Customer_ID from customers order by Customer_ID;";
        try {
            stmt.execute(getCustId);
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                custIdCBBox.getItems().add(rs.getInt("Customer_ID"));
            }
            rs.close();
        } catch (SQLException ex) {
            System.out.println(ex.getStackTrace());
        }

        //add data fro time Combo box
        LocalTime start = LocalTime.of(8, 0, 0);
        LocalTime end = LocalTime.of(22, 0, 0);

        while (start.isBefore(end.plusSeconds(1))) {
            startCBBox.getItems().add(start);
            endCBBox.getItems().add(start);
            start = start.plusMinutes(15);
        }

        //Setup combo box for time.
        startCBBox.setPromptText("Select start time...");
        endCBBox.setPromptText("Select end time...");

        // Setup the Combo box of user and contact
        contactCBBox.setPromptText("Select a contact...");
        contactCBBox.setItems(DataPresent.getAllContacts());

        userCBBox.setPromptText("Selecr an user...");
        userCBBox.setItems(DataPresent.getAllUsers());

    }

}
