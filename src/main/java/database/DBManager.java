package database;

import engine.Attraction;
import engine.Traveler;
import googleAPI.JsonAttraction;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;

public class DBManager {

    private Statement statement;
    private Connection sqlConnection;


    public DBManager(String dbUrl, String userName, String password) throws SQLException
    {
        this.sqlConnection = DriverManager.getConnection(dbUrl, userName, password);
        this.statement = sqlConnection.createStatement();
    }

//    public void insertDataToDataBase(Attraction attraction) throws SQLException
//    {
//
//        String query = "INSERT INTO attractionstable(attractionAPI_ID, Name, Address, PhoneNumber)" + "\n" +
//                "VALUES " + "(\"" + attraction.getPlaceID() + "\"" + ", \""+ attraction.getName() +"\""
//                    + ", \""  +attraction.getAddress() + "\", \"" + attraction.getPhoneNumber() + "\");";
//
//        this.statement.executeUpdate(query);
//
//    }

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
        PreparedStatement ps = this.sqlConnection.prepareStatement(
                "INSERT INTO attractionstable(attractionAPI_ID, Name, Address, PhoneNumber)" +
                        "VALUES (?,?,?,?)");

        ps.setString(1, attraction.getResult().getPlace_id());//ID
        ps.setString(2, attraction.getResult().getName());//NAME
        ps.setString(3, attraction.getResult().getFormatted_address());//ADDRESS
        ps.setString(4, attraction.getResult().getFormatted_phone_number());//PHONENUMBER
        //Types!
        //Sunday...
        ps.executeUpdate();


        ps = this.sqlConnection.prepareStatement(
                "INSERT INTO openinghours(day, Opening_Hours, Closing_Hours, attractionAPI_ID)" +
                        "VALUES (?,?,?,?)");
        ArrayList<JsonAttraction.JsonResult.OpeningHours.DayOpeningHours> periods = attraction.getResult().getOpening_hours().getPeriods();
        for(JsonAttraction.JsonResult.OpeningHours.DayOpeningHours currentPeriod : periods){
            ps.setString(4,attraction.getResult().getPlace_id() );
            ps.setInt(1 ,currentPeriod.getOpen().getDay());
            DateFormat formatter = new SimpleDateFormat("HH:mm");
            String timeString = currentPeriod.getOpen().getTime();
            timeString = timeString.substring(0,2) + ":" + timeString.substring(2) + ":00";

            Time time = new Time(formatter.parse(timeString).getTime());
            ps.setTime(2, time);
            timeString = currentPeriod.getClose().getTime();
            timeString = timeString.substring(0,2) + ":" + timeString.substring(2) + ":00";
            time = new Time(formatter.parse(timeString).getTime());
            ps.setTime(3, time);
            ps.executeUpdate();
        }






//        ps.setString(5, attraction.getResult().getOpening_hours().getWeekday_text().get(6).split(":",2)[1]);//SUNDAY
//        ps.setString(6, attraction.getResult().getOpening_hours().getWeekday_text().get(0).split(":",2)[1]);//MONDAY
//        ps.setString(7, attraction.getResult().getOpening_hours().getWeekday_text().get(1).split(":",2)[1]);//TUESDAY
//        ps.setString(8, attraction.getResult().getOpening_hours().getWeekday_text().get(2).split(":",2)[1]);//WEDNSDAY
//        ps.setString(9, attraction.getResult().getOpening_hours().getWeekday_text().get(3).split(":",2)[1]);//THURSDAY
//        ps.setString(10, attraction.getResult().getOpening_hours().getWeekday_text().get(4).split(":",2)[1]);//FRIDAY
//        ps.setString(11, attraction.getResult().getOpening_hours().getWeekday_text().get(5).split(":",2)[1]);//SATURDAY



    }

}
