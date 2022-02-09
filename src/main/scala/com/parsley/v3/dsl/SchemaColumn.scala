package com.parsley.v3.dsl

class SchemaColumn(
                      val columnType: String,
                      val columnName: String,
                      val primaryKey: Boolean,
                      val index: Boolean,
                      val unique: Boolean,
                      val nullable: Boolean) {
    def getColumnSQL(): String = {
        s"$columnName $columnType " //TODO finish mapping
    }
}
