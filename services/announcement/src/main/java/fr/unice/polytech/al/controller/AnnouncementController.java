package fr.unice.polytech.al.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.unice.polytech.al.assembler.AnnouncementResourceAssembler;
import fr.unice.polytech.al.kafka.AnnouncementKafkaSender;
import fr.unice.polytech.al.model.Announcement;
import fr.unice.polytech.al.repository.AnnouncementRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Controller
public class AnnouncementController {

    @Autowired
    AnnouncementKafkaSender kafkaSender;

    private AnnouncementRepository repository;
    private AnnouncementResourceAssembler assembler;

    @Autowired
    public AnnouncementController(AnnouncementRepository repository, AnnouncementResourceAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /*
    @GetMapping(value = "/announcements", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Resource<Announcement> findAll(
            @RequestParam(value = "transmitter", required = false) Long idTransmitter) {
        return assembler.toResource(
                (Announcement) repository.findAll());
    }
    */

    @GetMapping(path="/announcements")
    public @ResponseBody Iterable<Announcement> findAll() {
        // This returns a JSON or XML with the users
        return repository.findAll();
    }

    @GetMapping(value = "/announcements/{id}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Resource<Announcement> find(@PathVariable Long id) {
        return assembler.toResource(
                repository.findById(id)
                        .orElseThrow(EntityNotFoundException::new)
        );
    }

    @PostMapping(value = "/announcements",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Resource<Announcement>> create(@RequestBody Announcement announcement) throws JsonProcessingException, InterruptedException {
        announcement.setIdAnnouncementMatched(-1L);
        repository.save(announcement);

        //KAFKA -> MATCHING
        // Send message to matching service
        kafkaSender.send("announcement_created", announcement);
        System.out.println("announcement_created -> matching .... " );

        return ResponseEntity.created(
                linkTo(methodOn(AnnouncementController.class).find(announcement.getId())).toUri())
                .body(assembler.toResource(announcement));
    }

    @DeleteMapping(value = "/announcements", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JSONObject deleteAll() {
        repository.deleteAll();
        return new JSONObject().put("allAnnouncementDeleted", true);
    }
}
