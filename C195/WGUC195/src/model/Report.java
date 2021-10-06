/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * This is report class. with number of select item.
 *
 * @author Chuan
 */
public class Report {

    private int n;
    private String item;

    /**
     * Constructor of report class
     *
     * @param n number of item.
     * @param item item name
     */
    public Report(int n, String item) {
        this.n = n;
        this.item = item;
    }

    /**
     * Method get number of item
     *
     * @return number
     */
    public int getN() {
        return n;
    }

    /*
    public void setN(int n) {
        this.n = n;
    }
     */
    /**
     * Method get item name
     *
     * @return item name
     */
    public String getItem() {
        return item;
    }

    /*
    public void setItem(String item) {
        this.item = item;
    }
     */
    /**
     * This is override toString method. This method override toString method to
     * return correct information in listView
     *
     * @return correct format to be shown in list view
     */
    @Override
    public String toString() {
        return (item + "      " + n);

    }

}
