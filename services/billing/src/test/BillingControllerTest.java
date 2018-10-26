import fr.unice.polytech.al.model.Billing;
import fr.unice.polytech.al.repository.BillingRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BillingRepository repository;


    @Test
    public AccountControllerTest() throws Exception {
        Billing first = new Billing()
                .setClientId(2132)
                .setPoints(200)
                .build();
        Billing second = new Billing()
                .setClientId(21)
                .setPoints(20)
                .build();

        when(repository.findAll()).thenReturn( Arrays.asList(first, second));

        mockMvc.perform(get("/billing"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8));

        verify(repository, times(1)).findAll();
        verifyNoMoreInteractions(repository);

    }

}
