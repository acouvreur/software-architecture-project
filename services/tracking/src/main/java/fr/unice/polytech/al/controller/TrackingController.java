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


        //retrive the informations about tracking(in form of Announcement object) :
        @GetMapping(value = "/tracking/{idGoodAnnouncement}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
        public ResponseEntity<Announcement> getTrackingInformations(@PathVariable Long idGoodAnnouncement) {
            Announcement a  = repository.findById(idGoodAnnouncement).get();
            return new ResponseEntity<Announcement>(a, HttpStatus.OK);
        }



        @PatchMapping(value = "/tracking/{idGoodAnnouncement}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
        public ResponseEntity<Announcement> ChangeTrackingStatus(@PathVariable Long idGoodAnnouncement, @RequestBody State state, @RequestBody Long idCourseAnnouncement) {
            Announcement a  = repository.findById(idGoodAnnouncement).get();
            a.setStatusDriver(idCourseAnnouncement,state);
            repository.save(a);
            return new ResponseEntity<Announcement>(a, HttpStatus.OK);
        }

        @PostMapping(value = "/tracking", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
        public ResponseEntity<Announcement> ChangeTrackingStatus(@RequestBody Announcement announcement) {
            repository.save(announcement);
            return new ResponseEntity<Announcement>(announcement, HttpStatus.OK);
        }

    @PostMapping(value = "/tracking", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Integer> ChangeTrackingStatus2(@RequestBody Integer bla) {
        return new ResponseEntity<Integer>(bla, HttpStatus.OK);
    }


}
