package fr.unice.polytech.al.kafka;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.al.model.Match;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MatchingKafkaListener
{
    @Autowired
    MatchingKafkaSender kafkaSender;

    @Autowired
    private Match matchList;


    public Object deSerializedData(String str) {
        ObjectMapper mapper = new ObjectMapper();
        Object obj = null;
        try {
            obj = mapper.readValue(str, Object.class);
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return obj;
    }

    @KafkaListener(topics = "announcement_created")
    public void receiveAnnouncementCreated(String data) throws IOException, InterruptedException {
        //String announcementId = (new JSONObject(data)).getString("announcementId");
        System.out.println("\nService Matching. Received Message. Topic: announcement_created  - Message: " + data);

        matchList.add(data);
        //String result = matchList.toString();

        //if (matchList.size() > 1) {
            System.out.println("\n\nService Matching. Course Matched " +  data);
        System.out.println("\n\nService Matching. Course Matched " +  data);
             /*for(int i=0; i < matchList.size(); i++) {
                 JSONObject status = new JSONObject().put("announcementId", matchList.get(i)).put("status", "matched");
                 kafkaSender.send("announcement_status", status.toString());
             }*/

             //KAFKA -> TRACKING

            Object obj = deSerializedData(data);
            String json = (String)obj;
            System.out.println("object message value : " + obj);


            kafkaSender.send("announcement_matched", json);

        //}

        if (matchList.size() == 3) {
            matchList.empty();
        }
    }
}
