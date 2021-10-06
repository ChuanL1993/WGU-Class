/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * This is Division class. This class include id, country id and,division name.
 *
 * @author Chuan
 */
public class Division {

    private int divisionId;
    private String division;
    private int countryId;

    /**
     * Constructor for division class.
     *
     * @param divisionId division Id
     * @param division division name
     * @param countryId country id
     */
    public Division(int divisionId, String division, int countryId) {
        this.divisionId = divisionId;
        this.division = division;
        this.countryId = countryId;
    }

    /**
     * Method get division id
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

    /**
     * Method get division name
     *
     * @return division name
     */
    public String getDivision() {
        return division;
    }

    /**
     * Method set division name
     *
     * @param division division name to be set
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Method get countryId
     *
     * @return country id
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Method set country id
     *
     * @param countryId country to be set
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * This is override toString method. This method override toString method to
     * return correct information in combo box
     *
     * @return correct format to be shown in combo box
     */
    @Override
    public String toString() {
        return (division);
    }

}
