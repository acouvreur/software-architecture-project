package fr.unice.polytech.al.controller;

import fr.unice.polytech.al.kafka.MatchingKafkaSender;
import fr.unice.polytech.al.model.Match;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MatchingController
{
    @Autowired
    MatchingKafkaSender kafkaSender;

    private Match matchList = new Match();

    /*
     * Input: Id of the announcement created
     * Output: List of matching announcement
     */
    @GetMapping("/match/{idAnnonce}")
    public String match(@PathVariable String idAnnonce)
    {
        //kafkaSender.send("announcement_created", (new JSONObject().put("announcementId", 10)).toString());

        matchList.add(idAnnonce);
        String result = matchList.toJson().toString();

        //  if (matchList.size() > 1) {
        // kafkaSender.send("create_announcement", result);
        //  }

        if (matchList.size() == 3) {
            matchList.empty();
        }

        if (matchList.size() == 1) {
            return "";
        }

        System.out.println("match : " + result);
        return result;
    }
}
