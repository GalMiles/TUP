package googleAPI;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;


public class APIManager {

    //Attraction attraction = apiManager.getAttractionFromApi(name);
    public JsonAttraction getAttractionFromAPI(String attractionName) throws IOException {
        //Place ID: ChIJc2nSALkEdkgRkuoJJBfzkUI
        String placeID = getPlaceIDFromAPI(attractionName);
        String url ="https://maps.googleapis.com/maps/api/place/details/json?place_id=" + placeID +
                        "&fields=name,formatted_address,formatted_phone_number,opening_hours,geometry," +
                        "types,business_status,price_level,place_id&key=OUR_KEY";
        Gson gson = new Gson();
        JsonAttraction attraction = gson.fromJson(getJsonString(url), JsonAttraction.class);
        return attraction;
    }


    private String getPlaceIDFromAPI(String attractionName) throws IOException {
        String searchedPlace = attractionName.replace(" ","%20");
        String url = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input=" + searchedPlace +
             "&inputtype=textquery&fields=place_id&key=OUR_KEY";
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
}
