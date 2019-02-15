package fr.unice.polytech.al.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.al.model.Announcement;
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

    public void send(String topic, Announcement announcement) throws JsonProcessingException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        chaosBroker.broke(topic, announcement, kafkaTemplate);

        //kafkaTemplate.send(topic, mapper.writeValueAsString(announcement));
        //System.out.println("\n\nService Announcement. Send Message. Topic: " + topic + " - Message: " + data);
    }
}
