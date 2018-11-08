package fr.unice.polytech.al.controller;

import com.jayway.jsonpath.JsonPath;
import fr.unice.polytech.al.repository.CourseRepository;
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
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CourseRepository repository;

    @Before
    public void setUp() throws Exception {
        repository.deleteAll(); // clean database
    }

    @Test
    public void create() throws Exception {
        mockMvc.perform(post("/courses")
                .content("{\"idClient\":1234, \"idDriver\":666, \"idAnnouncement\":9898, \"idNextCourse\":23," +
                        " \"startPoint\":\"Nice\", \"endPoint\":\"Marseille\", \"startDate\":\"2018-11-01\", \"endDate\":\"2018-11-02\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idClient")      .value(1234))
                .andExpect(jsonPath("$.idDriver")   .value(666))
                .andExpect(jsonPath("$.idAnnouncement")  .value(9898))
                .andExpect(jsonPath("$.idNextCourse")   .value(23))
                .andExpect(jsonPath("$.startPoint")   .value("Nice"))
                .andExpect(jsonPath("$.endPoint")   .value("Marseille"))
                .andExpect(jsonPath("$.startDate")   .value("2018-11-01"))
                .andExpect(jsonPath("$.endDate")   .value("2018-11-02"));
    }

    @Test
    public void createNullNextCourse() throws Exception {
        mockMvc.perform(post("/courses")
                .content("{\"idClient\":1234, \"idDriver\":666, \"idAnnouncement\":9898," +
                        " \"startPoint\":\"Nice\", \"endPoint\":\"Marseille\", \"startDate\":\"2018-11-01\", \"endDate\":\"2018-11-02\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idClient")      .value(1234))
                .andExpect(jsonPath("$.idDriver")   .value(666))
                .andExpect(jsonPath("$.idAnnouncement")  .value(9898))
                .andExpect(jsonPath("$.idNextCourse").isEmpty())
                .andExpect(jsonPath("$.startPoint")   .value("Nice"))
                .andExpect(jsonPath("$.endPoint")   .value("Marseille"))
                .andExpect(jsonPath("$.startDate")   .value("2018-11-01"))
                .andExpect(jsonPath("$.endDate")   .value("2018-11-02"));
    }

    @Test
    public void findAll() throws Exception {

        mockMvc.perform(post("/courses")
                .content("{\"idClient\":1234, \"idDriver\":666, \"idAnnouncement\":9898, \"idNextCourse\":23," +
                        " \"startPoint\":\"Nice\", \"endPoint\":\"Marseille\", \"startDate\":\"2018-11-01\", \"endDate\":\"2018-11-02\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        mockMvc.perform(post("/courses")
                .content("{\"idClient\":1234, \"idDriver\":666, \"idAnnouncement\":9898, \"idNextCourse\":23," +
                        " \"startPoint\":\"Marseille\", \"endPoint\":\"Paris\", \"startDate\":\"2018-11-01\", \"endDate\":\"2018-11-02\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        mockMvc.perform(get("/courses"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.courses", hasSize(2)));
    }

    @Test
    public void findById() throws Exception {
        MvcResult res = mockMvc.perform(post("/courses")
                .content("{\"idClient\":1234, \"idDriver\":666, \"idAnnouncement\":9898, \"idNextCourse\":23," +
                        " \"startPoint\":\"Nice\", \"endPoint\":\"Marseille\", \"startDate\":\"2018-11-01\", \"endDate\":\"2018-11-02\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        String id = JsonPath.parse(res.getResponse().getContentAsString()).read("$.id").toString();
        mockMvc.perform(get("/courses/" + id))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void findByIdAnnouncement() throws Exception {
        mockMvc.perform(post("/courses")
                .content("{\"idClient\":1234, \"idDriver\":666, \"idAnnouncement\":9898, \"idNextCourse\":23," +
                        " \"startPoint\":\"Nice\", \"endPoint\":\"Marseille\", \"startDate\":\"2018-11-01\", \"endDate\":\"2018-11-02\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        mockMvc.perform(post("/courses")
                .content("{\"idClient\":1234, \"idDriver\":666, \"idAnnouncement\":9878, \"idNextCourse\":23," +
                        " \"startPoint\":\"Marseille\", \"endPoint\":\"Paris\", \"startDate\":\"2018-11-01\", \"endDate\":\"2018-11-02\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        mockMvc.perform(post("/courses")
                .content("{\"idClient\":1234, \"idDriver\":666, \"idAnnouncement\":9898, \"idNextCourse\":23," +
                        " \"startPoint\":\"Marseille\", \"endPoint\":\"Paris\", \"startDate\":\"2018-11-01\", \"endDate\":\"2018-11-02\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
        mockMvc.perform(get("/courses?announcement=9898"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.courses", hasSize(2)));
    }

}