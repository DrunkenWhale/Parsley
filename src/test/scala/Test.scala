import com.parsley.connect.DataBaseManager
import com.parsley.connect.connection.MysqlConnection

import scala.util.Random
import com.parsley.orm.DSL.*
import com.parsley.orm.Table

import scala.reflect.ClassTag

@main def test1(): Unit = {
    DataBaseManager.register(MysqlConnection(database = "parsely", password = "3777777"))
    val person = Person("野兽前辈", 114514)
    val persons = table[Person]
    on(persons)(person => declare(
        person.name is PrimaryKey
    ))
    println((query(limit(114514)) from persons))
    create(persons)
    val a = update("age" ==> 114514) into persons
    //    delete(*) in persons
}


case class Person(name: String = "114514", age: Int) {
    val gender: Boolean = true
    lazy val forigenKey = oneToMany()
}



@main def test2(): Unit = {
    DataBaseManager.register(MysqlConnection(database = "parsely", password = "3777777"))
    val persons = table[Person]
    //    on(persons)(person=>declare(
    //        person.name is PrimaryKey
    //    ))
    //    create(persons)
    //    val per = Person("????",114514)
    classOf[Person].getDeclaredFields.filter(x => x.getType.getSimpleName == "boolean").foreach(x => println(x))
    val resultList: List[Person] = query(*) from persons
    persons
    resultList.foreach(x => println(x.forigenKey))
}