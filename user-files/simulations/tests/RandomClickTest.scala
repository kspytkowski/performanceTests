package tests

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class RandomClickTest extends Simulation {

  val scn = scenario("RandomClickTest")
    .feed(csv("studentsData.csv"))
    .exec(
      Commons.homePageGet(),
      pause(2),
      Commons.logInPageGet(),
      pause(2),
      Commons.logIn("${username}", "${password}"),
      pause(2),
      Commons.availableCoursesPageGet(),

      //add more

      pause(2),
      Commons.logOutGetWithParameter()
    )

  setUp(scn.inject(atOnceUsers(Commons.NUMBER_OF_USERS))).protocols(Commons.HTTP_PROTOCOL)
}