package com.parsley

import com.parsley.schema.chars.{CharType, VarCharType}

import java.lang.reflect.Field
import com.parsley.schema.{AutomaicIncrease, Column, PrimaryKey, ClassToSchema, Unique}

import scala.annotation.Annotation

class ConvertClassToSchema {

}

object ConvertClassToSchema {
    def create(clazz: Class[_]): String = {

        //******************************************* schema name ********************************************

        // if this class not in any class
        // emmm will be exception

        val schemaName = getSchemaName(clazz)

        //******************************************* schema column *******************************************

        val fieldsArray = clazz.getDeclaredFields
        val columnStringBuilder = new StringBuilder
        for (field <- fieldsArray) {
            columnStringBuilder.append(mapFieldToSqlSentence(field))
        }
        val columnString = columnStringBuilder.result()
        //        clazz.getName.toLowerCase

        for (field <- fieldsArray){
            if (field.getDeclaredAnnotation(classOf[PrimaryKey])!=null){
                return "CREATE TABLE IF NOT EXISTS " + schemaName + "(\n" +
                    columnString + " PRIMARY KEY ( " + field.getName +" ) "
                    +  "\n);"
            }
        }
        throw Exception("can't create table: " + clazz.getName + "  =>  " +"illegal annoation! please primary key annotation is exist")
    }

    private val getSchemaName = (clazz: Class[_]) => {
        clazz.getDeclaredAnnotation(classOf[ClassToSchema])
        match {
            case null => getPackageSplitArray(clazz)
            case x =>
                if (x.schemaName() == "") getPackageSplitArray(clazz)
                else x.schemaName().toLowerCase()
        }
    }

    private val getPackageSplitArray = (clazz: Class[_]) => {
        val packageSplitArray = clazz.getName.split("\\.")
        packageSplitArray(packageSplitArray.length - 1).toLowerCase()
    }

    private val mapFieldToSqlSentence = (field: Field) =>
        val column = field.getDeclaredAnnotation(classOf[Column])
        if (column == null) {
            ""
        } else {
            val columnName = if (column.name() == "") field.getName() else column.name()
            val columnNullable = if (column.nullable()) "" else "NOT NULL"
            val columnUnique = if (field.getDeclaredAnnotation(classOf[Unique]) != null) "UNIQUE" else ""
            columnName + " " + (
                field.getType.getName.toString match
                    case "java.lang.String" =>
                        if (field.getDeclaredAnnotation(classOf[CharType]) != null) {
                            "CHAR(" + field.getDeclaredAnnotation(classOf[CharType]).size().toString + ")"
                        } else if (field.getDeclaredAnnotation(classOf[VarCharType]) != null) {
                            "VARCHAR(" + field.getDeclaredAnnotation(classOf[CharType]).size().toString + ")"
                        } else {
                            "TEXT"
                        }
                    case "char" => "CHAR(1)"
                    case "int" => "INT " + (if (field.getDeclaredAnnotation(classOf[AutomaicIncrease]) != null) "AUTO_INCREMENT " else "")
                    case "long" => "BIGINT " + (if (field.getDeclaredAnnotation(classOf[AutomaicIncrease]) != null) "AUTO_INCREMENT " else "")
                    case "float" => "FLOAT"
                    case "double" => "DOUBLE"
                    case x => throw Exception(s"this SQL data type: $x is not be implemented")

                ) + " " + columnUnique + " " + columnNullable + ",\n"
        }

}
