package tests

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class EditCourseTest extends Simulation {

  val scn = scenario("EditCourseTest")
    .exec(
      Commons.homePageGet(),
      pause(2),
      Commons.logInPageGet(),
      pause(2),
      Commons.logInToManageCoursesPost(Commons.ADMIN_LOGIN, Commons.ADMIN_PASSWORD),
      pause(2),
      Commons.coursePageGet(),
      pause(2),
      Commons.addNewCoursePageGet(),
      pause(20),
      Commons.createCoursePost(),
      pause(20),
      Commons.getSiteAdminBranchPost(),
      pause(2),
      Commons.managementPageGet(),
      pause(2),
      Commons.editCoursePageGet(),
      pause(2),
      Commons.editCoursePost(),
      pause(2),
      Commons.confirmationBeforeCourseDeleteGet(),
      pause(2),
      Commons.deleteCoursePost(),
      pause(2),
      Commons.managementPageRedirectGet(),
      pause(2),
      Commons.logOutGetWithParameter()
    )

  setUp(scn.inject(atOnceUsers(Commons.NUMBER_OF_USERS))).protocols(Commons.HTTP_PROTOCOL)
}