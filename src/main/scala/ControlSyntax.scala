object ControlSyntax {
  def main(args: Array[String]): Unit = {
    {
      println("A");
      println("B");
      1 + 2;
    }

    println(foo())

    var age = 17

    if (age < 18) {
      "18歳未満です"
    } else {
      "18歳以上です"
    }

    age = 18

    if (age < 18) {
      "18歳未満です"
    } else {
      "18歳以上です"
    }

    practice1()
    practice2()

    for (x <- 1 to 5; y <- 1 until 5) {
      println("x = " + x + " y = " + y)
    }

    for (x <- 1 to 5; y <- 1 until 5 if x != y) {
      println("x = " + x + " y = " + y)
    }

    for (e <- List("A", "B", "C", "D", "E")) println(e)

    println(for (e <- List("A", "B", "C", "D", "E")) yield {
      "Pre" + e
    })

    //    practice3()

    val taro = "Taro"

    taro match {
      case "Taro" => println("Male")
      case "Jiro" => println("Male")
      case "Hanako" => println("Female")
    }

    val one = 1

    one match {
      case 1 => println("one")
      case 2 => println("two")
      case _ => println("other")
    }

    "abc" match {
      case "abc" | "def" =>
        println("first")
        println("second")
    }

    val lst = List("A", "B", "C")

    lst match {
      case List("A", b, c) =>
        println("b = " + b)
        println("c = " + c)
      case _ => println("nothing")
    }

    val lst2 = List("A", "B", "C")

    lst2 match {
      case List("A", b, c) if b != "B" =>
        println("b = " + b)
        println("c = " + c)
      case _ =>
        println("nothing")
    }

    val lst3 = List(List("A"), List("B", "C"))

    lst3 match {
      case List(a@List("A"), x) =>
        println(a)
        println(x)
      case _ => println("nothing")
    }

    (List("a"): Any) match {
      case List(_) | Some(_) =>
        println("a")
    }

    val lst4 = List("A", "B", "C", "D", "E")

    lst4 match {
      case "A" :: b :: c :: _ =>
        println("b = " + b)
        println("c = " + c)
      case _ =>
        println("nothing")
    }

    import java.util.Locale

    val obj: AnyRef = "String Literal"

    obj match {
      case v: java.lang.Integer =>
        println("Integer!")
      case v: String =>
        println(v.toUpperCase(Locale.ENGLISH))
    }

    val obj2: Any = List("a")

    obj2 match {
      // 無視されてしまう
      case v: List[Int] => println("List[Int]")
      case v: List[String] => println("List[String]")
    }

    practice4()
  }

  def foo(): String = {
    "foo" + "foo"
  }

  def practice1(): Unit = {
    val age: Int = 5
    val isSchoolStarted: Boolean = false

    if (1 <= age && age <= 6 && !isSchoolStarted) {
      println("幼児です")
    } else {
      println("幼児ではありません")
    }
  }

  def practice2(): Unit = {
    var i = 0

    do {
      println(i)
      i += 1
    } while (i < 10)
  }

  def indexOf(array: Array[String], target: String): Int = {
    var index = -1
    var found = false
    var i = 0

    while (i < array.length && !found) {
      if (array(i) == target) {
        index = i
        found = true
      }

      i += 1
    }

    index
  }

  def indexOf2(array: Array[String], target: String): Int = {
    var i = 0

    while (i < array.length) {
      if (array(i) == target) return i
      i += 1
    }

    -1
  }

  def practice3(): Unit = {
    for (x <- 1 to 1000; y <- 1 to 1000; z <- 1 to 1000) {
      if (x * x == y * y + z * z) {
        printf("x, y, z, = %d, %d, %d\n", x, y, z)
      }
    }
  }

  def practice4(): Unit = {
    var i = 0
    while (i < 1000) {
      val randStrList = new scala.util.Random(new java.security.SecureRandom()).alphanumeric.take(5).toList

      randStrList match {
        case List(a, b, c, d, e) if a == e =>
          printf("%d: %s, %s, %s, %s, %s\n", i, a, b, c, d, e)
          i += 1

        case _ =>
        // nothing
      }
    }
  }
}
