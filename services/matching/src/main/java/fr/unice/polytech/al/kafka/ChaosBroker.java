package fr.unice.polytech.al.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
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

    private final Logger logger = Logger.getLogger(this.getClass());


    public ChaosBroker() {
        pDuplicate = 20.;
        pDelete = 20.;
        pSalt = 20.;
        pSlow = 20.;
        pNothing = 20.;
    }

    public void broke(String topic, String announcement, KafkaTemplate<String, String> template) throws JsonProcessingException, InterruptedException {
        switch (changeBrokerFeature) {
            case 0: //pDuplicate
                logger.info("CHAOS BROKER FEATURE : DUPLICATES MESSAGE :");
                template.send(topic,  announcement);
                //announcement.setId(announcement.getId()*2 );
                template.send(topic,  announcement);
                if (compt == (int)pDuplicate/10-1) {
                    compt = -1;
                    changeBrokerFeature = 1;
                    //System.out.println("Inside if");
                }
                break;
            case 1: //pDelete
                logger.info("CHAOS BROKER FEATURE : DELTES MESSAGE :");
                if (compt == (int) pDelete/10-1) {
                    compt = -1;
                    changeBrokerFeature = 2;
                    //System.out.println("Inside if");
                }
                break;
            case 2: //pSalt
                logger.info("CHAOS BROKER FEATURE : MAKES A MESS IN ANNOUNCEMENT MESSAGE :");
                Random rand = new Random();
                //announcement.setIdTransmitter((rand.nextInt(60) + 5));
                //announcement.setId( (long) (rand.nextInt(30) + 1) );
                template.send(topic,  announcement);
                if (compt == (int)pSalt/10-1) {
                    compt = -1;
                    changeBrokerFeature = 3;
                   // System.out.println("Inside if");
                }
                break;
            case 3: //pSlow
                logger.info("CHAOS BROKER FEATURE : SLOWS DOWN THE MESSAGE");
                new Thread(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(5);
                        template.send(topic,  announcement);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
                if (compt == (int)pSlow/10-1) {
                    compt = -1;
                    changeBrokerFeature = 4;
                    //System.out.println("Inside if");
                }
                break;
            case 4: //pNothing
                logger.info("CHAOS BROKER FEATURE : DOESN'T INTRODUCE ANY CHAGMENTS");
                template.send(topic,  announcement);
                if (compt == (int)pNothing/10-1) {
                    compt = -1;
                    changeBrokerFeature = 0;
                    //System.out.println("Inside if");
                }
                break;
        }

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