package fr.unice.polytech.al.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MatchingController
{
    @GetMapping("/match/{idAnnonce}")
    public String match(@PathVariable String idAnnonce) {
        return idAnnonce;
    }
}
