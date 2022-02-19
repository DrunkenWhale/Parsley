package com.parsley.dsl

import com.parsley.dsl.ColumnBody.scalaTypeMappingToSQL

import scala.reflect.ClassTag

protected case class ColumnBody(val columnName: String, val columnType: String, val attributes: Seq[ColumnAttribute]) {
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
