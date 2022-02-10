package com.parsley.dsl

class SchemaColumn(val columnType: String,
                   val columnName: String,
                   val primaryKey: Boolean = false,
                   val unique: Boolean = false,
                   val index: Boolean = false,
                   val nullable: Boolean = false) {
    
    def getColumnSQL(): String = {
        s"$columnName $columnType " //TODO finish mapping
    }

    

}
