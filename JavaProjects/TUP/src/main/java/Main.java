import database.DBManager;
import engine.Attraction;
import engine.Traveler;
import googleAPI.APIManager;
import googleAPI.JsonAttraction;

import java.io.IOException;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {



/*
        //Connect to the database server
        Connection sqlConnection =
                DriverManager.getConnection(dbURL, userName, password);

        //create a SQL statement(Query)
        Statement statement = sqlConnection.createStatement();

        //the results from the query will be here
        ResultSet result_test = statement.executeQuery("SELECT * FROM attractiontable");

*/







        //Gson gson = new Gson();
        //String lines = "{   \"html_attributions\" : [],   \"result\" : {      \"formatted_address\" : \"Riverside Building, County Hall, South Bank, London SE1 7PB, UK\",      \"formatted_phone_number\" : \"020 7967 8021\",      \"name\" : \"lastminute.com London Eye\",      \"opening_hours\" : {         \"open_now\" : false,         \"periods\" : [            {               \"close\" : {                  \"day\" : 0,                  \"time\" : \"1800\"               },               \"open\" : {                  \"day\" : 0,                  \"time\" : \"1100\"               }            },            {               \"close\" : {                  \"day\" : 1,                  \"time\" : \"1800\"               },               \"open\" : {                  \"day\" : 1,                  \"time\" : \"1100\"               }            },            {               \"close\" : {                  \"day\" : 2,                  \"time\" : \"1800\"               },               \"open\" : {                  \"day\" : 2,                  \"time\" : \"1100\"               }            },            {               \"close\" : {                  \"day\" : 3,                  \"time\" : \"1800\"               },               \"open\" : {                  \"day\" : 3,                  \"time\" : \"1100\"               }            },            {               \"close\" : {                  \"day\" : 4,                  \"time\" : \"1800\"               },               \"open\" : {                  \"day\" : 4,                  \"time\" : \"1100\"               }            },            {               \"close\" : {                  \"day\" : 5,                  \"time\" : \"1800\"               },               \"open\" : {                  \"day\" : 5,                  \"time\" : \"1100\"               }            },            {               \"close\" : {                  \"day\" : 6,                  \"time\" : \"1800\"               },               \"open\" : {                  \"day\" : 6,                  \"time\" : \"1100\"               }            }         ],         \"weekday_text\" : [            \"Monday: 11:00 AM – 6:00 PM\",            \"Tuesday: 11:00 AM – 6:00 PM\",            \"Wednesday: 11:00 AM – 6:00 PM\",            \"Thursday: 11:00 AM – 6:00 PM\",            \"Friday: 11:00 AM – 6:00 PM\",            \"Saturday: 11:00 AM – 6:00 PM\",            \"Sunday: 11:00 AM – 6:00 PM\"         ]      }   },   \"status\" : \"OK\"}\n";




//        googleAPI.gsonAttraction temp = gson.fromJson(lines, googleAPI.gsonAttraction.class);
//
//        engine.Attraction realAttraction = new engine.Attraction(temp);
//
//        database.DBManager db = new database.DBManager(dbURL, userName, password);
//
//        db.insertDataToDataBase(realAttraction);
//
//
//
//        db.closeConnection();





        //-----------------------------------------------------------------API Manager Test
//        try
//        {
//            APIManager apiManager = new APIManager();
//           JsonAttraction tempAttraction= apiManager.getAttractionFromAPI("london eye");
//           Attraction attraction = new Attraction(tempAttraction);
//           System.out.println(attraction);
//
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//

        //----------------- Traveler Test -------


//         Traveler traveler= new Traveler("Matan", "myPassOMG_Iam_So_Great",12,
//               "matanp103@gmail.com" );
//
//        DBManager database = new DBManager(dbURL, userName, password);
//        database.insertTravelerToDataBase(traveler);
//
//        database.closeConnection();





    }




}
