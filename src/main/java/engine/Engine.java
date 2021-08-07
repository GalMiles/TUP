package engine;

import common.Destinations;
import database.DBManager;
import engine.attraction.Attraction;
import engine.managers.AttractionsManager;
import engine.managers.TravelerManager;
import engine.planTrip.RouteTrip;
import engine.traveler.Traveler;

import java.io.IOException;
import java.sql.SQLException;

import java.text.ParseException;
import java.util.ArrayList;
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
        DBManager db = new DBManager();
        return db.getAllAttractionsByDestination(destination);
    }

    //getting favorite attractions, no destination related
    public Collection<Attraction> getFavAttractions() throws SQLException {
        DBManager db = new DBManager();
        return db.getFavAttractions();
    }

    public Collection<Attraction> deleteFromFavAttractions(String attractionName) throws SQLException, IOException, ParseException {
        Collection<Attraction> newFavAttraction = null;
        DBManager db = new DBManager();

        //creating Attraction object from string from db
        Attraction attraction = db.getAttractionFromDataBaseByName(attractionName, Destinations.london);
        //getting favAttraction from db
        newFavAttraction = db.getFavAttractions();
        newFavAttraction.remove(attraction);

        /////need to update in data base!!!!!////

        return newFavAttraction;

    }

    public Collection<Attraction> addToFavAttractions(String attractionName) throws SQLException, IOException, ParseException {
        Collection<Attraction> newFavAttraction = null;
        DBManager db = new DBManager();

        //creating Attraction object from string from db
        Attraction attraction = db.getAttractionFromDataBaseByName(attractionName, Destinations.london);

        //getting favAttraction from db
        newFavAttraction = db.getFavAttractions();
        newFavAttraction.add(attraction);

        /////need to update in data base!!!!!////

        return newFavAttraction;
    }

    public Collection<RouteTrip> getMyTrips() throws SQLException {
        Collection<RouteTrip> myTrips = null;
        DBManager db = new DBManager();

        myTrips = db.getMyTripsFromDB();
        return myTrips;
    }

    public Collection<RouteTrip> DeleteFromMyTrips(String tripId) throws SQLException {
        //parsing string to int
        int tripIdNum = Integer.parseInt(tripId);
        Collection<RouteTrip> myTrips = null;

        //using engine.getMyTrips
        myTrips = getMyTrips();
        RouteTrip tripToDelete = null;
        tripToDelete = tripToDelete.findTripById(myTrips, tripIdNum);
        myTrips.remove(tripToDelete);

        /////need to update in data base!!!!!////
        return myTrips;
    }

    public RouteTrip getTrip(String tripId) throws SQLException {
        //parsing string to int
        int tripIdNum = Integer.parseInt(tripId);
        DBManager db = new DBManager();
        RouteTrip trip = null;

        Collection<RouteTrip> myTrips = db.getMyTripsFromDB();
        trip = trip.findTripById(myTrips,tripIdNum);

        return trip;
    }
}
