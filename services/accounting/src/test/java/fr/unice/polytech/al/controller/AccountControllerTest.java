package fr.unice.polytech.al.controller;

import fr.unice.polytech.al.repository.AccountRepository;
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
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository repository;

    @Before
    public void setUp() throws Exception {
        repository.deleteAll(); // clean database
    }

    @Test
    public void create() throws Exception {
        mockMvc.perform(post("/accounts")
                .content("{\"email\":\"alexis.couvreur@etu.unice.fr\", \"username\":\"AlexLeBoss\", \"firstName\":\"Alexis\", \"lastName\":\"Couvreur\"}").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email")      .value("alexis.couvreur@etu.unice.fr"))
                .andExpect(jsonPath("$.username")   .value("AlexLeBoss"))
                .andExpect(jsonPath("$.firstName")  .value("Alexis"))
                .andExpect(jsonPath("$.lastName")   .value("Couvreur"));
    }

    @Test
    public void find() throws Exception {

        mockMvc.perform(post("/accounts")
                .content("{\"email\":\"alexis.couvreur@etu.unice.fr\", \"username\":\"AlexLeBoss\", \"firstName\":\"Alexis\", \"lastName\":\"Couvreur\"}").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        mockMvc.perform(get("/accounts/AlexLeBoss"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email")      .value("alexis.couvreur@etu.unice.fr"))
                .andExpect(jsonPath("$.username")   .value("AlexLeBoss"))
                .andExpect(jsonPath("$.firstName")  .value("Alexis"))
                .andExpect(jsonPath("$.lastName")   .value("Couvreur"));
    }

    @Test
    public void findAll() throws Exception {

        mockMvc.perform(post("/accounts")
                .content("{\"email\":\"alexis.couvreur@etu.unice.fr\", \"username\":\"AlexLeBoss\", \"firstName\":\"Alexis\", \"lastName\":\"Couvreur\"}").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        mockMvc.perform(post("/accounts")
                .content("{\"email\":\"jean.didier@etu.unice.fr\", \"username\":\"JDLeBoss\", \"firstName\":\"Jean\", \"lastName\":\"Didier\"}").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        mockMvc.perform(get("/accounts"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.accounts", hasSize(2)));
    }
}