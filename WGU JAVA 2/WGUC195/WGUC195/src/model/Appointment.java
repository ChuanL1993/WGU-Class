/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.text.DateFormat;
import java.text.Format;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

/**
 * This is appointment class. appointment class with all necessary data.
 *
 * @author Chuan
 */
public class Appointment {

    private int aptId;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private int custId;
    private int userId;
    private int contactId;
    private String contactName;

    /**
     * Constructor of appointment class.
     *
     * @param aptId appointment id
     * @param title appointment title
     * @param description appointment description
     * @param location appointment location
     * @param type appointment type
     * @param start appointment start time
     * @param end appointment end time
     * @param custId appointment customer id
     * @param userId appointment user id
     * @param contactId appointment contact id
     * @param contactName appointment contact name
     */
    public Appointment(int aptId, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int custId, int userId, int contactId, String contactName) {
        this.aptId = aptId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.custId = custId;
        this.userId = userId;
        this.contactId = contactId;
        this.contactName = contactName;
    }

    /**
     * This method get correct time with user zoneID
     *
     * @return a localdatetime with user default zoneId
     */
    public LocalDateTime getST() {
        //Instant instant = Instant.parse(start.getoString());
        //return instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
        ZonedDateTime utcDT = ZonedDateTime.of(start, ZoneId.of("UTC"));
        return utcDT.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * This method get correct time with user zoneID
     *
     * @return a localdatetime with user default zoneId
     */
    public LocalDateTime getED() {
        ZonedDateTime utcDT = ZonedDateTime.of(end, ZoneId.of("UTC"));
        return utcDT.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * This method get contact name of appointment.
     *
     * @return contact name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * This method set contact name for appointment.
     *
     * @param contactName contact name to be set
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * This method get appointment id.
     *
     * @return appointment id
     */
    public int getAptId() {
        return aptId;
    }

    /**
     * This method set appointment Id for appointment.
     *
     * @param aptId appointment id to be set
     */
    public void setAptId(int aptId) {
        this.aptId = aptId;
    }

    /**
     * This method get title of appointment.
     *
     * @return title of appointment
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method set title for appointment.
     *
     * @param title title to be set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * This method get description of appointment.
     *
     * @return description of appointment
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method set description for appointment.
     *
     * @param description description to be set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method get location of appointment.
     *
     * @return location of appointment
     */
    public String getLocation() {
        return location;
    }

    /**
     * This method set location for appointment.
     *
     * @param location location to be set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * This method get the type of appointment.
     *
     * @return type of appointment
     */
    public String getType() {
        return type;
    }

    /**
     * This method set type of appointment.
     *
     * @param type type to be set
     *
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Method get start time.
     *
     * @return start time
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Method set start time.
     *
     * @param start start time to be set
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * Method get end time .
     *
     * @return end time
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Method set end time.
     *
     * @param end end time to be set
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /**
     * Method get customer id
     *
     * @return customer id
     */
    public int getCustId() {
        return custId;
    }

    /**
     * Method set customer id
     *
     * @param custId customer id to be set
     */
    public void setCustId(int custId) {
        this.custId = custId;
    }

    /**
     * Method get user id
     *
     * @return user id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Method set user id
     *
     * @param userId user id to be set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Method get contact id
     *
     * @return contact id
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Method set contact id
     *
     * @param contactId contact Id to be set
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

}
