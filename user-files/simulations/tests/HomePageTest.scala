package tests

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class HomePageTest extends Simulation {

  val scn = scenario("HomePageTest")
    .exec(
      Commons.homePageGet()
    )

  setUp(scn.inject(Commons.USERS_STRATEGY)).protocols(Commons.HTTP_PROTOCOL)
}
