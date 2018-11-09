package computerdatabase

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.apache.commons.lang.RandomStringUtils

import scala.concurrent.duration._
import scala.language.postfixOps

class AnnouncementSimulation extends Simulation{
  val httpProtocol = http
    .baseUrl("http://localhost:8080/")
    .acceptHeader("application/json")
    .header("Content-Type", "application/json")

  val stressSample =
    scenario("Announcement")
      .repeat(2)
      {
        exec(
            http("Create_Announcement")
              .post("announcements")
              .body(StringBody(session => buildAnnouncement(session)))
              .check(status.is(201))
          )
          .pause(1 seconds)
          .exec(
            http("Consult_Announcement")
              .get("announcements?transmitter=69")
              .check(status.is(200))
          )
          .pause(1 seconds)
      }

  def buildAnnouncement(session: Session): String = {
    raw"""{
         "idTransmitter": 69,
         "nameTransmitter": "Jacky",
         "startPoint": "Marseille",
         "endPoint": "Nice",
         "startDate": "2018-11-01",
         "endDate": "2018-11-02",
         "type": "GOOD"
    }""""
  }

  setUp(stressSample.inject(constantConcurrentUsers(100) during (10 seconds), // 1
    rampConcurrentUsers(100) to (200) during (10 seconds)).protocols(httpProtocol))
}
