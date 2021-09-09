package common;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class converter {

    public static LocalTime convertStringToLocalTime(String time){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String newTime = "";
        if (time.length()==4){
            newTime+=time.charAt(0);
            newTime+=time.charAt(1);
            newTime+=':';
            newTime+=time.charAt(2);
            newTime+=time.charAt(3);
            time = newTime;
        }
        return(LocalTime.parse(time.trim(), formatter));
    }

    public static LocalDate convertStringToLocalDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return(LocalDate.parse(date, formatter));
    }

}
