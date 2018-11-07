package scenarios;

import cucumber.api.java.en.*;
import org.json.JSONObject;

import javax.ws.rs.core.MediaType;
import org.apache.cxf.jaxrs.client.WebClient;
import static org.junit.Assert.*;


public class CourseDefinition {

    private String host = "localhost";
    private int port = 8080;

    private JSONObject course;
    private JSONObject answer;

    private JSONObject call(JSONObject request) {
        String raw =
                WebClient.create("http://" + host + ":" + port + "/courses")
                        .accept(MediaType.APPLICATION_JSON_TYPE)
                        .header("Content-Type", MediaType.APPLICATION_JSON)
                        .post(request.toString(), String.class);
        return new JSONObject(raw);
    }

    @Given("^test$")
    public void initialize_port(String host, int port) {
        System.out.println("COUCOU");
    }

}
