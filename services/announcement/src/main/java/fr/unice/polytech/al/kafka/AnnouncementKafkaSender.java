package fr.unice.polytech.al.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class AnnouncementKafkaSender
{
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ChaosBroker chaosBroker;

    public void send(String topic, String data) {

        chaosBroker.broke(topic, data, kafkaTemplate);
        /*kafkaTemplate.send(topic, data);
        System.out.println("\n\nService Announcement. Send Message. Topic: " + topic + " - Message: " + data);*/
    }
}
