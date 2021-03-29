import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;


public class APIManager {

    public static gsonAttraction getAttractionFromAPI(String attractionName) throws IOException {
        String searchPlace = attractionName.replace(" ","%20");
        String url = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input=" + searchPlace +
             "&inputtype=textquery&fields=place_id&key=AIzaSyAujNlik91rOdQwXKVEQgHakotz7hfl9oM";
        Gson gson = new Gson();
        PlaceID idHolder = gson.fromJson(getJsonString(url), PlaceID.class);
        return getGsonAttractionObject(idHolder.getCandidates().get(0).getPlaceId());
    }

    public static gsonAttraction getGsonAttractionObject(String placeID) throws IOException {
        //Place ID: ChIJc2nSALkEdkgRkuoJJBfzkUI
        String url =
                "https://maps.googleapis.com/maps/api/place/details/json?place_id=" + placeID +
                        "&fields=name,formatted_address,formatted_phone_number,opening_hours,geometry,types,business_status,price_level,place_id&key=AIzaSyAujNlik91rOdQwXKVEQgHakotz7hfl9oM";
        Gson gson = new Gson();
        gsonAttraction attraction = gson.fromJson(getJsonString(url), gsonAttraction.class);
        return attraction;
    }

    private static String getJsonString(String url) throws IOException {
        URL urlObject = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String lines = reader.lines().collect(Collectors.joining());
        System.out.println(lines);
        reader.close();
        return lines;
    }
}
