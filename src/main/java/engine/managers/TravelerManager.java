package engine.managers;

import engine.traveler.Traveler;

import java.util.ArrayList;
import java.util.List;

public class TravelerManager {

    private List<Traveler> travelers = new ArrayList<Traveler>();


    public List<Traveler> getTravelers() {
        return travelers;
    }



    public void addTraveler(Traveler traveler) throws Traveler.IllegalValueException, Traveler.AlreadyExistsException, Traveler.NotFoundException {
        String email = traveler.getEmailAddress();
        validateTravelerEmail(email);
        travelers.add(new Traveler(traveler));
    }

    public void deleteTraveler(String email) throws Traveler.NotFoundException {
        Traveler traveler = getTraveler(email);
        if (traveler == null)
            throw new Traveler.NotFoundException("Traveler Not Found");
        travelers.remove(traveler);
    }

    private void validateTravelerEmail(String email) throws Traveler.AlreadyExistsException, Traveler.NotFoundException {
        if (getTraveler(email) != null)
            throw new Traveler.AlreadyExistsException("Member with email '" + email + "' already exist");
    }

    public Traveler getTraveler(String email) throws Traveler.NotFoundException {
        for(Traveler traveler: getTravelers()){
            if(traveler.getEmailAddress().equalsIgnoreCase(email))
                return traveler;
        }
        throw new Traveler.NotFoundException("Traveler Not Found");

    }
}
