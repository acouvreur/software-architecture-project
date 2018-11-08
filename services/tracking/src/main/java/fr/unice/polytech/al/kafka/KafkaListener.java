//package fr.unice.polytech.al.kafka;
//
//
//import fr.unice.polytech.al.model.Announcement;
//import fr.unice.polytech.al.repository.TrackingRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.support.Acknowledgment;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Random;
//
//import static fr.unice.polytech.al.State.STARTED;
//
//@Component
//public class KafkaListener {
//
//    @Autowired
//    private TrackingRepository repository;
//
//    private Random rand = new Random();
//
//
//
//    public KafkaListener() {
//    }
//
//
//    // where string ids has format:  IdAnnonceGood;IdAnnoceCourse
//    //
//    @org.springframework.kafka.annotation.KafkaListener(topics = "announcement_matched")
//    public void getIdsOfGoodAndCarAnnouncement(String ids, Acknowledgment acknowledgment) {
//        String [] stringArray = ids.split(";");
//        Long IdAnnonceGood = Long.parseLong(stringArray[0]);
//        Long IdAnnoceCourse = Long.parseLong(stringArray[1]);
//        repository.findById(IdAnnonceGood).get().setStatusDriver(IdAnnoceCourse, "STARTED");
//    }
//
//}
//
