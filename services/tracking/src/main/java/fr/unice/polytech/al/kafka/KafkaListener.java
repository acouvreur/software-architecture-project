package fr.unice.polytech.al.kafka;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.al.model.Announcement;
import fr.unice.polytech.al.repository.TrackingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static fr.unice.polytech.al.State.STARTED;

@Component
public class KafkaListener {

    @Autowired
    private TrackingRepository repository;

    private Random rand = new Random();



    public KafkaListener() {
    }

    public Announcement transformJsonToAnnouncement(Object jsonO) throws IOException, ParseException {
        String json = (String)jsonO;
        System.out.println("object message value : " + jsonO);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        //json = json.replaceAll( '\\',"" );
        JsonNode jsonNode = objectMapper.readTree(json);
        System.out.println("jsonNode : " + jsonNode);

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


    @org.springframework.kafka.annotation.KafkaListener(topics = "announcement_matched")
    public void getIdsOfGoodAndCarAnnouncement(String message, Acknowledgment acknowledgment) throws IOException, ParseException {
        Object json = deSerializedData(message);
        Announcement announcement = transformJsonToAnnouncement(json);
        repository.save( announcement );
    }

}

