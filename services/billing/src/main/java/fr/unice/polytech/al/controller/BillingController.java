package fr.unice.polytech.al.controller;

import fr.unice.polytech.al.assembler.BillingResourceAssembler;
import fr.unice.polytech.al.model.Billing;
import fr.unice.polytech.al.repository.BillingRepository;
import fr.unice.polytech.al.service.BillingService;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


@RestController
public class BillingController {

    private BillingRepository repository;
    private BillingResourceAssembler assembler;
    private BillingService service;
    private Random rand = new Random();

    @Autowired
    public BillingController(BillingRepository repository, BillingResourceAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }



    /*@GetMapping("/billing")
    public Resources<Resource<Billing>> findAll() {
        return new Resources<>(
                repository.findAll().stream()
                        .map(assembler::toResource)
                        .collect(Collectors.toList()),
                linkTo(methodOn( BillingController.class).findAll()).withSelfRel());

    }*/

    @GetMapping("/billing")
    public ResponseEntity<List<Billing>> findAll() {
        return new ResponseEntity<List<Billing>>(
                repository.findAll().stream()
                        .collect(Collectors.toList()), HttpStatus.OK);

    }


    @GetMapping("/billing/{clientId}")
    public ResponseEntity<Billing> findOne(@PathVariable long clientId) {
        Billing billing1 = repository.findById(clientId).get();
        return new ResponseEntity<Billing>(billing1, HttpStatus.OK);
    }

    @PostMapping ("/billing/{clientId}")
    public ResponseEntity<Billing> createOne(@PathVariable long clientId) {
        Billing billing1 = new Billing(clientId,200);
        repository.save(billing1);
        return new ResponseEntity<Billing>(billing1, HttpStatus.OK);
    }


    @PatchMapping ("/billing/{clientId}/balance")
    public ResponseEntity<Billing> withdrawPointFromClientWithId(@PathVariable long clientId) {
        Billing billingTmp = service.setNewBallanceForClient(clientId);
        return new ResponseEntity<Billing>(billingTmp, HttpStatus.OK);
    }


    /*public ResponseEntity<Resource<Account>> create(@RequestBody Account account) {

        return ResponseEntity
                .created(linkTo(methodOn(AccountController.class).find(account.getUsername())).toUri())
            .body(assembler.toResource(account));

*/

}
