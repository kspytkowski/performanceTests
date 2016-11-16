package tests

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class DeleteCourseTest extends Simulation {

  val scn = scenario("DeleteCourseTest")
    .exec(
      Commons.homePageGet,
      pause(2),
      Commons.logInPageGet,
      pause(2),
      Commons.logInToManageCoursesPost,
      pause(2),
      Commons.coursePageGet,
      pause(2),
      Commons.addNewCoursePageGet,
      pause(20),
      Commons.createCoursePost,
      pause(20),
      Commons.getSiteAdminBranchPost,
      pause(2),
      Commons.managementPageGet,
      pause(2),
      Commons.confirmationBeforeCourseDeleteGet,
      pause(2),
      Commons.deleteCoursePost,
      pause(2),
      Commons.managementPageRedirectGet,
      pause(2),
      Commons.logOutGetWithParameter
    )

  setUp(scn.inject(atOnceUsers(Commons.numberOfUsers))).protocols(Commons.httpProtocol)
}