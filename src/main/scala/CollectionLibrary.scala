object CollectionLibrary {
  def main(args: Array[String]): Unit = {
    val arr = Array(1, 2, 3, 4)
    println(arr)

    arr(0) = 7
    println(arr, arr.length)

    def swapArray[T](arr: Array[T])(i: Int, j: Int): Unit = {
      val tmp = arr(i)
      arr(i) = arr(j)
      arr(j) = tmp
    }

    println(1 to 5)
    println((1 to 5).toList)
    println(1 until 5)
    println((1 until 5).toList)

    val lst = List(1, 2, 3, 4, 5)
    println(lst, lst(0))

    println(Nil)

    val a1 = 1 :: Nil
    println(a1)
    val a2 = 2 :: a1
    println(a2)
    val a3 = 3 :: a2
    println(a3)

    println(List(1, 2) ++ List(3, 4))
    println(List(1) ++ List(2) ++ (3 :: 4 :: Nil))

    println(List(1, 2, 3, 4).mkString)
    println(List(1, 2, 3, 4).mkString(", "))
    println(List(1, 2, 3, 4).mkString("[", ", ", "]"))

    def joinByComma(start: Int, end: Int): String = {
      (start to end).mkString(",")
    }

    println(joinByComma(1, 9))

    println(List(1, 2, 3).foldLeft(6) { (x, y) =>
      println(x * y)
      x * y
    })

    println(List(List(1), List(2, 3), List(4)).foldLeft(Nil: List[Int])(_ ++ _))

    def reverse[T](list: List[T]): List[T] = {
      list.foldLeft(Nil: List[T])((x: List[T], y: T) => (y :: Nil) ++ x)
    }

    println(reverse(List(1, 2, 3)))

    def sum(list: List[Int]): Int = {
      if (list == Nil) 0
      else list.foldRight(0)((x, y) => x + y)
    }

    println(sum(Nil: List[Int]))

    def mul(list: List[Int]): Int = list.foldRight(1)((x, y) => x * y)

    println(mul(List(1, 2, 3, 4)))

    //    def mkString[T](list: List[T])(seq: String): String = {
    //      list.foldLeft("") { (x, y) =>
    //        x + seq + y.toString
    //      }
    //    }
    //
    //    println(mkString(List(1,2,3,4))(", "))

    def mkString[T](list: List[T])(seq: String): String = list match {
      case Nil => ""
      case x :: xs => xs.foldLeft(x.toString) { (x, y) =>
        x + seq + y
      }
    }

    println(mkString(List(1, 2, 3, 4))(", "))

    println(List(1, 2, 3, 4).map(x => x * 3))

    def map[T, U](list: List[T])(f: T => U): List[U] = {
      reverse(list).foldLeft(Nil: List[U]) { (x, y) =>
        f(y) :: x
      }
    }

    println(map(List(1, 1, 23))(x => x * 2))

    println(List(1, 2, 3, 4, 5).filter(x => x % 2 == 1))

    def filter[T](list: List[T])(f: T => Boolean): List[T] = {
      list.foldLeft(Nil: List[T])((x, y) => {
        if (f(y)) y :: x
        else x
      }).reverse
    }

    println(filter(List(1, 2, 3, 4))(x => x % 2 == 0))

    println(List(1, 2, 3).find(x => x % 2 == 1))

    def find[T](list: List[T])(f: T => Boolean): Option[T] = {
      list match {
        case Nil => None
        case x :: xs =>
          if (f(x)) Some(x)
          else find(xs)(f)
        case _ => None
      }
    }

    println(find(List(1, 2, 3, 4))(x => x == 6))

    println(List(11, 2, 3, 4, 65).takeWhile(x => x != 4))

    def takeWile[T](list: List[T])(f: T => Boolean): List[T] = {
      list match {
        case x :: xs if f(x) =>
          x :: takeWile(xs)(f)
        case _ =>
          Nil
      }
    }

    println(List(1, 2, 3, 4).count(x => x % 2 == 0))

    def count[T](list: List[T])(f: T => Boolean): Int = {
      list.foldLeft(0) { (x, y) => if (f(y)) x + 1 else x }
    }


    def flatMap[T, U](list: List[T])(f: T => List[U]): List[U] = {
      list match {
        case Nil => Nil
        case x :: xs => f(x) ::: flatMap(xs)(f)
      }
    }

    println(Vector(1, 2, 3, 4, 5))
    println(6 +: Vector(1, 2, 3, 4, 5))
    // Listの場合末尾に要素追加すると処理が遅いが、Vectorは高速に処理できる
    println(Vector(1, 2, 3, 4, 5) :+ 6)
    println(Vector(11, 3, 4, 5, 6).updated(2, 5))

    import scala.collection.immutable.Map

    val map2 = Map("A" -> 1, "B" -> 2, "C" -> 3)
    println(map2.updated("C", 4))
    // immutableのためもとのmapが変わるわけではない
    println(map2)

    import scala.collection.immutable.Set

    val set = Set(1, 2, 3, 4, 5)
    println(set)
    println(set - 2)
  }
}
