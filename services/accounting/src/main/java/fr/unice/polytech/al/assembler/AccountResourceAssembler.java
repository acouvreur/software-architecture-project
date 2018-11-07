package fr.unice.polytech.al.assembler;

import fr.unice.polytech.al.controller.AccountController;
import fr.unice.polytech.al.model.Account;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class AccountResourceAssembler implements ResourceAssembler<Account, Resource<Account>> {

    @Override
    public Resource<Account> toResource(Account entity) {
        return new Resource<>(entity,
                linkTo(methodOn(AccountController.class).find(entity.getUsername())).withSelfRel(),
                linkTo(methodOn(AccountController.class).findAll()).withRel("accounts")
        );
    }
}

