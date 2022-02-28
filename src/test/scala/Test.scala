import com.parsley.connect.DataBaseManager
import com.parsley.connect.connection.MysqlConnection
import com.parsley.orm.DSL.{create, declare, insert, on, table}

import java.sql.{Date, Time, Timestamp}
import com.parsley.orm.{Attribute, Table}

case class Person(name: String = "114514", age: Int) {
}
import com.parsley.orm.DSL.*

@main def test1(): Unit = {
    DataBaseManager.register(MysqlConnection(database = "parsely", password = "3777777"))
    val person = Person("秀吉", 114514)
    val persons = table[Person]
    on(persons)(person => declare(
        person.name is Attribute.PrimaryKey
    ))
    create(persons)
    insert(person) in persons
}

@main def test2(): Unit = {

}