package common;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;

// DayOfWeek is enum that contains all days
// DayOfWeek is an enum representing the 7 days of the week - Monday, Tuesday, Wednesday, Thursday, Friday, Saturday and Sunday.
// change ti it because it's easier to convert from localDate
public class DayOpeningHours {

    private boolean isAllDayLongOpened;
    private boolean isOpen;
    private DayOfWeek day;
    private ArrayList<String> openingHours = new ArrayList<>();
    private ArrayList<String> closingHours = new ArrayList<>();

    public DayOpeningHours(boolean isAllDayLongOpened,boolean isOpen, int day, String openingHours, String closingHours) {
        this.isAllDayLongOpened = isAllDayLongOpened;
        this.isOpen = isOpen;
        this.day = DayOfWeek.of(day);
        if(isAllDayLongOpened){
            this.addOpening("01:00");
            this.addClosing("23:59");
        }
        else {
            this.addOpening(openingHours);
            this.addClosing(closingHours);
        }
    }

    public DayOpeningHours(boolean isAllDayLongOpened, boolean isOpen, int day)
    {
        this.isAllDayLongOpened = isAllDayLongOpened;
        this.isOpen = isOpen;
        this.day = DayOfWeek.of(day);
        if(isAllDayLongOpened) {
            this.addOpening("01:00");
            this.addClosing("23:59");
        }

    }

    public DayOpeningHours(boolean isOpen, int day) {
        this.isAllDayLongOpened = false;
        this.isOpen = isOpen;
        this.day = DayOfWeek.of(day);
    }



    public boolean isAllDayLongOpened() {
        return isAllDayLongOpened;
    }

    public void setAllDayLongOpened(boolean allDayLongOpened) {
        isAllDayLongOpened = allDayLongOpened;
    }

    public DayOfWeek getDay() {
        return day;
    }
    public boolean isOpen() {
        return isOpen;
    }
    public ArrayList<String> getOpeningHours() {return openingHours;}

    public ArrayList<LocalTime> getOpeningHoursLocalTime() {
        ArrayList<LocalTime> res = new ArrayList<>();
        for(String time : openingHours)
            res.add(common.converter.convertStringToLocalTime(time));
        return res;
    }

    public ArrayList<String> getClosingHours() {return closingHours;}

    public ArrayList<LocalTime> getClosingHoursLocalTime() {
        ArrayList<LocalTime> res = new ArrayList<>();
        for(String time : closingHours)
            res.add(common.converter.convertStringToLocalTime(time));
        return res;
    }


    public DayOpeningHours(int day) {
        //set the day(0=Sunday....6= Saturday]

        this.day = DayOfWeek.of(day);
        this.isOpen = false;
    }

    public void addOpening(String openingHour) {
        this.isOpen = true;
        this.openingHours.add(openingHour);
    }

    public void addClosing(String closingHour) {
        this.isOpen = true;
        this.closingHours.add(closingHour);
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }



    @Override
    public String toString() {
        return "dayOpeningHours{" +
                "day=" + day +
                ", openingHours=" + openingHours +
                ", closingHours=" + closingHours +
                '}';
    }
}
