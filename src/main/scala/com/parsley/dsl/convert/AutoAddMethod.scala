package com.parsley.dsl.convert

import com.parsley.dsl.{Attribute, Index, Nullable, PrimaryKey, SchemaColumn, Unique}

sealed trait AutoAddMethod {
}

implicit class AddNamedMethod(x: Int | String | Double | Long | Float | Boolean | Char) extends AutoAddMethod {
    def named(columnName: String): SchemaColumn = {
        SchemaColumn(
            columnType = x.getClass.getSimpleName,
            columnName = columnName
        )
    }
}

implicit class AddIsMethod(x: SchemaColumn) extends AutoAddMethod {
    def is(attributes: Attribute*): SchemaColumn = {
        SchemaColumn(
            columnType = x.columnType,
            columnName = x.columnName,
            primaryKey = attributes.contains(PrimaryKey),
            unique = attributes.contains(Unique),
            index = attributes.contains(Index),
            nullable = attributes.contains(Nullable)
        )
    }
}
