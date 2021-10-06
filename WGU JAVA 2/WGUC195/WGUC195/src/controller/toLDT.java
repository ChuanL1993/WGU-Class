/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.time.LocalDateTime;

/**
 * This is toLDT interface. This interface include a method to convert String to
 * a LocalDateTime
 * 
 * It could used as the assignment target for a lambda expression or method reference.
 *
 *  A lambda expression for this interface is used in MainMenuController.java at line 568.
 * @author Chuan
 */
public interface toLDT {

    /**
     * Convert string to LocalDateTime.
     *
     * A lambda expression for this interface is used in MainMenuController.java at line 568.
     * @param s string to be converted
     * @return LocalDayTime Object
     */
    // A lambda expression for this interface is used in MainMenuController.java at line 568.
    LocalDateTime LDT(String s);

}
