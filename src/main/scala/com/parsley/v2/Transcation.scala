package com.parsley.v2

import scala.quoted.Type
import scala.reflect.{ClassTag, classTag}


/**
 * In this version
 * T type must be a case class
 * */
object Transcation {
    private val convertClassToSchema = (dataType: String) => dataType match {
        case "java.lang.Integer" => "INT"
        case "java.lang.Long" => "BIGINT"
        case "java.lang.Float" => "FLOAT"
        case "java.lang.Double" => "DOUBLE"
        case "java.lang.Boolean" => "INT" // 1 True 0 False
        case "java.lang.String" => "CHAR(255)"
        case "java.lang.Character" => "CHAR(1)"
    }
}

implicit class Transcation(x: AnyRef) {

    def query() = {
        x.asInstanceOf[Product].productIterator.map(x => x.getClass.getName match)
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