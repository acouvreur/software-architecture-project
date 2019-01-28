package fr.unice.polytech.al.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.al.model.Announcement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
public class ChaosBroker {

    private double pDuplicate;
    private double pDelete;
    private double pSalt;
    private double pSlow;
    private double pNothing;

    private static int compt = 0;
    private static int changeBrokerFeature = 0;

    public ChaosBroker() {
        pDuplicate = 20.;
        pDelete = 20.;
        pSalt = 20.;
        pSlow = 20.;
        pNothing = 20.;
    }

    public void broke(String topic, Announcement announcement, KafkaTemplate<String, String> template) throws JsonProcessingException, InterruptedException {
        System.out.println("*****************");
        System.out.println("changeBrokerFeature : " + changeBrokerFeature);
        System.out.println("compt : " + compt);
        ObjectMapper mapper = new ObjectMapper();
        switch (changeBrokerFeature) {
            case 0: //pDuplicate
                System.out.println("Chaos broker duplicate message");
                template.send(topic,  mapper.writeValueAsString(announcement));
                //announcement.setId(announcement.getId()*2 );
                template.send(topic,  mapper.writeValueAsString(announcement));
                if (compt == (int)pDuplicate/10-1) {
                    compt = -1;
                    changeBrokerFeature = 1;
                    //System.out.println("Inside if");
                }
                break;
            case 1: //pDelete
                System.out.println("Chaos broker delete message");
                if (compt == (int) pDelete/10-1) {
                    compt = -1;
                    changeBrokerFeature = 2;
                    //System.out.println("Inside if");
                }
                break;
            case 2: //pSalt
                System.out.println("Chaos broker make a mess in announcement message");
                Random rand = new Random();
                announcement.setIdTransmitter((rand.nextInt(60) + 5));
                announcement.setId( (long) (rand.nextInt(30) + 1) );
                template.send(topic,  mapper.writeValueAsString(announcement));
                if (compt == (int)pSalt/10-1) {
                    compt = -1;
                    changeBrokerFeature = 3;
                   // System.out.println("Inside if");
                }
                break;
            case 3: //pSlow
                System.out.println("Chaos broker slow down message");
                TimeUnit.SECONDS.sleep(5);
                template.send(topic,  mapper.writeValueAsString(announcement));
                if (compt == (int)pSlow/10-1) {
                    compt = -1;
                    changeBrokerFeature = 4;
                    //System.out.println("Inside if");
                }
                break;
            case 4: //pNothing
                System.out.println("Chaos broker send a message ordinarly");
                template.send(topic,  mapper.writeValueAsString(announcement));
                if (compt == (int)pNothing/10-1) {
                    compt = -1;
                    changeBrokerFeature = 0;
                    //System.out.println("Inside if");
                }
                break;
        }
        System.out.println("*****************");
        System.out.println("*****************");
        System.out.println("*****************");
        compt++;
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