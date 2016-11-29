package tests

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class LogInLogOutTest extends Simulation {

  val scn = scenario("LogInLogOutTest")
    .feed(csv("studentsData.csv"))
    .exec(
      Commons.homePageGet(),
      pause(2),
      Commons.logInPageGet(),
      pause(2),
      Commons.logIn("${username}", "${password}"),
      pause(2),
      Commons.logOutGetWithParameter()
    )

  setUp(scn.inject(Commons.USERS_STRATEGY)).protocols(Commons.HTTP_PROTOCOL)
}
