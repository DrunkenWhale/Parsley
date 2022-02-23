package com.parsley

import com.parsley.macroImpl.primaryConstructorParamList
import com.parsley.macroImpl.instanceBySeq

case class A(name:String)

@main def main(): Unit ={
    println(primaryConstructorParamList[A])
}

@main def test1(): Unit ={
    println(instanceBySeq[A](Seq("114514")))
}

