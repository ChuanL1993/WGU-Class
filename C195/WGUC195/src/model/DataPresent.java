/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This is DataPresent class. This class manage to Observablelist of each object
 *
 * @author Chuan
 */
public class DataPresent {

    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    private static ObservableList<Contact> allContacts = FXCollections.observableArrayList();

    private static ObservableList<User> allUsers = FXCollections.observableArrayList();

    private static ObservableList<Country> allCountries = FXCollections.observableArrayList();

    private static ObservableList<Division> allDivisions = FXCollections.observableArrayList();

    private static ObservableList<Report> allReports = FXCollections.observableArrayList();

    /**
     * Method add item to allReports
     *
     * @param r report to add
     */
    public static void addAllReports(Report r) {
        allReports.add(r);
    }

    /**
     * Method add item to allDivisions
     *
     * @param division division to add
     */
    public static void addAllDivisions(Division division) {
        allDivisions.add(division);
    }

    /**
     * Method add item to allCountries
     *
     * @param country country to add
     */
    public static void addAllCountries(Country country) {
        allCountries.add(country);
    }

    /**
     * Method add items to allUsers
     *
     * @param user user to add
     */
    public static void addAllUsers(User user) {
        allUsers.add(user);
    }

    /**
     * Method add items to allContacts
     *
     * @param contact contact to add
     */
    public static void addAllContacs(Contact contact) {
        allContacts.add(contact);
    }

    /**
     * Method add items to allAppointments
     *
     * @param appointment appointment to add
     */
    public static void addAllAppointments(Appointment appointment) {
        allAppointments.add(appointment);
    }

    /**
     * Method add item to allCustomers
     *
     * @param customer customer to add
     */
    public static void addAllCustomers(Customer customer) {
        allCustomers.add(customer);
    }

    /**
     * Method get allReports
     *
     * @return allReports
     */
    public static ObservableList<Report> getAllReports() {
        return allReports;
    }

    /*
    public static void setAllReports(ObservableList<Report> allReports) {
        DataPresent.allReports = allReports;
    }*/
    /**
     * Method get allCountries
     *
     * @return allCountries
     */
    public static ObservableList<Country> getAllCountries() {
        return allCountries;
    }

    /*
    public static void setAllCountries(ObservableList<Country> allCountries) {
        DataPresent.allCountries = allCountries;
    }
     */
    /**
     * Method get allDivisions
     *
     * @return allDivisions
     */
    public static ObservableList<Division> getAllDivisions() {
        return allDivisions;
    }

    /*
    public static void setAllDivisions(ObservableList<Division> allDivisions) {
        DataPresent.allDivisions = allDivisions;
    }
     */
    /**
     * Method get allAppointments
     *
     * @return allAppointments
     */
    public static ObservableList<Appointment> getAllAppointments() {
        return allAppointments;
    }

    /*
    public static void setAllAppointments(ObservableList<Appointment> allAppointments) {
        DataPresent.allAppointments = allAppointments;
    }
     */
    /**
     * Method get allCustomers
     *
     * @return allCustomers
     */
    public static ObservableList<Customer> getAllCustomers() {
        return allCustomers;
    }

    /*
    public static void setAllCustomers(ObservableList<Customer> allCustomers) {
        DataPresent.allCustomers = allCustomers;
    }
     */
    /**
     * Method get allContacts
     *
     * @return allContacts
     */
    public static ObservableList<Contact> getAllContacts() {
        return allContacts;
    }

    /*
    public static void setAllContacts(ObservableList<Contact> allContacts) {
        DataPresent.allContacts = allContacts;
    }
     */
    /**
     * Method get allUsers
     *
     * @return allUsers
     */
    public static ObservableList<User> getAllUsers() {
        return allUsers;
    }
    /*
    public static void setAllUsers(ObservableList<User> allUsers) {
        DataPresent.allUsers = allUsers;
    }
     */
}
