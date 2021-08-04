package servlets.utils;

import engine.Engine;
import engine.traveler.Traveler;

import javax.servlet.http.HttpServletRequest;

public class ResponseJson{
    public String status;
    public String message;


    public ResponseJson(){
        status = "ok";
    }

//
//    Traveler travelerFromJson(Traveler jsonTraveler, HttpServletRequest req) throws Traveler.IllegalValueException {
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
//            newTraveler.setEmail(jsonTraveler.getEmail());
//
//            newTraveler.setAge(jsonTraveler.getAge());
//            newTraveler.setNotes(jsonTraveler.getNotes());
//            newTraveler.setLevel(jsonTraveler.getLevel());
//            newTraveler.setBoatSerialNumber(jsonTraveler.getBoatSerialNumber());
//            newTraveler.setPhoneNumber(jsonTraveler.getPhoneNumber());
//            newTraveler.setManager(jsonTraveler.getManager());
//        }
//
//        return newTraveler;
//    }


}