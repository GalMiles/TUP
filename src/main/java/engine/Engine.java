package engine;

import com.google.gson.Gson;
import common.AttractionType;
import common.DesiredHoursInDay;
import common.Destinations;
import common.Geometry;
import database.DBManager;
import engine.attraction.Attraction;
import engine.managers.AttractionsManager;
import engine.managers.TravelerManager;
import engine.planTrip.DayPlan;
import engine.planTrip.RouteTrip;
import engine.traveler.Traveler;

import java.io.IOException;
import java.sql.SQLException;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class Engine {


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

    public Collection<Attraction> getFavAttractions() throws SQLException {
        DBManager db = new DBManager();
        return db.getFavAttractions();
    }

    public void deleteFromFavAttractions(String attractionId) throws SQLException {
        DBManager db = new DBManager();
        //db.deleteUserOneFavoriteAttraction(attractionId);
    }

    public void addToFavAttractions(String attractionNamId) throws SQLException {
        DBManager db = new DBManager();
        //db.addFavoriteAttraction(attractionId);
    }

    public Collection<RouteTrip> getUserTrips() throws SQLException, RouteTrip.NotFoundException {
        DBManager db = new DBManager();
        //return db.getUserTripsFromDB();
        return null;
    }

    public void deleteTripFromUserTrips(String tripId) throws SQLException {
        DBManager db = new DBManager();
        //db.deleteTripFromUserTripsInDB(tripId);
    }

    public ArrayList<DayPlan> createTripForUser(String destination, String stringHotelID,
            ArrayList<String> mustSeenAttractionsID, ArrayList<DesiredHoursInDay> desiredHoursInDays) throws SQLException{
        DBManager db = new DBManager();
        //Attraction hotel  = db.getAttractionFromDBByID(stringHotelID);

        ArrayList<AttractionType> typesHotel = new ArrayList<>();
        typesHotel.add(AttractionType.lodging);
        Attraction hotel = new Attraction("Baglioni Hotel - London", "60 Hyde Park Gate, South Kensington, London SW7 5BB, UK",
                "020 7368 5700", null, new Geometry("51.50167580000001", "-0.1847417"), "ChIJFSZeB1kFdkgRTixgFHqP13g",
                typesHotel, null);

        ArrayList<Attraction> mustSeenAttractions = createArrayListOfMustSeenAttractions(mustSeenAttractionsID, db);
        RouteTrip routeTrip = new RouteTrip(destination,hotel, mustSeenAttractions, desiredHoursInDays);
        routeTrip.planRouteTrip();
        return routeTrip.getPlanForDays();
    }

    private ArrayList<Attraction> createArrayListOfMustSeenAttractions(ArrayList<String> mustSeenAttractionsID,DBManager db) throws SQLException {
        ArrayList<Attraction> mustSeenAttractions = new ArrayList<>();
        for (String attractionID: mustSeenAttractionsID)
            mustSeenAttractions.add(db.getAttractionFromDBByID(attractionID));
        return mustSeenAttractions;
    }

    public void deleteFavoriteAttractionsById(String attractionId) throws SQLException,Attraction.NotFoundException {
        DBManager db = new DBManager();
        //db.deleteAttractionfromFavoriteList(attractionId);

    }
}
