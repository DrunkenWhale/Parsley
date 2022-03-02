import com.parsley.connect.DataBaseManager
import com.parsley.connect.connection.MysqlConnection
import scala.util.Random

import com.parsley.orm.DSL.*
import com.parsley.orm.Table

case class Person(name: String = "114514", age: Int)

@main def test1(): Unit = {
    DataBaseManager.register(MysqlConnection(database = "parsely", password = "3777777"))
    val person = Person("野兽前辈", 114514)
    val persons = table[Person]
    on(persons)(person => declare(
        person.name is PrimaryKey
    ))
    println((query("age" === 114514 and "name" === "野兽先辈" limit 1) from persons))
    println((query(limit(114514)) from persons))
    create(persons)
    val a = update("age"==>114514) where * into persons
    println(a)
//    delete(*) in persons
}

@main def test2(): Unit = {

}