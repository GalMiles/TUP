package engine.planTrip;

import engine.attraction.Attraction;

import java.time.LocalTime;


public class OnePlan {
    Attraction attraction;
    LocalTime startTime;
    LocalTime finishTime;
    Boolean isFavoriteAttraction = false;

    public OnePlan(Attraction attraction, LocalTime startTime , Boolean isFavoriteAttraction) {
        this.attraction = attraction;
        this.startTime = startTime;
        this.finishTime = startTime.plusHours(attraction.getDuration());
        this.isFavoriteAttraction = isFavoriteAttraction;
    }

    public Attraction getAttraction() {return attraction;}
    public Boolean getFavoriteAttraction() {
        return isFavoriteAttraction;
    }
    public void setAttraction(Attraction attraction) {this.attraction = attraction;}
    public LocalTime getStartTime() {return startTime;}
    public void setStartTime(LocalTime startTime) {this.startTime = startTime;}
    public LocalTime getFinishTime() {return finishTime;}
    public void setFinishTime(LocalTime finishTime) {this.finishTime = finishTime;}

    @Override
    public String toString() {
        return "OnePlan{" +
                "attraction=" + attraction +
                ", startTime=" + startTime +
                ", finishTime=" + finishTime +
                '}';
    }
}
