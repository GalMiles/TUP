import database.DBManager;
import engine.attraction.Attraction;

import java.text.ParseException;
import java.io.IOException;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException, ParseException, IOException {

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




        String[] AttractionToAdd = {"Buckingham Palace",
                "Harrods", "London Transport Museum","St. Paul’s Cathedral", "Tate", "Palace of Westminster", "Wembley Stadium", "Leadenhall Market"
                , "Hampton Court Palace", "National Gallery", "Trafalgar Square", "Greenwich Royal Observatory", "Covent Garden",
                "Kensington Palace", "Hyde Park", "Saturday market hopping", "The Mall, London", "Old Spitalfields Market", "Hampstead Heath",
                "Warner Bros. Studio Tour London – The Making of Harry Potter", "Borough Market", "Churchill War Rooms", "Admiralty Arch", "Seven Dials", "Wimbledon Lawn Tennis Museum and Tour",
                "Portobello Road Market", "Shakespeare’s Globe", "Somerset House", "River Thames", "Imperial War Museum",
                "The West End", "Fortnum & Mason", "Brick Lane", "Oxford Street", "Big Ben","Piccadilly Circus", "Trafalgar Square", "The Shard", "Natural History Museum"
                , "London Docklands"};

//        String[] hotels = {""};
        /*
        try
        {
        DBManager db = new DBManager();
        }
        catch (Exception e)
        {

        }

         */

        for(String currentAttractionName: AttractionToAdd)
        {
           // Attraction attraction = db.getAttractionFromDataBaseByName(currentAttractionName + "London");
        }

        //Gson gson = new Gson();
        //JsonAttraction att = gson.fromJson(londonsEye, JsonAttraction.class);
        //Attraction realAtt = new Attraction(att);

        //Attraction newAtt = db.getAttractionFromDataBaseByName("The British Museum");
        System.out.println("fin1");


       // db.closeConnection();
        //System.out.println(newAtt.attractionToJson());
        


    }




}
