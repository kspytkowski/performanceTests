package tests

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class CompleteQuizTest extends Simulation {

  val headersFirstQuestion = Map(
    "Content-Type" -> "multipart/form-data; boundary=---------------------------23874327418345425662108952501",
    "Upgrade-Insecure-Requests" -> "1")

  val headersSecondQuestion = Map(
    "Content-Type" -> "multipart/form-data; boundary=---------------------------8995734713942129141733338246",
    "Upgrade-Insecure-Requests" -> "1")

  val headersThirdQuestion = Map(
    "Content-Type" -> "multipart/form-data; boundary=---------------------------4943517595834122041069809851",
    "Upgrade-Insecure-Requests" -> "1")

  val headersFourthQuestion = Map(
    "Content-Type" -> "multipart/form-data; boundary=---------------------------686099722714968618186152106",
    "Upgrade-Insecure-Requests" -> "1")

  val scn = scenario("CompleteQuizTest")
    .feed(csv("studentsData.csv"))
    .exec(
      Commons.homePageGet(),
      pause(4),
      Commons.logInPageGet(),
      pause(4),
      Commons.logIn("${username}", "${password}"),
      pause(4),
      Commons.goToCoursePage("2"),
      pause(2),
      Commons.goToCourseQuiz("2"),
      pause(2),
      Commons.startQuiz("2"),
      pause(2),
      Commons.answerQuestion("CompleteQuizTest_FirstAnswer.txt", headersFirstQuestion),
      pause(2),
      Commons.answerQuestion("CompleteQuizTest_SecondAnswer.txt", headersSecondQuestion),
      pause(2),
      Commons.answerQuestion("CompleteQuizTest_ThirdAnswer.txt", headersThirdQuestion),
      pause(2),
      Commons.answerQuestion("CompleteQuizTest_FourthAnswer.txt", headersFourthQuestion),
      pause(2),
      Commons.endQuiz(),
      pause(2),
      Commons.submitQuiz("2"),
      pause(2),
      Commons.logOutGetWithParameter()
    )

  setUp(scn.inject(atOnceUsers(Commons.NUMBER_OF_USERS))).protocols(Commons.HTTP_PROTOCOL)
}
