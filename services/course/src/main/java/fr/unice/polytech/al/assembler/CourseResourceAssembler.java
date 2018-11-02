package fr.unice.polytech.al.assembler;

import fr.unice.polytech.al.controller.CourseController;
import fr.unice.polytech.al.model.Course;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class CourseResourceAssembler implements ResourceAssembler<Course, Resource<Course>> {

    @Override
    public Resource<Course> toResource(Course entity) {
        return new Resource<>(entity,
                linkTo(methodOn(CourseController.class).find(entity.getId())).withSelfRel(),
                linkTo(methodOn(CourseController.class).findAll(null)).withRel("courses")
        );
    }
}
