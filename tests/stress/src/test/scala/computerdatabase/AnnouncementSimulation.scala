package computerdatabase

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.util.Random

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
        exec(session =>
          session.set("idTransmitter", Random.nextInt(Integer.MAX_VALUE))
        )
          .exec(
            http("Create_Announcement")
              .post("announcements")
              .body(StringBody(session => buildAnnouncement(session)))
              .check(status.is(201))
          )
          .pause(1 seconds)
          .exec(
            http("Consult_Announcement")
              .get(StringBody(session => buildAnnouncementConsult(session)))
              .check(status.is(200))
          )
          .pause(1 seconds)
      }


  def buildAnnouncementConsult(session: Session): String = {
    val id = session("idTransmitter").as[Integer]
    raw"""announcements?transmitter=$id"""
  }

  def buildAnnouncement(session: Session): String = {
    val idTransmitter = session("idTransmitter").as[Integer]
    raw"""{
         "idTransmitter": $idTransmitter,
         "nameTransmitter": "Jacky",
         "startPoint": "Marseille",
         "endPoint": "Nice",
         "startDate": "2018-11-01",
         "endDate": "2018-11-02",
         "type": "GOOD"
    }""""
  }

  setUp(stressSample.inject(constantConcurrentUsers(10) during (10 seconds), // 1
    rampConcurrentUsers(10) to (50) during (30 seconds)).protocols(httpProtocol))
}
