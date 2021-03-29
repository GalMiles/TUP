import java.util.ArrayList;
import java.util.List;

public class gsonAttraction {
    List html_attributions = new ArrayList<String>();
    JsonResult result;
    String status;

    @Override
    public String toString() {
        return "Attraction{" +
                "html_attributions = " + html_attributions +"\n" +
                "result = \n" + result +"\n"+
                "status = " + status +
                '}';
    }

    public class JsonResult {
        String formatted_address;
        String formatted_phone_number;
        String name;
        String place_id;
        OpeningHours opening_hours;
        String business_status;
        ArrayList<String> types;
        Geometry geometry;


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

            private class DayOpeningHours {
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

                private class DetailsHours {
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
