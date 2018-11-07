package fr.unice.polytech.al.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
public class KafkaSender {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public KafkaSender(){

    }

    //where clientsId is a string of form "idClient1;idClient2"
    public void sendInformationEndOfTracking(String clientsId) {
        kafkaTemplate.send("tracking-finished",  clientsId);
    }
}
