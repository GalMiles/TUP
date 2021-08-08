package database;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import common.AttractionType;
import common.DayOpeningHours;
import common.Destinations;
import common.OpeningHours;
import engine.attraction.Attraction;
import engine.planTrip.RouteTrip;
import engine.traveler.Traveler;
import googleAPI.APIManager;
import googleAPI.JsonAttraction;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;

public class DBManager {

    private APIManager apiManager = new APIManager();
    private Statement statement;
    private Connection sqlConnection;


    public DBManager() throws SQLException {
        this.sqlConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tup", "root", "Galmiles31051960");
    }


    public void closeConnection() throws SQLException {
        this.sqlConnection.close();
    }




    public ArrayList<Attraction> getAllAttractionsByDestination(String destination) throws SQLException {
        ArrayList<Attraction> attractions = new ArrayList<>();
        String sql = "SELECT * FROM tup." + destination.toString();
        PreparedStatement ps = this.sqlConnection.prepareStatement(sql);
        ResultSet results = ps.executeQuery();
        while(results.next())
        {
            attractions.add(new Attraction(results));
        }
        return attractions;
    }


    public void Register(Traveler traveler) throws SQLException, Traveler.AlreadyExistsException {
        checkIfEmailIsFound(traveler.getEmailAddress());
        String query = "INSERT INTO travelers(Email, Password, FirstName, LastName) VALUES ( "
                + "\"" +traveler.getEmailAddress() + "\", \"" + traveler.getPassword() + "\", \"" + traveler.getFirstName() +
                    "\", \"" + traveler.getLastName()+ "\")";
        this.statement.executeUpdate(query);
    }


    //login
    public Traveler Login(String Email, String Password) throws SQLException, Traveler.NotFoundException {
        Traveler traveler = null;
        String query = "SELECT * FROM travelers WHERE Email=? and Password=?";
        PreparedStatement ps = this.sqlConnection.prepareStatement(query);
        ps.setString(1, Email);
        ps.setString(2, Password);
        ResultSet results = ps.executeQuery();
        if(results.next())
        {
            String userName = results.getString("Email");
            String password = results.getString("Password");
            String firstName = results.getString("FirstName");
            String lastName = results.getString("LastName");
            String id = Integer.toString(results.getInt("id"));
            traveler = new Traveler(firstName, lastName, userName, password, id);
        }
        else
        {
            throw new Traveler.NotFoundException("Traveler not found");
        }
        return traveler;
    }



    public void insertAttractionToDB(Attraction attraction, Destinations destination) throws SQLException {
        ArrayList<StringBuilder> dayStrArr = new ArrayList<>();
        for(int i = 0; i < 7 ;++i)
        {
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
        for(DayOpeningHours day: attraction.getOpeningHoursArr())
        {
            if(day.isAllDayLongOpened())
            {
                ps.setString(index, "All Day Long");
            }
            else
            {
                if(!day.isOpen())
                {
                    ps.setString(index, "Closed");
                }
                else
                {
                    int arraySize = day.getOpeningHours().size();
                    String dayOpeningHours = "";
                    for(int j=0;j < arraySize-1; ++j )
                    {
                        String currentOpeningHour = day.getOpeningHours().get(j) + "-" + day.getClosingHours().get(j);
                        dayOpeningHours += currentOpeningHour + ", ";
                    }
                    String currentOpeningHour = day.getOpeningHours().get(arraySize-1) + "-" + day.getClosingHours().get(arraySize-1);
                    dayOpeningHours += currentOpeningHour;
                    ps.setString(index, dayOpeningHours);
                }
            }
            index++;
        }
        ps.executeUpdate();
    }

    private String getTypesStr(ArrayList<AttractionType> types)
    {
        StringBuilder typesStr = new StringBuilder();
        int typesArrSize = types.size();
        for(int i = 0; i < typesArrSize-1; ++i)
        {
            if(types.get(i) != null)
            {
                typesStr.append(types.get(i).toString() + ",");
            }
        }
        if(types.get(typesArrSize-1) != null)
        {
            typesStr.append(types.get(typesArrSize-1).toString());
        }
        return typesStr.toString();
    }


    private String checkParameter(String param)
    {
        if(param == null)
        {
            return "N\\A";
        }
        else
        {
            return param;
        }
    }


    public void insertAttractionToDataBase(JsonAttraction attraction, Destinations destination) throws SQLException, ParseException {

        JsonAttraction.JsonResult result = attraction.getResult();
        StringBuilder typesStr = new StringBuilder();
        ArrayList<StringBuilder> dayStrArr = new ArrayList<>();
        for (int i = 0; i < 7; ++i) {
            dayStrArr.add(i, new StringBuilder());
        }
        PreparedStatement ps = this.sqlConnection.prepareStatement(
                "INSERT INTO " + destination.name() + " (attractionAPI_ID, Name, Address, PhoneNumber,Website, Geometry, types," +
                        "Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday)" +
                        "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, attraction.getResult().getPlace_id());//ID
        ps.setString(2, attraction.getResult().getName());//NAME
        ps.setString(3, attraction.getResult().getFormatted_address());//ADDRESS
        if (result.getFormatted_phone_number() != null) {
            ps.setString(4, attraction.getResult().getFormatted_phone_number());//PHONENUMBER
        } else {
            ps.setString(4, "N\\A");//PHONENUMBER
        }
        if (result.getWebsite() != null) {
            ps.setString(5, attraction.getResult().getWebsite());//Website
        } else {
            ps.setString(5, "N\\A");//Website

        }
        ps.setString(6, attraction.getResult().getGeometryAPI().toString());//Geometry

        StringBuilder typesIndexesString = new StringBuilder();
        ps.setString(7, attraction.getResult().AttractionTypesToStr());
        if (result.getOpening_hours() != null) {
            for (OpeningHours.DayOpeningHoursJson currentOpening : attraction.getResult().getOpening_hours().getPeriods()) {
                int day = currentOpening.getOpen().getDay();//0=Sunday....6=Saturday
                //if the current day is not empty
                if (!dayStrArr.get(day).toString().equals("")) {
                    dayStrArr.get(day).append(",");
                }
                dayStrArr.get(day).append(currentOpening.toString());
            }
            for (int i = 0; i < 7; ++i) {
                if (dayStrArr.get(i).toString().equals("")) {
                    dayStrArr.get(i).append("Closed");
                }
                ps.setString(i + 8, dayStrArr.get(i).toString());
            }
        } else {
            for (int i = 0; i < 7; ++i) {
                dayStrArr.get(i).append("All Day Long");
                ps.setString(i + 8, dayStrArr.get(i).toString());
            }
            ps.executeUpdate();
        }
    }



    public Attraction getAttractionFromDataBaseByName(String attractionName, Destinations destination) throws SQLException, IOException, ParseException {
        Attraction resAttraction;
        String query = "SELECT * FROM attractionstable WHERE Name=\"" + attractionName + "\"";
        ResultSet res = this.statement.executeQuery(query);
        if(res.next())  //check of there is a result from the query
        {
            //if there is a result, make an attraction
            resAttraction = new Attraction(res);
        }
        else {  //there is no result from the query(the attraction doesnt found in the database)
            JsonAttraction jsonAttraction = apiManager.getAttractionFromAPI(attractionName);
            resAttraction = new Attraction(jsonAttraction);
            this.insertAttractionToDB(resAttraction, destination);
        }
        return resAttraction;
    }

    public void insetAttractionToDBByID(String id, Destinations destination) throws IOException, SQLException, ParseException {
        JsonAttraction attraction = apiManager.getAttractionByID(id);
        Attraction finalAttraction = new Attraction(attraction);
        this.insertAttractionToDB( finalAttraction, destination);
    }



    public Attraction getAttractionFromDBByID(String id) throws SQLException {
        Attraction resAttracion = null;
        String query = "SELECT * FROM attractionstable WHERE attractionid = \" " + id + "\"";
        ResultSet resultSet = this.statement.executeQuery(query);
        if(resultSet.next())
        {
            resAttracion = new Attraction(resultSet);
        }
        return resAttracion;

    }


    public void checkIfEmailIsFound(String email) throws SQLException, Traveler.AlreadyExistsException {
        String query = "SELECT * FROM travelers WHERE Email = \" " + email + "\"";
        ResultSet resultSet = this.statement.executeQuery(query);
        if(resultSet.next())
        {
            //throw new Traveler.NotFoundException("Invalid Username\\Password");
            throw new Traveler.AlreadyExistsException("Traveler is exist in the DB");
        }
    }

    public ArrayList<Attraction> getAttractionsByOperationTime(String operationTime){
        ArrayList<Attraction> attractionsArr = new ArrayList<>();
        String[] operationTimeSplitArr = operationTime.split("-");
        String openingHour =operationTimeSplitArr[0];
        String closingHour =operationTimeSplitArr[1];

        return attractionsArr;
    }


    //getting favorite attractions from db
    public Collection<Attraction> getFavAttractions() {
        return null;
    }

    public Collection<RouteTrip> getMyTripsFromDB() {
        return null;
    }
}
