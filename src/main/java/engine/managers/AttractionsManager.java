package engine.managers;

import engine.attraction.Attraction;

import java.util.ArrayList;
import java.util.List;

public class AttractionsManager {
    private List<Attraction> attractions;

    public AttractionsManager(String destination){
        //get all attraction on data base by destination- we have to do this once anyway because we need them to calculate trip plan
    }

    public void addAttraction(Attraction attraction){
        attractions.add(new Attraction(attraction));
    }

    public Attraction getAttraction(String name) throws Attraction.NotFoundException {
        for(Attraction attraction: attractions){
            if(attraction.getName().equals(name))
                return attraction;
        }
        throw new Attraction.NotFoundException(name + "not found in DB");
    }

}
