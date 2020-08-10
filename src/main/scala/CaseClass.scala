object CaseClass {
  def main(args: Array[String]): Unit = {

  }

  sealed abstract class DayOfWeek

  case object Sunday extends DayOfWeek

  case object Monday extends DayOfWeek

  case object Tuesday extends DayOfWeek

  case object Wednesday extends DayOfWeek

  case object Thursday extends DayOfWeek

  case object Friday extends DayOfWeek

  case object Saturday extends DayOfWeek

  val x: DayOfWeek = Sunday

  println(
    x match {
      case Sunday => 1
      case Monday => 2
      case Tuesday => 3
      case Wednesday => 4
      case Thursday => 5
      case Friday => 6
      case Saturday => 7
    }
  )

  sealed abstract class Exp

  case class Add(lhs: Exp, rhs: Exp) extends Exp

  case class Sub(lhs: Exp, rhs: Exp) extends Exp

  case class Mul(lhs: Exp, rhs: Exp) extends Exp

  case class Div(lhs: Exp, rhs: Exp) extends Exp

  case class Lit(value: Int) extends Exp

  val example: Add = Add(Lit(1), Div(Mul(Lit(2), Lit(3)), Lit(2)))

  def eval(exp: Exp): Int = exp match {
    case Add(l, r) => eval(l) + eval(r)
    case Sub(l, r) => eval(l) - eval(r)
    case Mul(l, r) => eval(l) * eval(r)
    case Div(l, r) => eval(l) / eval(r)
    case Lit(v) => v
  }

  println(eval(example))

  case class Point(x: Int, y: Int)

  val p = Point(1, 2)
  println(p.x, p.y)
  println(Point(1, 3) == Point(1, 3))
  println(Point(1, 3) == Point(1, 4))

  // case classを簡単に記述すると以下のようになる
  class Point2(val x: Int, val y: Int) {
    override def equals(obj: Any): Boolean = obj match {
      case thatPoint: Point =>
        thatPoint.canEqual(this) && this.x == thatPoint.x && this.y == thatPoint.y
      case _ =>
        false
    }

    override def hashCode(): Int = x.hashCode() ^ y.hashCode()

    def canEqual(that: Any): Boolean = that.isInstanceOf[Point]
  }

  object Point2 {
    def apply(x: Int, y: Int): Point2 = new Point2(x, y)
  }

  def nextDayOfWeek(d: DayOfWeek): DayOfWeek = {
    d match {
      case Sunday => Monday
      case Monday => Tuesday
      case Tuesday => Wednesday
      case Wednesday => Thursday
      case Thursday => Friday
      case Friday => Saturday
      case Saturday => Sunday
    }
  }

  println(nextDayOfWeek(Sunday))

  sealed abstract class Tree
  case class Branch(value: Int, left: Tree, right: Tree) extends Tree
  case object Empty extends Tree

  val tree: Tree = Branch(1, Branch(2, Empty, Empty), Branch(3, Empty, Empty))

  def max(tree: Tree): Int = {
    tree match {
      case Branch(v, Empty, Empty) =>
        v
      case Branch(v, l, Empty) =>
        val maxVal = max(l)
        if (v > maxVal) v
        else maxVal
      case Branch(v, Empty, r) =>
        val maxVal = max(r)
        if(v > maxVal) v
        else maxVal
      case Branch(v, l, r) =>
        val lmaxVal = max(l)
        val rmaxVal = max(r)

        var maxVal: Int = 0
        if (lmaxVal > rmaxVal) maxVal = lmaxVal
        else maxVal = rmaxVal

        if(v > maxVal) v
        else maxVal
      case Empty => 0
    }
  }

  println(max(tree))
}
