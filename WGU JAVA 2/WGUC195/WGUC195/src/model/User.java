/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDateTime;

/**
 * This is user class.class class with all necessary data.
 *
 * @author Chuan
 */
public class User {

    private int userId;
    private String userName;
    private String password;

    /**
     * Constructor for user class.
     *
     * @param userId user id
     * @param userName user name
     * @param password user password
     */
    public User(int userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    /**
     * Method get user id
     *
     * @return user id
     */
    public int getUserId() {
        return userId;
    }

    /*
    public void setUserId(int userId) {
        this.userId = userId;
    }
     */
    /**
     * Method get user name
     *
     * @return username
     */
    public String getUserName() {
        return userName;
    }

    /*
    public void setUserName(String userName) {
        this.userName = userName;
    }
     */
    /**
     * This is override toString method. This method override toString method to
     * return correct information in combo box
     *
     * @return correct format to be shown in combo box
     */
    @Override
    public String toString() {
        return ("Id:" + userId + "     " + userName);
    }

}
