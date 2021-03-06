package Future

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

object FutureSample2 extends App {
  val s = "Hello"
  val f: Future[String] = Future {
    Thread.sleep(1000)
    println(s"[ThreadName1] In Future: ${Thread.currentThread.getName}")
    s + " future!"
  }

  f.foreach { case s: String =>
    println(s"[ThreadName2] In Success: ${Thread.currentThread.getName}")
    println(s)
  }

  println(f.isCompleted)

  Await.ready(f, 5000.millisecond)

  println(s"[ThreadName3] In App: ${Thread.currentThread.getName}")
  println(f.isCompleted)
}
