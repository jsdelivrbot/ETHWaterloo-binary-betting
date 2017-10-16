import akka.actor.ActorSystem
import akka.actor.{Actor, ActorRef, ActorLogging, Props}
import com.google.gson.Gson
import play.api.libs.json.Json
import scala.concurrent.duration._

import akka.http.scaladsl.model.{ ContentTypes, HttpEntity }
import akka.http.scaladsl.server.HttpApp
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json._
import ch.megard.akka.http.cors.scaladsl.CorsDirectives._

import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import org.web3j.crypto.Credentials
import org.web3j.crypto.WalletUtils


final case class Registration (
	betid: Int,
	timestamp: Int
)

class WebServer(checker: ActorRef) extends HttpApp {
	override def routes: Route =
		path("register") {
			post {
				cors() {
					entity(as[String]) { json =>
						val gson = new Gson
						val reg = gson.fromJson(json, classOf[Registration])
						println(reg)
						checker ! reg
						complete("OK")
					}
				}
			}
		}
}

/////////////////////////////////////////////////////////////
// 		path("register") {
// 			get {
// 				parameters('betid, 'timestamp) { (betid, timestamp) =>
// 					val reg = Registration(betid.toInt, timestamp.toInt)
// 					checker ! reg
// 					complete(s"OK GOT $reg")
// 				}
// 			}
// 		}
///////////////////////////////


object PriceChecker {
	case object Check
	def props: Props = Props(new PriceChecker)
}

class PriceChecker extends Actor with ActorLogging {
	import PriceChecker.Check

	var waitlist = List[Registration]()

	def get(url: String) = scala.io.Source.fromURL(url).mkString

	def check() {
		val jsonString = get("https://api.coinbase.com/v2/prices/BTC-USD/spot")
		val json = Json.parse(jsonString)
		for {
			priceString <- (json \ "data" \ "amount").asOpt[String]
			price = priceString.toDouble
		} {
			val currentTime = System.currentTimeMillis / 1000
			println(price);
			waitlist.filter{ reg => reg.timestamp <= currentTime }.foreach { reg =>
				// send price to contract with web3 /////////////////////
				// val web3 = Web3j.build(new HttpService())
				// val credentials = WalletUtils.loadCredentials("password", "/path/to/walletfile")
				get(s"http://localhost:5000/${reg.betid}-${price}")
				/////////////////////////////////////////////////////////
				println(s"$reg just expired at time $currentTime")
			}
			waitlist = waitlist.filter{ reg => reg.timestamp > currentTime }
			println(waitlist)
		}
	}

	context.system.scheduler.schedule(1 second, 1 second) {
		self ! Check
	}(context.system.dispatcher)

	override def receive: Receive = {
		case r: Registration =>
			waitlist = waitlist :+ r
		case Check =>
			check()
		case _ =>
	}
}

object PriceServer {
	def main(args: Array[String]) {
		val system = ActorSystem()
		val pc = system.actorOf(PriceChecker.props, "price-checker")
		val server = new WebServer(pc)
		server.startServer("localhost", 8080)
	}
}