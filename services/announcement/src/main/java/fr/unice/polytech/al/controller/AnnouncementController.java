package fr.unice.polytech.al.controller;

import fr.unice.polytech.al.assembler.AnnouncementResourceAssembler;
import fr.unice.polytech.al.kafka.AnnouncementKafkaSender;
import fr.unice.polytech.al.model.Announcement;
import fr.unice.polytech.al.repository.AnnouncementRepository;
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

    @GetMapping(value = "/announcements",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Resources<Resource<Announcement>> findAll() {
        return new Resources<Resource<Announcement>>(
                repository.findAll().stream()
                        .map(assembler::toResource)
                        .collect(Collectors.toList()),
                linkTo(methodOn(AnnouncementController.class).findAll()).withSelfRel()
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
    public ResponseEntity<Resource<Announcement>> create(@RequestBody Announcement announcement) {

        repository.save(announcement);

        // Send message to matching service
        kafkaSender.send("announcement_created", announcement.getId().toString());

        return ResponseEntity.created(
                linkTo(methodOn(AnnouncementController.class).find(announcement.getId())).toUri())
                .body(assembler.toResource(announcement));
    }
}
