package fr.unice.polytech.al.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.al.assembler.AnnouncementResourceAssembler;
import fr.unice.polytech.al.kafka.AnnouncementKafkaSender;
import fr.unice.polytech.al.model.Announcement;
import fr.unice.polytech.al.repository.AnnouncementRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
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

    @GetMapping(value = "/announcements", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Resources<Resource<Announcement>> findAll(
            @RequestParam(value = "transmitter", required = false) Long idTransmitter) {
        return new Resources<>(
                repository.findAll().stream().filter(c -> idTransmitter == null || c.getIdTransmitter() == idTransmitter)
                        .map(assembler::toResource)
                        .collect(Collectors.toList()),
                linkTo(methodOn(AnnouncementController.class).findAll(null)).withSelfRel()
        );
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
    public ResponseEntity<Resource<Announcement>> create(@RequestBody Announcement announcement) throws JsonProcessingException {

        repository.save(announcement);

        //KAFKA -> MATCHING
        // Send message to matching service
        ObjectMapper mapper = new ObjectMapper();
        kafkaSender.send("announcement_created",  mapper.writeValueAsString(announcement));
        System.out.println("announcement_created -> matching .... " );

        /*return ResponseEntity.created(
                linkTo(methodOn(AnnouncementController.class).find(announcement.getId())).toUri())
                .body(assembler.toResource(announcement));*/

        return ResponseEntity
                .created(linkTo(methodOn(AnnouncementController.class).find(announcement.getId())).toUri())
                .body(assembler.toResource(announcement));
    }

    @DeleteMapping(value = "/announcements", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JSONObject deleteAll() {
        repository.deleteAll();
        return new JSONObject().put("allAnnouncementDeleted", true);
    }
}
