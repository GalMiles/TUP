package database;

import engine.Attraction;
import engine.Traveler;

import java.sql.*;

public class DBManager {

    private Statement statement;
    private Connection sqlConnection;


    public DBManager(String dbUrl, String userName, String password) throws SQLException
    {
        this.sqlConnection = DriverManager.getConnection(dbUrl, userName, password);
        this.statement = sqlConnection.createStatement();
    }

    public void insertDataToDataBase(Attraction attraction) throws SQLException
    {

        String query = "INSERT INTO attractionstable(attractionAPI_ID, Name, Address, PhoneNumber)" + "\n" +
                "VALUES " + "(\"" + attraction.getPlaceID() + "\"" + ", \""+ attraction.getName() +"\""
                    + ", \""  +attraction.getAddress() + "\", \"" + attraction.getPhoneNumber() + "\");";

        this.statement.executeUpdate(query);

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
}
