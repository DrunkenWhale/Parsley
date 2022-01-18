package com.parsley

import java.lang.reflect.Field

class ConvertClassToSchema {

}

object ConvertClassToSchema{
    def create(clazz: Class[_]): Unit ={
        clazz.getDeclaredFields.foreach(x=> println(x.getType.getName.toString + " ||| " + x.getName))

        clazz.getAnnotations.foreach(x=>println(x.annotationType()))

        val fieldsArray = clazz.getDeclaredFields
        val schemaName = clazz.getName.toLowerCase

        for (field <- fieldsArray){
            field.getAnnotations.foreach(x => println(x))
        }
    }

    val mapToSqlDataType = (field:Field)=> field.getType.toString match
        case "java.lang.String" => "TEXT"
        case "int"              => "INT"
        case "long"             => "BIGINT"
        case "float"            => "FLOAT"
        case "double"           => "DOUBLE"

}
