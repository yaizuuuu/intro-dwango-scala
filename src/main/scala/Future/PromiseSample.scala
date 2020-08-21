package Future

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Promise, Future}
import scala.concurrent.duration._
import scala.util.{Success, Failure}

object PromiseSample extends App {
  val promiseGetInt: Promise[Int] = Promise[Int]
  val futureByPromise: Future[Int] = promiseGetInt.future

  val mappedFuture = futureByPromise.map { i =>
    println(s"Success! i: ${i}")
  }

  Future {
    Thread.sleep(300)
    promiseGetInt.success(1)
  }

  Await.ready(mappedFuture, 5000.millisecond)
}
