import com.parsley.connect.DataBaseManager
import com.parsley.connect.connection.{MysqlConnection, SqliteConnection}

import scala.util.Random
import com.parsley.orm.DSL.*
import com.parsley.orm.Table

import scala.reflect.ClassTag

case class Person(name: String = "114514", age: Int) {
  val gender: Boolean = true
}

@main def test1(): Unit = {
  DataBaseManager.register(MysqlConnection(database = "parsely", password = "3777777"))
  val person = Person("野兽前辈", 114514)
  val persons = table[Person]
  on(persons)(person => declare(
    person.name is primaryKey
  ))
  val a = persons.update("age" ==> 114514)
}


case class Student(name: String, age: Int)

case class Book(name: String, age: Int)

@main def test2(): Unit = {

  DataBase.students.create()
  DataBase.books.create()

  val student = Student("反射魔典", 514)
  val book = Book("红茶", 1145141919)
  DataBase.students.query("age" === 114514 limit 1).foreach(println)
  DataBase.books.query("name" === "下北泽").foreach(println)
  val list = DataBase.students.queryRelation[Book](student)
  list.foreach(println)
}

@main def test3(): Unit = {
  val student = Student("反射魔典", 514)
  val book = Book("红茶", 1145141919)
  DataBase.books.create()
  DataBase.students.create()
  DataBase.students.relatedManyToMany(student)(book)
  DataBase.books.insert(book)
  DataBase.students.insert(student)
}

@main def test4(): Unit = {
  val student = Student("反射魔典", 514)
  val book = Book("红茶", 1145141919)
  DataBase.books.create()
  DataBase.students.create()
  println(DataBase.students.queryManyToManyRelation[Book](student))
}

@main def test5(): Unit = {
  def getNameToValueSeqFromCaseClass[T <: Product](x: T): Seq[(String, Any)] = {
    val elementTuple = x.asInstanceOf[Tuple]
    val elementLength = elementTuple.productArity
    for (i <- 0 until elementLength) yield (elementTuple.productElementName(i), elementTuple.productElement(i))
  }

  case class ASC(name: String, value: Int, age: Long)
  println(getNameToValueSeqFromCaseClass(ASC("野兽前辈", 114, 514)))
}

object DataBase {
  DataBaseManager.register(MysqlConnection(database = "parsley", password = "3777777"))
  val books: Table[Book] = table[Book]
  val students: Table[Student] = table[Student]
  on(students)(student => declare(
    student.name is primaryKey
  ))
  on(books)(book => declare(
    book.name is primaryKey
  ))
  // create One(student)ToMany(book)relation between two tables
  students <==> books
}