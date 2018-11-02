package fr.unice.polytech.al.kafka;


import fr.unice.polytech.al.repository.BillingRepository;
import fr.unice.polytech.al.service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class KafkaListenerBean {

    @Autowired
    private BillingService service;

    /*@Autowired
    private KafkaSender sender;*/

    private Random rand = new Random();


    @KafkaListener(topics = "tracking-finished")
    public void modificationOfNumberOfPoints(String usersId, Acknowledgment acknowledgment) {
        String[] ids = usersId.split( ";" );
        long id1 = Integer.parseInt(ids[0]);
        long id2 = Integer.parseInt(ids[1]);
        service.setNewBallanceForClient(id1);
        service.setNewBallanceForClient(id2);
    }


}

