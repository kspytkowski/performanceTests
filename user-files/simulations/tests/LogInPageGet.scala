package tests

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import Headers._

object LogInPageGet {

  val logInPageGet = exec(http("LogInPageGetRQ")
    .get("/login/index.php")
    .headers(headers1)
    .resources(http("SessionKeyPost")
      .post("/lib/ajax/service.php?sesskey=${SESSKEY}")
      .headers(headers2)
      .body(RawFileBody("LogInLogOutTest_0007_request.txt"))))

}