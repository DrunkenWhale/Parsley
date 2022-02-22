package com.parsley

import com.parsley.macroImpl.primaryConstructor

case class A(name:String)

@main def main(): Unit ={
    println(primaryConstructor[A])
}