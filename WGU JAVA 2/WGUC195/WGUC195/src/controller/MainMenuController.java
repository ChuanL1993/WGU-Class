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
import javafx.scene.control.Label;
import model.User;
import utils.DBConnection;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.CallableStatement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import model.Appointment;
import model.DataPresent;
import utils.DBQuery;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import model.Report;

/**
 * FXML Controller class for Main Menu page. This class implement necessary text
 * field, label and buttons and table view that used on the MainMenu page. It
 * includes DateTimeFormatter for implement need.
 *
 * The future idea for this page is to add user section for user to add, modify
 * and even delete. It is current disabled.
 *
 * @author Chuan
 */
public class MainMenuController implements Initializable {

    Connection conn = DBConnection.getConnection();
    /*
        try {
            DBQuery.setStatemnt(conn);
        } catch (SQLException ex) {
            System.out.println(ex.getStackTrace());
        }
     */
    Statement statement = DBQuery.getStatemnt();

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    Stage stage;
    Parent scene;
    @FXML
    private Button newCustBtn;

    @FXML
    private TableColumn<Appointment, String> locationCol;

    @FXML
    private TableColumn<Appointment, String> typeCol;

    @FXML
    private TableColumn<Appointment, Integer> custIdCol;

    @FXML
    private TableColumn<Appointment, Integer> aptIdCol;

    @FXML
    private TableColumn<Appointment, String> titleCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> startCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> endCol;

    @FXML
    private TableColumn<Appointment, String> contactCol;

    @FXML
    private TableView<Appointment> aptTbl;

    @FXML
    private TableColumn<Appointment, String> descriptionCol;

    @FXML
    private TableColumn<Customer, String> custNameCol;

    @FXML
    private TableColumn<Customer, String> addressCol;

    @FXML
    private TableColumn<Customer, String> postalCodeCol;

    @FXML
    private TableView<Customer> custTbl;

    @FXML
    private TableColumn<Customer, String> divisionCol;

    @FXML
    private TableColumn<Customer, String> phoneCol;

    @FXML
    private TableColumn<Customer, Integer> custId2Col;

    @FXML
    private Label userLbl;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ToggleGroup dateFilter;

    @FXML
    private ToggleGroup report;

    @FXML
    private ListView<Report> reportView;

    @FXML
    void onActionTotal(ActionEvent event) throws SQLException {
        if (!(DataPresent.getAllReports().isEmpty())) {
            DataPresent.getAllReports().clear();
        }
        String countAll = "Select count(Appointment_ID) from appointments;";
        statement.execute(countAll);
        ResultSet rs = statement.getResultSet();
        if (rs.next()) {
            int i = rs.getInt("count(Appointment_ID)");
            DataPresent.addAllReports(new Report(i, "Total number of Appointment:"));
            reportView.setItems(DataPresent.getAllReports());

        }
        rs.close();

    }

    @FXML
    void onActionType(ActionEvent event) throws SQLException {
        if (!(DataPresent.getAllReports().isEmpty())) {
            DataPresent.getAllReports().clear();
        }
        String countByType = "select count(Type),Type from appointments group by Type;";
        statement.execute(countByType);
        ResultSet rs = statement.getResultSet();
        while (rs.next()) {
            int i = rs.getInt("count(Type)");
            String s = "Number of appointments of type " + rs.getString("Type") + " is: ";
            DataPresent.addAllReports(new Report(i, s));
        }
        rs.close();
        reportView.setItems(DataPresent.getAllReports());
    }

    @FXML
    void onActionUser(ActionEvent event) throws SQLException {
        if (!(DataPresent.getAllReports().isEmpty())) {
            DataPresent.getAllReports().clear();
        }
        String countByUser = "select count(Created_By),Created_By from appointments group by Created_By;";
        statement.execute(countByUser);
        ResultSet rs = statement.getResultSet();
        while (rs.next()) {
            int i = rs.getInt("count(Created_By)");
            String s = "Number of appointments created by " + rs.getString("Created_By") + " is: ";
            DataPresent.addAllReports(new Report(i, s));
        }
        rs.close();
        reportView.setItems(DataPresent.getAllReports());
    }

    @FXML
    void onActionTime(ActionEvent event) throws SQLException {
        if (!(DataPresent.getAllReports().isEmpty())) {
            DataPresent.getAllReports().clear();
        }
        String countByTime = "select min(start),max(start) from appointments";
        statement.execute(countByTime);
        ResultSet rs = statement.getResultSet();
        if (rs.next()) {
            String s = " The earliest appointment of all is on " + rs.getString("min(start)") + "--------";
            DataPresent.addAllReports(new Report(0, s));
            String s2 = " The latest appointment of all is on " + rs.getString("max(start)") + "--------";
            DataPresent.addAllReports(new Report(0, s2));
        }
        rs.close();
        reportView.setItems(DataPresent.getAllReports());
    }

    @FXML
    void onActionByWeek(ActionEvent event) {
        if (!(DataPresent.getAllAppointments().isEmpty())) {
            DataPresent.getAllAppointments().clear();
        }
        String selectStatment = "Select appointments.*,contacts.Contact_Name from appointments inner join contacts on appointments.Contact_ID=contacts.Contact_ID;";

        try {
            statement.execute(selectStatment);
        } catch (SQLException ex) {
            System.out.println(ex.getStackTrace());
        }
        try {
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                int ID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = LocalDateTime.parse(rs.getString("Start"), dtf);
                int custId = rs.getInt("Customer_ID");
                LocalDateTime end = LocalDateTime.parse(rs.getString("End"), dtf);
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                if (datePicker.getValue() != null) {
                    if (datePicker.getValue().getYear() == start.getYear()) {
                        if (datePicker.getValue().get(WeekFields.of(Locale.getDefault()).weekOfYear()) == start.get(WeekFields.of(Locale.getDefault()).weekOfYear())) {
                            DataPresent.addAllAppointments(new Appointment(ID, title, description, location, type, start, end, custId, userId, contactId, contactName));
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getStackTrace());
        }

    }

    @FXML
    void onActionByMonth(ActionEvent event) {
        if (!(DataPresent.getAllAppointments().isEmpty())) {
            DataPresent.getAllAppointments().clear();
        }
        String selectStatment = "Select appointments.*,contacts.Contact_Name from appointments inner join contacts on appointments.Contact_ID=contacts.Contact_ID;";

        try {
            statement.execute(selectStatment);
        } catch (SQLException ex) {
            System.out.println(ex.getStackTrace());
        }
        try {
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                int ID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = LocalDateTime.parse(rs.getString("Start"), dtf);
                int custId = rs.getInt("Customer_ID");
                LocalDateTime end = LocalDateTime.parse(rs.getString("End"), dtf);
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                if (datePicker.getValue() != null) {
                    if (datePicker.getValue().getYear() == start.getYear()) {
                        if (datePicker.getValue().getMonth() == start.getMonth()) {
                            DataPresent.addAllAppointments(new Appointment(ID, title, description, location, type, start, end, custId, userId, contactId, contactName));
                        }
                    }
                }
                /*else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Please select a date");
                    alert.showAndWait();
                }*/
            }
        } catch (SQLException ex) {
            System.out.println(ex.getStackTrace());
        }
    }

    @FXML
    void onActionAll(ActionEvent event) {
        if (!(DataPresent.getAllAppointments().isEmpty())) {
            DataPresent.getAllAppointments().clear();
        }

        String selectStatment = "Select appointments.*,contacts.Contact_Name from appointments inner join contacts on appointments.Contact_ID=contacts.Contact_ID;";

        try {
            statement.execute(selectStatment);
        } catch (SQLException ex) {
            System.out.println(ex.getStackTrace());
        }

        try {
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                int ID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = LocalDateTime.parse(rs.getString("Start"), dtf);
                int custId = rs.getInt("Customer_ID");
                LocalDateTime end = LocalDateTime.parse(rs.getString("End"), dtf);
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");

                DataPresent.addAllAppointments(new Appointment(ID, title, description, location, type, start, end, custId, userId, contactId, contactName));

            }
            rs.close();
        } catch (SQLException ex) {
            System.out.println(ex.getStackTrace());
        }
        /*
        aptTbl.setItems(DataPresent.getAllAppointments());

        aptIdCol.setCellValueFactory(new PropertyValueFactory<>("aptId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("ST"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("ED"));
        custIdCol.setCellValueFactory(new PropertyValueFactory<>("custId"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
         */
    }

    @FXML
    void onActionModApt(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyAppointment.fxml"));
            loader.load();

            ModifyAppointmentController MAController = loader.getController();
            MAController.setField(aptTbl.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select an item");
            alert.showAndWait();
        }
    }

    @FXML
    void onActionNewApt(ActionEvent event) throws IOException {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/NewAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void onActionDelApt(ActionEvent event) throws SQLException, IOException {
        //Connection conn = DBConnection.getConnection();
        Statement stmt = DBQuery.getStatemnt();
        try {
            Appointment toDel = aptTbl.getSelectionModel().getSelectedItem();
            int toDelId = toDel.getAptId();
            String delStmt = " delete from appointments where Appointment_ID = " + toDelId + ";";

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to cancel this appointment?\n"
                    + "Appoinetment ID: " + toDelId + "       Type: " + toDel.getTitle() + ".");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                stmt.execute(delStmt);
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION,"Appoinetment ID: " + toDelId + "       Type: " + toDel.getTitle()+" is cancelled");
                alert2.showAndWait();
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select an item");
            alert.showAndWait();
        }
    }

    @FXML
    void onActionDelCust(ActionEvent event) throws SQLException, IOException {
        //Connection conn = DBConnection.getConnection();
        Statement stmt = DBQuery.getStatemnt();
        try {

            int ToDel = custTbl.getSelectionModel().getSelectedItem().getCustomerId();
            String delStmt = " delete from customers where Customer_ID = " + ToDel + ";";

            String pullApt = "Select * from appointments where Customer_ID = " + ToDel + ";";
            stmt.execute(pullApt);
            ResultSet rs = stmt.getResultSet();
            if (rs.next()) {
                Alert alert1 = new Alert(Alert.AlertType.WARNING, "This customer have associated appointment.\n Please delete the appointment first!");
                alert1.showAndWait();
            } else {

                Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this item?");

                Optional<ButtonType> result = alert2.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    stmt.execute(delStmt);
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();

                }
            }
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select an item");
            alert.showAndWait();
        }

    }

    @FXML
    void onActionNewCust(ActionEvent event) throws IOException {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/NewCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void onActionModCust(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyCustomer.fxml"));
            loader.load();

            ModifyCustomerController MCController = loader.getController();
            MCController.setField(custTbl.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select an item");
            alert.showAndWait();
        }
    }

    /**
     * This method display user information when user login.
     *
     * @param user the login user
     */
    public void setUser(User user) {
        userLbl.setText("ID: " + user.getUserId() + " User:" + user.getUserName());
    }

    /**
     * This method check appointment within 15 minutes of user login time. The
     * method would show two different alerts base on the situation
     */
    public void checkApt() {
        LocalDateTime login = LocalDateTime.now();
        boolean hasApt = false;

        for (Appointment a : DataPresent.getAllAppointments()) {

            if (a.getST().isAfter(login.minusMinutes(1)) && a.getST().isBefore(login.plusMinutes(16))) {
                hasApt = true;
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "You have an appointment in 15 mins. \n"
                        + "Appointment ID: " + a.getAptId() + "\n"
                        + "Start Time:" + a.getST() + ".");
                alert1.showAndWait();
                break;
            }
        }
        if (hasApt == false) {
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION, "You have no appointment in next 15 mins.");
            alert2.showAndWait();
        }
    }

    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);

    }

    /**
     * Initializes the controller class. This method get called first when the
     * login page is loaded This method also popular the table view, when the
     * page is loaded, correct information is shown.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Clear OberservableList
        if (!(DataPresent.getAllAppointments().isEmpty())) {
            DataPresent.getAllAppointments().clear();
        }

        if (!(DataPresent.getAllCustomers().isEmpty())) {
            DataPresent.getAllCustomers().clear();
        }

        /*
        try {
            DBQuery.setStatemnt(conn);
        } catch (SQLException ex) {
            System.out.println(ex.getStackTrace());
        }
         */
        //Methods below retrive the data from appointment table.
        String selectStatment = "Select appointments.*,contacts.Contact_Name from appointments inner join contacts on appointments.Contact_ID=contacts.Contact_ID;";

        try {
            statement.execute(selectStatment);
        } catch (SQLException ex) {
            System.out.println(ex.getStackTrace());
        }

        try {
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                int ID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                //Lambda expression to conver time.
                //This override the method in toLDT interface and let user to convert
                //a string in a LocalDateTime.
                toLDT t = s -> LocalDateTime.parse(s, dtf);
                LocalDateTime start = t.LDT(rs.getString("Start"));
                //LocalDateTime start = LocalDateTime.parse(rs.getString("Start"), dtf);
                /*System.out.println(start);
                //LocalDate sDate = rs.getDate("Start").toLocalDate();
                //System.out.println(sDate);
                //LocalTime sTime = rs.getTime("Start").toLocalTime();
                //System.out.println(sTime);
                //LocalDateTime start = LocalDateTime.of(sDate, sTime);
                //System.out.println(start);
                LocalDate eDate = rs.getDate("End").toLocalDate();
                LocalTime eTime = rs.getTime("End").toLocalTime();
                LocalDateTime end = LocalDateTime.of(eDate, eTime);*/
                int custId = rs.getInt("Customer_ID");
                LocalDateTime end = LocalDateTime.parse(rs.getString("End"), dtf);
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");

                DataPresent.addAllAppointments(new Appointment(ID, title, description, location, type, start, end, custId, userId, contactId, contactName));

            }
            rs.close();
        } catch (SQLException ex) {
            System.out.println(ex.getStackTrace());
        }

        //Method below retrive data for customer tableView.
        String customerstmt = "select customers.*,first_level_divisions.Division from customers inner join first_level_divisions on customers.Division_ID=first_level_divisions.Division_ID;";
        try {
            statement.execute(customerstmt);

        } catch (SQLException ex) {
            System.out.println(ex.getStackTrace());
        }
        try {
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");

                DataPresent.addAllCustomers(new Customer(customerId, customerName, address, postalCode, phone, divisionId, divisionName));

            }
            rs.close();
        } catch (SQLException ex) {
            System.out.println(ex.getStackTrace());
        }

        //Set Value for appointment tableView
        aptTbl.setItems(DataPresent.getAllAppointments());

        aptIdCol.setCellValueFactory(new PropertyValueFactory<>("aptId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("ST"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("ED"));
        custIdCol.setCellValueFactory(new PropertyValueFactory<>("custId"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));

        //Set value for customer tableView
        custTbl.setItems(DataPresent.getAllCustomers());

        custId2Col.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        custNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        divisionCol.setCellValueFactory(new PropertyValueFactory<>("divisionName"));

        datePicker.setValue(LocalDate.now());
    }
}
