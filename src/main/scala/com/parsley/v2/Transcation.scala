package com.parsley.v2

import com.parsley.v1.schema.PrimaryKey
import com.parsley.v2.Transcation.convertClassToSchema

import scala.collection.mutable
import scala.quoted.Type
import scala.reflect.{ClassTag, classTag}


/**
 * In this version
 * T type must be a case class
 * */
object Transcation {
    private val convertClassToSchema: String => String = (dataType: String) => dataType match {
        case "Integer" => "INT"
        case "Long" => "BIGINT"
        case "Float" => "FLOAT"
        case "Double" => "DOUBLE"
        case "Boolean" => "INT"
        case "String" => "CHAR(255)"
        case "Character" => "CHAR(1)"
        case "Text" => "TEXT"
        case x => throw Exception(s" type: $x not be implement ")
    }
}

class Transcation(val obj: AnyRef) {

    val schemaName = obj.asInstanceOf[Product].productPrefix

    def query() = {

    }

    def create(primaryKey: String, uniqueColumns: Seq[String] = Seq(), nullableColumns: Seq[String] = Seq()): String = {

        val caseClass = obj.asInstanceOf[Product]

        val uniqueFieldSet = uniqueColumns.toSet[String]

        val nullableFieldSet = nullableColumns.toSet[String]

        var columnsSentence: String = ""

        for (index <- 0 until caseClass.productArity) {
            val columnName = caseClass.productElementName(index)
            val columnValue = caseClass.productElement(index)
            columnsSentence +=
                (s"$columnName ${convertClassToSchema(columnValue.getClass.getSimpleName)} "
                    + s"${if (nullableFieldSet.contains(columnName)) "NOT NULL" else ""} "
                    + s"${if (uniqueFieldSet.contains(columnName)) "UNIQUE" else ""} ,\n")
        }

        val sqlSentence: String =
            s"""
               |CREATE TABLE $schemaName (
               |$columnsSentence
               |PRIMARY KEY ( $primaryKey )
               |)""".stripMargin

        return sqlSentence
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