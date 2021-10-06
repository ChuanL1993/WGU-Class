/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDateTime;

/**
 * This is customer class. customer class class with all necessary data.
 *
 * @author Chuan
 */
public class Customer {

    private int customerId;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private int divisionId;
    private String divisionName;

    /**
     * constructor of customer class
     *
     * @param customerId customer id
     * @param customerName customer name
     * @param address address
     * @param postalCode customer postal code
     * @param phone phone number
     * @param divisionId customer division id
     * @param divisionName division name
     */
    public Customer(int customerId, String customerName, String address, String postalCode, String phone, int divisionId, String divisionName) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionId = divisionId;
        this.divisionName = divisionName;
    }

    /**
     * Method get division Name
     *
     * @return division name
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * method set division name
     *
     * @param divisionName division name to be set
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     * Method get customer ID
     *
     * @return customer ID
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Method set customer id
     *
     * @param customerId customer id to be set
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Method get customer name
     *
     * @return customer name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Method set customer name
     *
     * @param customerName customer name to be set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Method get address
     *
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Method set address
     *
     * @param address address to be set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Method get postal code
     *
     * @return postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Method set postal code
     *
     * @param postalCode postal code to be set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Method get phone
     *
     * @return phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Method set phone number
     *
     * @param phone phone number to be set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Method get divisionId
     *
     * @return division id
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Method set division id
     *
     * @param divisionId division id to be set
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

}
