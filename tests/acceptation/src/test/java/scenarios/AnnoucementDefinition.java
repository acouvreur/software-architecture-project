package scenarios;

import cucumber.api.java.en.Given;
import org.apache.cxf.jaxrs.client.WebClient;
import org.json.JSONObject;

import javax.ws.rs.core.MediaType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AnnoucementDefinition {

    private String host = "localhost";
    private int port = 8080;

    private JSONObject announcement;
    private JSONObject answer;

    private JSONObject callAnnouncement(JSONObject request) {
        String raw =
                WebClient.create("http://" + host + ":" + port + "/announcements")
                        .accept(MediaType.APPLICATION_JSON_TYPE)
                        .header("Content-Type", MediaType.APPLICATION_JSON)
                        .post(request.toString(), String.class);
        return new JSONObject(raw);
    }

    @Given("^an empty announcement deployed on (.*):(\\d+)$")
    public void initialize_port(String host, int port) {
        this.host = host;
        this.port = port;
        System.out.println("Announcement database dropAll");
    }

    @Given("^an announcement empty is created")
    public void preregistered_delivery() {
        JSONObject announcement = new JSONObject();
        announcement.put("idTransmitter", 1234);
        announcement.put("nameTransmitter", "Jacky");
        announcement.put("startPoint", "Nice");
        announcement.put("endPoint", "Marseille");
        announcement.put("startDate", "2018-11-01");
        announcement.put("endDate", "2018-11-02");
        announcement.put("type", "GOOD");
        JSONObject ans = callAnnouncement(announcement);
        assertEquals(1234, ans.getInt("idTransmitter"));
        assertEquals("Jacky", ans.getString("nameTransmitter"));
        assertEquals("Marseille", ans.getString("endPoint"));
    }

    @Given("^an announcement is created by (.*)$")
    public void init_name_transmitter(String name){
        announcement = new JSONObject();
        announcement.put("idTransmitter", 67); //connue lors de l'exécution la on fait en dur
        announcement.put("nameTransmitter", name);
    }

    @Given("^the id of (.*) is (.*)$")
    public void init_id_transmitter(String name, int id){
        announcement.put("idTransmitter", id);
    }
    /*
    @Given("^the object is a (.*)$")
    public void init_object(String object){
        announcement = new JSONObject();
        announcement.put("idTransmitter", 67); //connue lors de l'exécution la on fait en dur
        announcement.put("nameTransmitter", name);
    }
    */

    @Given("^the departure is at (.*)$")
    public void init_startPoint(String startPoint){
        announcement.put("startPoint", startPoint);
    }

    @Given("^started (.*)$")
    public void init_startDate(String startDate){
        announcement.put("startDate", startDate);
    }

    @Given("^the arrival is at (.*)$")
    public void init_endPoint(String endPoint){
        announcement.put("endPoint", endPoint);
    }

    @Given("^finished at (.*)$")
    public void init_endDate(String endDate){
        announcement.put("endDate", endDate);
    }

    @Given("^the announcement type is (.*)$")
    public void init_type(String type){
        announcement.put("type", type);
        System.out.println("\n\n\n----------------VOICI L'ENTITÉ QUE PRENDRA NOTRE SERVICE ANNOUNCEMENT----------------\n\n\n");
        System.out.println(announcement.toString());
        System.out.println("\n\n\n-------------------------------------------------------------------------------------\n\n\n");
        System.out.println("\n\n\n----------------APPEL DU SERVICE----------------\n\n\n");
        JSONObject ans = callAnnouncement(announcement);
        System.out.println(ans.toString());
        System.out.println("\n\n\n------------------------------------------------\n\n\n");
    }
}
