package servlets.utils;

import engine.Engine;
import engine.planTrip.RouteTrip;
import engine.traveler.Traveler;
import servlets.TripServlet;

import javax.servlet.http.HttpServletRequest;

public class ResponseJson{
    public String status;
    public Object message;


    public ResponseJson(){
        status = "ok";
    }


    public static Traveler travelerFromJson(Traveler jsonTraveler, HttpServletRequest req) throws Traveler.IllegalValueException {
        Traveler newTraveler;
        newTraveler = new Traveler(jsonTraveler.getFirstName(), jsonTraveler.getLastName(), jsonTraveler.getEmailAddress(), jsonTraveler.getPassword(), jsonTraveler.getId());
        newTraveler.setFirstName(jsonTraveler.getFirstName());
        newTraveler.setLastName(jsonTraveler.getLastName());
        newTraveler.setEmailAddress(jsonTraveler.getEmailAddress());
        newTraveler.setPassword(jsonTraveler.getPassword());
        return newTraveler;

//        Engine engine = ContextServletUtils.getEngine(req);

//        boolean editProfile = req.getServletPath().startsWith("/user/");
//        Traveler originalMember = editProfile ? getCurrentUser(req) : engine.getMember(jsonTraveler.getSerialNumber());
//        Traveler newTraveler;
//
//        if(jsonTraveler.getPassword() == null)
//            jsonTraveler.setPassword(originalMember.getPassword());
//
//        if(editProfile){
//            newTraveler = getCurrentUser(req);
//
//            newTraveler.setPhoneNumber(jsonTraveler.getPhoneNumber());
//            newTraveler.setEmail(jsonTraveler.getEmail());
//            newTraveler.setName(jsonTraveler.getName());
//            newTraveler.setPassword(jsonTraveler.getPassword());
//        } else{
//            newTraveler = new Traveler(jsonTraveler.getFirstName(), jsonTraveler.getLastName(), jsonTraveler.getEmailAddress(), jsonTraveler.getPassword());
//            newTraveler.setFirstName(jsonTraveler.getFirstName());
//            newTraveler.setLastName(jsonTraveler.getLastName());
//            newTraveler.setEmailAddress(jsonTraveler.getEmailAddress());
//            newTraveler.setPassword(jsonTraveler.getPassword());
//        //}

        //return newTraveler;
    }



}