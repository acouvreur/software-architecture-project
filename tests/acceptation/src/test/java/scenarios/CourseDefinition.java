package scenarios;

import cucumber.api.java.en.*;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.core.MediaType;
import org.apache.cxf.jaxrs.client.WebClient;

import java.util.List;

import static org.junit.Assert.*;


public class CourseDefinition {

    private String host = "localhost";
    private int port = 8083;

    private JSONObject course;
    private JSONObject answer;

    public JSONObject callCourse(JSONObject request) {
        String raw =
                WebClient.create("http://" + host + ":" + port + "/courses")
                        .accept(MediaType.APPLICATION_JSON_TYPE)
                        .header("Content-Type", MediaType.APPLICATION_JSON)
                        .post(request.toString(), String.class);
        return new JSONObject(raw);
    }

    public JSONObject callGetAnnouncement(String idTransmitter) {
        String raw =
                WebClient.create("http://" + host + ":" + 8080 + "/announcements" + idTransmitter)
                        .accept(MediaType.APPLICATION_JSON_TYPE)
                        .header("Content-Type", MediaType.APPLICATION_JSON)
                        .get(String.class);
        return new JSONObject(raw);
    }


    @Then("^the announcement of (.*) \\(with id (\\d+)\\) is changed to course$")
    public void change(String driver, int id) {
        course = new JSONObject();
        System.out.println("\n\n\n----------------ANNOUNCEMENT -> COURSE----------------\n\n\n");
        course.put("idDriver", id);
        System.out.println(""+driver+" avec comme id :"+ id);
        System.out.println("\n\n\n------------------------------------------------------\n\n\n");
    }

    @Then("^the course reffer to the announcement of (.*) \\(with id (\\d+)\\)$")
    public void reffer_announcement(String transmitter, int id) {
        course.put("idClient", id);
        answer = new JSONObject();

        System.out.println("\n\n\n----------------REFERENCE ANNOUNCEMENT----------------\n\n\n");
        System.out.println("Announcement of "+transmitter +" avec comme id :"+ id);
        System.out.println("\n\n\n------------------------------------------------------\n\n\n");


        System.out.println("\n\n\n----------------GET ANNOUNCEMENT----------------\n\n\n");
        answer = callGetAnnouncement("?transmitter="+id);
        JSONObject tmp = (JSONObject) answer.get("_embedded");
        JSONArray listTmp = (JSONArray) tmp.get("announcements");
        answer = (JSONObject) listTmp.get(0);
        System.out.println(answer.toString());
        System.out.println("\n\n\n------------------------------------------------\n\n\n");

        course.put("idClient", answer.get("idTransmitter"));
        course.put("idAnnouncement", answer.get("id"));
        course.put("startPoint", answer.get("startPoint"));
        course.put("endPoint", answer.get("endPoint"));
        course.put("startDate", answer.get("startDate"));
        course.put("endDate", answer.get("endDate"));

        System.out.println("\n\n\n----------------VOICI NOTRE COURSE----------------\n\n\n");
        System.out.println(course.toString());
        System.out.println("\n\n\n---------------------------------------------------\n\n\n");

        System.out.println("\n\n\n----------------APPEL DU SERVICE COURSE----------------\n\n\n");
        JSONObject ans = callCourse(course);
        System.out.println(ans.toString());
        System.out.println("\n\n\n---------------------------------------------------\n\n\n");
    }

}
