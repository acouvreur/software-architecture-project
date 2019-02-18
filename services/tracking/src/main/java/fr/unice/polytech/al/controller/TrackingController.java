package fr.unice.polytech.al.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.al.State;
import fr.unice.polytech.al.assembler.TrackingResourceAssembler;
import fr.unice.polytech.al.kafka.ChaosBroker;
import fr.unice.polytech.al.kafka.KafkaHelperClass;
import fr.unice.polytech.al.model.Announcement;
import fr.unice.polytech.al.repository.TrackingRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import static fr.unice.polytech.al.State.CONFIRMED;
import static fr.unice.polytech.al.State.DELIVERED;

@RestController
public class TrackingController {

    private TrackingRepository repository;
    private TrackingResourceAssembler assembler;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ChaosBroker chaosBroker;

    private final Logger logger = Logger.getLogger(this.getClass());


    @Autowired
    public TrackingController(TrackingRepository repository, TrackingResourceAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }


    //retrive the informations about tracking(in form of Announcement object) :
    @GetMapping(value = "/tracking/{idGoodAnnouncement}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Announcement> getTrackingInformations(@PathVariable Long idGoodAnnouncement) {
        Announcement a = repository.findById( idGoodAnnouncement ).get();
        logger.info("CHECKING THE STATUS OF THE ANNOUNCEMENT : " );
        return new ResponseEntity<Announcement>( a, HttpStatus.OK );
    }

    @PostMapping(value = "/tracking", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Announcement> ChangeTrackingStatus(@RequestBody Announcement announcement) {
        repository.save(announcement);
        logger.info("TRACKING CREATED");
        return new ResponseEntity<Announcement>( announcement, HttpStatus.OK );
    }



    /*@PostMapping(value = "/announcements",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Resource<Announcement>> create(@RequestBody Announcement announcement) throws JsonProcessingException {

        repository.save(announcement);

        // Send message to matching service
        ObjectMapper mapper = new ObjectMapper();
        kafkaSender.send("announcement_created",  mapper.writeValueAsString(announcement));

        return ResponseEntity.created(
                linkTo(methodOn(AnnouncementController.class).find(announcement.getId())).toUri())
                .body(assembler.toResource(announcement));
    }*/


    @PatchMapping(value = "/tracking/{idGoodAnnouncement}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Announcement> ChangeTrackingStatus(@PathVariable Long idGoodAnnouncement, @RequestBody String state) throws JsonProcessingException, InterruptedException {
        Announcement a = repository.findById( idGoodAnnouncement ).get();
        State stateAnnouncement = DELIVERED;
        try {
            stateAnnouncement = State.valueOf(state);
            //System.out.println("patching tracking -> state fine");
        } catch (Error e) {

        }
        a.setState(stateAnnouncement);
        logger.info("CHANGING STATE OF TRACKING TO : " + state);

        repository.save(a);

        //KAFKA --> BILLING
        if (stateAnnouncement.equals(CONFIRMED)) {
            Long idGood = a.getIdGoodAnnouncement();
            Long driverId = a.getIdDriverAnnouncement();
            KafkaHelperClass data = new KafkaHelperClass(idGood,driverId);

            //String data = idGood + ";" + driverId;
            logger.info("TRACKING IS FINISHED");
            logger.info("SENDING MESSAGE TO BILLING SERVICE TO PERFORM EXCHANGE OF POINTS BETWEEN DRIVER AND STUDENT");
            chaosBroker.broke("tracking-finished", data, kafkaTemplate);
            //kafkaTemplate.send("tracking-finished", mapper.writeValueAsString(data));
        }


        return new ResponseEntity<Announcement>( a, HttpStatus.OK );
    }







}
