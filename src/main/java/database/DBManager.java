package database;

import engine.Attraction;
import engine.Traveler;
import googleAPI.JsonAttraction;

import java.sql.*;

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

    public void insertDataToDataBase(JsonAttraction attraction) throws SQLException
    {
        PreparedStatement ps = this.sqlConnection.prepareStatement(
                "INSERT INTO attractionstable(attractionAPI_ID, Name, Address, PhoneNumber, Sunday, " +
                "Monday, Tuesday, Wednesday, Thursday, Friday, Saturday)" +
                        "VALUES (?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, attraction.getResult().getPlace_id());//ID
        ps.setString(2, attraction.getResult().getName());//NAME
        ps.setString(3, attraction.getResult().getFormatted_address());//ADDRESS
        ps.setString(4, attraction.getResult().getFormatted_phone_number());//PHONENUMBER



        ps.setString(5, attraction.getResult().getOpening_hours().getWeekday_text().get(6).split(":",2)[1]);//SUNDAY
        ps.setString(6, attraction.getResult().getOpening_hours().getWeekday_text().get(0).split(":",2)[1]);//MONDAY
        ps.setString(7, attraction.getResult().getOpening_hours().getWeekday_text().get(1).split(":",2)[1]);//TUESDAY
        ps.setString(8, attraction.getResult().getOpening_hours().getWeekday_text().get(2).split(":",2)[1]);//WEDNSDAY
        ps.setString(9, attraction.getResult().getOpening_hours().getWeekday_text().get(3).split(":",2)[1]);//THURSDAY
        ps.setString(10, attraction.getResult().getOpening_hours().getWeekday_text().get(4).split(":",2)[1]);//FRIDAY
        ps.setString(11, attraction.getResult().getOpening_hours().getWeekday_text().get(5).split(":",2)[1]);//SATURDAY



        ps.executeUpdate();
    }

}
