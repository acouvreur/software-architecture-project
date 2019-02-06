package fr.unice.polytech.al.kafka;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.al.model.Announcement;
import fr.unice.polytech.al.repository.AnnouncementRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@Component
public class AnnouncementKafkaListener
{
    @Autowired
    private AnnouncementRepository repository;

    @KafkaListener(topics = "announcement_matched")
    public void receiveAnnouncementMatched(String data) throws IOException 
    {        
        // data in json format but it's just a string
        Object obj = deSerializedData(data);
        String dataInJsonFormat = (String)obj;

        System.out.println("\nService Announcement. Received Message. Topic: announcement_matched  - Message: " + dataInJsonFormat);

        // data in object json
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode dataJson = objectMapper.readTree(dataInJsonFormat);

        // announcements ids
        Long idGood = dataJson.get("good").get("id").asLong();
        Long idCourse = dataJson.get("course").get("id").asLong();


        // set course announcement ID in good
        Announcement announcementGood = repository.findById(idGood).get();
        announcementGood.setIdAnnouncementMatched(idCourse);
        repository.save(announcementGood);

        // set good announcement ID in course
        Announcement announcementCourse = repository.findById(idCourse).get();
        announcementCourse.setIdAnnouncementMatched(idGood);
        repository.save(announcementCourse);

        //System.out.println("\n\n ANNOUNCE good: " + announcementGood.toString());
        //System.out.println("\n\n ANNOUNCE course: " + announcementCourse.toString());
    }



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
}