package com.parsley

import com.parsley.orm.DSL.{declare, on, table}

import java.sql.{Date, Time, Timestamp}
import com.parsley.orm.{Attribute, Table}

case class Person(name: String="114514", age: Int){
}
import com.parsley.orm.DSL.is

@main def test1(): Unit = {
    val person = Person("秀吉", 114514)
    val persons = table[Person]
    on(persons)(person => declare(
        person.name is Attribute.Indexed
    ))

}

@main def test2(): Unit ={}