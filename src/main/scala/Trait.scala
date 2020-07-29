object Trait {
  def main(args: Array[String]): Unit = {
    println("hello")

    (new ClassAA).greet()
    (new ClassBA).greet()
    (new ClassCC).printBar()
  }

  trait TraitA

  trait TraitB

  class ClassA

  class ClassB

  class ClassC extends ClassA with TraitA with TraitB

  val a = new ClassC

  class ClassD(name: String) {
    def printName(): Unit = println(name)
  }

  trait TraitE {
    val name: String

    def printName(): Unit = println(name)
  }

  class ClassE(val name: String) extends TraitE

  object ObjectE {
    val a = new ClassE("dwango")

    val a2 = new TraitE {
      override val name: String = "kadokawa"
    }
  }

  trait TraitAA {
    def greet(): Unit
  }

  trait TraitAB extends TraitAA {
    // overrideなし
    def greet(): Unit = println("Good morning!")
  }

  trait TraitAC extends TraitAA {
    // overrideなし
    def greet(): Unit = println("Good evening!")
  }

  class ClassAA extends TraitAB with TraitAC {
    // overrideなしでひし形継承してしまうとコンパイルエラーが発生する

    // overrideなしでひし形継承して候補が複数ある場合は以下のように記述する
    // override def greet(): Unit = super[TraitAC].greet()
    // 複数呼び出しを行う場合
    override def greet(): Unit = {
      super[TraitAB].greet()
      super[TraitAC].greet()
    }

    // override def greet(): Unit = println("How are you?")
  }

  trait TraitBA {
    def greet(): Unit
  }

  trait TraitBB extends TraitBA {
    // overrideあり
    override def greet(): Unit = println("Good morning!")
  }

  trait TraitBC extends TraitBA {
    // overrideあり
    override def greet(): Unit = println("Good evening!")
  }

  // 後からミックスインしているTraitBCが優先される
  class ClassBA extends TraitBB with TraitBC

  trait TraitCA {
    val foo: String
  }

  trait TraitCB extends TraitCA {
    val bar = foo + "World"

    // 処理を遅延させることができるlazy
//     lazy val bar = foo + "World"
  }

  class ClassCC extends TraitCB {
    val foo = "Hello"

    def printBar(): Unit = println(bar)
    // scala> "nullWorld"
    // scalaはスーパークラスから順に初期化されるため、TraitCBの時点でbarの値が決定されてしまっている
    // lazy val bar と定義されていた場合は実際に使われるまで初期化が遅延される
    // lazy val は val に比べて処理が若干重く、デッドロック(https://gist.github.com/xuwei-k/7b2be9957222bcb8f184)
    // は発生する可能性もある
  }
}

