package engine;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import googleAPI.JsonAttraction;
import org.w3c.dom.ranges.Range;

import java.util.ArrayList;




public class Attraction {
    public enum weekDay {Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday}

    private String name;
    private String address;
    private String phoneNumber;
    private String website;
    private Geometry geometry;
    private String placeID;
    private ArrayList<JsonAttraction.AttractionType> types = new ArrayList<>();
    private ArrayList<dayOpeningHours> OpeningHoursArr = new ArrayList<>();//


    public Attraction(ResultSet resultSet) throws SQLException {
        this.website = resultSet.getString("Website");
        this.placeID = resultSet.getString("attractionAPI_ID");  //ID
        this.name = resultSet.getString("Name");     // Name
        this.address = resultSet.getString("Address");  // Address
        this.phoneNumber = resultSet.getString("PhoneNumber"); //phone number
        String geometryStr = resultSet.getString("Geometry");
        String[] typesArray = resultSet.getString("types").split(",");
        for (String type: typesArray)
        {
            this.types.add(JsonAttraction.AttractionType.valueOf(type));
        }
        String[] geometryArr = geometryStr.split(",");
        this.geometry = new Geometry(geometryArr[0], geometryArr[1]);
        String[] daysColumns = {"Sunday","Monday", "Tuesday", "Wednesday", "Thursday", "Friday","Saturday"};
        for (int i=0;i<7;++i) {
            String day = daysColumns[i]; // get the day opening time
            String DayOpeningHours = resultSet.getString(day);
            this.OpeningHoursArr.add(new dayOpeningHours(i));
            if(!DayOpeningHours.equals("Closed"))
            {
                String[] dayOperationTimesArray = DayOpeningHours.split(",");
                for (String operationTime: dayOperationTimesArray) {
                    String[] openingAndClosingTimes = operationTime.split("-");
                    this.OpeningHoursArr.get(i).addOpening(openingAndClosingTimes[0]);
                    this.OpeningHoursArr.get(i).addClosing(openingAndClosingTimes[1]);
                }
            }
        }

    }


    public Attraction(JsonAttraction jsonAttraction)
    {
        this.website = jsonAttraction.getResult().getWebsite();
        this.address = jsonAttraction.getResult().getFormatted_address();
        this.name = jsonAttraction.getResult().getName();
        this.phoneNumber = jsonAttraction.getResult().getFormatted_phone_number();
        this.placeID = jsonAttraction.getResult().getPlace_id();
        this.types = jsonAttraction.getResult().getTypes();
        this.geometry = new Geometry(jsonAttraction.getResult().getGeometry().getLocation().getLat(),jsonAttraction.getResult().getGeometry().getLocation().getLng() );
        //initialize the array, create 7 cells (one for each weekday)
        for(int i = 0; i<7; ++i)
        {
            this.OpeningHoursArr.add(new dayOpeningHours(i));
        }
        for (JsonAttraction.JsonResult.OpeningHours.DayOpeningHours period:jsonAttraction.getResult().getOpening_hours().getPeriods())
        {
            this.OpeningHoursArr.get(period.getOpen().getDay()).isOpen = true;
            this.OpeningHoursArr.get(period.getOpen().getDay()).openingHours.add(period.getOpen().getTime());
            this.OpeningHoursArr.get(period.getClose().getDay()).closingHours.add(period.getClose().getTime());
        }
    }

    public double calcDistanceBetweenAttractions(Attraction other)
    {
        double xLat = Double.parseDouble(this.geometry.getLat());
        double xLng = Double.parseDouble(this.geometry.getLng());
        double yLat = Double.parseDouble(other.geometry.getLat());
        double yLng = Double.parseDouble(other.geometry.getLng());

        xLat = Math.toRadians(xLat);
        xLng = Math.toRadians(xLng);
        yLat = Math.toRadians(yLat);
        yLng = Math.toRadians(yLng);

        double dlon = yLng - xLng;
        double dlat = yLat - xLat;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(xLat) * Math.cos(yLat)
                * Math.pow(Math.sin(dlon / 2),2);
        double c = 2 * Math.asin(Math.sqrt(a));
        // Radius of earth in kilometers
        double r = 6371;
        return (c*r);
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

    public Geometry getGeometry() {
        return geometry;
    }

    public String getPlaceID() {
        return placeID;
    }

    public ArrayList<JsonAttraction.AttractionType> getTypes() {
        return types;
    }

    public ArrayList<dayOpeningHours> getOpeningHoursArr() {
        return OpeningHoursArr;
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
                ", openingHours=" + OpeningHoursArr +
                '}';
    }

        public class Geometry
        {
            private String lat;
            private String lng;

            public Geometry(String lat, String lng) {
                this.lat = lat;
                this.lng = lng;
            }

            public String getLat() {
                return lat;
            }

            public String getLng() {
                return lng;
            }
        }

        public class dayOpeningHours
        {
            private boolean isOpen;
            private weekDay day;
            private ArrayList<String> openingHours = new ArrayList<>();
            private ArrayList<String> closingHours = new ArrayList<>();


            dayOpeningHours(int day)
            {
                //set the day(0=Sunday....6= Saturday]
                this.day = weekDay.values()[day];
                this.isOpen = false;
            }

            public void addOpening(String openingHour)
            {
                this.isOpen = true;
                this.openingHours.add(openingHour);
            }

            public void addClosing(String closingHour)
            {
                this.isOpen = true;
                this.closingHours.add(closingHour);
            }


            public weekDay getDay() {
                return day;
            }

            public boolean isOpen() {
                return isOpen;
            }

            public void setOpen(boolean open) {
                isOpen = open;
            }

            @Override
            public String toString() {
                return "dayOpeningHours{" +
                        "day=" + day +
                        ", openingHours=" + openingHours +
                        ", closingHours=" + closingHours +
                        '}';
            }
        }




}

