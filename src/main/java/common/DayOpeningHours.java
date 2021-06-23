package common;
import java.util.ArrayList;

public class DayOpeningHours {
    public enum weekDay {Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday}

    private boolean isOpen;
    private weekDay day;
    private ArrayList<String> openingHours = new ArrayList<>();
    private ArrayList<String> closingHours = new ArrayList<>();

    public DayOpeningHours(boolean isOpen, int day, String openingHours, String closingHours) {
        this.isOpen = isOpen;
        this.day = weekDay.values()[day];
        this.addOpening(openingHours);
        this.addClosing(closingHours);
    }

    public DayOpeningHours(boolean isOpen, int day) {
        this.isOpen = isOpen;
        this.day = weekDay.values()[day];
    }

    public weekDay getDay() {
        return day;
    }
    public boolean isOpen() {
        return isOpen;
    }
    public ArrayList<String> getOpeningHours() {return openingHours;}
    public ArrayList<String> getClosingHours() {return closingHours;}

    public DayOpeningHours(int day) {
        //set the day(0=Sunday....6= Saturday]
        this.day = weekDay.values()[day];
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
