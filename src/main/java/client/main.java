package client;

import com.google.gson.Gson;
import common.*;
//import common.OpeningHours;
import database.DBManager;
import engine.Engine;
import engine.attraction.Attraction;
import engine.traveler.Traveler;
import engine.trip.DayPlan;
import engine.trip.OnePlan;
import engine.trip.RouteTrip;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


public class main {



    public static void main(String[] args) throws IOException, SQLException, ParseException, RouteTrip.AlreadyExistException, Traveler.HasNoTripsException, RouteTrip.NotFoundException {






        Gson gson = new Gson();
        DBManager db = new DBManager();
        Engine engine = new Engine();
        TripDetails trip = new TripDetails();

        trip.destination = "london";
        trip.hotelID = "ChIJ1TVZs1UFdkgRIeWxo-jEYaE";
        trip.mustSeenAttractionsID = new ArrayList<>();
        trip.mustSeenAttractionsID.add("ChIJ2dGMjMMEdkgRqVqkuXQkj7c");
        trip.mustSeenAttractionsID.add("ChIJ__8_XtYEdkgRtTLtWfASNFg");
        trip.mustSeenAttractionsID.add("ChIJc2nSALkEdkgRkuoJJBfzkUI");
        trip.mustSeenAttractionsID.add("ChIJG1YB2m4RdkgRsetv9D40NGY");
        trip.mustSeenAttractionsID.add("ChIJVbSVrt0EdkgRQH_FO4ZkHc0");
//        trip.mustSeenAttractionsID.add("ChIJh7MHVRyo2EcR39q58wztHZg");
//        trip.mustSeenAttractionsID.add("ChIJH7Vh1YYFdkgR6E9kkPZbg5M");
//        trip.mustSeenAttractionsID.add("ChIJh7wHoqwEdkgR3l-vqQE1HTo");
//        trip.mustSeenAttractionsID.add("ChIJIRauBakEdkgRjoeyuI53AOc");
//        trip.mustSeenAttractionsID.add("ChIJJX-rQLYcdkgRjiQDyLmOB-E");
//        trip.mustSeenAttractionsID.add("ChIJKZQaXxwbdkgRWLo89tC-_V8");

        ArrayList<Attraction> mustSeenAttractions = engine.createArrayListOfMustSeenAttractions(trip.mustSeenAttractionsID,db,"london" );

//        ArrayList<AttractionType> typesHotel = new ArrayList<>();
//        typesHotel.add(AttractionType.lodging);
//        Attraction hotel = new Attraction("Baglioni Hotel - London", "60 Hyde Park Gate, South Kensington, London SW7 5BB, UK",
//                "020 7368 5700", null, new Geometry("51.50167580000001", "-0.1847417"), "ChIJFSZeB1kFdkgRTixgFHqP13g",
//                typesHotel, null);


        ArrayList<DesiredHoursInDay> desiredHoursInDays = new ArrayList<>();
        DesiredHoursInDay d1 = new DesiredHoursInDay();
        d1.setDate("2021-08-30");
        d1.setStartTime("09:00");
        d1.setEndTime("21:00");
        desiredHoursInDays.add(d1);

        DesiredHoursInDay d2 = new DesiredHoursInDay();
        d2.setDate("2021-08-31");
        d2.setStartTime("10:00");
        d2.setEndTime("20:00");
        desiredHoursInDays.add(d2);

//        DesiredHoursInDay d3 = new DesiredHoursInDay();
//        d3.setDate("2021-09-01");
//        d3.setStartTime("10:00");
//        d3.setEndTime("20:00");
//        desiredHoursInDays.add(d3);
//
//        DesiredHoursInDay d4 = new DesiredHoursInDay();
//        d4.setDate("2021-09-02");
//        d4.setStartTime("10:00");
//        d4.setEndTime("20:00");
//        desiredHoursInDays.add(d4);


//        ArrayList<Attraction> allPossibleAttractions = db.getAllAttractionsByDestination("london");
//        ArrayList<Attraction> attractionsAvailable = new ArrayList<Attraction>();
//        for (Attraction attraction : allPossibleAttractions){
//            if(!mustSeenAttractions.contains(attraction))
//                attractionsAvailable.add(attraction);
//        }

//        attractionsAvailable.forEach(attraction -> System.out.println(attraction.getName()));
//        Collections.shuffle(attractionsAvailable);
//        System.out.println("--------------------------------------------");
//        attractionsAvailable.forEach(attraction -> System.out.println(attraction.getName()));
//        System.out.println("--------------------------------------------");
//        Collections.shuffle(attractionsAvailable);
//        attractionsAvailable.forEach(attraction -> System.out.println(attraction.getName()));
//

        trip.hoursEveryDay = desiredHoursInDays;
       // RouteTrip routeTrip = new RouteTrip("london",hotel,mustSeenAttractions, desiredHoursInDays);

        ArrayList<DayPlan> trip1 = engine.createTripForUser(trip.destination,trip.hotelID,trip.mustSeenAttractionsID,trip.hoursEveryDay);


        System.out.println(trip1);
        DayPlan d = new DayPlan();
        Attraction eye = db.getAttractionFromDBByID("ChIJc2nSALkEdkgRkuoJJBfzkUI",Destinations.valueOf("london"));
        Attraction bfi = db.getAttractionFromDBByID("ChIJEYfH57cEdkgRvTdhVI3xJzQ", Destinations.valueOf("london"));
        Attraction rfa = db.getAttractionFromDBByID("ChIJb2Mpz8UEdkgR6dpyR4XJgwE", Destinations.valueOf("london"));
        double s1 = d.calculateScore(eye,bfi, LocalTime.parse("16:00"), LocalDate.parse("2021-08-31"),LocalTime.parse("20:00"));
        double s2 = d.calculateScore(eye,rfa, LocalTime.parse("16:00"), LocalDate.parse("2021-08-31"),LocalTime.parse("20:00"));

        System.out.println("eye -> bfi  " + eye.calcDistanceBetweenAttractions(bfi));
        System.out.println("eye -> rfa  " + eye.calcDistanceBetweenAttractions(rfa));
        System.out.println("bfi" +s1);
        System.out.println("rfa" +s2);

//        for(String attraction :trip.mustSeenAttractionsID){
//            System.out.println(db.getAttractionFromDBByID(attraction,Destinations.valueOf("london")).getName());
//        }


 //       Attraction hotel = db.getHotelFromDBByID(trip.hotelID, Destinations.valueOf("london"));

//        for(String att: trip.mustSeenAttractionsID) {
//            Attraction a = db.getAttractionFromDBByID(att,Destinations.valueOf("london"));
//            System.out.println("hotel -> "+ a.getName() + hotel.calcDistanceBetweenAttractions(a));
//        }
//        ArrayList<OnePlan> d =  trip1.get(1).getDaySchedule();
//        for(OnePlan op : d){
//            System.out.println("hotel -> "+ op.getAttraction().getName() + "  "+ hotel.calcDistanceBetweenAttractions(op.getAttraction()));
//
//        }

//        routeTrip.planRouteTrip(attractionsAvailable);
//        System.out.println(gson.toJson(routeTrip.getPlanForDays()));

//
//        ArrayList<Attraction> mustSeenAttractions1 = engine.createArrayListOfMustSeenAttractions(trip.mustSeenAttractionsID, db, trip.destination);
//        RouteTrip routeTrip = new RouteTrip(trip.destination,db.getHotelFromDBByID(trip.hotelID,Destinations.london), mustSeenAttractions1, trip.hoursEveryDay);
//
//        routeTrip.planRouteTrip(db.getAllAttractionsByDestination("london"));
//        ArrayList<DayPlan> res = routeTrip.getPlanForDays();
//        System.out.println(gson.toJson(res));
//        System.out.println("-------------------------------------");

        //db.insertTripToDB("best trip2", res, "1", "london");
//        ArrayList<TripPlan> arr = db.getTripsFromDbByTravelerId("1");
//        try {
//            System.out.println();
//            db.insertTripToDB("best trip",res,"5");
//        } catch (RouteTrip.NotFoundException e) {
//            System.out.println(e.getMessage());
//
//        }
////
//        db.deleteTripFromUserTripsInDB("4","5");

        //db.getTripsFromDbByTravelerId("1");

//        ArrayList<ArrayList<DayPlan>> trips =  db.getTripsFromDbByTravelerId("3");
        //      System.out.println(trips.get(0));

      ///  System.out.println(engine.getAttractions("london"));





//        routeTrip.divideMustSeenAttractionsForEachDay();
//        int i =1;
//        for(DayPlan d: routeTrip.getPlanForDays()){
//            System.out.println("day " + i );
//            i++;
//            for(Attraction a: d.getMustSeenAttractionsForDay()){
//                System.out.println(a.getName() + " , ");
//            }
//        }

//
//        for(Attraction at: mustSeenAttractions){
//            System.out.println("hotel: "+ hotel.getName() + " ---> " + at.getName() + "  " + hotel.calcDistanceBetweenAttractions(at)
//            + "    " +at.getName() + "  Duration:" + at.getDuration() );
//
//
//        }
//
//        for(Attraction att: mustSeenAttractions) {
//            for (Attraction at : mustSeenAttractions) {
//                if(!att.equals(at))
//                    System.out.println(att.getName() + " ---> " + at.getName() + "  " + att.calcDistanceBetweenAttractions(at));
//            }
//        }



        //routeTrip.planRouteTrip(new ArrayList<>());






    }

    public static class TripDetails {
        String destination;
        String hotelID;
        ArrayList<String> mustSeenAttractionsID;
        ArrayList<DesiredHoursInDay> hoursEveryDay;

        @Override
        public String toString() {
            return "TripDetails{" +
                    "destination='" + destination + '\'' +
                    ", hotelID='" + hotelID + '\'' +
                    ", mustSeenAttractionsID=" + mustSeenAttractionsID +
                    ", hoursEveryDay=" + hoursEveryDay +
                    '}';
        }
    }



/*
//        AttractionsManager attractionsManager = new AttractionsManager();
//        APIManager APIManager = new APIManager();
//        Attraction attraction = new Attraction(APIManager.getAttractionFromAPI("Baglioni Hotel - London"));
//        System.out.println(attraction);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ArrayList<DayOpeningHours> openingHoursArr1 = new ArrayList<>();
        openingHoursArr1.add(new DayOpeningHours(true,0,"1100","1800"));
        openingHoursArr1.add(new DayOpeningHours(true, 1, "1100", "1800"));
        openingHoursArr1.add(new DayOpeningHours(true, 2, "1100", "1800"));
        openingHoursArr1.add(new DayOpeningHours(true,3,"1100","1800"));
        openingHoursArr1.add(new DayOpeningHours(true,4,"1100","1800"));
        openingHoursArr1.add(new DayOpeningHours(true,5,"1100","1800"));
        openingHoursArr1.add(new DayOpeningHours(true,6,"1100","1800"));
        ArrayList<AttractionType> types1 = new ArrayList<>();
        types1.add(AttractionType.tourist_attraction);
        Attraction attraction1 = new Attraction("lastminute.com London Eye", "Riverside Building, County Hall, London SE1 7PB, UK",
                "020 7967 8021", null, new Geometry("51.5032973", "-0.1195537"), "ChIJc2nSALkEdkgRkuoJJBfzkUI",
                types1, openingHoursArr1);


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ArrayList<DayOpeningHours> openingHoursArr2 = new ArrayList<>();
        openingHoursArr2.add(new DayOpeningHours(true,0,"1000","1800"));
        openingHoursArr2.add(new DayOpeningHours(false, 1));
        openingHoursArr2.add(new DayOpeningHours(true, 2, "1100", "1800"));
        openingHoursArr2.add(new DayOpeningHours(true,3,"1000","1800"));
        openingHoursArr2.add(new DayOpeningHours(true,4,"1000","1800"));
        openingHoursArr2.add(new DayOpeningHours(true,5,"1000","1800"));
        openingHoursArr2.add(new DayOpeningHours(true,6,"1000","1800"));
        Attraction attraction2 = new Attraction("Tower of London", "London EC3N 4AB, UK",
                "020 3166 6000", null, new Geometry("51.50811239999999", "-0.0759493"), "ChIJ3TgfM0kDdkgRZ2TV4d1Jv6g",
                new ArrayList<AttractionType>(Arrays.asList(AttractionType.tourist_attraction)), openingHoursArr2);

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ArrayList<DayOpeningHours> openingHoursArr3 = new ArrayList<>();
        openingHoursArr3.add(new DayOpeningHours(true,0,"0930","1800"));
        openingHoursArr3.add(new DayOpeningHours(true, 1,"0930","1800"));
        openingHoursArr3.add(new DayOpeningHours(true, 2, "0930", "1800"));
        openingHoursArr3.add(new DayOpeningHours(true,3,"0930","1800"));
        openingHoursArr3.add(new DayOpeningHours(true,4,"0930","1800"));
        openingHoursArr3.add(new DayOpeningHours(true,5,"0930","1800"));
        openingHoursArr3.add(new DayOpeningHours(true,6,"0930","1800"));
        Attraction attraction3 = new Attraction("Tower Bridge", "Tower Bridge Rd, London SE1 2UP, UK",
                "020 7403 3761", null, new Geometry("51.5054564", "-0.07535649999999999"), "ChIJSdtli0MDdkgRLW9aCBpCeJ4",
                new ArrayList<AttractionType>(Arrays.asList(AttractionType.tourist_attraction)), openingHoursArr3);


        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ArrayList<DayOpeningHours> openingHoursArr4 = new ArrayList<>();
        openingHoursArr4.add(new DayOpeningHours(true,0,"1000","1400"));
        openingHoursArr4.add(new DayOpeningHours(true, 1,"1000","1700"));
        openingHoursArr4.add(new DayOpeningHours(true, 2, "1000", "1700"));
        openingHoursArr4.add(new DayOpeningHours(true,3,"1000","1700"));
        openingHoursArr4.add(new DayOpeningHours(true,4,"1000","1700"));
        openingHoursArr4.add(new DayOpeningHours(true,5,"1000","1800"));
        openingHoursArr4.add(new DayOpeningHours(true,6,"0800","1700"));
        Attraction attraction4 = new Attraction("Borough Market", "8 Southwark St, London SE1 1TL, UK",
                "020 7407 1002", null, new Geometry("51.50545040000001", "-0.090963"), "ChIJD2bPdVcDdkgRuUSgnOXnKDE",
                new ArrayList<AttractionType>(Arrays.asList(AttractionType.tourist_attraction)), openingHoursArr4);

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ArrayList<DayOpeningHours> openingHoursArr5 = new ArrayList<>();
        openingHoursArr5.add(new DayOpeningHours(true,0,"1000","1800"));
        openingHoursArr5.add(new DayOpeningHours(false, 1));
        openingHoursArr5.add(new DayOpeningHours(false, 2));
        openingHoursArr5.add(new DayOpeningHours(true,3,"1000","1800"));
        openingHoursArr5.add(new DayOpeningHours(true,4,"1000","1800"));
        openingHoursArr5.add(new DayOpeningHours(true,5,"1000","1800"));
        openingHoursArr5.add(new DayOpeningHours(true,6,"1000","1800"));
        Attraction attraction5 = new Attraction("Hampton Court Palace", "Hampton Ct Way, Molesey, East Molesey KT8 9AU, UK",
                "020 3166 6000", null, new Geometry("51.4036128", "-0.3377623"), "ChIJR4IwDg4LdkgRNVpLiUw0UQ4",
                new ArrayList<AttractionType>(Arrays.asList(AttractionType.tourist_attraction)), openingHoursArr5);


        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ArrayList<DayOpeningHours> openingHoursArr6 = new ArrayList<>();
        openingHoursArr6.add(new DayOpeningHours(false,0));
        openingHoursArr6.add(new DayOpeningHours(true, 1,"0900","1700"));
        openingHoursArr6.add(new DayOpeningHours(true, 2,"0900","1700"));
        openingHoursArr6.add(new DayOpeningHours(true,3,"0900","1700"));
        openingHoursArr6.add(new DayOpeningHours(true,4,"0900","1700"));
        openingHoursArr6.add(new DayOpeningHours(true,5,"0900","1700"));
        openingHoursArr6.add(new DayOpeningHours(false,6));
        Attraction attraction6 = new Attraction("Palace of Westminster", "London SW1A 0AA, UK",
                "020 7219 3000", null, new Geometry("51.4994794", "-0.1248092"), "ChIJmZuNDMQEdkgRfB9O9456eQc",
                new ArrayList<AttractionType>(Arrays.asList(AttractionType.tourist_attraction)), openingHoursArr6);

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ArrayList<DayOpeningHours> openingHoursArr7 = new ArrayList<>();
        openingHoursArr7.add(new DayOpeningHours(true,0,"1000","1700"));
        openingHoursArr7.add(new DayOpeningHours(true, 1,"1000","1600"));
        openingHoursArr7.add(new DayOpeningHours(true, 2, "1000", "1600"));
        openingHoursArr7.add(new DayOpeningHours(true,3,"1000","1600"));
        openingHoursArr7.add(new DayOpeningHours(true,4,"1100","1600"));
        openingHoursArr7.add(new DayOpeningHours(true,5,"1000","1600"));
        openingHoursArr7.add(new DayOpeningHours(true,6,"1000","1700"));
        Attraction attraction7 = new Attraction("SEA LIFE Centre London Aquarium", "Riverside Building, County Hall, Westminster Bridge Rd, London SE1 7PB, UK",
                "020 7967 8025", null, new Geometry("51.5019938", "-0.1191926"), "ChIJc2nSALkEdkgRviluWxwFsxA",
                new ArrayList<AttractionType>(Arrays.asList(AttractionType.tourist_attraction)), openingHoursArr7);

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ArrayList<DayOpeningHours> openingHoursArr8 = new ArrayList<>();
        openingHoursArr8.add(new DayOpeningHours(true,0,"0500","0000"));
        openingHoursArr8.add(new DayOpeningHours(true, 1,"0500","0000"));
        openingHoursArr8.add(new DayOpeningHours(true, 2, "0500", "0000"));
        openingHoursArr8.add(new DayOpeningHours(true,3,"0500","0000"));
        openingHoursArr8.add(new DayOpeningHours(true,4,"0500","0000"));
        openingHoursArr8.add(new DayOpeningHours(true,5,"0500","0000"));
        openingHoursArr8.add(new DayOpeningHours(true,6,"0500","0000"));
        Attraction attraction8 = new Attraction("Hyde Park", "London, UK",
                "0300 061 2000", null, new Geometry("51.5072682", "-0.1657303"), "ChIJhRoYKUkFdkgRDL20SU9sr9E",
                new ArrayList<AttractionType>(Arrays.asList(AttractionType.park)), openingHoursArr8);


        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ArrayList<DayOpeningHours> openingHoursArr9 = new ArrayList<>();
        openingHoursArr9.add(new DayOpeningHours(true,0,"1000","1800"));
        openingHoursArr9.add(new DayOpeningHours(true, 1,"1000","1800"));
        openingHoursArr9.add(new DayOpeningHours(true, 2, "1000", "1800"));
        openingHoursArr9.add(new DayOpeningHours(true,3,"1000","1800"));
        openingHoursArr9.add(new DayOpeningHours(true,4,"1000","1800"));
        openingHoursArr9.add(new DayOpeningHours(true,5,"1000","1800"));
        openingHoursArr9.add(new DayOpeningHours(true,6,"1000","1800"));
        Attraction attraction9 = new Attraction("London Transport Museum", "Covent Garden, London WC2E 7BB, UK",
                null, null, new Geometry("51.5119054", "-0.1215648"), "ChIJ4bF21K8FdkgRDXc6FiSVAzE",
                new ArrayList<AttractionType>(Arrays.asList(AttractionType.museum)), openingHoursArr9);

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ArrayList<DayOpeningHours> openingHoursArr10 = new ArrayList<>();
        openingHoursArr10.add(new DayOpeningHours(true,0,"1000","1700"));
        openingHoursArr10.add(new DayOpeningHours(false, 1));
        openingHoursArr10.add(new DayOpeningHours(false, 2));
        openingHoursArr10.add(new DayOpeningHours(true,3,"1000","1700"));
        openingHoursArr10.add(new DayOpeningHours(true,4,"1000","1700"));
        openingHoursArr10.add(new DayOpeningHours(true,5,"1000","1700"));
        openingHoursArr10.add(new DayOpeningHours(true,6,"1000","1700"));
        Attraction attraction10 = new Attraction("Museum of London Docklands", "No, 1 Warehouse, West India Quay, Hertsmere Rd, London E14 4AL, UK",
                "020 7001 9844", null, new Geometry("51.5075466", "-0.0238582"), "ChIJVTZUsMcCdkgRMc_-OpJq9v8",
                new ArrayList<AttractionType>(Arrays.asList(AttractionType.museum)), openingHoursArr10);

        //System.out.println(attraction10);
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ArrayList<DayOpeningHours> openingHoursArr11 = new ArrayList<>();
        openingHoursArr11.add(new DayOpeningHours(true,0,"1000","1600"));
        openingHoursArr11.add(new DayOpeningHours(true, 1,"1000","1600"));
        openingHoursArr11.add(new DayOpeningHours(true, 2,"1000","1600"));
        openingHoursArr11.add(new DayOpeningHours(true,3,"1000","1600"));
        openingHoursArr11.add(new DayOpeningHours(true,4,"1000","1600"));
        openingHoursArr11.add(new DayOpeningHours(true,5,"1000","1600"));
        openingHoursArr11.add(new DayOpeningHours(true,6,"1000","1600"));
        Attraction attraction11 = new Attraction("Madame Tussauds London", "Marylebone Rd, London NW1 5LR, UK",
                "0871 894 3000", null, new Geometry("51.52301740000001", "-0.1543613"), "ChIJgZ24Us4adkgRpDNAwNPO_SY",
                new ArrayList<AttractionType>(Arrays.asList(AttractionType.tourist_attraction)), openingHoursArr11);


        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ArrayList<DayOpeningHours> openingHoursArr12 = new ArrayList<>();
        openingHoursArr12.add(new DayOpeningHours(false,0));
        openingHoursArr12.add(new DayOpeningHours(true, 1,"1200","1630"));
        openingHoursArr12.add(new DayOpeningHours(true, 2,"1200","1630"));
        openingHoursArr12.add(new DayOpeningHours(true,3,"1200","1630"));
        openingHoursArr12.add(new DayOpeningHours(true,4,"1200","1630"));
        openingHoursArr12.add(new DayOpeningHours(true,5,"1200","1630"));
        openingHoursArr12.add(new DayOpeningHours(true,6,"1200","1630"));
        Attraction attraction12 = new Attraction("St. Paul's Cathedral", "St. Paul's Churchyard, London EC4M 8AD, UK",
                "020 7246 8350", null, new Geometry("51.51384530000001", "-0.0983506"), "ChIJh7wHoqwEdkgR3l-vqQE1HTo",
                new ArrayList<AttractionType>(Arrays.asList(AttractionType.church)), openingHoursArr12);
*/


}

