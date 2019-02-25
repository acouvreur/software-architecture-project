package fr.unice.polytech.al.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.unice.polytech.al.kafka.ChaosBroker;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.IOException;

// TODO: change to not rest
@RestController
public class ChaosController {

    @Autowired
    private ChaosBroker broker;

    private final Logger logger = Logger.getLogger(this.getClass());

    @CrossOrigin
    @GetMapping(value = "/broker", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ObjectNode> getBrokerValues() {
        logger.info("Get request for broker");
        return new ResponseEntity<ObjectNode>(broker.toJson(), HttpStatus.OK);
    }

    @CrossOrigin
    @PatchMapping(value = "/broker", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ObjectNode> patchBroker(@RequestBody String jsonFormat) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode dataJson = objectMapper.readTree(jsonFormat);

        broker.setpDuplicate(dataJson.get("pDuplicate").asDouble());
        broker.setpDelete(dataJson.get("pDelete").asDouble());
        broker.setpNothing(dataJson.get("pNothing").asDouble());
        broker.setpSalt(dataJson.get("pSalt").asDouble());
        broker.setpSlow(dataJson.get("pSlow").asDouble());

        logger.info("Patch request for broker");
        return new ResponseEntity<ObjectNode>(broker.toJson(), HttpStatus.OK);
    }
}
