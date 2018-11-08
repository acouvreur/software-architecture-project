package fr.unice.polytech.al.kafka;

import fr.unice.polytech.al.model.Match;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MatchingKafkaListener
{
    @Autowired
    MatchingKafkaSender kafkaSender;

    private Match matchList = new Match();

    @KafkaListener(topics = "announcement_created")
    public void receiveAnnouncementCreated(String data)
    {
        //String announcementId = (new JSONObject(data)).getString("announcementId");
        System.out.println("\nService Matching. Received Message. Topic: announcement_created  - Message: " + data);


        matchList.add(data);
        String result = matchList.toString();

        if (matchList.size() > 1) {
            System.out.println("\n\nService Matching. Course Matched " +  result);
            kafkaSender.send("announcement_matched", result);
             /*for(int i=0; i < matchList.size(); i++) {
                 JSONObject status = new JSONObject().put("announcementId", matchList.get(i)).put("status", "matched");
                 kafkaSender.send("announcement_status", status.toString());
             }*/
        }

        if (matchList.size() == 3) {
            matchList.empty();
        }
    }
}
