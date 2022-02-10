package com.parsley

import scala.reflect.*

package object dsl {

    def on[T](operation: (T => (Seq[SchemaColumn]))) = {
    }

    /** ************************* Type : Primary Key / Unique / ... ************************************************ */

    type Attribute = Int

    val PrimaryKey: Attribute = 1
    val Index: Attribute = 2
    val Unique: Attribute = 3
    val Nullable: Attribute = 4
}
