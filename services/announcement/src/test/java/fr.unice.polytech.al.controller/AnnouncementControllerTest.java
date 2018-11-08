package fr.unice.polytech.al.controller;

import com.jayway.jsonpath.JsonPath;
import fr.unice.polytech.al.repository.AnnouncementRepository;
import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AnnouncementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AnnouncementRepository repository;

    @Before
    public void setUp() throws Exception {
        repository.deleteAll(); // clean database
    }

    @Test
    public void create() throws Exception {
        mockMvc.perform(post("/announcements")
                .content("{\"idTransmitter\":1234, \"nameTransmitter\":\"Jacky\", \"startPoint\":\"Nice\", \"endPoint\":\"Marseille\","
                        + " \"startDate\":\"2018-11-01\", \"endDate\":\"2018-11-02\", \"type\":\"GOOD\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idTransmitter")      .value(1234))
                .andExpect(jsonPath("$.nameTransmitter")   .value("Jacky"))
                .andExpect(jsonPath("$.startPoint")   .value("Nice"))
                .andExpect(jsonPath("$.endPoint")   .value("Marseille"))
                .andExpect(jsonPath("$.startDate")   .value("2018-11-01"))
                .andExpect(jsonPath("$.endDate")   .value("2018-11-02"))
                .andExpect(jsonPath("$.type")   .value("GOOD"));
    }

    @Test
    public void findByIdTransmitter() throws Exception {
        mockMvc.perform(post("/announcements")
                .content("{\"idTransmitter\":1234, \"nameTransmitter\":\"Jacky\", \"startPoint\":\"Nice\", \"endPoint\":\"Marseille\","
                        + " \"startDate\":\"2018-11-01\", \"endDate\":\"2018-11-02\", \"type\":\"GOOD\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idTransmitter")      .value(1234))
                .andExpect(jsonPath("$.nameTransmitter")   .value("Jacky"))
                .andExpect(jsonPath("$.startPoint")   .value("Nice"))
                .andExpect(jsonPath("$.endPoint")   .value("Marseille"))
                .andExpect(jsonPath("$.startDate")   .value("2018-11-01"))
                .andExpect(jsonPath("$.endDate")   .value("2018-11-02"))
                .andExpect(jsonPath("$.type")   .value("GOOD"));
        mockMvc.perform(post("/announcements")
                .content("{\"idTransmitter\":34, \"nameTransmitter\":\"Jacky\", \"startPoint\":\"Nice\", \"endPoint\":\"Marseille\","
                        + " \"startDate\":\"2018-11-01\", \"endDate\":\"2018-11-02\", \"type\":\"GOOD\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idTransmitter")      .value(34))
                .andExpect(jsonPath("$.nameTransmitter")   .value("Jacky"))
                .andExpect(jsonPath("$.startPoint")   .value("Nice"))
                .andExpect(jsonPath("$.endPoint")   .value("Marseille"))
                .andExpect(jsonPath("$.startDate")   .value("2018-11-01"))
                .andExpect(jsonPath("$.endDate")   .value("2018-11-02"))
                .andExpect(jsonPath("$.type")   .value("GOOD"));
        mockMvc.perform(get("/announcements?transmitter=1234"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.announcements", hasSize(1)));
    }

}