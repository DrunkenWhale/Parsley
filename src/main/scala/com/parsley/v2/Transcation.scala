package com.parsley.v2

import com.parsley.v2.annotation.Text

import scala.quoted.Type
import scala.reflect.{ClassTag, classTag}


/**
 * In this version
 * T type must be a case class
 * */
object Transcation {
    private val convertClassToSchema = (dataType: String) => dataType match {
        case "Integer" => "INT"
        case "Long" => "BIGINT"
        case "Float" => "FLOAT"
        case "Double" => "DOUBLE"
        case "Boolean" => "INT" // 1 True 0 False
        case "String" => "CHAR(255)"
        case "Character" => "CHAR(1)"
    }
}

implicit class Transcation(x: AnyRef) {

    def query() = {
        x.asInstanceOf[Product].productIterator.foreach(x=>println(x.getClass.getAnnotation(classOf[Text])))//.map(x => x.getClass.getSimpleName)
    }

    //    def update(): Boolean = {
    //
    //    }
    //
    //    def insert(): Boolean = {
    //
    //    }
    //
    //    def delete(): Boolean = {
    //
    //    }


}