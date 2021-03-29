//import gsonAttraction.JsonResult.*;
import java.util.ArrayList;


enum Type {HOTEL, EXTREEEEME, MUSEUM, FOOD, NIGHTLIFE};

public class Attraction {
    private String Name;
    private String Address;
    private String PhoneNumber;
    private gsonAttraction.JsonResult.Geometry geometry;
    private String PlaceID;
    private ArrayList<String> types;
    private gsonAttraction.JsonResult.OpeningHours openingHours;


    public Attraction(gsonAttraction gsonAttraction)
    {
        this.Address = gsonAttraction.result.formatted_address;
        this.Name = gsonAttraction.result.name;
        this.PhoneNumber = gsonAttraction.result.formatted_phone_number;
        this.PlaceID = gsonAttraction.result.place_id;
        this.openingHours = gsonAttraction.result.opening_hours;
        this.types = gsonAttraction.result.types;
        this.geometry = gsonAttraction.result.geometry;
    }

    public String getName() {
        return Name;
    }

    public String getAddress() {
        return Address;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public gsonAttraction.JsonResult.Geometry getGeometry() {
        return geometry;
    }

    public String getPlaceID() {
        return PlaceID;
    }

    public ArrayList<String> getTypes() {
        return types;
    }

    public gsonAttraction.JsonResult.OpeningHours getOpeningHours() {
        return openingHours;
    }

    @Override
    public String toString() {
        return "Attraction{" +
                "Name='" + Name + '\'' +
                ", Address='" + Address + '\'' +
                ", PhoneNumber='" + PhoneNumber + '\'' +
                ", PlaceID='" + PlaceID + '\'' +
                ", types=" + types +
                ", openingHours=" + openingHours +
                '}';
    }
}
