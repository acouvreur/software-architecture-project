package fr.unice.polytech.al.controller;

import fr.unice.polytech.al.assembler.AccountResourceAssembler;
import fr.unice.polytech.al.model.Account;
import fr.unice.polytech.al.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class AccountController {

    private AccountRepository repository;
    private AccountResourceAssembler assembler;

    @Autowired
    public AccountController(AccountRepository repository, AccountResourceAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/accounts")
    public Resources<Resource<Account>> findAll() {
        return new Resources<>(
                repository.findAll().stream()
                .map(assembler::toResource)
                .collect(Collectors.toList()),
                linkTo(methodOn(AccountController.class).findAll()).withSelfRel()
        );
    }
}