import com.parsley.connect.DataBaseManager
import com.parsley.connect.connection.MysqlConnection

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
    println((query(limit(114514)) from persons))
    create(persons)
    val a = update("age" ==> 114514) into persons
    //    delete(*) in persons
}


case class Student(name: String, age: Int)

case class Book(name: String, age: Int)

@main def test2(): Unit = {
    DataBaseManager.register(MysqlConnection(database = "parsely", password = "3777777"))
    val students = table[Student]
    val books = table[Book]
    on(students)(student => declare(
        student.name is primaryKey
    ))
    on(books)(book => declare(
        book.name is primaryKey
    ))
    students <== books
    books <== students
    create(students)
    create(books)
    val student = Student("反射魔典", 514)
//    students.insert(student)
    val list = students.queryRelation(books)(student)
    list.foreach(println)
}

object DataBase {
    val students = table[Student]
    on(students)(student => declare(
        student.name is primaryKey
    ))
}