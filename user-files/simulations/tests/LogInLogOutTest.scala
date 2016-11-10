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

  val scn2 = scenario("LogInLogOutTest")
    .exec(Commons.homePageGet)
    .exec(pause(2))
    .exec(Commons.logInPageGet)
    .exec(pause(2))
    .exec(Commons.logInPost)
    .exec(pause(2))
    .exec(Commons.logOutGetWithParameter)

  //difference between scn and scn2??

  setUp(scn.inject(atOnceUsers(Commons.numberOfUsers))).protocols(Commons.httpProtocol)
}