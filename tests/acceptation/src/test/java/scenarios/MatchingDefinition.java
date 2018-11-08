package scenarios;

import cucumber.api.java.en.When;
import org.json.JSONObject;

public class MatchingDefinition {

    //IT'S A TRAP
    //Service mocked


    @When("^the system finds a matching route between (.*) and (.*)$")
    public void matching(String transmitter, String driver) {
        System.out.println("\n\n\n---------------- MATCHING MOCK ----------------\n\n\n");
        System.out.println("Un match entre " + transmitter + " et " + driver + " a été trouvé...\n" +
                "Une notification est envoyé à " + transmitter + " et " + driver);
        System.out.println("\n\n\n------------------------------------------------\n\n\n");
    }
}
