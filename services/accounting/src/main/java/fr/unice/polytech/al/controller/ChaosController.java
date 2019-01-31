package fr.unice.polytech.al.controller;

import fr.unice.polytech.al.kafka.ChaosBroker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// TODO: change to not rest
@RestController
public class ChaosController {

    @Autowired
    private ChaosBroker broker;

    @PatchMapping(value = "/broker/slowdown/percentage", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Resource<ChaosBroker>> changeSlow(@RequestBody double percentage) {
        broker.setpSlow(percentage);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/broker/delete/percentage", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Resource<ChaosBroker>> changeDelete(@RequestBody double percentage) {
        broker.setpDelete(percentage);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/broker/duplicate/percentage", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Resource<ChaosBroker>> changeDuplicate(@RequestBody double percentage) {
        broker.setpDuplicate(percentage);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/broker/salt/percentage", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Resource<ChaosBroker>> changeSalt(@RequestBody double percentage) {
        broker.setpSalt(percentage);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/broker/nothing/percentage", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Resource<ChaosBroker>> changeNothing(@RequestBody double percentage) {
        broker.setpNothing(percentage);
        return ResponseEntity.ok().build();
    }
}
