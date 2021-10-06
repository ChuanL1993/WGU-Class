/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * This is ESTtoLDT interface. This is functional interface to convert a LocalDate and
 * LocalTime to a LocalDateTime with special requirement.
 * It could used as the assignment target for a lambda expression or method reference.
 *
 *  A lambda expression for this interface is used in NewAppointmentController.java at line 110.
 * @author Chuan
 */
public interface ESTtoLDT {

    /**
     * Method convert LocalDate and LocalTime to LocalDateTime.
     *
     * A lambda expression is used in NewAppointmentController.java at line 110
     *
     * @param d LocalDate to convert
     * @param t LocalTime to convert
     * @return LocalDateTime Object
     */
    // A lambda expression is used in NewAppointmentController.java at line 110
    LocalDateTime conver(LocalDate d, LocalTime t);

}
