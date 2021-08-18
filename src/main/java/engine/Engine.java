package engine;

import common.AttractionType;
import common.DesiredHoursInDay;
import common.Destinations;
import common.Geometry;
import database.DBManager;
import engine.attraction.Attraction;
import engine.planTrip.DayPlan;
import engine.planTrip.RouteTrip;
import engine.traveler.Traveler;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collection;

public class Engine {

    String currentTravelerID = null;

    public String getCurrentTravelerID() {
        return currentTravelerID;
    }
    public void setCurrentTravelerID(String currentTravelerID) {
        this.currentTravelerID = currentTravelerID;
    }

    //here we need to implements all the action we wand the app will have
    public Traveler login(String emailAddress, String password) throws Traveler.NotFoundException, SQLException {
        DBManager db = new DBManager();
        return db.Login(emailAddress,password);
    }

    public String Register(Traveler traveler) throws SQLException, Traveler.AlreadyExistsException {
        DBManager db = new DBManager();
        return db.Register(traveler);
    }

    public Collection<Attraction> getAttractions(String destination) throws SQLException {
        DBManager db = new DBManager();
        return db.getAllAttractionsByDestination(destination);
        //return null;
    }

    public Collection<Attraction> getFavoriteAttractions() throws SQLException {
        DBManager db = new DBManager();
        return db.getFavoriteAttractions(currentTravelerID);
    }

    public void deleteFromFavoriteAttractions(String attractionId) throws SQLException,Attraction.NotFoundException {
        DBManager db = new DBManager();
        //db.deleteUserOneFavoriteAttraction(attractionId,currentTravelerID);
    }

    public void addToFavoriteAttractions(String attractionNamId) throws SQLException {
        DBManager db = new DBManager();
        //db.addFavoriteAttraction(attractionId,currentTravelerID);
    }

    public Collection<RouteTrip> getUserTrips() throws SQLException, RouteTrip.NotFoundException {
        DBManager db = new DBManager();
        //return db.getUserTripsFromDB(currentTravelerID);
        return null;
    }

    public void deleteTripFromUserTrips(String tripId) throws SQLException {
        DBManager db = new DBManager();
        //db.deleteTripFromUserTripsInDB(tripId,currentTravelerID);
    }

    public ArrayList<DayPlan> createTripForUser(String destination, String stringHotelID,
             ArrayList<String> mustSeenAttractionsID, ArrayList<DesiredHoursInDay> desiredHoursInDays) throws SQLException{
        DBManager db = new DBManager();
        //Attraction hotel  = db.getAttractionFromDBByID(stringHotelID, Destinations.valueOf(destination));

        ArrayList<AttractionType> typesHotel = new ArrayList<>();
        typesHotel.add(AttractionType.lodging);
        Attraction hotel = new Attraction("Baglioni Hotel - London", "60 Hyde Park Gate, South Kensington, London SW7 5BB, UK",
                "020 7368 5700", null, new Geometry("51.50167580000001", "-0.1847417"), "ChIJFSZeB1kFdkgRTixgFHqP13g",
                typesHotel, null);

        ArrayList<Attraction> mustSeenAttractions = createArrayListOfMustSeenAttractions(mustSeenAttractionsID, db, destination);
        RouteTrip routeTrip = new RouteTrip(destination,hotel, mustSeenAttractions, desiredHoursInDays);
        ArrayList<Attraction> attractionsAvailable = createListOfRestAttractionAvailableInDestination(db,destination,mustSeenAttractions);
        routeTrip.planRouteTrip(attractionsAvailable);
        return routeTrip.getPlanForDays();

    }

    private ArrayList<Attraction> createListOfRestAttractionAvailableInDestination(DBManager db,String destination, ArrayList<Attraction> mustSeenAttractions) throws SQLException {
        ArrayList<Attraction> allPossibleAttractions = db.getAllAttractionsByDestination(destination);
        ArrayList<Attraction> attractionsAvailable = new ArrayList<Attraction>();
        for (Attraction attraction : allPossibleAttractions){
            if(!mustSeenAttractions.contains(attraction))
                attractionsAvailable.add(attraction);
        }
        return attractionsAvailable;
    }

    public ArrayList<Attraction> createArrayListOfMustSeenAttractions(ArrayList<String> mustSeenAttractionsID,DBManager db,String destination) throws SQLException {
        ArrayList<Attraction> mustSeenAttractions = new ArrayList<>();
        for (String attractionID: mustSeenAttractionsID)
            mustSeenAttractions.add(db.getAttractionFromDBByID(attractionID, Destinations.valueOf(destination)));
        return mustSeenAttractions;
    }


    public ArrayList<String> getDestinations() {
        ArrayList<String> destinations = new ArrayList<>();
        Destinations[] destinationsList = Destinations.values();
        for (Destinations des: destinationsList)
            destinations.add(des.name());
        return destinations;
    }
}
