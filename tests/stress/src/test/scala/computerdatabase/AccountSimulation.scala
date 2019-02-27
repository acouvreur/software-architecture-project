package computerdatabase

import java.util.UUID

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.apache.commons.lang.RandomStringUtils

import scala.concurrent.duration._
import scala.language.postfixOps

class AccountSimulation extends Simulation{
  val httpProtocol = http
    .baseUrl("http://localhost:8081/")
    .acceptHeader("application/json")
    .header("Content-Type", "application/json")

  val stressSample =
    scenario("Account")
      .repeat(2)
      {
        exec(session =>
          session.set("mail", RandomStringUtils.randomAlphabetic(50))
            .set("name", RandomStringUtils.randomAlphabetic(50))
        )
          .exec(
            http("Create_Account")
              .post("accounts")
              .body(StringBody(session => buildAccount(session)))
              .check(status.is(201))
          )
          .pause(1 seconds)
      }


  def buildAccount(session: Session): String = {
    val mail = session("mail").as[String]
    val name = session("name").as[String]
    raw"""{"email":"$mail", "username":"$name", "firstName":"Alexis", "lastName":"Couvreur"}""""
  }

  setUp(stressSample.inject(constantConcurrentUsers(10) during (5 seconds), // 1
    rampConcurrentUsers(10) to (50) during (5 seconds)).protocols(httpProtocol))
}
