package computerdatabase


import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.util.Random

import scala.concurrent.duration._
import scala.language.postfixOps

class MatchingSimulation extends Simulation {
  val httpProtocol = http
    .baseUrl("http://localhost:8080/")
    .acceptHeader("application/json")
    .header("Content-Type", "application/json")

  val stressSample =
    scenario("Matching")
      .repeat(2)
      {
        exec(session =>
          session.set("idTransmitter", Random.nextInt(Integer.MAX_VALUE))
        )
          .exec(
            http("Create_Announcement_GOOD")
              .post("announcements")
              .body(StringBody(session => buildAnnouncementGood(session)))
              .check(status.is(201))
          )
          .pause(1 seconds)
          .exec(
            http("Create_Announcement_COURSE")
              .post("announcements")
              .body(StringBody(session => buildAnnouncementCourse(session)))
              .check(status.is(201))
          )
          .exec(
            http("delete_Announcement")
              .delete("/announcements")
              .check(status.is(200))
          )
          .pause(1 seconds)
      }

  def buildAnnouncementGood(session: Session): String = {
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

  def buildAnnouncementCourse(session: Session): String = {
    val idTransmitter = session("idTransmitter").as[Integer]
    raw"""{
         "idTransmitter": $idTransmitter,
         "nameTransmitter": "Francoise",
         "startPoint": "Marseille",
         "endPoint": "Nice",
         "startDate": "2018-11-01",
         "endDate": "2018-11-02",
         "type": "COURSE"
    }""""
  }

  setUp(stressSample.inject(constantConcurrentUsers(10) during (10 seconds), // 1
    rampConcurrentUsers(10) to (50) during (30 seconds)).protocols(httpProtocol))
}
