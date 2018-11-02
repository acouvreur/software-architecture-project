package fr.unice.polytech.al.service;


import fr.unice.polytech.al.model.Billing;
import fr.unice.polytech.al.model.Course;
import fr.unice.polytech.al.repository.BillingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Random;

@Service("BillingService")
public class BillingService {


    @Autowired
    private BillingRepository repository;

    private Random rand = new Random();


    public int estimateBilling(LinkedList<Course> courses) {
        int res = 0;
        for(Course course : courses) {
            res += (rand.nextInt() * 60) + 20;
        }
        return res;
    }

    public Billing setNewBallanceForClient(long clientId) {
        Billing billingTmp = repository.findById(clientId).get();
        billingTmp.setPoints(rand.nextInt(100) + 10);
        repository.save(billingTmp);
        return billingTmp;
    }



}
