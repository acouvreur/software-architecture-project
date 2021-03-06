package fr.unice.polytech.al.service;


import fr.unice.polytech.al.model.Billing;
import fr.unice.polytech.al.model.Course;
import fr.unice.polytech.al.repository.BillingRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Service("BillingService")
public class BillingService {


    @Autowired
    private BillingRepository repository;

    private final Logger logger = Logger.getLogger(this.getClass());



    public int estimateBilling(Course[] announcementIds) {
        int res = 0;
        long id_ = 0;
        Random rand = new Random();
        for(Course id : announcementIds) {
            res += (rand.nextInt(60) + 20);
            System.out.println("res......." + res);
            id_ = id.getId() ;
        }
        logger.info("ESTIMATE THE NUMBER OF POINTS FOR COURSE BASED ON ANNOUNCEMENT WITH ID " + id_+ " : " + res);
        return res;
    }

    public Billing setNewBallanceForClient(long clientId) {
        Billing billingTmp = null;
        Random rand = new Random();
        int res = 0;
        try {
            billingTmp = repository.findById( clientId ).get();
            res = rand.nextInt( 100 ) + 10;
            billingTmp.setPoints( res );
        } catch (Error e) {}
        if ( billingTmp == null) {
            res = 300;
            billingTmp = new Billing(clientId, res);
        }
        repository.save(billingTmp);
        logger.info("CLIENT WITH ID " +clientId + " BALANCE : " + res);
        return billingTmp;
    }



}
