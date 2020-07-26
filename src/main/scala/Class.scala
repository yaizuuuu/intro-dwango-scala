object Class {
  def main(args: Array[String]): Unit = {
    new APrinter().print()

    new BPrinter().print()

    val p = new PracticePoint(10, 20, 30)

    println(p.x)
    println(p.y)
    println(p.z)

    var shape: Shape = new Rectangle(10.0, 20.0)
    println(shape.area)

    shape = new Circle(2.0)
    println(shape.area)
  }
}

// クラスのフィールドの定義
//class Point(_x: Int, _y: Int) {
//  val x = _x
//  val y = _y
//}

// コンストラクトに定義する事もできる
//class Point(val x: Int, val y: Int)

// var | val で定義されたフィールドは外部からアクセス可能
class Point(val x: Int, val y: Int) {
  def +(p: Point): Point = {
    new Point(x + p.x, y + p.y)
  }

  override def toString: String = "(" + x + ", " + y + ")"
}

// val p1 = new Point(1, 1)
// (1, 1)
// val p2 = new Point(2, 2)
// (2, 2)
// val p3 = p1 + p2
// (3, 3)
// https://qiita.com/tag1216/items/d0c5970f840c9ff30925#%E3%83%A1%E3%82%BD%E3%83%83%E3%83%89%E5%91%BC%E3%81%B3%E5%87%BA%E3%81%97%E3%81%AE%E6%9B%B8%E3%81%8D%E6%96%B9%E4%B8%80%E8%A6%A7
// Scalaのメソッド呼び出しは他言語と比べるとちょっとクセがある

class Adder {
  def add(x: Int)(y: Int): Int = x + y
  // 下記と変換可能
  // def add(x: Int)(y: Int): Int = {
  //   x + y
  // }
}

// val adder = new Adder()
// val res1 = adder.add(2)(3)
// res1: Int = 5

// https://qiita.com/yotsak/items/c7db219fd90248288841
// Scalaにおけるカリー化
// val fun = adder.add(2) _
// fun: Int => Int = <function>
// val res2 = fun(3)
// res2: Int = 5

// 同様の実装を行いたい場合、違う書き方もできる
class Adder2 {
  def add(x: Int, y: Int): Int = x + y
}

// val adder2 = new Adder2
// val res3 = adder2.add(2, 3)
// res3: Int = 5

// val fun2 = adder2.add(2, _)
// fun: Int => Int = <function>
// val res4 = fun2(3)
// res4: Int = 5

abstract class XY {
  def x: Int
  def y: Int
}

 class APrinter() {
   def print(): Unit = {
     println("A")
   }
 }

class BPrinter extends APrinter {
  override def print(): Unit = {
    println("B")
  }
}

class PracticePoint(val x: Int, val y: Int, val z: Int)

abstract class Shape {
  def area: Double
}

class Rectangle(w: Double, h: Double) extends Shape {
  override def area: Double = w * h
}

class Circle(r: Double) extends Shape {
  override def area: Double = r * r * scala.math.Pi
}
