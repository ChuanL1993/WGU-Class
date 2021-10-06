/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * This is country class. country class with id and name
 *
 * @author Chuan
 */
public class Country {

    private int countryId;
    private String name;

    /**
     * Constructor for country class
     *
     * @param countryId country id
     * @param name country name
     */
    public Country(int countryId, String name) {
        this.countryId = countryId;
        this.name = name;
    }

    /**
     * Method get country Id
     *
     * @return country id
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Method set country Id
     *
     * @param countryId country Id to be set
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * Method get country name.
     *
     * @return country name
     */
    public String getName() {
        return name;
    }

    /**
     * Method set country name
     *
     * @param name name of country to be set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This is override toString method. This method override toString method to
     * return correct information in combo box
     *
     * @return correct format to be shown in combo box
     */
    @Override
    public String toString() {
        return (name);

    }
}
