package com.parsley

import scala.reflect.*

package object dsl {

    def on[T](operation: (T => (Seq[SchemaColumn]))) = {

    }

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

    /** ************************* Type : Primary Key / Unique / ... ************************************************ */

    type Attribute = Int

    val PrimaryKey: Attribute = 1
    val Index: Attribute = 2
    val Unique: Attribute = 3
    val Nullable: Attribute = 4
}
