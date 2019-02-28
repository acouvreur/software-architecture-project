package fr.unice.polytech.al.kafka;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.al.model.Billing;
import fr.unice.polytech.al.repository.BillingRepository;
import fr.unice.polytech.al.service.BillingService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

@Component
public class KafkaListenerBean {

    @Autowired
    private BillingService service;

    @Autowired
    private BillingRepository repository;

    private final Logger logger = Logger.getLogger(this.getClass());

    private Set<Long> accountsIdSet = new HashSet<>();
    private Set<Long> idTransmitterSet = new HashSet<>();
    private Set<Long> idSet = new HashSet<>();


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

        Object json0 = deSerializedData(message);
        String json = (String) json0;

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure( DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        JsonNode jsonNode = objectMapper.readTree(json);

        String idGood = jsonNode.get("idGood").asText();
        //System.out.println("idGood : " + idGood);

        String idDriver = jsonNode.get("idDriver").asText();
        //System.out.println("idDriver : " + idDriver);

        long goodId = Long.valueOf(idGood);
        long driverId = Long.valueOf(idDriver);

        if (!idTransmitterSet.contains( driverId ) && !idSet.contains( goodId )) {

            logger.info( "TRACKING OF GOOD WITH ID : " + goodId + " HAS FINISHED (DRIVER : " + driverId + ")" );

            service.setNewBallanceForClient( goodId );
            service.setNewBallanceForClient( driverId );

            logger.info( "SETTING THE BALANCE FOR CLIENTS WITH ID : " + goodId + " and " + driverId );

            idTransmitterSet.add( driverId );
            accountsIdSet.add( goodId );
        }else {
            logger.info("THE ANNOUNCEMENT WITH ID " + goodId +" AND ID TRANSMITTER " + driverId + " WAS DUPLICATED" );
        }

    }


    @KafkaListener(topics = "account_created")
    public void createBillingOnceAccountCreated(String message, Acknowledgment acknowledgment) throws IOException {


        Object json0 = deSerializedData(message);
        String json = (String) json0;

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure( DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        JsonNode jsonNode = objectMapper.readTree(json);

        String id = jsonNode.get("id").asText();
        long accountId = Long.valueOf(id);


        if (!accountsIdSet.contains(accountId)) {
            accountsIdSet.add( accountId );

            logger.info("MESSAGE FROM SERVICE ACCOUNT RECEIVED. CREATING OBJECT BILLING FOR ACCOUNT WITH ID " + accountId );


            Billing billing = new Billing(accountId,200);
            repository.save(billing);
        } else {
            logger.info("THIS ACCOUNT EXISTS ALREADY, THIS MESSAGE IS A DUPLICATION" + accountId );

        }

    }

}

