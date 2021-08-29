package database;

import com.google.gson.Gson;
import common.AttractionType;
import common.DayOpeningHours;
import common.Destinations;
import common.TripPlan;
import engine.attraction.Attraction;
import engine.trip.DayPlan;
import engine.trip.OnePlan;
import engine.trip.RouteTrip;
import engine.traveler.Traveler;
import googleAPI.APIManager;
import googleAPI.JsonAttraction;


import wikipediaAPI.wikiAPIManager;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;

public class DBManager {

    private APIManager apiManager = new APIManager();
    private Statement statement;
    public Connection sqlConnection;


    public DBManager() throws SQLException {
//        this.sqlConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tup", "root", "Galmiles31051960");
//        this.statement = sqlConnection.createStatement();

        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

        sqlConnection = DriverManager.getConnection("jdbc:mysql://database-1.cxfxbg1niylb.us-west-2.rds.amazonaws.com:3306/tup", "admin", "Galmiles1105");
        statement = sqlConnection.createStatement();
    }

    public void closeConnection() throws SQLException {
        this.sqlConnection.close();
    }

    public ArrayList<Attraction> getAllAttractionsByDestination(String destination) throws SQLException {
        ArrayList<Attraction> attractions = new ArrayList<>();
        String sql = "SELECT * FROM tup." + destination.toString();
        PreparedStatement ps = this.sqlConnection.prepareStatement(sql);
        ResultSet results = ps.executeQuery();
        while (results.next()) {
            attractions.add(new Attraction(results));
        }
        return attractions;
    }

    public String Register(Traveler traveler) throws SQLException, Traveler.AlreadyExistsException {
        String idString = null;
        checkIfEmailIsFound(traveler.getEmailAddress());
        String query = "INSERT INTO travelers(Email, Password, FirstName, LastName) VALUES ( "
                + "\"" + traveler.getEmailAddress() + "\", \"" + traveler.getPassword() + "\", \"" + traveler.getFirstName() +
                "\", \"" + traveler.getLastName() + "\")";
        this.statement.executeUpdate(query);
        query = "SELECT traveler_id FROM travelers WHERE Email =  \"" + traveler.getEmailAddress() + "\"";
        ResultSet resultSet = this.statement.executeQuery(query);
        if (resultSet.next()) {
            idString = resultSet.getString("traveler_id");
        }
        return idString;
    }

    public Traveler Login(String Email, String Password) throws SQLException, Traveler.NotFoundException, Traveler.IllegalValueException {
        Traveler traveler = null;
        String query = "SELECT * FROM travelers WHERE Email=? and Password=?";
        PreparedStatement ps = this.sqlConnection.prepareStatement(query);
        ps.setString(1, Email);
        ps.setString(2, Password);
        ResultSet results = ps.executeQuery();
        if (results.next()) {
            String userName = results.getString("Email");
            String password = results.getString("Password");
            String firstName = results.getString("FirstName");
            String lastName = results.getString("LastName");
            int travelerId = results.getInt("traveler_id");
            traveler = new Traveler(firstName, lastName, userName, password);
            traveler.setTravelerId(travelerId);

        } else {
            throw new Traveler.NotFoundException("Traveler not found");
        }
        return traveler;
    }

    private String getTypesStr(ArrayList<AttractionType> types) {
        StringBuilder typesStr = new StringBuilder();
        int typesArrSize = types.size();
        for (int i = 0; i < typesArrSize - 1; ++i) {
            if (types.get(i) != null) {
                typesStr.append(types.get(i).toString() + ",");
            }
        }
        if (types.get(typesArrSize - 1) != null) {
            typesStr.append(types.get(typesArrSize - 1).toString());
        }
        return typesStr.toString();
    }

    private String checkParameter(String param) {
        if (param == null) {
            return "N\\A";
        } else {
            return param;
        }
    }

    public Attraction getAttractionFromDBByID(String id, Destinations destination) throws SQLException {
        Attraction resAttracion = null;
        String query = "SELECT * FROM tup." + destination.toString() + " WHERE attractionAPI_ID =\"" + id + "\"";
        ResultSet resultSet = this.statement.executeQuery(query);
        if (resultSet.next()) {
            resAttracion = new Attraction(resultSet);
        }
        return resAttracion;
    }

    public void checkIfEmailIsFound(String email) throws SQLException, Traveler.AlreadyExistsException {
        String query = "SELECT * FROM travelers WHERE Email = \"" + email + "\"";
        ResultSet resultSet = this.statement.executeQuery(query);
        if (resultSet.next()) {
            throw new Traveler.AlreadyExistsException("Traveler is exist in the DB");
        }
    }

    public ArrayList<Attraction> getFavoriteAttractions(String travelerID) throws SQLException {
        Destinations destination = null;
        ArrayList<Attraction> attractions = new ArrayList<>();

        PreparedStatement p = sqlConnection.prepareStatement("SELECT * FROM favorites_attractions WHERE traveler_id = ? ");
        p.setString(1, travelerID);
        //p.execute();
        ResultSet results = p.executeQuery();
        while (results.next()) {
            String attractionId = results.getString("attractionAPI_ID");
            Attraction attraction = getAttractionFromDBByID(attractionId, destination.london);
            attractions.add(attraction);
        }
        return attractions;
    }

    public void deleteUserOneFavoriteAttraction(String attractionId, String travelerID) throws SQLException {

        PreparedStatement p = sqlConnection.prepareStatement("DELETE FROM favorites_attractions WHERE traveler_id = ? AND attractionAPI_ID = ?");
        p.setString(1, travelerID);
        p.setString(2, attractionId);
        p.execute();
    }

    public void addFavoriteAttraction(String attractionId, String travelerID) throws SQLException {
        PreparedStatement p = sqlConnection.prepareStatement("INSERT INTO favorites_attractions (traveler_id, attractionAPI_ID) VALUES (?, ?)");
        p.setString(1, travelerID);
        p.setString(2, attractionId);
        p.execute();
    }

    public Attraction getHotelFromDBByID(String id, Destinations destination) throws SQLException {
        Attraction resAttracion = null;
        String query = "SELECT * FROM tup." + destination.toString() + "_hotels" + " WHERE attractionAPI_ID =\"" + id + "\"";
        ResultSet resultSet = this.statement.executeQuery(query);
        if (resultSet.next()) {
            resAttracion = new Attraction(resultSet);
        }
        return resAttracion;

    }

    public ArrayList<Attraction> getAllHotelsFromDB(String destination) throws SQLException, Attraction.NoHotelsOnDestination {
        ArrayList<Attraction> hotels = new ArrayList<>();
        String sql = "SELECT * FROM tup." + destination + "_hotels";
        PreparedStatement ps = this.sqlConnection.prepareStatement(sql);
        ResultSet results = ps.executeQuery();
        if(!results.next()) {throw new Attraction.NoHotelsOnDestination("there are not hotels on this destination");}
        while (results.next()) {
            hotels.add(new Attraction(results));
        }
        return hotels;
    }

    public void updateTravelerDetailsOnDB(Traveler newTraveler, String currentTravelerID, Boolean isSameEmail) throws Traveler.AlreadyExistsException, SQLException {
        if (!isSameEmail)
            checkIfEmailIsFound(newTraveler.getEmailAddress());

        PreparedStatement p = sqlConnection.prepareStatement("UPDATE travelers SET Email = ? , Password = ? , FirstName = ? , " +
                "LastName =? WHERE traveler_id =?");

        p.setString(1, newTraveler.getEmailAddress());
        p.setString(2, newTraveler.getPassword());
        p.setString(3, newTraveler.getFirstName());
        p.setString(4, newTraveler.getLastName());
        p.setString(5, currentTravelerID);

        p.execute();
    }

    public Traveler getTravelerFromDBByID(String id) throws SQLException, Traveler.IllegalValueException {
        Traveler resTraveler = null;
        String query = "SELECT * FROM tup.travelers WHERE traveler_id =\"" + id + "\"";
        ResultSet resultSet = this.statement.executeQuery(query);
        if (resultSet.next()) {
            resTraveler = new Traveler(resultSet);
        }
        return resTraveler;
    }

    public ArrayList<TripPlan> getTripsFromDbByTravelerId(String travelerID) throws SQLException, Traveler.HasNoTripsException {
        ArrayList<TripPlan> trips = new ArrayList<>();
        PreparedStatement p = sqlConnection.prepareStatement("SELECT * FROM trips WHERE traveler_id = ?");
        p.setString(1, travelerID);
        ResultSet results = p.executeQuery();

        //if(!results.next()) { throw new Traveler.HasNoTripsException("Traveler with id " + travelerID); }

        while (results.next()) {
            RouteTrip routeTrip = new RouteTrip();
            routeTrip = routeTrip.createRouteTripFromJson(results);
            fillRouteTrip(routeTrip);
            TripPlan tripPlan = new TripPlan(results.getString("trip_name"),routeTrip.getPlanForDays(), routeTrip.getDestination().name(),results.getInt("trip_id"));
            trips.add(tripPlan);
        }
        return trips;
    }

    private void fillRouteTrip(RouteTrip routeTrip) throws SQLException {
        ArrayList<DayPlan> res = routeTrip.getPlanForDays();
        for (DayPlan d : res) {
            ArrayList<OnePlan> re = d.getDaySchedule();
            for (OnePlan p1 : re) {
                Attraction attraction = getAttractionFromDBByID(p1.getAttractionId(), routeTrip.getDestination());
                if (attraction == null)
                    attraction = getHotelFromDBByID(p1.getAttractionId(), routeTrip.getDestination());
                p1.setAttraction(attraction);
            }
        }
    }

    public int insertTripToDB(String tripName, ArrayList<DayPlan> tripPlan, String currentTravelerID, String destination) throws SQLException, RouteTrip.AlreadyExistException, RouteTrip.NotFoundException {
        Gson gson = new Gson();
        RouteTrip routeTrip = createRouteTripToDB(tripPlan, destination);
        CheckIfTripExists(routeTrip,currentTravelerID, gson);

       // insert RoutTrip to DB
        PreparedStatement p = sqlConnection.prepareStatement("INSERT INTO trips (traveler_id, trip, trip_name) VALUES (? ,?,? )");
        p.setString(1, currentTravelerID);
        p.setString(2, gson.toJson(routeTrip));
        p.setString(3,tripName);
        p.execute();

        return updateTripID(routeTrip, currentTravelerID, gson);
    }

    private RouteTrip createRouteTripToDB(ArrayList<DayPlan> tripPlan, String destination){
        RouteTrip routeTrip = new RouteTrip(tripPlan, destination);
        ArrayList<DayPlan> res = routeTrip.getPlanForDays();
        for (DayPlan d : res) {
            ArrayList<OnePlan> re = d.getDaySchedule();
            //d.setHotel(null);
            d.setNullMustSeenAttractionsForDay();
            for (OnePlan p : re) {
                p.setAttractionId(p.getAttractionId());
                p.setAttraction(null);
            }
        }
        return routeTrip;
    }

    private int updateTripID(RouteTrip routeTrip, String currentTravelerID, Gson gson) throws SQLException, RouteTrip.NotFoundException {
        PreparedStatement p = sqlConnection.prepareStatement("SELECT trip_id FROM trips WHERE (trip = CAST(? AS JSON) AND traveler_id = ?)");
        p.setString(1, gson.toJson(routeTrip));
        p.setString(2, currentTravelerID);
        ResultSet results = p.executeQuery();
        int tripID = 0;
        if(results.next()) {
            tripID = results.getInt("trip_id");
            routeTrip.setTripId(tripID);
        }
        else
         throw new RouteTrip.NotFoundException("cant find trip to update its tripID");

//        p = sqlConnection.prepareStatement("UPDATE trips SET trip = ? WHERE trip_id = ?");
//        p.setString(1,gson.toJson(routeTrip));
//        p.setInt(2, tripID);
//        p.execute();
        return tripID;
    }

    private void CheckIfTripExists(RouteTrip routeTrip, String currentTravelerID, Gson gson) throws SQLException, RouteTrip.AlreadyExistException{
        PreparedStatement p = sqlConnection.prepareStatement("SELECT * FROM trips WHERE (trip = CAST(? AS JSON) AND traveler_id = ?)");
        p.setString(1, gson.toJson(routeTrip));
        p.setString(2, currentTravelerID);
            ResultSet results = p.executeQuery();
            if (results.next()) {
                throw new RouteTrip.AlreadyExistException("route trip with the same plans already exists by name" + results.getString("trip_name"));
            }
        }

    public void deleteTripFromUserTripsInDB(String tripId, String currentTravelerID) throws SQLException {
        PreparedStatement p = sqlConnection.prepareStatement("DELETE FROM trips WHERE traveler_id = ? AND trip_id = ?");
        p.setString(1, currentTravelerID);
        p.setString(2, tripId);
        p.execute();
    }

    public void isTravelerExistInDB(String travelerID) throws SQLException, Traveler.NotFoundException {
        String query = "SELECT * FROM travelers WHERE traveler_id=?";
        PreparedStatement ps = this.sqlConnection.prepareStatement(query);
        ps.setString(1, travelerID);
        ResultSet results = ps.executeQuery();
        if(!results.next()){
            throw new Traveler.NotFoundException("Traveler id doesn't exist!");
        }
    }
    public void insertHotelsImagesToDB() throws SQLException, IOException, Attraction.NoHotelsOnDestination {

        String image = null;
        wikiAPIManager wiki = new wikiAPIManager();
        ArrayList<Attraction> attractions = this.getAllHotelsFromDB("london");
        for (Attraction att : attractions) {
            image = wiki.getAttractionImageFromWiki(att.getName());

            PreparedStatement p = sqlConnection.prepareStatement("UPDATE london_hotels SET Image = ? WHERE attractionAPI_ID = ?");
            p.setString(1, image);
            p.setString(2, att.getPlaceID());
            p.execute();
        }

    }

    public void insertAttractionsImagesToDB() throws SQLException, IOException {

        String image = null;
        wikiAPIManager wiki = new wikiAPIManager();
        ArrayList<Attraction> attractions = this.getAllAttractionsByDestination("london");
        for (Attraction att : attractions) {
            image = wiki.getAttractionImageFromWiki(att.getName());

            PreparedStatement p = sqlConnection.prepareStatement("UPDATE london SET Image = ? WHERE attractionAPI_ID = ?");
            p.setString(1, image);
            p.setString(2, att.getPlaceID());
            p.execute();
        }

    }

    public void insertAttractionsDescriptionToDB() throws SQLException, IOException {

        String description = null;
        wikiAPIManager wiki = new wikiAPIManager();
        ArrayList<Attraction> attractions = this.getAllAttractionsByDestination("london");
        for (Attraction att : attractions) {
            description = wiki.getAttractionDescriptionFromWiki(att.getName());

            PreparedStatement p = sqlConnection.prepareStatement("UPDATE london SET Description = ? WHERE attractionAPI_ID = ?");
            p.setString(1, description);
            p.setString(2, att.getPlaceID());
            p.execute();
        }

    }

    public void insertToDB() throws SQLException, IOException, ParseException {

        wikiAPIManager w = new wikiAPIManager();

        String attID = null;
        String atractionName = "Britannia_Hotels";
        attID = "ChIJyZap4LsCdkgR15bK4_L6-m0";

//        String url = "https://en.wikipedia.org/w/api.php?action=query&titles=" + atractionName+ "&prop=extracts&format=json";
//       String description =  w.getAttractionDescriptionFromWikiWithUrl(url);
//
//        PreparedStatement p = sqlConnection.prepareStatement("UPDATE london SET Description = ? WHERE attractionAPI_ID = ?");
//        p.setString(1, description);
//        p.setString(2, attID);
//        p.execute();

        String url = "https://en.wikipedia.org/w/api.php?action=query&titles=" + atractionName + "&prop=pageimages&format=json&pithumbsize=150";
        String image = w.getAttractionImageFromWikiWithUrl(url);


        PreparedStatement p = sqlConnection.prepareStatement("UPDATE london_hotels SET Image = ? WHERE attractionAPI_ID = ?");
        p.setString(1, image);
        p.setString(2, attID);
        p.execute();
    }
    public void insertAttractionToDB(Attraction attraction, Destinations destination) throws SQLException {
        ArrayList<StringBuilder> dayStrArr = new ArrayList<>();
        for (int i = 0; i < 7; ++i) {
            dayStrArr.add(i, new StringBuilder());
        }
        PreparedStatement ps = this.sqlConnection.prepareStatement(
                "INSERT INTO " + destination.name() + " (attractionAPI_ID, Name, Address, PhoneNumber,Website, Geometry, types," +
                        "Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday)" +
                        "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, attraction.getPlaceID());
        ps.setString(2, attraction.getName());
        ps.setString(3, attraction.getAddress());//ADDRESS
        ps.setString(4, this.checkParameter(attraction.getPhoneNumber()));//PHONENUMBER
        ps.setString(5, this.checkParameter(attraction.getWebsite()));//Website
        ps.setString(6, attraction.getGeometry().toString());//Geometry
        ps.setString(7, this.getTypesStr(attraction.getTypes()));
        int index = 8;
        for (DayOpeningHours day : attraction.getOpeningHoursArr()) {
            if (day.isAllDayLongOpened()) {
                ps.setString(index, "All Day Long");
            } else {
                if (!day.isOpen()) {
                    ps.setString(index, "Closed");
                } else {
                    int arraySize = day.getOpeningHours().size();
                    String dayOpeningHours = "";
                    for (int j = 0; j < arraySize - 1; ++j) {
                        String currentOpeningHour = day.getOpeningHours().get(j) + "-" + day.getClosingHours().get(j);
                        dayOpeningHours += currentOpeningHour + ", ";
                    }
                    String currentOpeningHour = day.getOpeningHours().get(arraySize - 1) + "-" + day.getClosingHours().get(arraySize - 1);
                    dayOpeningHours += currentOpeningHour;
                    ps.setString(index, dayOpeningHours);
                }
            }
            index++;
        }
        ps.executeUpdate();
    }

}
