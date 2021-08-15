package engine.planTrip;


import common.*;
import engine.attraction.Attraction;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collectors;

public class RouteTrip {
    ArrayList<DayPlan> planForDays;
    int tripDuration;
    Destinations destination;    //an Enum that saves destinations(include Paris, London)
    Attraction hotel;
    LocalDate arrivingDate;
    LocalDate leavingDate;
    ArrayList<Attraction> mustSeenAttractions;
    static int tripId = 0;

    public RouteTrip(String destination, Attraction hotel, ArrayList<Attraction> mustSeenAttractions, ArrayList<DesiredHoursInDay> desiredHoursInDays) {
        setArrivingDate(LocalDate.parse(((desiredHoursInDays.get(0)).getDate())));
        String lastDay = desiredHoursInDays.get(desiredHoursInDays.size()-1).getDate();
        setLeavingDate(LocalDate.parse(lastDay));
        setTripDuration(leavingDate.getDayOfMonth() - arrivingDate.getDayOfMonth() + 1);
        setMustSeenAttractions(mustSeenAttractions);
        setDestination(Destinations.valueOf(destination));
        setHotel(hotel);
        setPlanForDays(desiredHoursInDays,this.hotel);

        tripId++;

    }

    public int getTripDuration() { return tripDuration; }
    public Destinations getDestination() {
        return destination;
    }
    public Attraction getHotel() {
        return hotel;
    }
    public LocalDate getArrivingDate() {
        return arrivingDate;
    }
    public LocalDate getLeavingDate() {
        return leavingDate;
    }
    public ArrayList<Attraction> getMustSeenAttractions() {
        return mustSeenAttractions;
    }
    public ArrayList<DayPlan> getPlanForDays() {
        return planForDays;
    }

    public void setTripDuration(int tripDuration) {
        this.tripDuration = tripDuration;
    }

    public void setDestination(Destinations destination) {
        this.destination = destination;
    }

    public void setHotel(Attraction hotel) {
        this.hotel = hotel;
    }

    public void setArrivingDate(LocalDate arrivingDate) {
        this.arrivingDate = arrivingDate;
    }

    public void setLeavingDate(LocalDate leavingDate) {
        this.leavingDate = leavingDate;
    }

    public void setMustSeenAttractions(ArrayList<Attraction> mustSeenAttractions) {
        this.mustSeenAttractions = new ArrayList<Attraction>();
        if(mustSeenAttractions != null) {
            for (Attraction attraction : mustSeenAttractions)
                this.mustSeenAttractions.add(attraction);
        }
    }

    public void setPlanForDays(ArrayList<DesiredHoursInDay> planForDays, Attraction hotel) {
        this.planForDays = new ArrayList<DayPlan>();
        for(DesiredHoursInDay day: planForDays){
            DayPlan dayPlan = new DayPlan(LocalDate.parse(day.getDate()),
                    LocalTime.parse(day.getStartTime()),
                            LocalTime.parse(day.getEndTime()),hotel);
            this.planForDays.add(dayPlan);
        }
    }

    public void planRouteTrip(ArrayList<Attraction> attractionsAvailable){
        divideMustSeenAttractionsForEachDay();
        for(DayPlan dayPlan: this.planForDays){
            dayPlan.calculateDayPlanWithMustSeenAttractions();
            dayPlan.calculateDayPlan(attractionsAvailable);
        }
    }

    private void divideMustSeenAttractionsForEachDay() {
        int minimumDistance = 1;
        while (mustSeenAttractions.size() != 0) {
            for (DayPlan dayPlan : this.planForDays) {
                dayPlan.setMustSeenAttractions(mustSeenAttractions, minimumDistance);
            }
            minimumDistance += 1;
        }
        this.planForDays.forEach(dayPlan -> dayPlan.setDurationDay(0));
    }



//        int sizeMust = mustSeenAttractions.size();
//
//        if (sizeMust == 0)
//            return;
//
//        double numberOfAttractionsEachDay = Math.ceil(sizeMust / tripDuration);
//        int minimumDestination = 1;
//        this.planForDays.get(0).getMustSeenAttractionsForDay().add(mustSeenAttractions.get(0)); //init
//        while(sizeMust != 0){
//            for(DayPlan dayPlan: this.planForDays) {
//                for(Attraction attraction: mustSeenAttractions){
//                    //if(dayPlan.getMustSeenAttractionsForDay())
//                }
//            }
//
//            sizeMust--;
//        }


 //   }




    @Override
    public String toString() {
        return "RouteTrip{" +
                "planForDays=" + planForDays +
                //", attractionsForTheTrip=" + attractionsForTheTrip +
                ", tripDuration=" + tripDuration +
                ", destination='" + destination + '\'' +
                ", hotel=" + hotel +
                ", arrivingDate=" + arrivingDate +
                ", leavingDate=" + leavingDate +
                '}';
    }

    public ArrayList<Attraction> getPossibleAttractions(String destination){
        ArrayList<Attraction> possibleAttractions = new ArrayList<>();
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ArrayList<DayOpeningHours> openingHoursArr1 = new ArrayList<>();
        openingHoursArr1.add(new DayOpeningHours(false,true,1,"1100","1800"));
        openingHoursArr1.add(new DayOpeningHours(false,true, 2, "1100", "1800"));
        openingHoursArr1.add(new DayOpeningHours(false,true, 3, "1100", "1800"));
        openingHoursArr1.add(new DayOpeningHours(false,true,4,"1100","1800"));
        openingHoursArr1.add(new DayOpeningHours(false,true,5,"1100","1800"));
        openingHoursArr1.add(new DayOpeningHours(false,true,6,"1100","1800"));
        openingHoursArr1.add(new DayOpeningHours(false,true,7,"1100","1800"));
        ArrayList<AttractionType> types1 = new ArrayList<>();
        types1.add(AttractionType.tourist_attraction);
        Attraction attraction1 = new Attraction("lastminute.com London Eye", "Riverside Building, County Hall, London SE1 7PB, UK",
                "020 7967 8021", null, new Geometry("51.5032973", "-0.1195537"), "ChIJc2nSALkEdkgRkuoJJBfzkUI",
                types1, openingHoursArr1);


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ArrayList<DayOpeningHours> openingHoursArr2 = new ArrayList<>();
        openingHoursArr2.add(new DayOpeningHours(false,true,1,"1000","1800"));
        openingHoursArr2.add(new DayOpeningHours(false, 2));
        openingHoursArr2.add(new DayOpeningHours(false,true, 3, "1100", "1800"));
        openingHoursArr2.add(new DayOpeningHours(false,true,4,"1000","1800"));
        openingHoursArr2.add(new DayOpeningHours(false,true,5,"1000","1800"));
        openingHoursArr2.add(new DayOpeningHours(false,true,6,"1000","1800"));
        openingHoursArr2.add(new DayOpeningHours(false,true,7,"1000","1800"));
        Attraction attraction2 = new Attraction("Tower of London", "London EC3N 4AB, UK",
                "020 3166 6000", null, new Geometry("51.50811239999999", "-0.0759493"), "ChIJ3TgfM0kDdkgRZ2TV4d1Jv6g",
                new ArrayList<AttractionType>(Arrays.asList(AttractionType.tourist_attraction)), openingHoursArr2);

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ArrayList<DayOpeningHours> openingHoursArr3 = new ArrayList<>();
        openingHoursArr3.add(new DayOpeningHours(false,true,1,"0930","1800"));
        openingHoursArr3.add(new DayOpeningHours(false,true, 2,"0930","1800"));
        openingHoursArr3.add(new DayOpeningHours(false,true, 3, "0930", "1800"));
        openingHoursArr3.add(new DayOpeningHours(false,true,4,"0930","1800"));
        openingHoursArr3.add(new DayOpeningHours(false,true,5,"0930","1800"));
        openingHoursArr3.add(new DayOpeningHours(false,true,6,"0930","1800"));
        openingHoursArr3.add(new DayOpeningHours(false,true,7,"0930","1800"));
        Attraction attraction3 = new Attraction("Tower Bridge", "Tower Bridge Rd, London SE1 2UP, UK",
                "020 7403 3761", null, new Geometry("51.5054564", "-0.07535649999999999"), "ChIJSdtli0MDdkgRLW9aCBpCeJ4",
                new ArrayList<AttractionType>(Arrays.asList(AttractionType.tourist_attraction)), openingHoursArr3);


        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ArrayList<DayOpeningHours> openingHoursArr4 = new ArrayList<>();
        openingHoursArr4.add(new DayOpeningHours(false,true,1,"1000","1400"));
        openingHoursArr4.add(new DayOpeningHours(false,true, 2,"1000","1700"));
        openingHoursArr4.add(new DayOpeningHours(false,true, 3, "1000", "1700"));
        openingHoursArr4.add(new DayOpeningHours(false,true,4,"1000","1700"));
        openingHoursArr4.add(new DayOpeningHours(false,true,5,"1000","1700"));
        openingHoursArr4.add(new DayOpeningHours(false,true,6,"1000","1800"));
        openingHoursArr4.add(new DayOpeningHours(false,true,7,"0800","1700"));
        Attraction attraction4 = new Attraction("Borough Market", "8 Southwark St, London SE1 1TL, UK",
                "020 7407 1002", null, new Geometry("51.50545040000001", "-0.090963"), "ChIJD2bPdVcDdkgRuUSgnOXnKDE",
                new ArrayList<AttractionType>(Arrays.asList(AttractionType.tourist_attraction)), openingHoursArr4);

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ArrayList<DayOpeningHours> openingHoursArr5 = new ArrayList<>();
        openingHoursArr5.add(new DayOpeningHours(false,true,1,"1000","1800"));
        openingHoursArr5.add(new DayOpeningHours(false, 2));
        openingHoursArr5.add(new DayOpeningHours(false, 3));
        openingHoursArr5.add(new DayOpeningHours(false,true,4,"1000","1800"));
        openingHoursArr5.add(new DayOpeningHours(false,true,5,"1000","1800"));
        openingHoursArr5.add(new DayOpeningHours(false,true,6,"1000","1800"));
        openingHoursArr5.add(new DayOpeningHours(false,true,7,"1000","1800"));
        Attraction attraction5 = new Attraction("Hampton Court Palace", "Hampton Ct Way, Molesey, East Molesey KT8 9AU, UK",
                "020 3166 6000", null, new Geometry("51.4036128", "-0.3377623"), "ChIJR4IwDg4LdkgRNVpLiUw0UQ4",
                new ArrayList<AttractionType>(Arrays.asList(AttractionType.tourist_attraction)), openingHoursArr5);


        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ArrayList<DayOpeningHours> openingHoursArr6 = new ArrayList<>();
        openingHoursArr6.add(new DayOpeningHours(false,1));
        openingHoursArr6.add(new DayOpeningHours(false,true, 2,"0900","1700"));
        openingHoursArr6.add(new DayOpeningHours(false,true, 3,"0900","1700"));
        openingHoursArr6.add(new DayOpeningHours(false,true,4,"0900","1700"));
        openingHoursArr6.add(new DayOpeningHours(false,true,5,"0900","1700"));
        openingHoursArr6.add(new DayOpeningHours(false,true,6,"0900","1700"));
        openingHoursArr6.add(new DayOpeningHours(false,7));
        Attraction attraction6 = new Attraction("Palace of Westminster", "London SW1A 0AA, UK",
                "020 7219 3000", null, new Geometry("51.4994794", "-0.1248092"), "ChIJmZuNDMQEdkgRfB9O9456eQc",
                new ArrayList<AttractionType>(Arrays.asList(AttractionType.tourist_attraction)), openingHoursArr6);

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ArrayList<DayOpeningHours> openingHoursArr7 = new ArrayList<>();
        openingHoursArr7.add(new DayOpeningHours(false,true,1,"1000","1700"));
        openingHoursArr7.add(new DayOpeningHours(false,true, 2,"1000","1600"));
        openingHoursArr7.add(new DayOpeningHours(false,true, 3, "1000", "1600"));
        openingHoursArr7.add(new DayOpeningHours(false,true,4,"1000","1600"));
        openingHoursArr7.add(new DayOpeningHours(false,true,5,"1100","1600"));
        openingHoursArr7.add(new DayOpeningHours(false,true,6,"1000","1600"));
        openingHoursArr7.add(new DayOpeningHours(false,true,7,"1000","1700"));
        Attraction attraction7 = new Attraction("SEA LIFE Centre London Aquarium", "Riverside Building, County Hall, Westminster Bridge Rd, London SE1 7PB, UK",
                "020 7967 8025", null, new Geometry("51.5019938", "-0.1191926"), "ChIJc2nSALkEdkgRviluWxwFsxA",
                new ArrayList<AttractionType>(Arrays.asList(AttractionType.tourist_attraction)), openingHoursArr7);

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ArrayList<DayOpeningHours> openingHoursArr8 = new ArrayList<>();
        openingHoursArr8.add(new DayOpeningHours(false,true,1,"0500","0000"));
        openingHoursArr8.add(new DayOpeningHours(false,true, 2,"0500","0000"));
        openingHoursArr8.add(new DayOpeningHours(false,true, 3, "0500", "0000"));
        openingHoursArr8.add(new DayOpeningHours(false,true,4,"0500","0000"));
        openingHoursArr8.add(new DayOpeningHours(false,true,5,"0500","0000"));
        openingHoursArr8.add(new DayOpeningHours(false,true,6,"0500","0000"));
        openingHoursArr8.add(new DayOpeningHours(false,true,7,"0500","0000"));
        Attraction attraction8 = new Attraction("Hyde Park", "London, UK",
                "0300 061 2000", null, new Geometry("51.5072682", "-0.1657303"), "ChIJhRoYKUkFdkgRDL20SU9sr9E",
                new ArrayList<AttractionType>(Arrays.asList(AttractionType.park)), openingHoursArr8);


        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ArrayList<DayOpeningHours> openingHoursArr9 = new ArrayList<>();
        openingHoursArr9.add(new DayOpeningHours(false,true,1,"1000","1800"));
        openingHoursArr9.add(new DayOpeningHours(false,true, 2,"1000","1800"));
        openingHoursArr9.add(new DayOpeningHours(false,true, 3, "1000", "1800"));
        openingHoursArr9.add(new DayOpeningHours(false,true,4,"1000","1800"));
        openingHoursArr9.add(new DayOpeningHours(false,true,5,"1000","1800"));
        openingHoursArr9.add(new DayOpeningHours(false,true,6,"1000","1800"));
        openingHoursArr9.add(new DayOpeningHours(false,true,7,"1000","1800"));
        Attraction attraction9 = new Attraction("London Transport Museum", "Covent Garden, London WC2E 7BB, UK",
                null, null, new Geometry("51.5119054", "-0.1215648"), "ChIJ4bF21K8FdkgRDXc6FiSVAzE",
                new ArrayList<AttractionType>(Arrays.asList(AttractionType.museum)), openingHoursArr9);

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ArrayList<DayOpeningHours> openingHoursArr10 = new ArrayList<>();
        openingHoursArr10.add(new DayOpeningHours(false,true,1,"1000","1700"));
        openingHoursArr10.add(new DayOpeningHours(false, 2));
        openingHoursArr10.add(new DayOpeningHours(false, 3));
        openingHoursArr10.add(new DayOpeningHours(false,true,4,"1000","1700"));
        openingHoursArr10.add(new DayOpeningHours(false,true,5,"1000","1700"));
        openingHoursArr10.add(new DayOpeningHours(false,true,6,"1000","1700"));
        openingHoursArr10.add(new DayOpeningHours(false,true,7,"1000","1700"));
        Attraction attraction10 = new Attraction("Museum of London Docklands", "No, 1 Warehouse, West India Quay, Hertsmere Rd, London E14 4AL, UK",
                "020 7001 9844", null, new Geometry("51.5075466", "-0.0238582"), "ChIJVTZUsMcCdkgRMc_-OpJq9v8",
                new ArrayList<AttractionType>(Arrays.asList(AttractionType.museum)), openingHoursArr10);

        //System.out.println(attraction10);
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ArrayList<DayOpeningHours> openingHoursArr11 = new ArrayList<>();
        openingHoursArr11.add(new DayOpeningHours(false,true,1,"1000","1600"));
        openingHoursArr11.add(new DayOpeningHours(false,true, 2,"1000","1600"));
        openingHoursArr11.add(new DayOpeningHours(false,true, 3,"1000","1600"));
        openingHoursArr11.add(new DayOpeningHours(false,true,4,"1000","1600"));
        openingHoursArr11.add(new DayOpeningHours(false,true,5,"1000","1600"));
        openingHoursArr11.add(new DayOpeningHours(false,true,6,"1000","1600"));
        openingHoursArr11.add(new DayOpeningHours(false,true,7,"1000","1600"));
        Attraction attraction11 = new Attraction("Madame Tussauds London", "Marylebone Rd, London NW1 5LR, UK",
                "0871 894 3000", null, new Geometry("51.52301740000001", "-0.1543613"), "ChIJgZ24Us4adkgRpDNAwNPO_SY",
                new ArrayList<AttractionType>(Arrays.asList(AttractionType.tourist_attraction)), openingHoursArr11);


        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ArrayList<DayOpeningHours> openingHoursArr12 = new ArrayList<>();
        openingHoursArr12.add(new DayOpeningHours(false,1));
        openingHoursArr12.add(new DayOpeningHours(false,true, 2,"1200","1630"));
        openingHoursArr12.add(new DayOpeningHours(false,true, 3,"1200","1630"));
        openingHoursArr12.add(new DayOpeningHours(false,true,4,"1200","1630"));
        openingHoursArr12.add(new DayOpeningHours(false,true,5,"1200","1630"));
        openingHoursArr12.add(new DayOpeningHours(false,true,6,"1200","1630"));
        openingHoursArr12.add(new DayOpeningHours(false,true,7,"1200","1630"));
        Attraction attraction12 = new Attraction("St. Paul's Cathedral", "St. Paul's Churchyard, London EC4M 8AD, UK",
                "020 7246 8350", null, new Geometry("51.51384530000001", "-0.0983506"), "ChIJh7wHoqwEdkgR3l-vqQE1HTo",
                new ArrayList<AttractionType>(Arrays.asList(AttractionType.church)), openingHoursArr12);

        possibleAttractions.add(attraction1);
        possibleAttractions.add(attraction2);
        possibleAttractions.add(attraction3);
        possibleAttractions.add(attraction4);
        possibleAttractions.add(attraction5);
        possibleAttractions.add(attraction6);
        possibleAttractions.add(attraction7);
        possibleAttractions.add(attraction8);
        possibleAttractions.add(attraction9);
        possibleAttractions.add(attraction10);
        possibleAttractions.add(attraction11);
        possibleAttractions.add(attraction12);

        return possibleAttractions;

    }
    public RouteTrip findTripById(Collection<RouteTrip> trips, int id){
        Iterator itr = trips.iterator();
        RouteTrip element = null;
        boolean isFound = false;

        while(itr.hasNext() && !isFound){
            element = (RouteTrip) itr.next();
            if(element.tripId == id)
                isFound = true;
        }
        return element;
    }

    public static class NotFoundException extends Exception {
        public NotFoundException(String message) {
            super(message);
        }
    }


}
