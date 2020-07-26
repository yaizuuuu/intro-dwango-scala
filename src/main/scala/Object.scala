object Object {
  def main(args: Array[String]): Unit = {
    val p = Point(10, 30)
    println(p.x, p.y)

    Person.printWeight()
  }

  class Point(val x: Int, val y: Int)

  object Point {
    def apply(x: Int, y: Int): Point = new Point(x, y)
  }

  class Person(name: String, private[this] val age: Int, private val weight: Int)

  object Person {
    def printWeight(): Unit = {
      val taro = new Person("Taro", 20, 70)

      // 同じファイルで定義されたclassとobject名が一致している場合、privateのフィールドにもアクセス兼がある
      // コンパニオンオブジェクトという
      // `private[this]` というよりアクセス権の厳しいフィールドに対してはコンパニオンオブジェクトはアクセスできない
      println(taro.weight)
    }

     // コンパイルエラー
//    def printAge(): Unit = {
//      val taro = new Person("Taro", 20, 70)
//
//      println(taro.age)
//    }
  }
}