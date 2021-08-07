import common.Destinations;
import database.DBManager;
import engine.attraction.Attraction;

import java.text.ParseException;
import java.io.IOException;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException, ParseException, IOException {
/*


        String londonsEye = "{\n" +
                "    \"html_attributions\": [],\n" +
                "    \"result\": {\n" +
                "        \"business_status\": \"OPERATIONAL\",\n" +
                "        \"formatted_address\": \"Riverside Building, County Hall, London SE1 7PB, UK\",\n" +
                "        \"formatted_phone_number\": \"020 7967 8021\",\n" +
                "        \"geometry\": {\n" +
                "            \"location\": {\n" +
                "                \"lat\": 51.5032973,\n" +
                "                \"lng\": -0.1195537\n" +
                "            }\n" +
                "        },\n" +
                "        \"name\": \"lastminute.com London Eye\",\n" +
                "        \"opening_hours\": {\n" +
                "            \"open_now\": true,\n" +
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
                "        ],\n" +
                "        \"website\": \"https://www.londoneye.com/\"\n" +
                "    },\n" +
                "    \"status\": \"OK\"\n" +
                "}";
        //String IsraelPost =
 */

        DBManager db = new DBManager();

        String[] AttractionToAdd = {
                "Capel Manor Gardens",
                "Carew Manor & Dovecote",
                "Carlyle's House",
                "Charles Dickens Museum",
                "Charlton House",
                "Chelsea Bridge",
                "Chelsea Old Church (All Saints)",
                "Chelsea Physic Garden",
                "Chiswick House",
                "Christchurch Greyfriars Church and Garden",
                "Churchill War Rooms",
                "Cinema Museum",
                "Clarence House",
               "Cleopatra's Needle",
                "Clerk's Well",
                "Clink Prison Museum",
                "Clockmakers' Museum",
                "College of Arms",
                "Courtauld Gallery",
                "Covent Garden Market",
                "Cross Bones Graveyard",
                "Crossness Pumping Station",
                "Cutty Sark"

        };



        for(String currentAttractionName: AttractionToAdd)
        {
           Attraction attraction = db.getAttractionFromDataBaseByName(currentAttractionName, Destinations.london);
        }

        //db.insetAttractionToDBByID("ChIJtV5bzSAFdkgRpwLZFPWrJgo", Destinations.london);
        //try{

        //db.insetAttractionToDBByID("ChIJ_zAq7TgFdkgRQg1OSw1j7hU", Destinations.london);
 //       } catch (Exception e)
 //       {
 //           System.out.println(e.getMessage());
  //      }

        //Gson gson = new Gson();
        //JsonAttraction att = gson.fromJson(londonsEye, JsonAttraction.class);
        //Attraction realAtt = new Attraction(att);

        //Attraction newAtt = db.getAttractionFromDataBaseByName("The British Museum");
        System.out.println("fin1");


       db.closeConnection();
        //System.out.println(newAtt.attractionToJson());
        


    }




}
