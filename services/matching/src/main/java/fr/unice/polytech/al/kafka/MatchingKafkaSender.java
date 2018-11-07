package fr.unice.polytech.al.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MatchingKafkaSender
{
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(String topic, String data) {
        kafkaTemplate.send(topic, data);
        System.out.println("Message sent: "+ data +" - to topic: " + topic);
    }
}
