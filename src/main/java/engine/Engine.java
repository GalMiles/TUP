package engine;

import database.DBManager;
import engine.attraction.Attraction;
import engine.managers.AttractionsManager;
import engine.managers.TravelerManager;
import engine.traveler.Traveler;

import java.sql.SQLException;

public class Engine {
    TravelerManager travelers = new TravelerManager();
    AttractionsManager attractions;
    DBManager db = new DBManager();

    public Engine() throws SQLException {}

//    public Attraction getAttractionByName(String name) {
//        try {
//            attractions.getAttraction(name);
//        } catch (Attraction.NotFoundException e) {
//            e.printStackTrace();
//        }
//
//    }




    //here we need to implements all the action we wand the app will have

    public Traveler getTraveler(String emailAddress, String password) throws Traveler.NotFoundException, SQLException {
        return db.getTravelerFromB(emailAddress,password);
    }

    public void addTraveler(Traveler traveler) throws SQLException {
        db.insertTravelerToDataBase(traveler);
    }
}
