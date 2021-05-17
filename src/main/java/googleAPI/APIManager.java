package googleAPI;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;


public class APIManager {

    public JsonAttraction getAttractionFromAPI(String attractionName) throws IOException {
        String placeID = getPlaceIDFromAPI(attractionName);
        String url ="https://maps.googleapis.com/maps/api/place/details/json?place_id=" + placeID +
                "&fields=name,formatted_address,formatted_phone_number,opening_hours/periods,geometry/location," +
                "types,business_status,price_level,place_id" +
                "&key=AIzaSyAujNlik91rOdQwXKVEQgHakotz7hfl9oM";
        Gson gson = new Gson();
        JsonAttraction attraction = gson.fromJson(getJsonString(url), JsonAttraction.class);
        return attraction;
    }


    private String getPlaceIDFromAPI(String attractionName) throws IOException {
        String searchedPlace = attractionName.replace(" ","%20");
        String url = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input=" + searchedPlace +
             "&inputtype=textquery&fields=place_id&key=AIzaSyAujNlik91rOdQwXKVEQgHakotz7hfl9oM";
        Gson gson = new Gson();
        JsonPlaceID idHolder = gson.fromJson(getJsonString(url), JsonPlaceID.class);
        return idHolder.getCandidates().get(0).getPlaceId();
    }

    private String getJsonString(String url) throws IOException {
        URL urlObject = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String lines = reader.lines().collect(Collectors.joining());
        System.out.println(lines);
        reader.close();
        return lines;
    }

//    private String getAttractionWikiID(String attractionName) throws IOException {
//        attractionName = attractionName.replace(" ", "%20");
//        String url = "http://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&explaintext&exintro&titles=" + attractionName;
//        String json = getJsonString(url);
//
//    }
}
