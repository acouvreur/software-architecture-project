package fr.unice.polytech.al.kafka;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.al.model.Announcement;
import fr.unice.polytech.al.repository.TrackingRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static fr.unice.polytech.al.State.STARTED;

@Component
public class TrackingKafkaListener {

    @Autowired
    private TrackingRepository repository;

    private Random rand = new Random();

    private Set<Long> idTransmitterSet = new HashSet<>();
    private Set<Long> idSet = new HashSet<>();

    public TrackingKafkaListener() {
    }

    private final Logger logger = Logger.getLogger(this.getClass());


    public Announcement transformJsonToAnnouncement(JsonNode jsonNode) throws IOException, ParseException
    {
        String id = jsonNode.get("id").asText();
        System.out.println("class announcement id : " + id);

        String idTransmitter = jsonNode.get("idTransmitter").asText();
        System.out.println("class announcement idTransmitter : " + idTransmitter);

        String nameTransmitter = jsonNode.get("nameTransmitter").asText();
        System.out.println("class announcement nameTransmitter : " + nameTransmitter);

        String startPoint = jsonNode.get("startPoint").asText();
        System.out.println("class announcement startPoint : " + startPoint);

        String endPoint = jsonNode.get("endPoint").asText();
        System.out.println("class announcement endPoint : " + endPoint);

        String startDate = jsonNode.get("startDate").asText();
        System.out.println("class announcement startDate : " + startDate);


        String endDate = jsonNode.get("endDate").asText();
        System.out.println("class announcement endDate : " + endDate);


        //    public Announcement(Long idGoodAnnouncement, Long idDriverAnnouncement, String nameTransmitter, String startPoint, String endPoint, Date startDate, Date endDate, State state) {
        Announcement announcement = new Announcement(Long.valueOf(id), Long.valueOf(idTransmitter), nameTransmitter, startPoint, endPoint, startDate, endDate, STARTED);

        return announcement;

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


    @KafkaListener(topics = "announcement_matched")
    public void getIdsOfGoodAndCarAnnouncement(String message, Acknowledgment acknowledgment) throws IOException, ParseException {
        Object obj = deSerializedData(message);
        String json = (String)obj;

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);

        JsonNode jsonNode = objectMapper.readTree(json);

        Announcement announcement = transformJsonToAnnouncement(jsonNode.get("course"));

        Long incomingId = announcement.getIdGoodAnnouncement();
        Long incomingIdTransmitter = announcement.getIdDriverAnnouncement();

        //prevent receiving 2 times the same message
        if (idTransmitterSet.contains( incomingIdTransmitter ) && idSet.contains( incomingId )) {
            logger.info("THE ANNOUNCEMENT WITH ID " + incomingIdTransmitter +" AND ID TRANSMITTER " + incomingId + " WAS DUPLICATED" );

        } else {
            repository.save( announcement );

            logger.info("RECEIVED MESSAGE OF MATCHING BETWEEN ANNOUNCEMENT WITH ID DRIVER : " + incomingId + " AND ANNOUNCEMENT WITH ID GOOD" + incomingIdTransmitter);

            idTransmitterSet.add( incomingIdTransmitter );
            idTransmitterSet.add( incomingId );

        }


    }

}

