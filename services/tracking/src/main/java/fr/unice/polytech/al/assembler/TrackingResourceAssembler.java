package fr.unice.polytech.al.assembler;

import fr.unice.polytech.al.model.Tracking;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
public class TrackingResourceAssembler implements ResourceAssembler<Tracking, Resource<Tracking>> {

    @Override
    public Resource<Tracking> toResource(Tracking entity) {
        return new Resource<>(entity);
    }
}
