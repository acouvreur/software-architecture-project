package fr.unice.polytech.al.service;


import fr.unice.polytech.al.model.Billing;
import fr.unice.polytech.al.model.Course;
import fr.unice.polytech.al.repository.BillingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Service("BillingService")
public class BillingService {


    @Autowired
    private BillingRepository repository;


    public int estimateBilling(Course[] announcementIds) {
        int res = 0;
        Random rand = new Random();
        for(Course id : announcementIds) {
            res += (rand.nextInt(60) + 20);
            System.out.println("res......." + res);
        }
        return res;
    }

    public Billing setNewBallanceForClient(long clientId) {
        Billing billingTmp = null;
        Random rand = new Random();
        try {
            billingTmp = repository.findById( clientId ).get();
            billingTmp.setPoints( rand.nextInt( 100 ) + 10 );
        } catch (Error e) {}
        if ( billingTmp == null) {billingTmp = new Billing(clientId, 300);}
        repository.save(billingTmp);
        return billingTmp;
    }



}
