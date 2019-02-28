package computerdatabase

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.util.Random
import spray.json._

import scala.util.parsing.json._

import scala.concurrent.duration._
import scalaj.http.Http
import scala.language.postfixOps

class EndToEndSimulation extends Simulation {
  val httpProtocol = http
    .acceptHeader("application/json")
    .header("Content-Type", "application/json")

  val stressSample =
    scenario("EndToEnd")
      .repeat(1)
      {
        exec(session =>
          session.set("idTransmitter", Random.nextInt(Integer.MAX_VALUE))
            .set("idStudent", Random.nextInt(Integer.MAX_VALUE))
            .set("idTracking", 0)
        )
          .exec(
            http("create_announcement_GOOD")
              .post("http://localhost:8080/announcements")
              .body(StringBody(session => buildAnnouncementGood(session)))
              .check(status.is(201))
          )
          .pause(1 seconds)
          .exec(
            http("create_announcement_COURSE")
              .post("http://localhost:8080/announcements")
              .body(StringBody(session => buildAnnouncementCourse(session)))
              .check(status.is(201))
          )
          .pause(1 seconds)
          .exec(
            http("Consult_Announcement_Tracking")
              .get(StringBody(session => buildTracking(session)))
              .check(status.is(200))
          )
          .pause(300 milliseconds)
          .exec(
            http("Change_Announcement_State_RECEIVED")
              .patch(StringBody(session => buildTracking(session)))
              .body(StringBody(raw"""RECEIVED"""))
              .check(status.is(200))
          )
          .pause(300 milliseconds)
          .exec(
            http("Consult_Announcement_Tracking")
              .get(StringBody(session => buildTracking(session)))
              .check(status.is(200))
          )
          .pause(300 milliseconds)
          .exec(
            http("Change_Announcement_State_STARTED")
              .patch(StringBody(session => buildTracking(session)))
              .body(StringBody(raw"""STARTED"""))
              .check(status.is(200))
          )
          .pause(300 milliseconds)
          .exec(
            http("Consult_Announcement_Tracking")
              .get(StringBody(session => buildTracking(session)))
              .check(status.is(200))
          )
          .pause(300 milliseconds)
          .exec(
            http("Change_Announcement_State_DELIVERING")
              .patch(StringBody(session => buildTracking(session)))
              .body(StringBody(raw"""DELIVERING"""))
              .check(status.is(200))
          )
          .pause(300 milliseconds)
          .exec(
            http("Consult_Announcement_Tracking")
              .get(StringBody(session => buildTracking(session)))
              .check(status.is(200))
          )
          .pause(300 milliseconds)
          .exec(
            http("Change_Announcement_State_DELIVERED")
              .patch(StringBody(session => buildTracking(session)))
              .body(StringBody(raw"""DELIVERED"""))
              .check(status.is(200))
          )
          .pause(300 milliseconds)
          .exec(
            http("Consult_Announcement_Tracking")
              .get(StringBody(session => buildTracking(session)))
              .check(status.is(200))
          )
          .pause(300 milliseconds)
          .exec(
            http("Change_Announcement_State_CONFIRMED")
              .patch(StringBody(session => buildTracking(session)))
              .body(StringBody(raw"""CONFIRMED"""))
              .check(status.is(200))
          )
      }




  def buildTracking(session: Session): String = {
    val id = session("idStudent").as[String]
    val announcement = Http("http://localhost:8080/announcements").param("transmitter", id).asString
    val parsed = announcement.body.toString().parseJson
    val tmp = parsed.asJsObject().getFields("_embedded")(0)
      .asJsObject().getFields("announcements")(0).toString()
      .replace("[", "").replace("]", "")
    val idAnnouncement = tmp.parseJson.asJsObject.getFields("id")(0)
    /*
    println("--------------------------------------------------------")
    println(tmp)
    println(idAnnouncement)
    println("--------------------------------------------------------")
    */
    //session.set("idTracking", idAnnouncement)
    raw"""http://localhost:8085/tracking/$idAnnouncement"""
  }

  def buildAnnouncementConsult(session: Session): String = {
    val id = session("idTransmitter").as[Integer]
    raw"""announcements?transmitter=$id"""
  }


  def buildAnnouncementGood(session: Session): String = {
    val idStudent = session("idStudent").as[Integer]
    raw"""{
         "idTransmitter": $idStudent,
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