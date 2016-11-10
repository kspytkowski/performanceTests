
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class HomePageTest extends Simulation {

  val httpProtocol = http
    .baseURL("http://localhost:8080")
    .inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.(t|o)tf""", """.*\.png"""), WhiteList())
    .acceptHeader("*/*")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .userAgentHeader("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:49.0) Gecko/20100101 Firefox/49.0")
    .extraInfoExtractor(extraInfo => List(extraInfo.request, extraInfo.response, extraInfo.session, extraInfo.response.body.string))

  val headers = Map(
    "Accept" -> "application/json, text/javascript, */*; q=0.01",
    "Content-Type" -> "application/json",
    "X-Requested-With" -> "XMLHttpRequest")

  val scn = scenario("HomePageTest")
    .exec(http("HomePageGet")
      .get("/")
      .check(status.is(200))
      .check(regex(""","sesskey":"(..........)",""").saveAs("SESSKEY")))
    .exec(http("SessionKeyPost")
      .post("/lib/ajax/service.php?sesskey=${SESSKEY}")
      .headers(headers)
      .body(RawFileBody("HomePageTest_0005_request.txt"))
    )

  setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}