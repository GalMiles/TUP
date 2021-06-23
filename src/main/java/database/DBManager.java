package database;

import common.OpeningHours;
import engine.attraction.Attraction;
import engine.Traveler;
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


    public DBManager(String dbUrl, String userName, String password) throws SQLException
    {
        this.sqlConnection = DriverManager.getConnection(dbUrl, userName, password);
        this.statement = sqlConnection.createStatement();
    }


    public void closeConnection() throws SQLException
    {
        this.sqlConnection.close();
    }

    public void insertTravelerToDataBase(Traveler traveler) throws SQLException {
        String query = "INSERT INTO travelers(EmailAddress, Name, Password, Age)" +"\n" + "VALUES" + "(\"" +
                traveler.getEmailAddress() + "\"" + ", \"" + traveler.getName() + "\"" + ", \"" + traveler.getPassword() +
                "\", " + traveler.getAge() + ");";
        this.statement.executeUpdate(query);
    }

    public void insertDataToDataBase(JsonAttraction attraction) throws SQLException, ParseException {

        StringBuilder typesStr = new StringBuilder();

        ArrayList<StringBuilder> dayStrArr = new ArrayList<>();
        for(int i = 0; i < 7; ++i)
        {
            dayStrArr.add(i, new StringBuilder());
        }
        PreparedStatement ps = this.sqlConnection.prepareStatement(
                "INSERT INTO attractionstable(attractionAPI_ID, Name, Address, PhoneNumber,Website, Geometry, types," +
                        "Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday)" +
                        "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

        ps.setString(1, attraction.getResult().getPlace_id());//ID
        ps.setString(2, attraction.getResult().getName());//NAME
        ps.setString(3, attraction.getResult().getFormatted_address());//ADDRESS
        ps.setString(4, attraction.getResult().getFormatted_phone_number());//PHONENUMBER
        ps.setString(5, attraction.getResult().getWebsite());//Website
        ps.setString(6, attraction.getResult().getGeometryAPI().toString());//Geometry

        StringBuilder typesIndexesString = new StringBuilder();
        ps.setString(7, attraction.getResult().AttractionTypesToStr());
        for (OpeningHours.DayOpeningHoursJson currentOpening:attraction.getResult().getOpening_hours().getPeriods()) {
            int day = currentOpening.getOpen().getDay();//0=Sunday....6=Saturday
            //if the current day is not empty
            if(!dayStrArr.get(day).toString().equals(""))
            {
                dayStrArr.get(day).append(",");
            }
            dayStrArr.get(day).append(currentOpening.toString());
        }
        for(int i =0; i < 7;++i)
        {
            if(dayStrArr.get(i).toString().equals(""))
            {
                dayStrArr.get(i).append("Closed");
            }
            ps.setString(i+8, dayStrArr.get(i).toString());
        }
        ps.executeUpdate();
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
            this.insertDataToDataBase(jsonAttraction);
            resAttraction = new Attraction(jsonAttraction);
        }
        return resAttraction;
    }

    public ArrayList<Attraction> getAttractionsByOperationTime(String operationTime)
    {
        ArrayList<Attraction> attractionsArr = new ArrayList<>();
        String[] operationTimeSplitArr = operationTime.split("-");
        String openingHour =operationTimeSplitArr[0];
        String closingHour =operationTimeSplitArr[1];





        return attractionsArr;
    }


}
