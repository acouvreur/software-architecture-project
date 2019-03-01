package fr.unice.polytech.al.kafka;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.al.model.Announcement;
import fr.unice.polytech.al.repository.AnnouncementRepository;
import org.apache.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Optional;

@Component
public class AnnouncementKafkaListener
{
    @Autowired
    private AnnouncementRepository repository;

    private final Logger logger = Logger.getLogger(this.getClass());


    @KafkaListener(topics = "announcement_matched")
    public void receiveAnnouncementMatched(String data) throws IOException 
    {        
        // data in json format but it's just a string
        Object obj = deSerializedData(data);
        String dataInJsonFormat = (String)obj;


        // data in object json
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode dataJson = objectMapper.readTree(dataInJsonFormat);

        // announcements ids
        Long idGood = dataJson.get("good").get("id").asLong();
        Long idCourse = dataJson.get("course").get("id").asLong();

        logger.info("RECEIVED MESSAGE WITH TOPIC : ANNOUNCEMENT_MATCHED, BETWEEN ANNOUNCEMENTS WITH IDS : " + idGood + " AND " + idCourse);

        Optional<Announcement> goodOpt = repository.findById(idGood);
        if(goodOpt.isPresent()) {
            Announcement announcementGood = goodOpt.get();
            announcementGood.setIdAnnouncementMatched(idCourse);
            repository.save(announcementGood);
        }

        Optional<Announcement> courseOpt = repository.findById(idCourse);
        if(courseOpt.isPresent()) {
            Announcement announcementCourse = courseOpt.get();
            announcementCourse.setIdAnnouncementMatched(idCourse);
            repository.save(announcementCourse);
        }
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