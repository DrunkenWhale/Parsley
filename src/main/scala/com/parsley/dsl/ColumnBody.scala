package com.parsley.dsl

import com.parsley.dsl.ColumnBody.scalaTypeMappingToSQL

import scala.reflect.ClassTag

class ColumnBody[T](xs: sourcecode.Text[T])(implicit ClassTagT: ClassTag[T]) {

    val columnName:String = xs.source
    val columnType:String = scalaTypeMappingToSQL(ClassTagT.runtimeClass.getSimpleName)

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
