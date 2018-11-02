package fr.unice.polytech.al.controller;

import fr.unice.polytech.al.assembler.CourseResourceAssembler;
import fr.unice.polytech.al.model.Course;
import fr.unice.polytech.al.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class CourseController {

    private CourseRepository repository;
    private CourseResourceAssembler assembler;

    @Autowired
    public CourseController(CourseRepository repository, CourseResourceAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping(value = "/courses", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Resources<Resource<Course>> findAll(
            @RequestParam(value = "announcement", required = false) Long idAnnouncement) {
        return new Resources<>(
                repository.findAll().stream().filter(c -> idAnnouncement == null || c.getIdAnnouncement().equals(idAnnouncement))
                        .map(assembler::toResource)
                        .collect(Collectors.toList()),
                linkTo(methodOn(CourseController.class).findAll(null)).withSelfRel()
        );
    }

    @GetMapping(value = "/courses/{idCourse}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Resource<Course> find(@PathVariable Long idCourse) {
        return assembler.toResource(
                repository.findById(idCourse)
                        .orElseThrow(() -> new EntityNotFoundException(idCourse.toString()))
        );
    }


    @PostMapping(value = "/courses", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Resource<Course>> create(@RequestBody Course course) {

        repository.save(course);

        return ResponseEntity
                .created(linkTo(methodOn(CourseController.class).find(course.getId())).toUri())
                .body(assembler.toResource(course));
    }

}
