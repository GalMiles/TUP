package engine;

import common.Destinations;
import database.DBManager;
import engine.attraction.Attraction;
import engine.managers.AttractionsManager;
import engine.managers.TravelerManager;
import engine.traveler.Traveler;

import java.sql.SQLException;

import java.util.Collection;

public class Engine {
    TravelerManager travelers = new TravelerManager();
    AttractionsManager attractions;

/*

    public Engine() throws SQLException {

    }

 */

//    public Attraction getAttractionByName(String name) {
//        try {
//            attractions.getAttraction(name);
//        } catch (Attraction.NotFoundException e) {
//            e.printStackTrace();
//        }
//
//    }




    //here we need to implements all the action we wand the app will have
    public Traveler login(String emailAddress, String password) throws Traveler.NotFoundException, SQLException {
        DBManager db = new DBManager();
        return db.Login(emailAddress,password);
    }

    public void Register(Traveler traveler) throws SQLException, Traveler.AlreadyExistsException {
        DBManager db = new DBManager();
        db.Register(traveler);
    }

    public Collection<Attraction> getAttractions(String destination) throws SQLException {
        //return null;
        DBManager db = new DBManager();
        return db.getAllAttractionsByDestination(Destinations.valueOf(destination));
    }
}
