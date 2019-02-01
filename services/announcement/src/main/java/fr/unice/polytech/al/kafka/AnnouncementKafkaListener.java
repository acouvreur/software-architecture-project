package fr.unice.polytech.al.kafka;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.al.model.Announcement;
import fr.unice.polytech.al.repository.AnnouncementRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AnnouncementKafkaListener
{
    private AnnouncementRepository repository;

    @KafkaListener(topics = "announcement_matched")
    public void receiveAnnouncementMatched(String data) throws IOException {
        System.out.println("\nService Announcement. Received Message. Topic: announcement_matched  - Message: " + data);

        // data in json format but it's just a string
        Object obj = deSerializedData(data);
        String dataInJsonFormat = (String)obj;
        //System.out.println("\n\n JSON deserialize : " + dataInJsonFormat);


        // data in object json
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode dataJson = objectMapper.readTree(dataInJsonFormat);
        //System.out.println("\n\n JSON NODE : " + dataJson);
        System.out.println("\n\n JSON type text: " + dataJson.get("good").asText());


        JsonNode good = (new ObjectMapper()).readTree(dataJson.get("good").asText());
        JsonNode course = (new ObjectMapper()).readTree(dataJson.get("course").asText());

        // set course announcement ID in good
        Announcement announcementGood = repository.findById( good.get("id").asLong() ).get();
        announcementGood.setIdAnnouncementMatched(course.get("id").asText());
        repository.save(announcementGood);

        // set good announcement ID in course
        Announcement announcementCourse = repository.findById( course.get("id").asLong() ).get();
        announcementCourse.setIdAnnouncementMatched(good.get("id").asText());
        repository.save(announcementCourse);
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