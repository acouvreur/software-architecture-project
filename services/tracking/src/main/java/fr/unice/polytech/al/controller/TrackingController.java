package fr.unice.polytech.al.controller;

import fr.unice.polytech.al.assembler.TrackingResourceAssembler;
import fr.unice.polytech.al.model.Course;
import fr.unice.polytech.al.model.Tracking;
import fr.unice.polytech.al.repository.TrackingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class TrackingController {


        private TrackingRepository repository;
        private TrackingResourceAssembler assembler;

        @Autowired
        public TrackingController(TrackingRepository repository, TrackingResourceAssembler assembler) {
            this.repository = repository;
            this.assembler = assembler;
        }

        /*@GetMapping(value = "/tracking/{idCourse}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
        public ResponseEntity<Course> getLocation(@PathVariable Long idCourse) {
            Tracking tr = repository.findById.get();
            Course course= (Course) tr.getCourses().stream().filter( x -> x.getState().equals("INPROGRESS"));
            return new ResponseEntity<Course>(course, HttpStatus.OK);
        }*/


    @GetMapping(value = "/tracking/history/{idCourse}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Tracking> getTrackingHistory(@PathVariable Long clientId) {
        Tracking tr = repository.findById(clientId).get();
        return new ResponseEntity<Tracking>(tr, HttpStatus.OK);
    }



}
