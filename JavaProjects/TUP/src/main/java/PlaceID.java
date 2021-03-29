import java.util.ArrayList;
import java.util.List;

public class PlaceID {

    private ArrayList<Candidate> candidates;


    public ArrayList<Candidate> getCandidates() {
        return candidates;
    }

    public class Candidate
    {
        private String place_id;

        public String getPlaceId()
        {
            return this.place_id;
        }

        @Override
        public String toString() {
            return "Candidate{" +
                    "place_id='" + place_id + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "PlaceID{" +
                "candidates=" + candidates +
                '}';
    }


}
