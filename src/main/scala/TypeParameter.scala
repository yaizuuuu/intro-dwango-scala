import javafx.scene.control.Cell

object TypeParameter {
  def main(args: Array[String]): Unit = {
    val cell = new Cell[Int](1)
    println(cell.get())
    cell put 2
    println(cell.get())

    println(divide(7, 3))

    // 予めPairクラスのようなものが実装されている
    val m = 7
    val n = 3
    val tap2 = new Tuple2(m, n)
    println(tap2)
    // (7, 3)
    // Tupleの後の数字は要素数を表す
    // 例の場合は要素数が2なので、Tuple2(MAXはTuple22まで)
    // またよく使われる記述なため、new Tupleと書く必要がないようにエイリアスが用意されている
    val tap2Alias = (m / n, m % n)
    println(tap2Alias)

    val pair: Pair2[AnyRef, AnyRef] = new Pair2[String, String]("foo", "bar")
    println(pair)
  }

  class Cell[A](var value: A) {
    def put(newValue: A): Unit = {
      value = newValue
    }

    def get(): A = value
  }

  class Pair[A, B](val a: A, val b: B) {
    override def toString(): String = "(" + a + ", " + b + ")"
  }

  def divide(m: Int, n: Int): Pair[Int, Int] = new Pair(m / n, m % n)
  // new Pair(m / n, m % n)のように引数の型が明確であれば、型パラメータは省略できる

  // 型パラメータの前に `+` をつけることによって共変を許可する(共変 = 指定した型を継承していれば、直接指定した型でなくても受け入れること)
  // `+` を取り除くとコンパイルエラーが発生する
  class Pair2[+A, +B](val a: A, val b: B) {
    override def toString: String = "(" + a + ", " + b + ")"
  }

  trait Stack[+A] {
    // E >: Aは、EはAの継承元である、という制約を表しています。
    def push[E >: A](e: E): Stack[E]
    def top: A
    def pop: Stack[A]
    def isEmpty: Boolean
  }

  class NonEmptyStack[+A](private val first: A, private val rest: Stack[A]) extends Stack[A] {
    def push[E >: A](e: E): Stack[E] = new NonEmptyStack[E](e, this)

    def top: A = first

    def pop: Stack[A] = rest

    def isEmpty: Boolean = false
  }

  case object EmptyStack extends Stack[Nothing] {
    def push[E >: Nothing](e: E): Stack[E] = new NonEmptyStack[E](e, this)

    def top: Nothing = throw new IllegalArgumentException("empty stack")

    def pop: Nothing = throw new IllegalArgumentException("empty stack")

    def isEmpty: Boolean = true
  }

  object Stack {
    def apply(): Stack[Nothing] = EmptyStack
  }
}
