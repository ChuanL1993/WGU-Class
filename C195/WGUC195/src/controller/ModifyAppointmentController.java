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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.DataPresent;
import model.User;
import utils.DBConnection;
import utils.DBQuery;

/**
 * FXML Controller class for modify appointment page. This class implement
 * necessary text field, label and buttons and table view that used on the
 * modifyAppointment page. It includes DateTimeFormatter for implement need.
 *
 * The future idea of this page is to add a timezone list for user to choose
 * from.
 *
 * @author Chuan
 */
public class ModifyAppointmentController implements Initializable {

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

    @FXML
    void onActionSetST(ActionEvent event) {
        if (datePicker.getValue() != null) {
            localSTLbl.setText(ZonedDateTime.of(datePicker.getValue(), startCBBox.getValue(), ZoneId.of("America/New_York")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime().toString());
        }

    }

    @FXML
    void onActionSetET(ActionEvent event) {
        if (datePicker.getValue() != null) {
            localETLbl.setText(ZonedDateTime.of(datePicker.getValue(), endCBBox.getValue(), ZoneId.of("America/New_York")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime().toString());
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

        if (datePicker.getValue() != null && startCBBox.getValue() != null && endCBBox.getValue() != null && custIdCBBox.getValue() != null && (!(idTxt.getText().isEmpty()))) {
            LocalDateTime inST = ZonedDateTime.of(datePicker.getValue(), startCBBox.getValue(), ZoneId.of("America/New_York")).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
            LocalDateTime inET = ZonedDateTime.of(datePicker.getValue(), endCBBox.getValue(), ZoneId.of("America/New_York")).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();

            String aptIdS = idTxt.getText();
            String id = (custIdCBBox.getValue()).toString();
            Connection con = DBConnection.getConnection();
            Statement stmt = DBQuery.getStatemnt();
            String pullTime = "select Start, End from appointments where Customer_ID=" + id + " and Appointment_ID!=" + aptIdS + ";";
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

            String insertStmt = "update appointments "
                    + "set Title = ?,Description = ?,Location= ?,Type = ?, Start = ?,"
                    + "End = ?, Last_Update = CURRENT_TIMESTAMP,Last_Updated_By =?,"
                    + "User_ID = ?, Contact_ID =? where Appointment_ID =" + aptId + ";";
            DBQuery.setPreparedStatement(conn, insertStmt);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setString(5, startDT);
            ps.setString(6, endDT);
            ps.setString(7, userName);

            ps.setInt(8, userId);
            ps.setInt(9, contactId);

            ps.execute();

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Appointment update successfully");
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
 * This method grab the data and populate the correct text field and combo box.
 * DatePicker and time combo box have do some conversion to show correct time and 
 * date.
 * @param apt the appointment object which need to be modified.
 */
    public void setField(Appointment apt) {
        idTxt.setText(String.valueOf(apt.getAptId()));
        //custIdCBBox.setItems(apt.getCustId());
        titleTxt.setText(apt.getTitle());
        typeTxt.setText(apt.getType());
        descriptionTxt.setText(apt.getDescription());
        locationTxt.setText(apt.getLocation());
        datePicker.setValue(ZonedDateTime.of(apt.getStart(), ZoneId.of("UTC")).withZoneSameInstant(ZoneId.of("America/New_York")).toLocalDate());
        startCBBox.setValue(ZonedDateTime.of(apt.getStart(), ZoneId.of("UTC")).withZoneSameInstant(ZoneId.of("America/New_York")).toLocalTime());
        endCBBox.setValue(ZonedDateTime.of(apt.getEnd(), ZoneId.of("UTC")).withZoneSameInstant(ZoneId.of("America/New_York")).toLocalTime());
        for (Contact c : contactCBBox.getItems()) {
            if (apt.getContactId() == c.getContactId()) {
                contactCBBox.setValue(c);
                break;
            }
        }
        for (User u : userCBBox.getItems()) {
            if (apt.getUserId() == u.getUserId()) {
                userCBBox.setValue(u);
                break;
            }
        }
        for (int i : custIdCBBox.getItems()) {
            if (apt.getCustId() == i) {
                custIdCBBox.setValue(i);
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
