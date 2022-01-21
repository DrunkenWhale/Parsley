package com.parsley.v2

import com.parsley.v1.schema.PrimaryKey
import com.parsley.v2.Transcation.{connection, convertClassToSchema}

import java.sql.DriverManager
import scala.collection.mutable
import scala.quoted.Type
import scala.reflect.{ClassTag, classTag}


/**
 * In this version
 * T type must be a case class
 * */
object Transcation {


    Class.forName("com.mysql.cj.jdbc.Driver")

    val connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "3777777")

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

    private val caseClass = obj.asInstanceOf[Product]
    
    private val schemaName = obj.asInstanceOf[Product].productPrefix

    private val caseClassColumnsSet: Seq[(String, Any)] =
        for (index <- 0 until caseClass.productArity) yield {
            val columnName = caseClass.productElementName(index)
            val columnValue = caseClass.productElement(index)
            (columnName, columnValue)
        }

    def query(constraint: String) = {
        val parameterSeq = caseClassColumnsSet.map(x=>x._2)
        val c = Class.forName("scala.Tuple" + caseClassColumnsSet.size).getConstructors.head.newInstance()
        println(c)
        println(c.getClass.getSimpleName)

        println (obj.getClass.getConstructors.head.newInstance())
//        val resultInterator = connection.prepareStatement(s"SELECT * FROM $schemaName " + constraint).executeQuery()
//        while (resultInterator.next()) {
//
//        }

    }

    def create(primaryKey: String, uniqueColumns: Seq[String] = Seq(), nullableColumns: Seq[String] = Seq()): Unit = {

        val uniqueFieldSet = uniqueColumns.toSet[String]

        val nullableFieldSet = nullableColumns.toSet[String]

        var columnsSentence: String = ""

        for ((name, value) <- caseClassColumnsSet) {
            columnsSentence +=
                (s"$name ${convertClassToSchema(value.getClass.getSimpleName)} "
                    + s"${if (!nullableFieldSet.contains(name)) "NOT NULL" else ""} "
                    + s"${if (uniqueFieldSet.contains(name)) "UNIQUE" else ""} ,\n")
        }

        val sqlSentence: String =
            s"""
               |CREATE TABLE $schemaName (
               |$columnsSentence
               |PRIMARY KEY ( $primaryKey )
               |);""".stripMargin

        connection.prepareStatement(sqlSentence).execute();
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