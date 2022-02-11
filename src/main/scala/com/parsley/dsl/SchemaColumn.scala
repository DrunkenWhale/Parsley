package com.parsley.dsl

import com.parsley.dsl.SchemaColumn.scalaTypeMappingToSQLType

protected class SchemaColumn(val columnType: String,
                             val columnName: String,
                             val primaryKey: Boolean = false,
                             val unique: Boolean = false,
                             val index: Boolean = false,
                             val nullable: Boolean = false) {
}

object SchemaColumn {
    private def scalaTypeMappingToSQLType(columnType: String): String = {
        columnType match {
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

    def convertSchemaColumnClassToSQL(column: SchemaColumn): String = {
        s"${column.columnName} ${scalaTypeMappingToSQLType(column.columnType)} " +
            (if (column.primaryKey) " PRIMARY KEY " else " ") +
            (if (column.index) " INDEX " else " ") +
            (if (column.nullable) "  " else " NOT NULL ") +
            (if (column.unique) " UNIQUE " else " ")
    }
}