package tests

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class LogInLogOutTest extends Simulation {

  val scn = scenario("LogInLogOutTest")
    .exec(
      Commons.homePageGet,
      pause(2),
      Commons.logInPageGet,
      pause(2),
      Commons.logInPost,
      pause(2),
      Commons.logOutGetWithParameter
    )

  setUp(scn.inject(atOnceUsers(Commons.numberOfUsers))).protocols(Commons.httpProtocol)
}