package tests

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object Commons {

  val MOODLE_URL = "http://192.168.2.1/moodle";

  val numberOfUsers = 1;

  val successStatus: Int = 200

  val LOGIN_CONTEXT_ID = "5";
  val COURSE_CONTEXT_ID = "38"
  val COURSE_QUIZ_CONTEXT_ID = "51"

  val headers1 = Map("Upgrade-Insecure-Requests" -> "1")

  val headers2 = Map(
    "Accept" -> "application/json, text/javascript, */*; q=0.01",
    "Content-Type" -> "application/json",
    "X-Requested-With" -> "XMLHttpRequest")

  val headers3 = Map(
    "Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
    "Upgrade-Insecure-Requests" -> "1")

  val headers4 = Map(
    "Content-Type" -> "application/x-www-form-urlencoded; charset=UTF-8",
    "X-Requested-With" -> "XMLHttpRequest")

  val headers5 = Map(
    "Content-Type" -> "application/json",
    "X-Requested-With" -> "XMLHttpRequest")

  val headers6 = Map("Upgrade-Insecure-Requests" -> "1")

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

  val httpProtocol = http
    .baseURL(MOODLE_URL)
    .inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.(t|o)tf""", """.*\.png"""), WhiteList())
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .userAgentHeader("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:49.0) Gecko/20100101 Firefox/49.0")
//    .extraInfoExtractor(extraInfo => List(extraInfo.request, extraInfo.response, extraInfo.session, extraInfo.response.body.string))

  val homePageGet = exec(http("HomePageGetRQ")
    .get("/")
    .headers(headers1)
    .check(status.is(successStatus)) //TODO add this check to all execs
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

  def logIn(name: String, password: String) = {
    exec(http("LogInUsingCredentials " + name + " " + password)
    .post("/login/index.php")
    .headers(headers1)
    .check(regex(""","sesskey":"(..........)",""").saveAs("SESSKEY"))
    .formParam("username", name)
    .formParam("password", password)
    .formParam("anchor", "")
    .resources(http("SessionKeyPostRQ")
      .post("/lib/ajax/service.php?sesskey=${SESSKEY}")
      .headers(headers2)
      .body(StringBody(generateStandardBody(LOGIN_CONTEXT_ID))))
    )
  }

  def goToCoursePage(courseId: String) = {
    exec(http("go to course page")
			.get("/course/view.php?id=" + courseId)
			.headers(headers1)
			.resources(http("GoToCoursePagePostRQ")
			.post("/lib/ajax/service.php?sesskey=${SESSKEY}")
			.headers(headers2)
      .body(StringBody(generateStandardBody(COURSE_CONTEXT_ID)))
			// .body(RawFileBody("CompleteQuizTest_0007_request.txt"))
      )
    )
  }

  def goToCourseQuiz(courseId: String) = {
    exec(http("got to course quiz")
			.get("/mod/quiz/view.php?id=" + courseId)
			.headers(headers1)
			.resources(http("GoToCourseQuizPostRQ")
			.post("/lib/ajax/service.php?sesskey=${SESSKEY}")
			.headers(headers2)
			.body(StringBody(generateStandardBody(COURSE_QUIZ_CONTEXT_ID))))
    )
  }

  def generateStandardBody(contextId: String): String = {
    "[{\"index\":0,\"methodname\":\"core_fetch_notifications\",\"args\":{\"contextid\":" + contextId +"}}]"
  }

  def startQuiz(quizId: String) = {
    exec(http("Start quiz")
			.post("/mod/quiz/startattempt.php")
			.headers(headers1)
			.formParam("cmid", quizId)
			.formParam("sesskey", "${SESSKEY}")
			.resources(http("StartQuizPostRQ")
			.post("/lib/ajax/service.php?sesskey=${SESSKEY}")
			.headers(headers2)
			.body(StringBody(generateStandardBody(COURSE_QUIZ_CONTEXT_ID)))))
  }

  def answerQuestion(answerFile: String, questionHeader: Map) = {
    exec(http("AnswerQuestion")
			.post("/mod/quiz/processattempt.php")
			.headers(questionHeader)
			.body(RawFileBody(answerFile))
			.resources(http("AnswerQuestionPostRQ")
			.post("/lib/ajax/service.php?sesskey=${SESSKEY}")
			.headers(headers2)
			.body(StringBody(generateStandardBody(COURSE_QUIZ_CONTEXT_ID)))))
  }

  def endQuiz() = {
    exec(http("EndQuiz")
			.post("/mod/quiz/processattempt.php")
			.headers(headers1)
			.formParam("attempt", "2")
			.formParam("finishattempt", "1")
			.formParam("timeup", "0")
			.formParam("slots", "")
			.formParam("sesskey", "${SESSKEY}")
			.resources(http("EndQuizPostRQ")
			.post("/lib/ajax/service.php?sesskey=${SESSKEY}")
			.headers(headers2)
			.body(StringBody(generateStandardBody(COURSE_QUIZ_CONTEXT_ID)))))
  }

  def submitQuiz(quizId: String) = {
    exec(http("SubmitQuiz")
			.get("/mod/quiz/view.php?id=" + quizId)
			.headers(headers1)
			.resources(http("SubmitQuizPostRQ")
			.post("/lib/ajax/service.php?sesskey=${SESSKEY}")
			.headers(headers2)
			.body(StringBody(generateStandardBody(COURSE_QUIZ_CONTEXT_ID)))))
  }

  val logInPost = exec(http("LogInPostRQ")
    .post("/login/index.php")
    .headers(headers1)
    .check(regex(""","sesskey":"(..........)",""").saveAs("SESSKEY"))
    .formParam("username", "admin")
    .formParam("password", "adminM1!")
    .formParam("anchor", "")
    .resources(http("SessionKeyPostRQ")
      .post("/lib/ajax/service.php?sesskey=${SESSKEY}")
      .headers(headers2)
      .body(RawFileBody("RequestExtra3.txt"))))

  val logInToManageCoursesPost = exec(http("LogInToManageCoursesPostRQ")
    .post("/login/index.php")
    .headers(headers1)
    .check(regex(""","sesskey":"(..........)",""").saveAs("SESSKEY"))
    .check(regex(""","sesskey":"(..........)",""").saveAs("COURSE_NAME"))
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
    .check(status.is(successStatus))
    .check(regex(""","sesskey":"(..........)",""").saveAs("SESSKEY"))
    .resources(http("SessionKeyPostRQ")
      .post("/lib/ajax/service.php?sesskey=${SESSKEY}")
      .headers(headers2)
      .body(RawFileBody("RequestExtra1.txt"))))


  val availableCoursesPageGet = exec(http("AvailableCoursesPageGetRQ")
    .get("/?redirect=0")
    .headers(headers3)
    .resources(http("SessionKeyPostRQ")
      .post("/lib/ajax/service.php?sesskey=${SESSKEY}")
      .headers(headers2)
      .body(RawFileBody("RequestExtra1.txt"))))

  val filterSettingsPageGet = exec(http("FilterSettingsPageGetRQ")
    .get("/filter/manage.php?contextid=2")
    .headers(headers3)
    .resources(http("SessionKeyPostRQ")
      .post("/lib/ajax/service.php?sesskey=${SESSKEY}")
      .headers(headers2)
      .body(RawFileBody("RequestExtra1.txt"))))

  val coursePageGet = exec(http("coursePageGetRQ")
    .get("/course/index.php")
    .headers(headers1)
    .resources(http("NavBranchPostRQ")
      .post("/lib/ajax/getnavbranch.php")
      .headers(headers2)
      .formParam("elementid", "expandable_branch_0_courses")
      .formParam("id", "courses")
      .formParam("type", "0")
      .formParam("sesskey", "${SESSKEY}")
      .formParam("instance", "4"),
      http("NavBranchPostRQ")
        .post("/lib/ajax/getnavbranch.php")
        .headers(headers2)
        .formParam("elementid", "expandable_branch_0_courses")
        .formParam("id", "courses")
        .formParam("type", "0")
        .formParam("sesskey", "${SESSKEY}")
        .formParam("instance", "4"),
      http("SessionKeyPostRQ")
        .post("/lib/ajax/service.php?sesskey=${SESSKEY}")
        .headers(headers2)
        .body(RawFileBody("RequestExtra2.txt"))))

  val addNewCoursePageGet = exec(http("AddNewCoursePageGetRQ")
    .get("/course/edit.php?category=1&returnto=category")
    .headers(headers1)
    .resources(http("SessionKeyPostRQ")
      .post("/lib/ajax/service.php?sesskey=${SESSKEY}")
      .headers(headers2)
      .body(RawFileBody("RequestExtra4.txt"))))

  val createCoursePost = exec(http("CreateCoursePostRQ")
    .post("/course/edit.php")
    .headers(headers1)
    .formParam("returnto", "category")
    .formParam("returnurl", "http://localhost:8080/course/index.php?categoryid=1")
    .formParam("mform_isexpanded_id_descriptionhdr", "1")
    .formParam("addcourseformatoptionshere", "")
    .formParam("id", "")
    .formParam("sesskey", "${SESSKEY}")
    .formParam("_qf__course_edit_form", "1")
    .formParam("mform_isexpanded_id_general", "1")
    .formParam("mform_isexpanded_id_courseformathdr", "1")
    .formParam("mform_isexpanded_id_appearancehdr", "1")
    .formParam("mform_isexpanded_id_filehdr", "1")
    .formParam("mform_isexpanded_id_completionhdr", "1")
    .formParam("mform_isexpanded_id_groups", "1")
    .formParam("mform_isexpanded_id_rolerenaming", "1")
    .formParam("mform_isexpanded_id_tagshdr", "1")
    .formParam("fullname", "${SESSKEY}") //TODO parameterize!
    .formParam("shortname", "${SESSKEY}") //TODO parameterize!
    .formParam("category", "1")
    .formParam("visible", "1")
    .formParam("startdate[day]", "1")
    .formParam("startdate[month]", "4")
    .formParam("startdate[year]", "2017")
    .formParam("idnumber", "${SESSKEY}") //TODO parameterize!
    .formParam("summary_editor[text]", "<p>CourseSummary<br></p>")
    .formParam("summary_editor[format]", "1")
    .formParam("summary_editor[itemid]", "1")
    .formParam("overviewfiles_filemanager", "1")
    .formParam("format", "weeks")
    .formParam("numsections", "12")
    .formParam("hiddensections", "0")
    .formParam("coursedisplay", "0")
    .formParam("lang", "")
    .formParam("newsitems", "5")
    .formParam("showgrades", "1")
    .formParam("showreports", "0")
    .formParam("maxbytes", "0")
    .formParam("enablecompletion", "0")
    .formParam("groupmode", "0")
    .formParam("groupmodeforce", "0")
    .formParam("defaultgroupingid", "0")
    .formParam("role_1", "")
    .formParam("role_2", "")
    .formParam("role_3", "Teacher")
    .formParam("role_4", "")
    .formParam("role_5", "Student")
    .formParam("role_6", "")
    .formParam("role_7", "")
    .formParam("role_8", "")
    .formParam("tags", "_qf__force_multiselect_submission")
    .formParam("saveanddisplay", "Save and display"))

  val getSiteAdminBranchPost = exec(http("GetSiteAdminBranchPostRQ")
    .post("/lib/ajax/getsiteadminbranch.php")
    .headers(headers4)
    .formParam("type", "71")
    .formParam("sesskey", "${SESSKEY}"))

  val managementPageGet = exec(http("ManagementPageGetRQ")
    .get("/course/management.php")
    .headers(headers3)
    .check(regex("""title="${COURSE_NAME}" href="http://localhost:8080/course/view.php\?id=(.*)">${COURSE_NAME}""").saveAs("COURSE_ID")) //TODO
    //${COURSE_NAME} is a session key which was used for creation of this course...
    //${COURSE_ID} is an interior moodle database course id (different than course id assigned by used while creating a course)
    .resources(http("SessionKeyPostRQ")
    .post("/lib/ajax/service.php?sesskey=${SESSKEY}")
    .headers(headers5)
    .body(RawFileBody("RequestExtra4.txt"))))

  val confirmationBeforeCourseDeleteGet = exec(http("ConfirmationBeforeCourseDeleteGetRQ")
    .get("/course/delete.php?id=${COURSE_ID}") //TODO
    .headers(headers3)
    .check(regex("""<input type="hidden" name="delete" value="(.*)" /><input type="hidden" name="sesskey"""").saveAs("COURSE_DELETE_HASH")) //TODO
    .resources(http("SessionKeyPostRQ")
    .post("/lib/ajax/service.php?sesskey=${SESSKEY}")
    .headers(headers5)
    .body(RawFileBody("RequestExtra4.txt"))))

  val deleteCoursePost = exec(http("DeleteCoursePostRQ")
    .post("/course/delete.php")
    .headers(headers3)
    .formParam("id", "${COURSE_ID}") //TODO
    .formParam("delete", "${COURSE_DELETE_HASH}") //TODO
    .formParam("sesskey", "${SESSKEY}")
    .resources(http("SessionKeyPostRQ")
      .post("/lib/ajax/service.php?sesskey=${SESSKEY}")
      .headers(headers5)
      .body(RawFileBody("RequestExtra4.txt"))))

  val managementPageRedirectGet = exec(http("ManagementPageRedirectGetRQ")
    .get("/course/management.php?categoryid=1")
    .headers(headers3)
    .resources(http("SessionKeyPostRQ")
      .post("/lib/ajax/service.php?sesskey=${SESSKEY}")
      .headers(headers5)
      .body(RawFileBody("RequestExtra4.txt"))))

  val editCoursePageGet = exec(http("EditCoursePageGetRQ")
    .get("/course/edit.php?id=${COURSE_ID}&returnto=catmanage")
    .headers(headers6))

  val editCoursePost = exec(http("EditCoursePostRQ")
    .post("/course/edit.php")
    .headers(headers1)
    .formParam("returnto", "catmanage")
    .formParam("returnurl", "http://localhost:8080/course/management.php?categoryid=0")
    .formParam("mform_isexpanded_id_descriptionhdr", "1")
    .formParam("addcourseformatoptionshere", "")
    .formParam("id", "${COURSE_ID}") //TODO
    .formParam("sesskey", "${SESSKEY}") //TODO
    .formParam("_qf__course_edit_form", "1")
    .formParam("mform_isexpanded_id_general", "1")
    .formParam("mform_isexpanded_id_courseformathdr", "0")
    .formParam("mform_isexpanded_id_appearancehdr", "0")
    .formParam("mform_isexpanded_id_filehdr", "0")
    .formParam("mform_isexpanded_id_completionhdr", "0")
    .formParam("mform_isexpanded_id_groups", "0")
    .formParam("mform_isexpanded_id_rolerenaming", "0")
    .formParam("mform_isexpanded_id_tagshdr", "0")
    .formParam("fullname", "${SESSKEY}") //TODO
    .formParam("shortname", "${SESSKEY}NEWSHORTNAME") //TODO
    .formParam("category", "1")
    .formParam("visible", "1")
    .formParam("startdate[day]", "1") //TODO
    .formParam("startdate[month]", "1") //TODO
    .formParam("startdate[year]", "2017") //TODO
    .formParam("idnumber", "${SESSKEY}") //TODO
    .formParam("summary_editor[text]", "<p>NewCourseSummary<br></p>") //TODO
    .formParam("summary_editor[format]", "1")
    .formParam("summary_editor[itemid]", "1")
    .formParam("overviewfiles_filemanager", "1")
    .formParam("format", "weeks")
    .formParam("numsections", "12")
    .formParam("hiddensections", "0")
    .formParam("coursedisplay", "0")
    .formParam("lang", "")
    .formParam("newsitems", "5")
    .formParam("showgrades", "1")
    .formParam("showreports", "0")
    .formParam("maxbytes", "0")
    .formParam("enablecompletion", "0")
    .formParam("groupmode", "0")
    .formParam("groupmodeforce", "0")
    .formParam("defaultgroupingid", "0")
    .formParam("role_1", "")
    .formParam("role_2", "")
    .formParam("role_3", "")
    .formParam("role_4", "")
    .formParam("role_5", "")
    .formParam("role_6", "")
    .formParam("role_7", "")
    .formParam("role_8", "")
    .formParam("tags", "_qf__force_multiselect_submission")
    .formParam("saveandreturn", "Save and return"))


}
