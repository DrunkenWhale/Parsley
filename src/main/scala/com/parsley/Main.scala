package com.parsley

import java.sql.{Date, Time, Timestamp}
import com.parsley.orm.Table

case class A(name: String, age: Int){
}

@main def main(): Unit = {
    com.parsley.orm.DSL.table[A].test()
}
