
import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class RandomClickTest extends Simulation {

	val httpProtocol = http
		.baseURL("http://localhost:8080")
		.inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.(t|o)tf""", """.*\.png"""), WhiteList())
		.acceptHeader("*/*")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("en-US,en;q=0.5")
		.userAgentHeader("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:49.0) Gecko/20100101 Firefox/49.0")

	val headers_0 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_1 = Map(
		"Accept" -> "application/json, text/javascript, */*; q=0.01",
		"Content-Type" -> "application/json",
		"X-Requested-With" -> "XMLHttpRequest")

	val headers_8 = Map(
		"Accept" -> "application/json, text/javascript, */*; q=0.01",
		"Content-Type" -> "application/x-www-form-urlencoded; charset=UTF-8",
		"X-Requested-With" -> "XMLHttpRequest")

	val headers_46 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
		"Content-Type" -> "application/x-www-form-urlencoded; charset=UTF-8",
		"X-Requested-With" -> "XMLHttpRequest")

    val uri2 = "http://cdn.mathjax.org/mathjax/2.6-latest"

	val scn = scenario("RandomClickTest")
		.exec(http("request_2")
			.get("/login/index.php")
			.headers(headers_0)
			.resources(http("request_3")
			.post("/lib/ajax/service.php?sesskey=5U4mTEHeEq")
			.headers(headers_1)
			.body(RawFileBody("RandomClickTest_0003_request.txt"))))
		.pause(14)
		.exec(http("request_4")
			.post("/login/index.php")
			.headers(headers_0)
			.formParam("username", "admin")
			.formParam("password", "adminM1!")
			.formParam("anchor", "")
			.resources(http("request_5")
			.post("/lib/ajax/service.php?sesskey=IxY8EdCfHB")
			.headers(headers_1)
			.body(RawFileBody("RandomClickTest_0005_request.txt"))))
		.pause(4)
		.exec(http("request_6")
			.get("/theme/image.php/clean/core/1478020039/i/loading_small")
			.resources(http("request_7")
			.get("/course/index.php")
			.headers(headers_0),
            http("request_8")
			.post("/lib/ajax/getnavbranch.php")
			.headers(headers_8)
			.formParam("elementid", "expandable_branch_0_courses")
			.formParam("id", "courses")
			.formParam("type", "0")
			.formParam("sesskey", "IxY8EdCfHB")
			.formParam("instance", "4"),
            http("request_9")
			.get("/theme/image.php/clean/core/1478020039/t/collapsed_empty"),
            http("request_10")
			.get("/theme/image.php/clean/core/1478020039/t/expanded"),
            http("request_11")
			.post("/lib/ajax/getnavbranch.php")
			.headers(headers_8)
			.formParam("elementid", "expandable_branch_0_courses")
			.formParam("id", "courses")
			.formParam("type", "0")
			.formParam("sesskey", "IxY8EdCfHB")
			.formParam("instance", "4"),
            http("request_12")
			.post("/lib/ajax/service.php?sesskey=IxY8EdCfHB")
			.headers(headers_1)
			.body(RawFileBody("RandomClickTest_0012_request.txt"))))
		.pause(2)
		.exec(http("request_13")
			.get("/?redirect=0")
			.headers(headers_0)
			.resources(http("request_14")
			.post("/lib/ajax/service.php?sesskey=IxY8EdCfHB")
			.headers(headers_1)
			.body(RawFileBody("RandomClickTest_0014_request.txt"))))
		.pause(3)
		.exec(http("request_15")
			.get("/filter/manage.php?contextid=2")
			.headers(headers_0)
			.resources(http("request_16")
			.post("/lib/ajax/service.php?sesskey=IxY8EdCfHB")
			.headers(headers_1)
			.body(RawFileBody("RandomClickTest_0016_request.txt"))))
		.pause(1)
		.exec(http("request_17")
			.get("/admin/settings.php?section=frontpagesettings")
			.headers(headers_0)
			.resources(http("request_18")
			.post("/lib/ajax/service.php?sesskey=IxY8EdCfHB")
			.headers(headers_1)
			.body(RawFileBody("RandomClickTest_0018_request.txt")),
            http("request_19")
			.get(uri2 + "/MathJax.js?delayStartupUntil=configured"),
            http("request_20")
			.get("/theme/image.php/clean/atto_collapse/1478020039/icon"),
            http("request_21")
			.get("/theme/image.php/clean/core/1478020039/e/bold"),
            http("request_22")
			.get("/theme/image.php/clean/core/1478020039/e/styleprops"),
            http("request_23")
			.get("/theme/image.php/clean/core/1478020039/e/italic"),
            http("request_24")
			.get("/theme/image.php/clean/core/1478020039/e/bullet_list"),
            http("request_25")
			.get("/theme/image.php/clean/core/1478020039/e/numbered_list"),
            http("request_26")
			.get("/theme/image.php/clean/core/1478020039/e/remove_link"),
            http("request_27")
			.get("/theme/image.php/clean/core/1478020039/e/strikethrough"),
            http("request_28")
			.get("/theme/image.php/clean/core/1478020039/e/insert_edit_image"),
            http("request_29")
			.get("/theme/image.php/clean/core/1478020039/e/insert_edit_link"),
            http("request_30")
			.get("/theme/image.php/clean/core/1478020039/e/underline"),
            http("request_31")
			.get("/theme/image.php/clean/core/1478020039/e/subscript"),
            http("request_32")
			.get(uri2 + "/config/Accessible.js?rev=2.6.1"),
            http("request_33")
			.get("/theme/image.php/clean/core/1478020039/e/decrease_indent"),
            http("request_34")
			.get("/theme/image.php/clean/core/1478020039/e/align_center"),
            http("request_35")
			.get("/theme/image.php/clean/core/1478020039/e/special_character"),
            http("request_36")
			.get("/theme/image.php/clean/core/1478020039/e/superscript"),
            http("request_37")
			.get("/theme/image.php/clean/core/1478020039/e/math"),
            http("request_38")
			.get("/theme/image.php/clean/core/1478020039/e/undo"),
            http("request_39")
			.get(uri2 + "/config/Safe.js?rev=2.6.1"),
            http("request_40")
			.get("/theme/image.php/clean/core/1478020039/e/source_code"),
            http("request_41")
			.get("/theme/image.php/clean/core/1478020039/e/clear_formatting"),
            http("request_42")
			.get("/theme/image.php/clean/core/1478020039/e/redo"),
            http("request_43")
			.get("/theme/image.php/clean/core/1478020039/i/info"),
            http("request_44")
			.get("/theme/image.php/clean/core/1478020039/e/accessibility_checker"),
            http("request_45")
			.get("/theme/image.php/clean/core/1478020039/i/warning"),
            http("request_46")
			.post("/lib/editor/atto/autosave-ajax.php")
			.headers(headers_46)
			.formParam("actions[0][contextid]", "1")
			.formParam("actions[0][action]", "resume")
			.formParam("actions[0][draftid]", "-1")
			.formParam("actions[0][elementid]", "summary")
			.formParam("actions[0][pageinstance]", "yui_3_17_2_1_1478731257084_133")
			.formParam("actions[0][pagehash]", "6fbe2042dc4504deb9e263435849e7305c4159d6")
			.formParam("sesskey", "IxY8EdCfHB"),
            http("request_47")
			.get("/theme/image.php/clean/core/1478020039/e/screenreader_helper"),
            http("request_48")
			.get(uri2 + "/extensions/Safe.js?rev=2.6.1"),
            http("request_49")
			.get("/theme/image.php/clean/core/1478020039/e/table"),
            http("request_50")
			.get("/theme/image.php/clean/core/1478020039/e/increase_indent"),
            http("request_51")
			.get("/theme/image.php/clean/core/1478020039/e/align_left"),
            http("request_52")
			.get("/admin/settings.php?section=optionalsubsystems")
			.headers(headers_0),
            http("request_53")
			.post("/lib/ajax/service.php?sesskey=IxY8EdCfHB")
			.headers(headers_1)
			.body(RawFileBody("RandomClickTest_0053_request.txt"))))
		.pause(4)
		.exec(http("request_54")
			.get("/admin/tool/assignmentupgrade/index.php")
			.headers(headers_0)
			.resources(http("request_55")
			.post("/lib/ajax/service.php?sesskey=IxY8EdCfHB")
			.headers(headers_1)
			.body(RawFileBody("RandomClickTest_0055_request.txt"))))
		.pause(2)
		.exec(http("request_56")
			.get("/login/logout.php?sesskey=IxY8EdCfHB")
			.headers(headers_0)
			.resources(http("request_57")
			.post("/lib/ajax/service.php?sesskey=SHAbCGF9ce")
			.headers(headers_1)
			.body(RawFileBody("RandomClickTest_0057_request.txt"))))

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}