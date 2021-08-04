package database;

import common.OpeningHours;
import engine.attraction.Attraction;
import engine.traveler.Traveler;
import googleAPI.APIManager;
import googleAPI.JsonAttraction;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;

public class DBManager {

    private APIManager apiManager = new APIManager();
    private Statement statement;
    private Connection sqlConnection;


    public DBManager() throws SQLException {
        this.sqlConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tup", "root", "");
    }


    public void closeConnection() throws SQLException {
        this.sqlConnection.close();
    }

    public void Register(Traveler traveler) throws SQLException, Traveler.AlreadyExistsException, Traveler.NotFoundException {
        checkIfEmailIsFound(traveler.getEmailAddress());
        String query = "INSERT INTO travelers(Email, Password, FirstName, LastName) VALUES ( " +
                    traveler.getEmailAddress() + "," + traveler.getPassword() + "," + traveler.getFirstName() +
                    "," + traveler.getLastName()+ ")";
        this.statement.executeUpdate(query);
    }
    //login


    public Traveler Login(String Email, String Password) throws SQLException, Traveler.NotFoundException {
        Traveler traveler = null;
        String query = "SELECT Email, Password FROM travelers WHERE Email=? and Password=?";
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
            traveler = new Traveler(userName, password, firstName, lastName);
        }
        else
        {
            throw new Traveler.NotFoundException("Traveler not found");
        }
        return traveler;
    }




    public void insertAttractionToDataBase(JsonAttraction attraction) throws SQLException, ParseException {

        JsonAttraction.JsonResult result = attraction.getResult();
        StringBuilder typesStr = new StringBuilder();
        ArrayList<StringBuilder> dayStrArr = new ArrayList<>();
        for (int i = 0; i < 7; ++i) {
            dayStrArr.add(i, new StringBuilder());
        }
        PreparedStatement ps = this.sqlConnection.prepareStatement(
                "INSERT INTO attractionstable(attractionAPI_ID, Name, Address, PhoneNumber,Website, Geometry, types," +
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

    public Attraction getAttractionFromDataBaseByName(String attractionName) throws SQLException, IOException, ParseException {
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
            this.insertAttractionToDataBase(jsonAttraction);
            resAttraction = new Attraction(jsonAttraction);
        }
        return resAttraction;
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


    public void checkIfEmailIsFound(String email) throws SQLException, Traveler.NotFoundException, Traveler.AlreadyExistsException {
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

}
