object ErrorHandling {
  def main(args: Array[String]): Unit = {
    val o = Option("hoge")

    println(o.get, o.isEmpty, o.isDefined)

    val o2 = Option(null)

    println(o2.isEmpty, o2.isDefined)

    println(o.getOrElse("nothing"), o2.getOrElse("nothing2"))

    // 例外もgetOrElse内で投げられる
    // o2.getOrElse(throw new RuntimeException("nullは受け入れらません"))

    val s: Option[String] = Some("hoge")

    val result = s match {
      case Some(str) => str
      case None => "none matched"
    }

    println(result)

    println(Some(3).map(_ * 3))

    val n: Option[Int] = None
    println(n.map(_ * 3))

    // Noneであれば、第一引数の処理を実行する
    println(Some(3).fold(throw new RuntimeException)(_ * 3))
    val n2: Option[Int] = None
    // Noneなので、第一引数の例外を投げつける
    // n2.fold(throw new RuntimeException)(_ * 3)

    val v1: Option[Int] = Some(3)
    val v2: Option[Int] = Some(5)

    // このように記述するとOption[Option[Int]]のようにOptionが入れ子になってしまう
    println(v1.map(i1 => v2.map(i2 => i1 * i2)))
    // 最後にflattenを実行することで、入れ子になることを防ぐ(flatMapというそれがひとまとまりになった関数がそもそもある)
    println(v1.map(i1 => v2.map(i2 => i1 * i2)).flatten)
    println(v1.flatMap(i1 => v2.map(i2 => i1 * i2)))

    val v11: Option[Int] = Some(3)
    val v12: Option[Int] = None

    // Noneが途中で入り込んできても成立する
    println(v11.map(i11 => v12.map(i12 => i12 * i11)).flatten)

    val p1: Option[Int] = Some(2)
    val p2: Option[Int] = Some(3)
    val p3: Option[Int] = Some(5)
    val p4: Option[Int] = Some(7)
    val p5: Option[Int] = Some(11)

    println(p1.map(q1 => p2.map(q2 => p3.map(q3 => p4.map(q4 => p5.map(q5 => q1 * q2 * q3 * q4 * q5)).flatten).flatten).flatten).flatten)

    println(p1.flatMap(q1 => p2.flatMap(q2 => p3.flatMap(q3 => p4.flatMap(q4 => p5.map(q5 => q1 * q2 * q3 * q4 * q5))))))

    val v21: Option[Int] = Some(3)
    val v22: Option[Int] = Some(5)
    val v23: Option[Int] = Some(7)

    println(for {
      i1 <- v21
      i2 <- v22
      i3 <- v23
    } yield i1 * i2 * i3)

    val e1: Either[String, Int] = Right(123)
    val e2: Either[String, Int] = Left("abc")

    println(e2 match {
      case Right(i) => println(i)
      case Left(s) => println(s)
    })

    sealed trait LoginError
    // パスワードが間違っている場合のエラー
    case object InvalidPassword extends LoginError
    // nameで指定されたユーザーが見つからない場合のエラー
    case object UserNotFound extends LoginError
    // パスワードがロックされている場合のエラー
    case object PasswordLocked extends LoginError

    case class User(id: Long, name: String, password: String)

    object LoginService {
      def login(name: String, password: String): Either[LoginError, User] = Right(User(123412344, name, password))
    }

    println(LoginService.login(name = "dwango", password = "password") match {
      case Right(user) => println(s"id: ${user.id}")
      case Left(InvalidPassword) => println(s"Invalid Password!")
    })

    val e11: Either[String, Int] = Right(123)
    val e12: Either[String, Int] = Left("a")

    println(e11.map(_ * 2), e12.map(_ * 2))

    def g(): Unit = println("g")

    def f(g: Unit): Unit = println("f")
    // gが実行されてfが実行される
    f(g())

    // 名前渡しパラメータを使うと、変数が実際に使われる箇所まで評価を遅延させる
    // 引数の式が例外を投げるかもしれないので、 try-finally構文の中で評価したい
    // 引数の処理がものすごく計算コストが高いかもしれないが、計算結果を本当に使うかわからないので、使われる箇所で実行したい
    def gg(): Unit = println("gg")

    def ff(g: => Unit): Unit = {
      println("prologue ff")
      g
      println("epilogue ff")
    }

    ff(gg())

    import scala.util.Try

    val v: Try[Int] = Try(throw new RuntimeException("to be caught"))
    // v: Try[Int] = Failure(java.lang.RuntimeException: to be caught)
    // TryはNoFatalのエラーをキャッチすることができる

    val try1 = Try(3)
    // try1: Try[Int] = Success(3)
    val try2 = Try(5)
    val try3 = Try(7)

    println(for {
      i1 <- try1
      i2 <- try2
      i3 <- try3
    } yield i1 * i2 * i3)

    import scala.util.control.NonFatal
    // NonFatalなエラーをまとめて処理できる
    try {

    } catch {
      case NonFatal(e) => println("All NonFatal Error!")
    }

    case class Address(id: Int, name: String, postalCode: Option[String])
    case class User2(id: Int, name: String, addressId: Option[Int])

    val userDatabase: Map[Int, User2] = Map(
      1 -> User2(1, "太郎", Some(1)),
      2 -> User2(2, "太郎", Some(2)),
      3 -> User2(3, "プー太郎", None)
    )

    val addressDatabase: Map[Int, Address] = Map(
      1 -> Address(1, "渋谷", Some("150-0002")),
      2 -> Address(2, "国際宇宙ステーション", None)
    )

    sealed abstract class PostalCodeResult
    case class Success(postalCode: String) extends PostalCodeResult
    sealed abstract class Failure extends PostalCodeResult
    case object UserNotFound2 extends Failure
    case object UserNotHasAddress extends Failure
    case object AddressNotFount extends Failure
    case object AddressNotHasPostalCode extends Failure

//    def getPostalCodeResult(userId: Int): PostalCodeResult = {
//      findUser(userId) match {
//        case Some(user) =>
//          user.addressId match {
//            case Some(addressId) =>
//              findAddress(addressId) match {
//                case Some(address) =>
//                  address.postalCode match {
//                    case Some(postalCode) => Success(postalCode)
//                    case None => AddressNotFount
//                  }
//                case None => AddressNotFount
//              }
//            case None => UserNotHasAddress
//          }
//        case None => UserNotFound2
//      }
//    }
//
//    def findUser(userId: Int): Option[User2] = {
//      userDatabase.get(userId)
//    }
//
//    def findAddress(addressId: Int): Option[Address] = {
//      addressDatabase.get(addressId)
//    }
//
//    println(getPostalCodeResult(1))
//    println(getPostalCodeResult(2))
//    println(getPostalCodeResult(3))
//    println(getPostalCodeResult(4))
//    println(getPostalCodeResult(5))

    def gertPostalCodeResult(userId: Int): PostalCodeResult = {
      (for {
        user <- findUser(userId)
        address <- findAddress(user)
        postalCode <- findPostalCode(address)
      } yield Success(postalCode)).merge
    }

    def findUser(userId: Int): Either[Failure, User2] = {
      userDatabase.get(userId).toRight(UserNotFound2)
    }

    def findAddress(user: User2): Either[Failure, Address] = {
      for {
        addressId <- user.addressId.toRight(UserNotHasAddress)
        address <- addressDatabase.get(addressId).toRight(AddressNotFount)
      } yield address
    }

    def findPostalCode(address: Address): Either[Failure, String] = {
      address.postalCode.toRight(AddressNotHasPostalCode)
    }

    println(gertPostalCodeResult(1))
    println(gertPostalCodeResult(2))
    println(gertPostalCodeResult(3))
    println(gertPostalCodeResult(4))
  }
}
