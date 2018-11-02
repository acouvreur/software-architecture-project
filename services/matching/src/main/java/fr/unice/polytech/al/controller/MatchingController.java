package fr.unice.polytech.al.controller;

import fr.unice.polytech.al.model.Announcement;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MatchingController
{
    /*
    * Input: Id of the new announcement
    * Output: json object with an array of the json representation of the courses
    */
    @GetMapping("/match/{idAnnonce}")
    public String match(@PathVariable long idAnnonce)
    {
        JSONArray coursesMocked = new JSONArray();

        coursesMocked.put((new Announcement(idAnnonce)).toJson());
        coursesMocked.put((new Announcement(2)).toJson());
        coursesMocked.put((new Announcement(3)).toJson());

       return new JSONObject().put("announcements", coursesMocked).toString();
    }
}
