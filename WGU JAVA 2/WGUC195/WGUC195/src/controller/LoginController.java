/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.*;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import utils.DBConnection;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.User;
import utils.DBQuery;

/**
 * FXML Controller class for Login page. This would implement the text field,
 * label and buttons that used on the login page. This class include
 * dateTimeFormatter for proper format need in the methods. And include a
 * resourceBundle to handle different locale setting. The future idea of this
 * page would be including a check box to let user see the password And a option
 * for user to choose a timezone if they need to.
 *
 * @author Chuan
 */
public class LoginController implements Initializable {

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    ResourceBundle rb = ResourceBundle.getBundle("Property/Login", Locale.getDefault());

    Stage stage;
    Parent scene;

    @FXML
    private Label passwordLbl;

    @FXML
    private Button loginBtn;

    @FXML
    private Button clearBtn;

    @FXML
    private Label warningLbl;

    /* @FXML
    private CheckBox showCbox;
     */
    @FXML
    private Label TZMenuLbl;

    @FXML
    private Label usernameLbl;

    @FXML
    private TextField usernameTxt;

    @FXML
    private PasswordField passwordTxt;

    @FXML
    private TextField passwordShowTxt;

    @FXML
    private Label TZLbl;

    @FXML
    void onActionLogin(ActionEvent event) throws IOException, SQLException {
        String username = null;
        String password = null;
        String warning = "*\n";
        boolean success = false;
        if (!(usernameTxt.getText().isEmpty())) {
            username = usernameTxt.getText();
        } else {
            warning += rb.getString("w1") + "\n";
            //warning += "Username cannot be empty!\n";
        }
        if (!(passwordTxt.getText().isEmpty())) {
            password = passwordTxt.getText();
        } else {
            warning += rb.getString("w2") + "\n";
            //warning += "Password cannot be empty!\n";
        }
        Connection conn = DBConnection.getConnection();
        DBQuery.setStatemnt(conn);
        //DBConnection.getConnection();
        Statement statement = DBQuery.getStatemnt();
        String selectStatement = "Select User_ID, Password from users where Binary User_Name=\'" + username + "\'";
        statement.execute(selectStatement);
        ResultSet passwordRs = statement.getResultSet();
        if (!(passwordTxt.getText().isEmpty())) {
            if (passwordRs.next()) {
                if (password.equals(passwordRs.getString("Password"))) {
                    success = true;

                    //Create an user object with correct userName Id and password
                    User user = new User(passwordRs.getInt("User_ID"), username, password);
                    loginLog(user.getUserName(), success);

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/view/MainMenu.fxml"));
                    loader.load();

                    MainMenuController MMController = loader.getController();
                    MMController.checkApt();
                    MMController.setUser(user);

                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Parent scene = loader.getRoot();
                    stage.setScene(new Scene(scene));
                    stage.show();
                } else {
                    warning += rb.getString("w3") + "\n";
                    //warning += "Please check your username and password.\n";.
                    User user = new User(passwordRs.getInt("User_ID"), username, password);
                    loginLog(user.getUserName(), success);                   
                }

            } else {
                warning += rb.getString("w4") + "\n";
                //warning += "Username does not exist.\n";       
            }
            
        }       
        warningLbl.setText(warning);

    }

    @FXML
    void onActionClear(ActionEvent event) {
        usernameTxt.clear();
        passwordTxt.clear();
    }

    /**
     * This method print login log into a txt file. This method take a string
     * and print proper information to the file.
     *
     * @param s string to pass in.
     * @param success indicate the login success or fail
     * @throws IOException
     */
    public void loginLog(String s, boolean success) throws IOException {
        String filename = "login_activity.txt";
        FileWriter fwriter = new FileWriter(filename, true);
        PrintWriter output = new PrintWriter(fwriter);
        if (success == true) {
            output.println(s + " log in @ " + ZonedDateTime.now().format(dtf) + " " + ZoneId.systemDefault() + "   Successful" + "\n");
        } else {
            output.println(s + " log in @ " + ZonedDateTime.now().format(dtf) + " " + ZoneId.systemDefault() + "   failed" + "\n");
        }
        output.close();
    }

    /* @FXML
    void onActionShowTxt(ActionEvent event) {
        if (showCbox.isSelected()) {
            passwordShowTxt.setText(passwordTxt.getText());
            passwordShowTxt.toFront();
        } else {
            System.out.println("n");
        }
    }
     */
    /**
     * Initializes the controller class. This method get called first when the
     * login page is loaded ResourceBundle handle to proper locale setting for
     * each field.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ResourceBundle rb2 = ResourceBundle.getBundle("Property/Login", Locale.getDefault());
        usernameLbl.setText(rb2.getString("username"));
        passwordLbl.setText(rb2.getString("password"));
        loginBtn.setText(rb2.getString("login"));
        clearBtn.setText(rb2.getString("clear"));
        TZMenuLbl.setText(rb2.getString("TZ"));
        TZLbl.setText(ZoneId.systemDefault().toString());
    }

}
