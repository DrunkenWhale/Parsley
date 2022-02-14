package com.parsley.dsl

import scala.reflect.ClassTag
import scala.collection.mutable
import com.parsley.dsl.ColumnAttribute
import com.parsley.dsl.ColumnExpression.typeNameMappingToSQL
import com.parsley.dsl.ColumnExpressionResult

implicit class ColumnExpression[T](xs: T)(implicit ClassTagT: ClassTag[T]) {

    protected val typeName = typeNameMappingToSQL(ClassTagT.runtimeClass.getSimpleName)
    protected val attributes: mutable.Seq[ColumnAttribute] = mutable.Seq[ColumnAttribute]()

    def is(attributes: ColumnAttribute*): ColumnExpressionResult = {
        return ColumnExpressionResult(typeName, attributes)
    }

}

private object ColumnExpression {

    def typeNameMappingToSQL(typeName: String): String = {

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

protected case class ColumnExpressionResult(columnType: String, attributes: Seq[ColumnAttribute])