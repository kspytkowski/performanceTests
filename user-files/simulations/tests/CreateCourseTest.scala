package tests

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class CreateCourseTest extends Simulation {

  val scn = scenario("CreateCourseTest")
    .exec(
      Commons.homePageGet,
      pause(2),
      Commons.logInPageGet,
      pause(2),
      Commons.logInPost,
      pause(2),
      Commons.coursePageGet,
      pause(2),
      Commons.addNewCoursePageGet,
      pause(20),
      Commons.createCoursePost,
      pause(20),
      Commons.logOutGetWithParameter
    )

  setUp(scn.inject(atOnceUsers(Commons.numberOfUsers))).protocols(Commons.httpProtocol)
}