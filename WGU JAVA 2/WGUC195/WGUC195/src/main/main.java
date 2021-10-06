/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.Locale;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.User;
import utils.DBConnection;


 /**This is main class enter this schedule program. This class would be the entry point for the program*/
 
public class main extends Application { 
    /** This is main method. This is the entry and first method get call when run the program.
        @param args that we pass in the main method
        */
    public static void main(String[] args) {
        //Locale locale = new Locale("fr");Locale.setDefault(locale); // change the default
        //System.out.println(Locale.getDefault()); // fr
        // TODO code application logic here
        DBConnection.startConnection();
        launch(args);
        DBConnection.closeConnection();
        
    }
     /**This is the override of star method.
     This would get the stage.
     @param primaryStage the stage
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        
        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        //primaryStage.setTitle("Inventory Managment System");
        primaryStage.setScene(new Scene(root,450, 450));
        primaryStage.show();
    }
    
}
