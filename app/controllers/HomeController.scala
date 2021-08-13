package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import akka.util._
import play.api.http._

/** This controller creates an `Action` to handle HTTP requests to the application's home page.
  */
@Singleton
class HomeController @Inject() (val controllerComponents: ControllerComponents) extends BaseController {

  /** Create an Action to render an HTML page.
    *
    * The configuration in the `routes` file means that this method will be called when the application receives a `GET`
    * request with a path of `/`.
    */
  def index() = Action {
    //// Resultインスタンスでページを描画
    // Result(
    //   header = ResponseHeader(200, Map.empty),
    //   body = HttpEntity.Strict(
    //     ByteString("This is sample text!"),
    //     Some("text/plain")
    //   )
    // )

    //// Okメソッドでレンダリングすることも可能
    // Ok("<h1>Hello!</h1><p>This is sample message.</p>").as("text/html");

    //// xmlやjsonのレンダリング
    // Ok("<root><title>Hello!</title><message>This is sample message.</message></root>").as("application/xml");
    // Ok("""{"title": "Hello!", "message": "This is sample message."}""").as("application/json");

    //// withHeaderによるヘッダ設定
    Ok("<h1>Hello!</h1><p>This is sample message.</p>")
      .withHeaders(
        // ACCEPT_CHARSETやACCEPT_LANGUAGEはAbstractControllerクラスに用意されているメンバ変数
        ACCEPT_CHARSET -> "utf-8",
        ACCEPT_LANGUAGE -> "ja-JP"
      )
      .as("text/html");
  }

  // 引数を受け取るAction
  def index_with_param(id: Int, name: Option[String]) = Action {
    Ok(s"<h1>Hello!</h1><p>ID = $id, Name = " + name.getOrElse("(未入力)") + "です。</p>")
      // getOrElseでnullの場合の値を設定できる
      .as("text/html; charset=utf-8"); // .as(HTML);でも同様
  }

  // cookieサンプル
  def cookie(name: Option[String]) = Action { request =>
    val param: String = name.getOrElse("")
    var message = "<p>nameはありません。</p>"
    if (param != "") {
      message = "<p>nameが送られました。</p>"
    }

    val cookie = request.cookies.get("name")
    // println("**********")
    // println(request.cookies)
    // println(cookie)
    message += "<p>cookie: " + cookie.getOrElse(Cookie("name", "no-cookie.")).value + "</p>"
    val res = Ok("<title>Hello!</title><h1>Cookie Sample!</h1>" + message).as(HTML)
    if (param != "") {
      res.withCookies(Cookie("name", param)).bakeCookies()
    } else {
      res
    }
  }

  // sessionサンプル
  def session(name: Option[String]) = Action { request =>
    val param: String = name.getOrElse("")
    var message = "<p>nameはありません。</p>"
    if (param != "") {
      message = "<p>nameが送られました。</p>"
    }

    val session = request.session.get("name")
    val sessionValue = session.getOrElse("no-session.")
    message += "<p>session: " + sessionValue + "</p>"
    val res = Ok("<title>Hello!</title><h1>Session Sample!</h1>" + message).as(HTML)
    if (param != "") {
      // withSession("name" -> param))とするだけだと、既にsessionに記録されていた値が消失する
      // そのため、算術演算子+を使って値をマージする
      res.withSession(request.session + ("name" -> param))
    } else {
      res
    }
  }

  // slickサンプル

}
