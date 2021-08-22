package engine.planTrip;

import common.DayOpeningHours;
import engine.attraction.Attraction;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;

public class DayPlan {
    double durationDesireByUser;
    ArrayList<OnePlan> daySchedule = new ArrayList<>();
    LocalDate date;
    Attraction hotel;
    LocalTime startTime;
    LocalTime finishTime;
    int durationDay = 0;
    ArrayList<Attraction> mustSeenAttractionsForDay = new ArrayList<>();


    public DayPlan(LocalDate date, LocalTime startTime, LocalTime finishTime ,Attraction hotel){
        setHotel(hotel);
        setDate(date);
        setStartTime(startTime);
        setFinishTime(finishTime);
        setDurationDesireByUser((startTime.until(finishTime,ChronoUnit.MINUTES))/60.0);
    }


    public double getDurationDesireByUser() {
        return durationDesireByUser;
    }
    public ArrayList<OnePlan> getDaySchedule() {
        return daySchedule;
    }
    public LocalDate getDate() {
        return date;
    }
    public Attraction getHotel() {
        return hotel;
    }
    public LocalTime getStartTime() {
        return startTime;
    }
    public LocalTime getFinishTime() {
        return finishTime;
    }
    public int getDurationDay() {
        return durationDay;
    }

    public ArrayList<Attraction> getMustSeenAttractionsForDay() {
        return mustSeenAttractionsForDay;
    }

    public void setDurationDesireByUser(double durationDesireByUser) {
        this.durationDesireByUser = durationDesireByUser;
    }
    public void setDaySchedule(ArrayList<OnePlan> daySchedule) {
        this.daySchedule = daySchedule;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public void setHotel(Attraction hotel) {
        this.hotel = hotel;
    }
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
    public void setFinishTime(LocalTime finishTime) {
        this.finishTime = finishTime;
    }
    public void setDurationDay(int durationDay) {
        this.durationDay = durationDay;
    }

    public void setMustSeenAttractionsForDay(ArrayList<Attraction> mustSeenAttractions) {
       for (Attraction attraction : mustSeenAttractions) {
            if (this.mustSeenAttractionsForDay.isEmpty()) {
                this.mustSeenAttractionsForDay.add(new Attraction(attraction));
            }
            else{
                Attraction listRepresent = this.mustSeenAttractionsForDay.get(this.mustSeenAttractionsForDay.size()-1);
                Attraction closestAttraction = findClosestAttractionToSource(listRepresent, mustSeenAttractions);
                if(closestAttraction == null)
                    break;
                this.mustSeenAttractionsForDay.add(new Attraction(closestAttraction));
            }
        }
         mustSeenAttractions.removeIf(a -> this.mustSeenAttractionsForDay.contains(a));
    }

    private Attraction findClosestAttractionToSource(Attraction sourceAttraction, ArrayList<Attraction> mustSeenAttractions)
    {
        CellDetails closestAttraction = null;
        int V = mustSeenAttractions.size();
        ArrayList<CellDetails> dist = new ArrayList(V);
        double minDistance = Integer.MAX_VALUE;

        for (Attraction att: mustSeenAttractions) {
             CellDetails cellDetails = new CellDetails();
             cellDetails.attraction = att;
             cellDetails.distance = att.calcDistanceBetweenAttractions(sourceAttraction);
             if(this.mustSeenAttractionsForDay.contains(cellDetails.attraction) ||
                     att.getDuration() > durationDesireByUser - durationDay)
                 cellDetails.isOptional = false;
             dist.add(cellDetails);
        }

        for(CellDetails cd: dist){
            if(cd.distance < minDistance && cd.isOptional) {
                minDistance = cd.distance;
                closestAttraction = cd;
            }
        }
        return (closestAttraction == null) ? null : closestAttraction.attraction;
    }

    public void calculateDayPlanWithMustSeenAttractions(){
        daySchedule.add(new OnePlan(hotel,startTime, false));
        if(this.mustSeenAttractionsForDay.isEmpty()){
            return;
        }
        calculateDayPlan(this.mustSeenAttractionsForDay, true);
    }


    public void calculateDayPlan(ArrayList<Attraction> attractionsAvailable, Boolean isFavoriteAttraction) {

        Attraction nextAttraction;
        Attraction currentAttraction = daySchedule.get(daySchedule.size()-1).getAttraction();
        LocalTime currentTime = startTime.plusHours(durationDay);

        while (durationDay < durationDesireByUser) {
            nextAttraction = chooseBestNextAttraction(currentAttraction, currentTime,attractionsAvailable);
            if(nextAttraction != null) {
                daySchedule.add(new OnePlan(nextAttraction, currentTime, isFavoriteAttraction));
                attractionsAvailable.remove(nextAttraction); // -object of any class are reference so this action delete chosen attraction so we dont add visited attraction to rhe next day
                currentAttraction = nextAttraction;
                currentTime = currentTime.plusHours(nextAttraction.getDuration());

                durationDay += nextAttraction.getDuration();
            }
            else
                break;
        }

    }

    private Attraction chooseBestNextAttraction(Attraction currentAttraction, LocalTime time, ArrayList<Attraction> possibleAttractions){
        Attraction nextAttraction = null;
        double minScore = Integer.MAX_VALUE;
        double currentScore;
        for(Attraction attraction : possibleAttractions){
            currentScore = calculateScore(currentAttraction,attraction,time,this.date);
            if (currentScore < minScore){
                nextAttraction = attraction;
                minScore = currentScore;
            }
        }
        return nextAttraction; //in case its null, it mean that the day is over
    }


    private double calculateScore(Attraction currentAttraction,Attraction nextAttraction, LocalTime hourOnClock, LocalDate date) {
        double scoreDistance = currentAttraction.calcDistanceBetweenAttractions(nextAttraction);
        long differenceBetweenClockAndStartTime;
        long minValue = Integer.MAX_VALUE;
        double scoreTime;
        Boolean closeAttraction;  //in case the next attraction is close on the hourOnClock - cant go to close attraction
        Boolean overPossibleDuration;  //in case according to duration of attraction we'll stay longer then we can

        DayOpeningHours dayOpeningHoursNext = nextAttraction.getOpeningHoursByDay(date.getDayOfWeek());
        ArrayList<LocalTime> openingHoursNext = new ArrayList<>(dayOpeningHoursNext.getOpeningHoursLocalTime());
        ArrayList<Boolean> openingHoursNextPossible = new ArrayList<>(Collections.nCopies(openingHoursNext.size(), true));
        ArrayList<LocalTime> closingHoursNext = new ArrayList<>(dayOpeningHoursNext.getClosingHoursLocalTime());
        ArrayList<Boolean> closingHoursNextPossible = new ArrayList<>(Collections.nCopies(closingHoursNext.size(), true));

        int sizeOpeningHoursNext = openingHoursNext.size();
        LocalTime hourOnClockAfterEnjoying = hourOnClock.plusHours(nextAttraction.getDuration());

        for (int i = 0; i < sizeOpeningHoursNext; i++) {

            closeAttraction = hourOnClock.isBefore(openingHoursNext.get(i));
            overPossibleDuration = closingHoursNext.get(i).isBefore(hourOnClockAfterEnjoying);

            if (closeAttraction || overPossibleDuration) {
                openingHoursNextPossible.set(i, false);
                closingHoursNextPossible.set(i, false);


//                openingHoursNext.remove(openingHoursNext.get(i));
//                closingHoursNext.remove(closingHoursNext.get(i));
            }
//            if (openingHoursNext.size() == 0)
//                return Integer.MAX_VALUE;
        }

        openingHoursNext = removeNotPossibleHours(openingHoursNext,openingHoursNextPossible);
        closingHoursNext = removeNotPossibleHours(closingHoursNext,closingHoursNextPossible);

        if (openingHoursNext.size() == 0)
            return Integer.MAX_VALUE;




        // the hourClock = 10:00
        //the thought was that hourClock = 10:00 and if there are att1 = 7:00  and att2 = 8:00 then Chances are that att1 will close earlier so we should choose her
        // that's why i give her the -1*minValue

        //why minValue?
        // in case there are for one attraction 7:00-14:00 and 15:00-19:00
        //  we want to choose 7:00
        sizeOpeningHoursNext = openingHoursNext.size();

        for (int i = 0; i < sizeOpeningHoursNext; i++) {
            differenceBetweenClockAndStartTime = openingHoursNext.get(i).until(hourOnClock, ChronoUnit.HOURS);
            if (minValue > differenceBetweenClockAndStartTime )
                minValue = differenceBetweenClockAndStartTime;
        }
        scoreTime = -1 * minValue;
        return scoreDistance + scoreTime;
    }

    private ArrayList<LocalTime> removeNotPossibleHours(ArrayList<LocalTime> hoursNext, ArrayList<Boolean> hoursNextPossible) {
        ArrayList<LocalTime> result = new ArrayList<>();
        int size = hoursNext.size();
        for (int i = 0; i < size; i++) {
            if (hoursNextPossible.get(i))
                result.add(hoursNext.get(i));
        }
        return result;
    }


    /*
    public ArrayList<Integer> po(ArrayList<Integer> hoursNext, ArrayList<Boolean> hoursNextPossible) {
        ArrayList<Integer> h = new ArrayList<>();
        int size = hoursNext.size();
        for (int i = 0; i < size; i++) {
            if (hoursNextPossible.get(i))
                h.add(hoursNext.get(i));
        }
        return h;
    }
    * */





//    @Override
//    public String toString() {
//        return "DayPlan{" +
//                "durationDesireByUser=" + durationDesireByUser +
//                ", daySchedule=" + daySchedule +
//                ", date=" + date +
//                ", hotel=" + hotel +
//                ", startTime=" + startTime +
//                ", finishTime=" + finishTime +
//                ", durationDay=" + durationDay +
//                '}';

//    }

    @Override
    public String toString() {
        printDaySchedule(daySchedule,date);
        return "";
    }

    private void printDaySchedule(ArrayList<OnePlan> daySchedule,LocalDate date)
    {
        int i = 0;
        for (OnePlan plan : daySchedule) {
            System.out.println(plan.getStartTime() + "-" + plan.getFinishTime() + "  " + date +" DAY " +String.valueOf(i)+ ":"+ plan.getAttraction().getName() + "                favorite: " + plan.getFavoriteAttraction());
            i++;
        }

    }
}
