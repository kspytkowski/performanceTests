package tests

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class RandomClickTest extends Simulation {

  val scn = scenario("RandomClickTest")
    .exec(
      Commons.homePageGet,
      pause(2),
      Commons.logInPageGet,
      pause(2),
      Commons.logInPost,
      pause(2),
      Commons.availableCoursesPageGet,


      pause(2),
      Commons.logOutGetWithParameter
    )

  setUp(scn.inject(atOnceUsers(Commons.numberOfUsers))).protocols(Commons.httpProtocol)
}