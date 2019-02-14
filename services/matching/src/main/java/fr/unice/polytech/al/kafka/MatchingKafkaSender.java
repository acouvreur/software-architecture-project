package fr.unice.polytech.al.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MatchingKafkaSender
{
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ChaosBroker chaosBroker;

    public void send(String topic, String data) throws JsonProcessingException, InterruptedException {
        //chaosBroker.broke(topic, data, kafkaTemplate);
        kafkaTemplate.send(topic, data);
        System.out.println("Service Matching. Send Message. Topic: " + topic + " - Message: " + data);
    }
}
