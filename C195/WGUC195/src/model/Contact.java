/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * This is contact class. Contact class with all necessary data.
 *
 * @author Chuan
 */
public class Contact {

    private int contactId;
    private String contactName;
    private String email;

    /**
     * Constructor of contact class.
     *
     * @param contactId contact id
     * @param contactName contact name
     * @param email contact email
     */
    public Contact(int contactId, String contactName, String email) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
    }

    /**
     * Method get contact id.
     *
     * @return contact id
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Method set contact id
     *
     * @param contactId contact id to be set
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * Method get contact name.
     *
     * @return contact name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Method set contact Name
     *
     * @param contactName contact Name to be set
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Method get contact email
     *
     * @return contact email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Method set contact email
     *
     * @param email email to be set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * This is override toString method. This method override toString method to
     * return correct information in combo box
     *
     * @return correct format to be shown in combo box
     */
    @Override
    public String toString() {
        return ("Id:" + contactId + "     " + contactName);
    }
}
