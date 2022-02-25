package com.parsley

import com.parsley.macroImpl.primaryConstructorParamList
import com.parsley.macroImpl.instanceBySeq
import com.parsley.orm.{Attribute, ColumnMeta}
import com.parsley.orm.DSL.*

import java.sql.{Date, Time, Timestamp}

case class A(name: String)

@main def main(): Unit = {
    println(primaryConstructorParamList[A])
}

@main def test1(): Unit = {
    println(instanceBySeq[A](Seq("114514")))
}

@main def test2(): Unit = {
    val index = com.parsley.orm.Attribute.Indexed
    println(index == Attribute.Indexed)
}

@main def test3(): Unit = {
    case class ACM(a: Int, B: Double, c: Long, D: Float, E: String, k: Boolean, f: Date, m: Time, ml: Timestamp)
}

@main def test4(): Unit = {
    inline def create[T](): Unit = {
        println(primaryConstructorParamList[T])
    }

    case class Person(name: String, age: Int)
    create[Person]()
}

import com.parsley.orm.curd.createImpl
import com.parsley.orm.ImplConv.convertToMeta
import com.parsley.orm.Attribute.*

@main def test5(): Unit = {
    case class Person(name: String, age: Int, gender: Boolean)
    println(createImpl[Person](declare(
        "name" ==> (Indexed, PrimaryKey)
    )))
}