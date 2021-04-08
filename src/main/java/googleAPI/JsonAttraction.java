package googleAPI;


import java.util.ArrayList;
import java.util.List;


public class JsonAttraction {
    public enum AttractionType {airport,
        amusement_park,
        aquarium,
        art_gallery,
        bakery,
        bar,
        bowling_alley,
        cafe,
        casino,
        cemetery,
        church,
        embassy,
        hindu_temple,
        lodging,
        mosque,
        museum,
        night_club,
        movie_theater,
        park,
        restaurant,
        shopping_mall,
        stadium,
        synagogue,
        spa,
        tourist_attraction,
        zoo}


    private List html_attributions = new ArrayList<String>();
    private JsonResult result;
    private String status;

    @Override
    public String toString() {
        return "engine.Attraction{" +
                "html_attributions = " + html_attributions +"\n" +
                "result = \n" + result +"\n"+
                "status = " + status +
                '}';
    }

    public List getHtml_attributions() {
        return html_attributions;
    }

    public JsonResult getResult() {
        return result;
    }

    public String getStatus() {
        return status;
    }

    public class JsonResult {
        String formatted_address;
        String formatted_phone_number;
        String name;
        String place_id;
        OpeningHours opening_hours;
        String business_status;
        ArrayList<AttractionType> attractionTypes;
        Geometry geometry;

        public String getFormatted_address() {
            return formatted_address;
        }

        public String getFormatted_phone_number() {
            return formatted_phone_number;
        }

        public String getName() {
            return name;
        }

        public String getPlace_id() {
            return place_id;
        }

        public OpeningHours getOpening_hours() {
            return opening_hours;
        }

        public String getBusiness_status() {
            return business_status;
        }

        public ArrayList<AttractionType> getTypes() {
            return attractionTypes;
        }

        public Geometry getGeometry() {
            return geometry;
        }

        @Override
        public String toString() {
            return "JsonResult{ \n" +
                    "formatted_address = " + formatted_address +"\n"+
                    "formatted_phone_number = " + formatted_phone_number +"\n"+
                    "name = " + name + "\n"+
                    "opening_hours = " + opening_hours +
                    '}';
        }


        public class Geometry
        {
            Location location;
            private class Location
            {
                String lat;
                String lng;
            }

        }


        public class OpeningHours {
            Boolean open_now;
            ArrayList<DayOpeningHours> periods = new ArrayList();



            public Boolean getOpen_now() {
                return open_now;
            }

            public ArrayList<DayOpeningHours> getPeriods() {
                return periods;
            }

            @Override
            public String toString() {
                String result = "OpeningHours{" + "open_now = " + open_now + "\n"+ "periods = \n";
                for(DayOpeningHours day: periods)
                    result += day.toString();
                return result;
            }

            public class DayOpeningHours {
                DetailsHours close;
                DetailsHours open;

                public DetailsHours getClose() {
                    return close;
                }

                public DetailsHours getOpen() {
                    return open;
                }

                @Override
                public String toString() {
                    return "DayOpeningHours{" +
                            "open = " + open +
                            ", close = " + close +
                            '}' +"\n";
                }

                public class DetailsHours {
                    int day;
                    String time;

                    public int getDay() {
                        return day;
                    }

                    public String getTime() {
                        return time;
                    }

                    @Override
                    public String toString() {
                        return "DetailsHours{" +
                                "day = " + day +
                                ", time = '" + time + '\'' +
                                '}';
                    }
                }
            }
        }
    }
}
