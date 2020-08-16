object Implicit {
  def main(args: Array[String]): Unit = {
    // この記述で暗黙の型変換を行っているため、下記のif文でコンパイルエラーにならない
    implicit def intToBool(arg: Int): Boolean = arg != 0

    if (1) {
      println("1は真なり")
    }

    // class RichString(val src: String) {
    //   def smile: String = src + ":-)"
    // }
    // implicit def enrichString(arg: String): RichString = new RichString(arg)
    // class構文で書くこともできる
    implicit class RichString(val src: String) {
      def smile: String = src + ":-)"
    }

    println("Hi, ".smile)

    implicit class Tap[T](self: T) {
      def tap[U](block: T => U): T = {
        block(self)
        self
      }
    }

    "Hello, World!".tap(s => println(s)).reverse.tap(s => println(s))

//    trait Additive[A] {
//      def plus(a: A, b: A): A
//
//      def zero: A
//    }
//
//    def sum[A](list: List[A])(m: Additive[A]) = list.foldLeft(m.zero)((x, y) => m.plus(x, y))
//
//    object StringAdditive extends Additive[String] {
//      def plus(a: String, b: String): String = a + b
//      def zero = ""
//    }
//
//    object IntAdditive extends Additive[Int] {
//      def plus(a: Int, b: Int): Int = a + b
//      def zero = 0
//    }
//
//    println(sum(List(1,2,3))(IntAdditive), sum(List("A", "B", "C"))(StringAdditive))

    trait Additive[A] {
      def plus(a: A, b: A): A
      def zero: A
    }

    implicit object StringAdditive extends Additive[String] {
      def plus(a: String, b: String): String = a + b
      def zero = ""
    }

    implicit object IntAdditive extends Additive[Int] {
      def plus(a: Int, b:Int): Int = a + b
      def zero = 0
    }

    def sum[A](lst: List[A])(implicit m: Additive[A]) = lst.foldLeft(m.zero)((x, y) => m.plus(x, y))

    println(sum(List(1,2,3)), sum(List("a", "b", "c")))

    List(1,2,3).sum
  }
}
