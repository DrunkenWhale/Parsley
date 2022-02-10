package com.parsley.dsl

object Operation {
    
    def declare(columns: SchemaColumn*):Seq[SchemaColumn] = {
        columns
    }

    implicit class AddNamedMethod[T](x: T) {
        def named(columnName: String): SchemaColumn = {
            SchemaColumn(
                columnType = x.getClass.getSimpleName,
                columnName = columnName
            )
        }
    }

    implicit class AddIsMethod(x: SchemaColumn) {
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
}
