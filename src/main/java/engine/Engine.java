package engine;

import common.DesiredHoursInDay;
import common.Destinations;
import common.TripPlan;
import database.DBManager;
import engine.attraction.Attraction;
import engine.trip.DayPlan;
import engine.trip.RouteTrip;
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

    public ArrayList<Attraction> getFavoriteAttractions() throws SQLException {
        DBManager db = new DBManager();
        return db.getFavoriteAttractions(currentTravelerID); //or currentId? not sure
    }

    public void deleteFromFavoriteAttractions(ArrayList<String> favoriteAttractionsList) throws SQLException,Attraction.NotFoundException {
        DBManager db = new DBManager();
        for(String favoriteAttraction : favoriteAttractionsList) {
            db.deleteUserOneFavoriteAttraction(favoriteAttraction, currentTravelerID);
        }
    }

    public void addToFavoriteAttractions(ArrayList<String> favoriteAttractionsList) throws SQLException, Traveler.NotFoundException {
        DBManager db = new DBManager();
        for(String favoriteAttraction : favoriteAttractionsList){
            db.addFavoriteAttraction(favoriteAttraction,currentTravelerID);
        }
    }

    public ArrayList<TripPlan> getUserTrips() throws SQLException, Traveler.HasNoTripsException {
        DBManager db = new DBManager();
        return db.getTripsFromDbByTravelerId(currentTravelerID);
    }

    public void deleteTripFromUserTrips(ArrayList<String> tripsListToDelete) throws SQLException {
        DBManager db = new DBManager();
        for(String tripId : tripsListToDelete)
            db.deleteTripFromUserTripsInDB(tripId,currentTravelerID);
    }

    public ArrayList<DayPlan> createTripForUser(String destination, String stringHotelID,
             ArrayList<String> mustSeenAttractionsID, ArrayList<DesiredHoursInDay> desiredHoursInDays) throws SQLException, RouteTrip.AlreadyExistException {
        DBManager db = new DBManager();
        Attraction hotel  = db.getHotelFromDBByID(stringHotelID, Destinations.valueOf(destination));

//        ArrayList<AttractionType> typesHotel = new ArrayList<>();
//        typesHotel.add(AttractionType.lodging);
//        Attraction hotel = new Attraction("Baglioni Hotel - London", "60 Hyde Park Gate, South Kensington, London SW7 5BB, UK",
//                "020 7368 5700", null, new Geometry("51.50167580000001", "-0.1847417"), "ChIJFSZeB1kFdkgRTixgFHqP13g",
//                typesHotel, null);

        ArrayList<Attraction> mustSeenAttractions = createArrayListOfMustSeenAttractions(mustSeenAttractionsID, db, destination);
        RouteTrip routeTrip = new RouteTrip(destination,hotel, mustSeenAttractions, desiredHoursInDays);
        ArrayList<Attraction> attractionsAvailable = createListOfRestAttractionAvailableInDestination(db,destination,mustSeenAttractions);

//        ArrayList<Attraction> mustSeenAttractions = null;
//        RouteTrip routeTrip = new RouteTrip(destination,hotel, mustSeenAttractions, desiredHoursInDays);
//
//        ArrayList<Attraction> attractionsAvailable = null;

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

    public void updateTravelerDetails(Traveler newTraveler) throws SQLException, Traveler.AlreadyExistsException, Traveler.IllegalValueException {
        DBManager db = new DBManager();
        Boolean isSameEmail = newTraveler.getEmailAddress().equals(db.getTravelerFromDBByID(currentTravelerID).getEmailAddress());
        db.updateTravelerDetailsOnDB(newTraveler,currentTravelerID, isSameEmail);

    }


    public int saveUserTripOnDB(TripPlan tripPlan) throws SQLException, RouteTrip.AlreadyExistException, RouteTrip.NotFoundException {
        DBManager db = new DBManager();
       return db.insertTripToDB(tripPlan.getTripName(), tripPlan.getPlans(), currentTravelerID);
    }

    public ArrayList<Attraction> getHotelsByDestination(String destination) throws SQLException, Attraction.NoHotelsOnDestination {
        DBManager db = new DBManager();
        return db.getAllHotelsFromDB(destination);

    }
}
