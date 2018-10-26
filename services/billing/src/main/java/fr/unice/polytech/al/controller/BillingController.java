package fr.unice.polytech.al.controller;

import fr.unice.polytech.al.assembler.BillingResourceAssembler;
import fr.unice.polytech.al.model.Billing;
import fr.unice.polytech.al.repository.BillingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;

import java.util.Random;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


@RestController
public class BillingController {

    private BillingRepository repository;
    private BillingResourceAssembler assembler;
    private Random rand = new Random();

    @Autowired
    public BillingController(BillingRepository repository, BillingResourceAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/billing")
    public Resources<Resource<Billing>> findAll() {
        return new Resources<>(
                repository.findAll().stream()
                        .map(assembler::toResource)
                        .collect(Collectors.toList()),
                linkTo(methodOn( BillingController.class).findAll()).withSelfRel()
        );
    }

    @GetMapping("/billing/{clientId}")
    public Resource<Billing> findOne(@PathVariable long clientId) {
        return new Resource(repository.findById(clientId).get());
    }

    @PostMapping("/billing/{clientId}")
    public Billing newBilling(@RequestBody Billing billing, @PathVariable long clientId) {
        billing.setClientId( clientId );
        billing.setPoints( (int) (rand.nextInt(100) + 50) );
        return repository.save( billing );
    }


    @PatchMapping ("/billing/{clientId}/balance")
    public Billing withdrawPointFromClientWithId(@PathVariable long clientId) {
        Billing billingTmp = repository.findById(clientId).get();
        billingTmp.setPoints(rand.nextInt(100) + 10);
        return repository.save(billingTmp);
    }




}
