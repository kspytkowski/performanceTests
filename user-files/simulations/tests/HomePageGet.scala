package tests

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import Headers._

object HomePageGet {

  val homePageGet = exec(http("HomePageGetRQ")
    .get("/")
    .headers(headers1)
    .check(status.is(200))
    .check(regex(""","sesskey":"(..........)",""").saveAs("SESSKEY"))
    .resources(http("SessionKeyPost")
      .post("/lib/ajax/service.php?sesskey=${SESSKEY}")
      .headers(headers2)
      .body(RawFileBody("LogInLogOutTest_0005_request.txt"))))

}