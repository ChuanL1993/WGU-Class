/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author heych
 */
public class main {

    public static void main(String[] args) {
        // TODO code application logic here
        TimeZone zone = TimeZone.getTimeZone(ZoneId.systemDefault());
        DateFormat format = DateFormat.getDateTimeInstance();
        format.setTimeZone(zone);
        Date dt = new Date(LocalDateTime.now().toString());

        System.out.println(format.format(dt));
        ZonedDateTime zdt = ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault());

        LocalDateTime test = LocalDateTime.now();
        LocalDate test2 = LocalDate.now();
        LocalTime test3 = LocalTime.of(10, 20);

        System.out.println(zdt);/*
        System.out.println(test2);
        System.out.println(test3);
        System.out.println(test);
        String rs = test2.toString()+" "+test3.toString();
        System.out.println(rs);
        System.out.println(test.format(DateTimeFormatter.ISO_DATE));
         */
    }

}
