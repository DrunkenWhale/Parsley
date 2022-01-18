package com.parsley

import java.lang.reflect.Field
import com.parsley.schema.Table

import scala.annotation.Annotation

class ConvertClassToSchema {

}

object ConvertClassToSchema {
    def create(clazz: Class[_]): String = {
        clazz.getDeclaredFields.foreach(x => println(x.getType.getName.toString + " ||| " + x.getName))
        clazz.getDeclaredFields.foreach(x => x.getDeclaredAnnotations.foreach(y => println("+++" + y + "\n")))
        clazz.getAnnotations.foreach(x => println(x.annotationType()))

        //******************************************* schema name ********************************************

        // if this class not in any class
        // emmm will be exception

        val schemaName = getSchemaName(clazz)

        //******************************************* schema column *******************************************

        val fieldsArray = clazz.getDeclaredFields
        for (field <- fieldsArray) {
            val annotations = field.getDeclaredAnnotations
            field.getDeclaredAnnotation()
        }
        //        clazz.getName.toLowerCase


        "CREATE TABLE IF NOT EXISTS" + schemaName + "("

        +");"
    }

    private val getSchemaName = (clazz: Class[_]) => {
        clazz.getDeclaredAnnotation(classOf[Table])
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

    private val mapFieldToSqlSentence = (field: Field) => field.toString match
        case "java.lang.String" => "TEXT"
        case "int" => "INT"
        case "long" => "BIGINT"
        case "float" => "FLOAT"
        case "double" => "DOUBLE"
        case _ => throw Exception("Unknown SQL Type")

}
