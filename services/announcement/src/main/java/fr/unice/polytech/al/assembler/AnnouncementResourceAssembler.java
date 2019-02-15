package fr.unice.polytech.al.assembler;

import fr.unice.polytech.al.controller.AnnouncementController;
import fr.unice.polytech.al.model.Announcement;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class AnnouncementResourceAssembler implements ResourceAssembler<Announcement, Resource<Announcement>> {

    @Override
    public Resource<Announcement> toResource(Announcement entity) {
        return new Resource<>(entity,
                linkTo(methodOn(AnnouncementController.class).find(entity.getId())).withSelfRel(),
                linkTo(methodOn(AnnouncementController.class).findAll(null)).withRel("announcements")
        );
    }
}