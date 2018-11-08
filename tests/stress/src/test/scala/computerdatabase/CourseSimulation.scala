package computerdatabase

import java.util.UUID

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._
import scala.language.postfixOps

class CourseSimulation extends Simulation{

  val httpProtocol = http
    .baseUrl("http://localhost:8083/")
    .acceptHeader("application/json")
    .header("Content-Type", "application/json")

  val stressSample =
    scenario("Course")
      .repeat(2)
      {
        exec(session =>
          session.set("id", UUID.randomUUID().toString)
        )
          .exec(
            http("Create_Course")
              .post("courses")
              .body(StringBody(session => buildCourse(session)))
              .check(status.is(201))
          )
          .pause(1 seconds)
          .exec(
            http("Consult_Course")
              .get("courses?announcement=42")
              .check(status.is(200))
          )
          .pause(1 seconds)
      }

  def buildCourse(session: Session): String = {
    val id = session("id").as[String]
    raw"""{
         "idClient":456,
         "idDriver":2345,
         "idAnnouncement":42,
         "idNextCourse":3,
         "startPoint":"Paris",
         "endPoint":"Nice",
         "startDate":"2018-11-01",
         "endDate":"2018-11-02"
    }""""
  }

  setUp(stressSample.inject(atOnceUsers(200)).protocols(httpProtocol))
}
