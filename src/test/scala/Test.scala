import com.parsley.connect.DataBaseManager
import com.parsley.connect.connection.MysqlConnection
import com.parsley.orm.DSL.{declare, on, table}

import java.sql.{Date, Time, Timestamp}
import com.parsley.orm.{Attribute, Table}

case class Person(name: String = "114514", age: Int) {
}
import com.parsley.orm.DSL.is

@main def test1(): Unit = {
    DataBaseManager.register(MysqlConnection(database = "parsely", password = "3777777"))
    val person = Person("秀吉", 114514)
    val persons = table[Person]
    on(persons)(person => declare(
        person.name is Attribute.PrimaryKey
    ))
    persons.create()
    persons.insert(person)
}

@main def test2(): Unit ={

}