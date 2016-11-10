package tests

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object Commons {

  val headers1 = Map("Upgrade-Insecure-Requests" -> "1")

  val headers2 = Map(
    "Accept" -> "application/json, text/javascript, */*; q=0.01",
    "Content-Type" -> "application/json",
    "X-Requested-With" -> "XMLHttpRequest")

  val httpProtocol = http
    .baseURL("http://localhost:8080")
    .inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.(t|o)tf""", """.*\.png"""), WhiteList())
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .userAgentHeader("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:49.0) Gecko/20100101 Firefox/49.0")
//    .extraInfoExtractor(extraInfo => List(extraInfo.request, extraInfo.response, extraInfo.session, extraInfo.response.body.string))

  val homePageGet = exec(http("HomePageGetRQ")
    .get("/")
    .headers(headers1)
    .check(status.is(200))
    .check(regex(""","sesskey":"(..........)",""").saveAs("SESSKEY"))
    .resources(http("SessionKeyPostRQ")
      .post("/lib/ajax/service.php?sesskey=${SESSKEY}")
      .headers(headers2)
      .body(RawFileBody("RequestExtra1.txt"))))

  val logInPageGet = exec(http("LogInPageGetRQ")
    .get("/login/index.php")
    .headers(headers1)
    .resources(http("SessionKeyPostRQ")
      .post("/lib/ajax/service.php?sesskey=${SESSKEY}")
      .headers(headers2)
      .body(RawFileBody("RequestExtra2.txt"))))

  val logInPost = exec(http("LogInPostRQ")
    .post("/login/index.php")
    .headers(headers1)
    .formParam("username", "admin")
    .formParam("password", "adminM1!")
    .formParam("anchor", "")
    .resources(http("SessionKeyPostRQ")
      .post("/lib/ajax/service.php?sesskey=${SESSKEY}")
      .headers(headers2)
      .body(RawFileBody("RequestExtra3.txt"))))

  val logOutGetWithParameter = exec(http("LogOutGetWithParameterRQ")
    .get("/login/logout.php?sesskey=${SESSKEY}")
    .headers(headers1)
    .check(status.is(200))
    .check(regex(""","sesskey":"(..........)",""").saveAs("SESSKEYNEW"))
    .resources(http("SessionKeyPostRQ")
      .post("/lib/ajax/service.php?sesskey=${SESSKEYNEW}")
      .headers(headers2)
      .body(RawFileBody("RequestExtra1.txt"))))

  val numberOfUsers = 2;
}