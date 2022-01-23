package com.parsley.v2

import com.parsley.v1.schema.PrimaryKey
import com.parsley.v2.Transcation.connection

import java.sql.{DriverManager, ResultSet}
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

}

class Transcation[T](val obj: T) {

    private val caseClass = obj.asInstanceOf[Product]

    private val schemaName = obj.asInstanceOf[Product].productPrefix

    private val caseClassColumnsSet: Seq[(String, Any)] =
        for (index <- 0 until caseClass.productArity) yield {
            val columnName = caseClass.productElementName(index)
            val columnValue = caseClass.productElement(index)
            (columnName, columnValue)
        }

    def create(primaryKey: String, uniqueColumns: Seq[String] = Seq(), nullableColumns: Seq[String] = Seq()): Unit = {

        val uniqueFieldSet = uniqueColumns.toSet[String]

        val nullableFieldSet = nullableColumns.toSet[String]

        var columnsSentence: String = ""

        for ((name, value) <- caseClassColumnsSet) {
            columnsSentence +=
                (s"$name ${TypeMapping.convertClassToSchema(value.getClass.getSimpleName)} "
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

    /**
     * query database to get all the line in this table
     *
     * @return list of given case class
     *
     * */
    def query(): Seq[T] = {
        val parameterTypeSeq = caseClassColumnsSet.map(x => x._2.getClass.getSimpleName)

        val resultSet = connection.prepareStatement(s"SELECT * FROM $schemaName;").executeQuery()

        var caseClassSeq: Seq[T] = Seq[T]()

        while (resultSet.next()) {
            val parameterSeq: Seq[Any] = for (i <- 0 until parameterTypeSeq.size) yield {
                TypeMapping.getColumnFromResultSet(parameterTypeSeq(i), resultSet, i + 1)
            }
            caseClassSeq = caseClassSeq appended (TypeMapping.getCaseClassWithInstance(caseClass, parameterSeq).asInstanceOf[T])
        }
        caseClassSeq
    }

    def insert(): Unit = {
        val tempColumnSeqString = caseClassColumnsSet.map(x => x._1).toString().substring(6)
        val tempQuestionMarkString = (new StringBuilder).append((for (i <- 1 to caseClassColumnsSet.size) yield "?")
            .toString()).delete(0, 6).result()
        val statement = connection.prepareStatement(s"INSERT INTO $schemaName  $tempColumnSeqString   VALUES  $tempQuestionMarkString;")
        for (index <- 0 until caseClassColumnsSet.size) {
            TypeMapping.setColumnFromCaseClass(caseClassColumnsSet(index)._2, statement, index + 1)
        }
        statement.execute()
    }


    /**
     * create table in the database with given case class
     *
     * must given which column is primary key
     *
     * all basic type will be mapping to sql type
     * specially,Text class will be mapping to TEXT
     * String will be mapping to CHAR(255)
     * */


    def update(): Unit = {

    }


    //
    //    def delete(): Boolean = {
    //
    //    }


}