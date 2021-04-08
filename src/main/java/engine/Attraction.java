package engine;
import java.time.LocalTime;
import googleAPI.JsonAttraction;
import java.util.ArrayList;




public class Attraction {
    private String name;
    private String address;
    private String phoneNumber;
    private JsonAttraction.JsonResult.Geometry geometry;
    private String placeID;
    private ArrayList<JsonAttraction.AttractionType> types;
    //private JsonAttraction.JsonResult.OpeningHours openingHours;//String+int
    private ArrayList<ArrayList<openingHours>> attractionOpeningHours;


    public Attraction(String name, String address, String phoneNumber,
                      JsonAttraction.JsonResult.Geometry geometry, String placeID,
                      ArrayList<JsonAttraction.AttractionType> types,
                      ArrayList<ArrayList<openingHours>> attractionOpeningHours)
    {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.geometry = geometry;
        this.placeID = placeID;
        this.types = types;
        this.attractionOpeningHours = attractionOpeningHours;
    }



    public Attraction(JsonAttraction jsonAttraction)
    {
        this.address = jsonAttraction.getResult().getFormatted_address();
        this.name = jsonAttraction.getResult().getName();
        this.phoneNumber = jsonAttraction.getResult().getFormatted_phone_number();
        this.placeID = jsonAttraction.getResult().getPlace_id();
        this.openingHours = jsonAttraction.getResult().getOpening_hours();
        this.types = jsonAttraction.getResult().getTypes();
        this.geometry = jsonAttraction.getResult().getGeometry();
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public JsonAttraction.JsonResult.Geometry getGeometry() {
        return geometry;
    }

    public String getPlaceID() {
        return placeID;
    }

    public ArrayList<JsonAttraction.AttractionType> getTypes() {
        return types;
    }

    public JsonAttraction.JsonResult.OpeningHours getOpeningHours() {
        return openingHours;
    }

    @Override
    public String toString()
    {
        return "engine.Attraction{" +
                "Name='" + name + '\'' +
                ", Address='" + address + '\'' +
                ", PhoneNumber='" + phoneNumber + '\'' +
                ", googleAPI.PlaceID='" + placeID + '\'' +
                ", types=" + types +
                ", openingHours=" + openingHours +
                '}';
    }

    private class openingHours{
        LocalTime open;
        LocalTime close;

        public openingHours(String open, String close)
        {
            this.open=LocalTime.parse(open);
            this.close=LocalTime.parse(close);
        }
    }
}
