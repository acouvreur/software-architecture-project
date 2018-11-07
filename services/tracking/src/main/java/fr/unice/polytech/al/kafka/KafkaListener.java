package fr.unice.polytech.al.kafka;


import fr.unice.polytech.al.model.Course;
import fr.unice.polytech.al.model.Tracking;
import fr.unice.polytech.al.repository.TrackingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class KafkaListener {

    @Autowired
    private TrackingRepository repository;

    @Autowired
    private KafkaSender sender;
    private Random rand = new Random();



    public KafkaListener() {
    }


    @org.springframework.kafka.annotation.KafkaListener(topics = "course-creation")
    public void getNewCourseToTrack(List<Course> courses, Acknowledgment acknowledgment) {
        Tracking tracking = new Tracking();
        tracking.setCourses(courses);
        tracking.setClientId(courses.get(0).getId());
        repository.save(tracking);
    }

    /*@org.springframework.kafka.annotation.KafkaListener(topics = "billing-balance-modifications")
    public void getBalanceAfterCourse(int billing, Acknowledgment acknowledgment) {
        System.out.print(billing);
    }*/


}

