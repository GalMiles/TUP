package engine;

import googleAPI.JsonAttraction;
import java.util.ArrayList;




public class Attraction {
    private String name;
    private String address;
    private String phoneNumber;
    private JsonAttraction.JsonResult.Geometry geometry;
    private String placeID;
    private ArrayList<JsonAttraction.AttractionType> types;
    private JsonAttraction.JsonResult.OpeningHours openingHours;//String+int



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
}
