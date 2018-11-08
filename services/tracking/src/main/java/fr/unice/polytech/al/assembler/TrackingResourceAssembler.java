package fr.unice.polytech.al.assembler;

import fr.unice.polytech.al.model.Announcement;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
public class TrackingResourceAssembler implements ResourceAssembler<Announcement, Resource<Announcement>> {

    @Override
    public Resource<Announcement> toResource(Announcement entity) {
        return new Resource<>(entity);
    }
}
