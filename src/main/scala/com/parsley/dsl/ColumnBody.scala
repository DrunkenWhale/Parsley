package com.parsley.dsl

import com.parsley.dsl.ColumnBody.scalaTypeMappingToSQL

import scala.reflect.ClassTag

protected class ColumnBody(val columnName: String, val columnType: String, val attributes: Seq[ColumnAttribute]) {
    
    def is(): Unit = {
        println("succeed" + columnName)
    }

    override def toString: String = {
        s"name: $columnName ,type:$columnType, attriubte: $attributes"
    }

}

private object ColumnBody {

    def scalaTypeMappingToSQL(typeName: String): String = {
        typeName match {
            case "int" => "INT"
            case "long" => "BIGINT"
            case "float" => "FLOAT"
            case "double" => "DOUBLE"
            case "boolean" => "INT"
            case "String" => "CHAR(255)"
            case "char" => "CHAR(1)"
            case x => throw Exception(s" type: $x not be implement ")
        }
    }

}
