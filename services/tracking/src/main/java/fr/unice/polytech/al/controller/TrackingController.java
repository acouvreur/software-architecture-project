package fr.unice.polytech.al.controller;

import fr.unice.polytech.al.State;
import fr.unice.polytech.al.assembler.TrackingResourceAssembler;
import fr.unice.polytech.al.model.Announcement;
import fr.unice.polytech.al.repository.TrackingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
public class TrackingController {


        private TrackingRepository repository;
        private TrackingResourceAssembler assembler;

        @Autowired
        public TrackingController(TrackingRepository repository, TrackingResourceAssembler assembler) {
            this.repository = repository;
            this.assembler = assembler;
        }


        @GetMapping(value = "/tracking/history/{idDriverAnnouncement}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
        public ResponseEntity<Announcement> getTrackingInformations(@PathVariable Long idDriverAnnouncement) {
            Optional<Announcement> an = repository.findAll().stream().findFirst().filter( x -> x.ifExistsDriverAnnouncementStatus(idDriverAnnouncement));
            Announcement a = an.orElse(null);
            return new ResponseEntity<Announcement>(a, HttpStatus.OK);
        }



        @PatchMapping(value = "/tracking/history/{idDriverAnnouncement}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
        public ResponseEntity<Announcement> ChangeTrackingStatus(@PathVariable Long idDriverAnnouncement, @RequestBody State state) {
            Optional<Announcement> an = repository.findAll().stream().findFirst().filter( x -> x.ifExistsDriverAnnouncementStatus(idDriverAnnouncement));
            an.get().setStatusDriver(idDriverAnnouncement,state);
            Announcement a = an.orElse(null);
            return new ResponseEntity<Announcement>(a, HttpStatus.OK);
        }



}
