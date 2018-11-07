package fr.unice.polytech.al;

import fr.unice.polytech.al.model.Account;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component("testListener")
public class TestListener {

    @KafkaListener(topics = "account_created")
    public void onCreatedAccount(Account acc, Acknowledgment ack) {
        System.out.println("account_created event !");
        System.out.println(acc);
        ack.acknowledge();
    }
}
