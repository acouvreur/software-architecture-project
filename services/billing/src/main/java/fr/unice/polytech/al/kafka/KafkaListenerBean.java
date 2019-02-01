package fr.unice.polytech.al.kafka;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.al.model.Billing;
import fr.unice.polytech.al.repository.BillingRepository;
import fr.unice.polytech.al.service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class KafkaListenerBean {

    @Autowired
    private BillingService service;

    @Autowired
    private BillingRepository repository;


    /*@Autowired
    private KafkaSender sender;*/

    private Random rand = new Random();


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



    @KafkaListener(topics = "tracking-finished")
    public void modifyBalanceOnceTrackingFinished(String message, Acknowledgment acknowledgment) throws IOException {
        System.out.println("tracking has finished .... " );

        Object json0 = deSerializedData(message);
        String json = (String) json0;


        System.out.println("object message value : " + json);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure( DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        JsonNode jsonNode = objectMapper.readTree(json);
        System.out.println("jsonNode : " + jsonNode);

        String idGood = jsonNode.get("idGood").asText();
        System.out.println("idGood : " + idGood);

        String idDriver = jsonNode.get("idDriver").asText();
        System.out.println("idDriver : " + idDriver);

        long goodId = Long.valueOf(idGood);
        long driverId = Long.valueOf(idDriver);
        service.setNewBallanceForClient(goodId);
        service.setNewBallanceForClient(driverId);
    }


    @KafkaListener(topics = "account_created")
    public void createBillingOnceAccountCreated(String message, Acknowledgment acknowledgment) throws IOException {
        System.out.println("account created -> creation of billing .... " + message );



        Object json0 = deSerializedData(message);
        String json = (String) json0;

        System.out.println("object message value : " + json);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure( DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        JsonNode jsonNode = objectMapper.readTree(json);
        System.out.println("jsonNode : " + jsonNode);

        String id = jsonNode.get("id").asText();
        long accountId = Long.valueOf(id);
        System.out.println("accountId : " + accountId);

        Billing billing = new Billing(accountId,200);
        repository.save(billing);
    }

}

