package com.parsley

import com.parsley.macroImpl.primaryConstructorParamList
import com.parsley.macroImpl.instanceBySeq
import com.parsley.orm.Attribute

case class A(name:String)

@main def main(): Unit ={
    println(primaryConstructorParamList[A])
}

@main def test1(): Unit ={
    println(instanceBySeq[A](Seq("114514")))
}

@main def test2(): Unit ={
    val index = com.parsley.orm.Attribute.Indexed
    println(index==Attribute.Indexed)
}

