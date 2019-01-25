package fr.unice.polytech.al.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ChaosBroker {

    private double pDuplicate;
    private double pDelete;
    private double pSalt;
    private double pSlow;
    private double pNothing;

    public ChaosBroker() {
        pDuplicate = 20.;
        pDelete = 20.;
        pSalt = 20.;
        pSlow = 20.;
        pNothing = 20.;
    }

    public void broke(String topic, String message, KafkaTemplate<String, String> template) {
        // Random protocol
        // System.out.println("\n\nService Announcement. Send Message. Topic: " + topic + " - Message: " + data);
        // template.send(topic, message);
    }

    public double getpDuplicate() {
        return pDuplicate;
    }

    public void setpDuplicate(double pDuplicate) {
        this.pDuplicate = pDuplicate;
    }

    public double getpDelete() {
        return pDelete;
    }

    public void setpDelete(double pDelete) {
        this.pDelete = pDelete;
    }

    public double getpSalt() {
        return pSalt;
    }

    public void setpSalt(double pSalt) {
        this.pSalt = pSalt;
    }

    public double getpSlow() {
        return pSlow;
    }

    public void setpSlow(double pSlow) {
        this.pSlow = pSlow;
    }

    public double getpNothing() {
        return pNothing;
    }

    public void setpNothing(double pNothing) {
        this.pNothing = pNothing;
    }
}