package fr.unice.polytech.al.controller;

import fr.unice.polytech.al.assembler.CourseResourceAssembler;
import fr.unice.polytech.al.repository.CourseRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseController {

    private CourseRepository repository;
    private CourseResourceAssembler assembler;
}
