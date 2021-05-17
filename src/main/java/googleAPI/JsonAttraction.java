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
        point_of_interest,
        embassy,
        establishment,
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
        String website;
        OpeningHours opening_hours;
        String business_status;
        ArrayList<AttractionType> types = new ArrayList();
        Geometry geometry;

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }


        public String AttractionTypesToStr()
        {
            StringBuilder stringBuilder = new StringBuilder();
            int arrSize = this.types.size();
            for(int i = 0; i < arrSize-1; ++i)
            {
                if(types.get(i) != null)
                {
                    stringBuilder.append(types.get(i));
                    stringBuilder.append(',');
                }
            }
            stringBuilder.append(types.get(arrSize-1));
            return stringBuilder.toString();
        }

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
            return types;
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
            private Location location;
            public class Location
            {
                private String lat;
                private String lng;

                public String getLat() {
                    return lat;
                }

                public void setLat(String lat) {
                    this.lat = lat;
                }

                public void setLng(String lng) {
                    this.lng = lng;
                }

                public String getLng() {
                    return lng;
                }

                @Override
                public String toString() {
                    return lat + "," + lng;
                }
            }

            public Location getLocation() {
                return location;
            }
        }


        public class OpeningHours {
            Boolean open_now;
            ArrayList<DayOpeningHours> periods = new ArrayList();

            ArrayList<String> weekday_text = new ArrayList();

            public ArrayList<String> getWeekday_text(){ return weekday_text;}


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
                    StringBuilder openingHourStr = new StringBuilder();
                    StringBuilder closingHourStr = new StringBuilder();

                    openingHourStr.append(this.getOpen().getTime());  //Opening time
                    openingHourStr.insert(2, ':'); // insert ':'

                    closingHourStr.append(this.getClose().getTime());  //Closing time
                    closingHourStr.insert(2, ':'); // insert ':'
                    return openingHourStr + "-" + closingHourStr;
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
