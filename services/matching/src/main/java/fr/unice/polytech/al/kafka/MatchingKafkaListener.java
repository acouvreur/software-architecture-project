package fr.unice.polytech.al.kafka;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedList;

@Component
public class MatchingKafkaListener
{
    @Autowired
    MatchingKafkaSender kafkaSender;

    private LinkedList<String> announcementCourse = new LinkedList<String>();
    private LinkedList<String> announcementGoods  = new LinkedList<String>();


    @KafkaListener(topics = "announcement_created")
    public void receiveAnnouncementCreated(String data) throws IOException, InterruptedException
    {
        // data in json format but it's just a string
        Object obj = deSerializedData(data);
        String dataInJsonFormat = (String)obj;
        
        System.out.println("\nService Matching. Received Message. Topic: announcement_created  - Message: " + dataInJsonFormat);

        // data in object json
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode dataJson = objectMapper.readTree(dataInJsonFormat);

        //System.out.println("\n BEFORE list goods: " + announcementGoods.size() + "      list course: " + announcementCourse.size());

        if(dataJson.get("type").asText().equals("GOOD")) {
            announcementGoods.add(dataInJsonFormat);

        } else if(dataJson.get("type").asText().equals("COURSE")) {
            announcementCourse.add(dataInJsonFormat);
        }


        // THERE IS A MATCH!
        if(!announcementCourse.isEmpty() && !announcementGoods.isEmpty()) {
            String good = announcementGoods.poll();
            JsonNode goodJson = (new ObjectMapper()).readTree(good);

            String course = announcementCourse.poll();
            JsonNode courseJson = (new ObjectMapper()).readTree(course);

            //String msg = "{ \"idGood\" : \"" + goodJson.get("id").asText() + "\", \"idCourse\" : \"" + courseJson.get("id").asText() + "\" }";
            String msg = "{ \"good\" : " + good + ", \"course\" : " + course + " }";
            //System.out.println("\n AFTER list goods: " + announcementGoods.size() + "      list course: " + announcementCourse.size());


            //KAFKA -> ANNOUNCEMENT & TRACKING
            kafkaSender.send("announcement_matched", msg);
            System.out.println("announcement_matched -> announcement & tracking .... " );

        } else {
            System.out.println("\n\n NOOOO MATCH!!\n");
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



/*
Stop and remove all containers:
    docker stop $(docker ps -a -q)
    docker rm $(docker ps -a -q)


remove all images:
	docker rmi $(docker images -a -q)



mvn clean install -Dmaven.test.skip=true
docker-compose up --build -d

docker logs -f blablamove-matching
docker logs -f blablamove-tracking




curl -s -d '{"idTransmitter":1, "nameTransmitter":"NNNNN", "startPoint":"Sophia", "endPoint":"Paris","startDate":"2018-10-12", "endDate":"2018-12-24", "type" :"GOOD"}' -H "Content-Type: application/json" -X POST http://localhost:8080/announcements
curl -s -d '{"idTransmitter":1, "nameTransmitter":"Steve", "startPoint":"Sophia", "endPoint":"Paris","startDate":"2018-10-12", "endDate":"2018-12-24", "type" :"GOOD"}' -H "Content-Type: application/json" -X POST http://localhost:8080/announcements


curl -s -d '{"idTransmitter":2, "nameTransmitter":"HAY MATCH OOOOO", "startPoint":"Sophia", "endPoint":"Paris","startDate":"2018-10-12", "endDate":"2018-12-24", "type" :"COURSE"}' -H "Content-Type: application/json" -X POST http://localhost:8080/announcements
*/