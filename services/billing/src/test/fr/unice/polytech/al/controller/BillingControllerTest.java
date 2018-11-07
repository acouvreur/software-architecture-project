package fr.unice.polytech.al.controller;

import fr.unice.polytech.al.model.Billing;
import fr.unice.polytech.al.repository.BillingRepository;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BillingControllerTest {

    //@MockBean
    //BillingRepository repositoryMockSimple;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    protected BillingRepository billingRepository;



    @Test
    public void exampleTest() throws Exception {
        this.mockMvc.perform(get("/billing")).andExpect(status().isOk());
                //.andExpect(content().string("Hello World"));
    }


    @Test
    public void postMethodCreateBilling() throws Exception {
        this.mockMvc.perform(post("/billing/123").content("{\"clientId\": 123, \"points\": 200}")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string("{\"clientId\":123,\"points\":200}"))
                .andExpect(jsonPath("$.clientId")      .value(123))
                .andExpect(jsonPath("$.points")   .value(200));

    }


    @Test
    public void getExistingBilling() throws Exception {
        this.mockMvc.perform(post("/billing/123").content("{\"clientId\": 123, \"points\": 200}")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());


        this.mockMvc.perform(get("/billing/123")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.clientId")      .value(123))
                .andExpect(jsonPath("$.points")   .value(200));

    }


   /*@Test
    public void getAll() throws Exception { //with @mockBean repository only
        Billing first = new Billing( (long) 23, 200);
        Billing second = new Billing( (long) 12, 300);
        Billing third = new Billing( (long) 1, 250);



       when(repositoryMockSimple.findAll()).thenReturn( Arrays.asList(first, second, third));

        mockMvc.perform(get("/billing"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(content().string("[{\"clientId\":23,\"points\":200},{\"clientId\":12,\"points\":300},{\"clientId\":1,\"points\":250}]"));

       verify(repositoryMockSimple, times(1)).findAll();
       verifyNoMoreInteractions(repositoryMockSimple);

    }*/

    @Test
    public void getAll() throws Exception {
        this.mockMvc.perform( post( "/billing/23" ).content( "{\"clientId\": 23, \"points\": 200}" ).contentType( MediaType.APPLICATION_JSON ) ).andDo( print() ).andExpect( status().isOk() );

        this.mockMvc.perform( post( "/billing/12" ).content( "{\"clientId\": 12, \"points\": 200}" ).contentType( MediaType.APPLICATION_JSON ) ).andDo( print() ).andExpect( status().isOk() );

        this.mockMvc.perform( post( "/billing/1" ).content( "{\"clientId\": 1, \"points\": 200}" ).contentType( MediaType.APPLICATION_JSON ) ).andDo( print() ).andExpect( status().isOk() );


        mockMvc.perform( get( "/billing" ) ).andExpect( status().isOk() ).andExpect( content().contentType( APPLICATION_JSON_UTF8 ) ).andDo( print() ).andExpect( content().string( "[{\"clientId\":1,\"points\":200},{\"clientId\":12,\"points\":200},{\"clientId\":23,\"points\":200}]" ) );

    }

        @Test
    public void testGetClientBilling() throws Exception {
        mockMvc.perform(get("/billing/{12}", "clientId"))//.content("{\"clientId\": 12, \"points\": 200}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(content().string("{\"clientId\":123,\"points\":200}"));

        verify(billingRepository, times(1)).findAll();
        verifyNoMoreInteractions(billingRepository);

    }

    @Test
    public void changeValueOfExistingBilling() throws Exception {


        this.mockMvc.perform(post("/billing/123").content("{\"clientId\": 123, \"points\": 200}")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());


        this.mockMvc.perform(patch("/billing/123/balance")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.clientId")      .value(123))
                .andExpect(jsonPath("$.points")   .exists());

    }



}
