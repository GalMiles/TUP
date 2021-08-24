package common;

import engine.trip.DayPlan;

import java.util.ArrayList;

public class TripPlan {
    private String tripName;
    private ArrayList<DayPlan> plans = new ArrayList<>();


    public String getTripName() {
        return tripName;
    }
    public void setTripName(String tripName) {
        this.tripName = tripName;
    }
    public ArrayList<DayPlan> getPlans() {
        return plans;
    }
    public void setPlans(ArrayList<DayPlan> plans) {
        this.plans = plans;
    }
}
