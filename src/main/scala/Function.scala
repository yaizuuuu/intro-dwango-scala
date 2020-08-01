object Function {
  def main(args: Array[String]): Unit = {
    val add = new Function2[Int, Int, Int] {
      def apply(x: Int, y: Int): Int = x + y
    }

    println(add.apply(50, 50))
    println(add(50, 50))

    // 無名関数
    val add2 = (x: Int, y: Int) => x + y
    println(add2.apply(100, 100))

    // カリー化
    val addCurried = (x: Int) => ((y: Int) => x + y)
    println(addCurried(150)(150))

    val add3 = (x: Int, y: Int) => x + y
    val addCurried3 = add3(150, _)
    println(addCurried3(150))

    def addMultiparameterList(x: Int)(y: Int): Int = x + y

    val addMultiparameterListCurried = addMultiparameterList(200) _
    println(addMultiparameterListCurried(200))

    def double(n: Int, f: Int => Int): Int = f(f(n))

    println(double(1, m => m * 2))
    println(double(2, m => m * 3))

    def around(init: () => Unit, body: () => Any, fin: () => Unit): Any = {
      init()

      try {
        body()
      } finally {
        fin()
      }
    }

    around(
      () => println("ファイルを開く"),
      () => println("ファイルに対する処理"),
      () => println("ファイルを閉じる")
    )

//    around(
//      () => println("ファイルを開く2"),
//      () => throw new Exception("例外発生"),
//      () => println("ファイルを閉じる")
//    )

    import scala.io.Source
    def withFile[A](filename: String)(f: Source => A): A = {
      val s = Source.fromFile(filename)

      try {
        f(s)
      } finally {
        s.close()
      }
    }

    def printFile(filename: String): Unit = {
      withFile(filename) { file =>
        file.getLines().foreach(println)
      }
    }

//    printFile("Function.scala")
  }
}
