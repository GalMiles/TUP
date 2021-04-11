import com.google.gson.Gson;
import database.DBManager;
import engine.Attraction;
import engine.Traveler;
import googleAPI.APIManager;
import googleAPI.JsonAttraction;

import java.text.ParseException;
import java.time.LocalTime;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws SQLException, ParseException {


        String lines1 = "{\n" +
                "    \"html_attributions\": [],\n" +
                "    \"result\": {\n" +
                "        \"business_status\": \"OPERATIONAL\",\n" +
                "        \"formatted_address\": \"Riverside Building, County Hall, South Bank, London SE1 7PB, UK\",\n" +
                "        \"formatted_phone_number\": \"020 7967 8021\",\n" +
                "        \"geometry\": {\n" +
                "            \"location\": {\n" +
                "                \"lat\": 51.5032973,\n" +
                "                \"lng\": -0.1195537\n" +
                "            },\n" +
                "            \"viewport\": {\n" +
                "                \"northeast\": {\n" +
                "                    \"lat\": 51.5046479302915,\n" +
                "                    \"lng\": -0.1164435999999999\n" +
                "                },\n" +
                "                \"southwest\": {\n" +
                "                    \"lat\": 51.50194996970851,\n" +
                "                    \"lng\": -0.1205904\n" +
                "                }\n" +
                "            }\n" +
                "        },\n" +
                "        \"name\": \"lastminute.com London Eye\",\n" +
                "        \"opening_hours\": {\n" +
                "            \"open_now\": false,\n" +
                "            \"periods\": [\n" +
                "                {\n" +
                "                    \"close\": {\n" +
                "                        \"day\": 0,\n" +
                "                        \"time\": \"1800\"\n" +
                "                    },\n" +
                "                    \"open\": {\n" +
                "                        \"day\": 0,\n" +
                "                        \"time\": \"1100\"\n" +
                "                    }\n" +
                "                },\n" +
                "                {\n" +
                "                    \"close\": {\n" +
                "                        \"day\": 1,\n" +
                "                        \"time\": \"1800\"\n" +
                "                    },\n" +
                "                    \"open\": {\n" +
                "                        \"day\": 1,\n" +
                "                        \"time\": \"1100\"\n" +
                "                    }\n" +
                "                },\n" +
                "                {\n" +
                "                    \"close\": {\n" +
                "                        \"day\": 2,\n" +
                "                        \"time\": \"1800\"\n" +
                "                    },\n" +
                "                    \"open\": {\n" +
                "                        \"day\": 2,\n" +
                "                        \"time\": \"1100\"\n" +
                "                    }\n" +
                "                },\n" +
                "                {\n" +
                "                    \"close\": {\n" +
                "                        \"day\": 3,\n" +
                "                        \"time\": \"1800\"\n" +
                "                    },\n" +
                "                    \"open\": {\n" +
                "                        \"day\": 3,\n" +
                "                        \"time\": \"1100\"\n" +
                "                    }\n" +
                "                },\n" +
                "                {\n" +
                "                    \"close\": {\n" +
                "                        \"day\": 4,\n" +
                "                        \"time\": \"1800\"\n" +
                "                    },\n" +
                "                    \"open\": {\n" +
                "                        \"day\": 4,\n" +
                "                        \"time\": \"1100\"\n" +
                "                    }\n" +
                "                },\n" +
                "                {\n" +
                "                    \"close\": {\n" +
                "                        \"day\": 5,\n" +
                "                        \"time\": \"1800\"\n" +
                "                    },\n" +
                "                    \"open\": {\n" +
                "                        \"day\": 5,\n" +
                "                        \"time\": \"1100\"\n" +
                "                    }\n" +
                "                },\n" +
                "                {\n" +
                "                    \"close\": {\n" +
                "                        \"day\": 6,\n" +
                "                        \"time\": \"1800\"\n" +
                "                    },\n" +
                "                    \"open\": {\n" +
                "                        \"day\": 6,\n" +
                "                        \"time\": \"1100\"\n" +
                "                    }\n" +
                "                }\n" +
                "            ],\n" +
                "            \"weekday_text\": [\n" +
                "                \"Monday: 11:00 AM – 6:00 PM\",\n" +
                "                \"Tuesday: 11:00 AM – 6:00 PM\",\n" +
                "                \"Wednesday: 11:00 AM – 6:00 PM\",\n" +
                "                \"Thursday: 11:00 AM – 6:00 PM\",\n" +
                "                \"Friday: 11:00 AM – 6:00 PM\",\n" +
                "                \"Saturday: 11:00 AM – 6:00 PM\",\n" +
                "                \"Sunday: 11:00 AM – 6:00 PM\"\n" +
                "            ]\n" +
                "        },\n" +
                "        \"place_id\": \"ChIJc2nSALkEdkgRkuoJJBfzkUI\",\n" +
                "        \"types\": [\n" +
                "            \"tourist_attraction\",\n" +
                "            \"point_of_interest\",\n" +
                "            \"establishment\"\n" +
                "        ]\n" +
                "    },\n" +
                "    \"status\": \"OK\"\n" +
                "}";

        String lines2 = "{\n" +
                "    \"html_attributions\": [],\n" +
                "    \"result\": {\n" +
                "        \"business_status\": \"OPERATIONAL\",\n" +
                "        \"formatted_address\": \"Eli Cohen St 24, Bat Yam, 5964419, Israel\",\n" +
                "        \"formatted_phone_number\": \"1599-500171\",\n" +
                "        \"geometry\": {\n" +
                "            \"location\": {\n" +
                "                \"lat\": 32.0134845,\n" +
                "                \"lng\": 34.75914360000001\n" +
                "            },\n" +
                "            \"viewport\": {\n" +
                "                \"northeast\": {\n" +
                "                    \"lat\": 32.0148594302915,\n" +
                "                    \"lng\": 34.76037738029151\n" +
                "                },\n" +
                "                \"southwest\": {\n" +
                "                    \"lat\": 32.0121614697085,\n" +
                "                    \"lng\": 34.75767941970851\n" +
                "                }\n" +
                "            }\n" +
                "        },\n" +
                "        \"name\": \"Israel Post\",\n" +
                "        \"opening_hours\": {\n" +
                "            \"open_now\": false,\n" +
                "            \"periods\": [\n" +
                "                {\n" +
                "                    \"close\": {\n" +
                "                        \"day\": 0,\n" +
                "                        \"time\": \"1230\"\n" +
                "                    },\n" +
                "                    \"open\": {\n" +
                "                        \"day\": 0,\n" +
                "                        \"time\": \"0800\"\n" +
                "                    }\n" +
                "                },\n" +
                "                {\n" +
                "                    \"close\": {\n" +
                "                        \"day\": 0,\n" +
                "                        \"time\": \"1800\"\n" +
                "                    },\n" +
                "                    \"open\": {\n" +
                "                        \"day\": 0,\n" +
                "                        \"time\": \"1530\"\n" +
                "                    }\n" +
                "                },\n" +
                "                {\n" +
                "                    \"close\": {\n" +
                "                        \"day\": 1,\n" +
                "                        \"time\": \"1230\"\n" +
                "                    },\n" +
                "                    \"open\": {\n" +
                "                        \"day\": 1,\n" +
                "                        \"time\": \"0800\"\n" +
                "                    }\n" +
                "                },\n" +
                "                {\n" +
                "                    \"close\": {\n" +
                "                        \"day\": 1,\n" +
                "                        \"time\": \"1800\"\n" +
                "                    },\n" +
                "                    \"open\": {\n" +
                "                        \"day\": 1,\n" +
                "                        \"time\": \"1530\"\n" +
                "                    }\n" +
                "                },\n" +
                "                {\n" +
                "                    \"close\": {\n" +
                "                        \"day\": 2,\n" +
                "                        \"time\": \"1300\"\n" +
                "                    },\n" +
                "                    \"open\": {\n" +
                "                        \"day\": 2,\n" +
                "                        \"time\": \"0800\"\n" +
                "                    }\n" +
                "                },\n" +
                "                {\n" +
                "                    \"close\": {\n" +
                "                        \"day\": 3,\n" +
                "                        \"time\": \"1230\"\n" +
                "                    },\n" +
                "                    \"open\": {\n" +
                "                        \"day\": 3,\n" +
                "                        \"time\": \"0800\"\n" +
                "                    }\n" +
                "                },\n" +
                "                {\n" +
                "                    \"close\": {\n" +
                "                        \"day\": 3,\n" +
                "                        \"time\": \"1800\"\n" +
                "                    },\n" +
                "                    \"open\": {\n" +
                "                        \"day\": 3,\n" +
                "                        \"time\": \"1530\"\n" +
                "                    }\n" +
                "                },\n" +
                "                {\n" +
                "                    \"close\": {\n" +
                "                        \"day\": 4,\n" +
                "                        \"time\": \"1230\"\n" +
                "                    },\n" +
                "                    \"open\": {\n" +
                "                        \"day\": 4,\n" +
                "                        \"time\": \"0800\"\n" +
                "                    }\n" +
                "                },\n" +
                "                {\n" +
                "                    \"close\": {\n" +
                "                        \"day\": 4,\n" +
                "                        \"time\": \"1800\"\n" +
                "                    },\n" +
                "                    \"open\": {\n" +
                "                        \"day\": 4,\n" +
                "                        \"time\": \"1530\"\n" +
                "                    }\n" +
                "                },\n" +
                "                {\n" +
                "                    \"close\": {\n" +
                "                        \"day\": 5,\n" +
                "                        \"time\": \"1230\"\n" +
                "                    },\n" +
                "                    \"open\": {\n" +
                "                        \"day\": 5,\n" +
                "                        \"time\": \"0800\"\n" +
                "                    }\n" +
                "                }\n" +
                "            ],\n" +
                "            \"weekday_text\": [\n" +
                "                \"Monday: 8:00 AM – 12:30 PM, 3:30 – 6:00 PM\",\n" +
                "                \"Tuesday: 8:00 AM – 1:00 PM\",\n" +
                "                \"Wednesday: 8:00 AM – 12:30 PM, 3:30 – 6:00 PM\",\n" +
                "                \"Thursday: 8:00 AM – 12:30 PM, 3:30 – 6:00 PM\",\n" +
                "                \"Friday: 8:00 AM – 12:30 PM\",\n" +
                "                \"Saturday: Closed\",\n" +
                "                \"Sunday: 8:00 AM – 12:30 PM, 3:30 – 6:00 PM\"\n" +
                "            ]\n" +
                "        },\n" +
                "        \"place_id\": \"ChIJoY1ptW6zAhUR0mWfHj0EG-c\",\n" +
                "        \"types\": [\n" +
                "            \"post_office\",\n" +
                "            \"finance\",\n" +
                "            \"point_of_interest\",\n" +
                "            \"establishment\"\n" +
                "        ]\n" +
                "    },\n" +
                "    \"status\": \"OK\"\n" +
                "}";


        Gson gson = new Gson();
        JsonAttraction temp = gson.fromJson(lines2, JsonAttraction.class);
        System.out.println("fin");



        DBManager db = new DBManager("jdbc:mysql://localhost:3306/attractions","root","742!GDFMP");
        db.insertDataToDataBase(temp);
        db.closeConnection();



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
//        db.closeConnection();blas blas





        //-----------------------------------------------------------------API Manager Test
//        try
//        {
//            APIManager apiManager = new APIManager();
//           JsonAttraction tempAttraction= apiManager.getAttractionFromAPI("london eye");
//           //Attraction attraction = new Attraction(tempAttraction);
//           //System.out.println(attraction);
//
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }


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
