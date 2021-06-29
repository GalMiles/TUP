package common;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class converter {

    public static LocalTime convertStringToLocalTime(String time){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return(LocalTime.parse(time, formatter));
    }

}
