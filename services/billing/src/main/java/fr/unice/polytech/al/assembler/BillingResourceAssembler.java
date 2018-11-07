package fr.unice.polytech.al.assembler;

import fr.unice.polytech.al.model.Billing;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
public class BillingResourceAssembler implements ResourceAssembler<Billing, Resource<Billing>> {

    @Override
    public Resource<Billing> toResource(Billing entity) {
        return new Resource<>(entity);
    }
}
