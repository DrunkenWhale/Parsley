package com.parsley

import java.lang.reflect.Field
import com.parsley.schema.Table

class ConvertClassToSchema {

}

object ConvertClassToSchema {
    def create(clazz: Class[_]): Unit = {
        clazz.getDeclaredFields.foreach(x => println(x.getType.getName.toString + " ||| " + x.getName))
        clazz.getDeclaredFields.foreach(x => x.getDeclaredAnnotations.foreach(y => println("+++" + y + "\n")))
        clazz.getAnnotations.foreach(x => println(x.annotationType()))

        //******************************************* schema name ********************************************

        // if this class not in any class
        // emmm will be exception
        val packagePrefix = clazz.getPackageName + "."
        val schemaName = clazz.getDeclaredAnnotation(classOf[Table])
        match {
            case null => clazz.getName.split(packagePrefix)(1).toLowerCase()
            case x =>
                if (x.schemaName() == "") clazz.getName.split(packagePrefix)(1).toLowerCase()
                else x.schemaName().toLowerCase()
        }
        println(schemaName + "[]")

        //******************************************* schema column *******************************************

        val fieldsArray = clazz.getDeclaredFields
        fieldsArray.foreach(x=>
            x.getDeclaredAnnotations.foreach(y=>
                println(y.annotationType().getName)
            )
        )
        //        clazz.getName.toLowerCase
    }

    val mapToSqlDataType = (field: Field) => field.getType.toString match
        case "java.lang.String" => "TEXT"
        case "int" => "INT"
        case "long" => "BIGINT"
        case "float" => "FLOAT"
        case "double" => "DOUBLE"

}
