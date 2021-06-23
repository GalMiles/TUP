package engine.attraction;

import java.util.ArrayList;
import java.util.List;

public class AttractionsManager {
    private List<Attraction> attractions = new ArrayList<>();

    public void addAttraction(Attraction attraction){
        attractions.add(new Attraction(attraction));
    }
}
