package fr.unice.polytech.al.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.al.assembler.AccountResourceAssembler;
import fr.unice.polytech.al.kafka.ChaosBroker;
import fr.unice.polytech.al.model.Account;
import fr.unice.polytech.al.repository.AccountRepository;
import org.apache.log4j.Logger;
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
import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin
public class AccountController {

    private AccountRepository repository;
    private AccountResourceAssembler assembler;
    private final Logger logger = Logger.getLogger(this.getClass());


    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ChaosBroker chaosBroker;

    @Autowired
    public AccountController(AccountRepository repository, AccountResourceAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
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
    public ResponseEntity<Resource<Account>> create(@RequestBody Account account) throws JsonProcessingException, InterruptedException {
        //KAFKA -> BILLING

        repository.save(account);

        logger.info("OBJECT ACCOUNT WITH ID " + account.getId() + " AND EMAIL " + account.getEmail() +  " CREATED");

        ObjectMapper mapper = new ObjectMapper();
        System.out.println("SENDING MESSAGE TO SERVICE BILLING WITH TOPIC ACCOUNT_CREATED (TO CREATE OBJECT BILLING ASSOCIATED WITH THIS ACCOUNT)" );
        //kafkaTemplate.send("account_created", String.valueOf(account.getId()));

        chaosBroker.broke( "account_created", account, kafkaTemplate);


        /*return ResponseEntity
                        .created(linkTo(methodOn(AccountController.class).find(account.getUsername())).toUri())
                .body(assembler.toResource(account));*/

        return ResponseEntity.created(
                linkTo(methodOn(AccountController.class).find(account.getUsername())).toUri())
                .body(assembler.toResource(account));
    }

    @DeleteMapping(value = "/accounts", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JSONObject deleteAll() {
        repository.deleteAll();
        return new JSONObject().put("allAccountDeleted", true);
    }
}
