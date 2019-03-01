package fr.unice.polytech.al.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.al.assembler.BillingResourceAssembler;
import fr.unice.polytech.al.model.Billing;
import fr.unice.polytech.al.model.Course;
import fr.unice.polytech.al.repository.BillingRepository;
import fr.unice.polytech.al.service.BillingService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.CrossOrigin;


@Controller
@CrossOrigin
public class BillingEstimationController {


        private BillingService service;





    @Autowired
        public BillingEstimationController(BillingService service) {
            this.service = service;
        }


        @PostMapping("/billing/estimate")
        public ResponseEntity<Integer> estimate(@RequestBody String listCourses) throws IOException {
            ObjectMapper mapper = new ObjectMapper();
            Course[] arrayCourses = mapper.readValue(listCourses, Course[].class);

            int res = service.estimateBilling(arrayCourses);
            return new ResponseEntity<Integer>(res, HttpStatus.OK);
        }

}
