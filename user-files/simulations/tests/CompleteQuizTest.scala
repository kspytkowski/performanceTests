package tests

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class CompleteQuizTest extends Simulation {

	val headers_0 = Map("Upgrade-Insecure-Requests" -> "1")

	val headers_1 = Map(
		"Accept" -> "application/json, text/javascript, */*; q=0.01",
		"Content-Type" -> "application/json",
		"X-Requested-With" -> "XMLHttpRequest")

	val scn = scenario("CompleteQuizTest")
	.feed(csv("studentsData.csv"))
	.exec(
		Commons.homePageGet,
		pause(4),
		Commons.logInPageGet,
		pause(4),
		Commons.logIn("${username}", "${password}"),
		pause(4),
		Commons.goToCoursePage("2"),
		pause(2),
		Commons.goToCourseQuiz("2"),
		pause(2),
		Commons.startQuiz("2"),
		pause(2),
		Commons.answerQuestion("CompleteQuizTest_FirstQuestion.txt", Commons.headersFirstQuestion),
		pause(2),
		Commons.answerQuestion("CompleteQuizTest_SecondQuestion.txt", Commons.headersSecondQuestion),
		pause(2),
		Commons.answerQuestion("CompleteQuizTest_ThirdQuestion.txt", Commons.headersThirdQuestion),
		pause(2),
		Commons.answerQuestion("CompleteQuizTest_FourthQuestion.txt", Commons.headersFourthQuestion),
		pause(2),
		Commons.endQuiz(),
		pause(2),
		Commons.submitQuiz("2"),
		pause(2),
		Commons.logOutGetWithParameter
	)

	setUp(scn.inject(atOnceUsers(Commons.numberOfUsers))).protocols(Commons.httpProtocol)
}
