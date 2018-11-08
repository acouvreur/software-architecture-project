package fr.unice.polytech.al.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CourseKafkaListener
{
    @Autowired
    CourseKafkaSender kafkaSender;


    @KafkaListener(topics = "announcement_matched")
    public void receiveAnnouncementCreated(String data)
    {
        System.out.println("\nService Course. Received Message. Topic: announcement_matched  - Message: " + data);

    }
}
