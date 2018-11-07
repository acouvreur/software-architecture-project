package fr.unice.polytech.al.controller;

import fr.unice.polytech.al.assembler.AccountResourceAssembler;
import fr.unice.polytech.al.model.Account;
import fr.unice.polytech.al.repository.AccountRepository;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class AccountController {

    private AccountRepository repository;
    private AccountResourceAssembler assembler;
    private KafkaTemplate<String, Account> kafkaTemplate;

    @Autowired
    public AccountController(AccountRepository repository, AccountResourceAssembler assembler, KafkaTemplate<String, Account> kafkaTemplate) {
        this.repository = repository;
        this.assembler = assembler;
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping(value = "/accounts", 
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Resources<Resource<Account>> findAll() {
        return new Resources<>(
                repository.findAll().stream()
                .map(assembler::toResource)
                .collect(Collectors.toList()),
                linkTo(methodOn(AccountController.class).findAll()).withSelfRel()
        );
    }

    @GetMapping(value = "/accounts/{username}", 
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Resource<Account> find(@PathVariable String username) {
        return assembler.toResource(
                repository.findByUsername(username)
                        .orElseThrow(() -> new EntityNotFoundException(username))
        );
    }

    @PostMapping(value = "/accounts",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Resource<Account>> create(@RequestBody Account account) {

        repository.save(account);

        kafkaTemplate.send("account_created", account);

        return ResponseEntity
                        .created(linkTo(methodOn(AccountController.class).find(account.getUsername())).toUri())
                .body(assembler.toResource(account));
    }
}
