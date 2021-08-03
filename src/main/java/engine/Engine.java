package engine;

import engine.attraction.Attraction;
import engine.managers.AttractionsManager;
import engine.managers.TravelerManager;
import engine.traveler.Traveler;

public class Engine {
    TravelerManager travelers = new TravelerManager();
    AttractionsManager attractions;


//    public Attraction getAttractionByName(String name) {
//        try {
//            attractions.getAttraction(name);
//        } catch (Attraction.NotFoundException e) {
//            e.printStackTrace();
//        }
//
//    }




    //here we need to implements all the action we wand the app will have

    public Traveler getTraveler(String emailAddress) throws Traveler.NotFoundException {
       return travelers.getTraveler(emailAddress);
    }

}
