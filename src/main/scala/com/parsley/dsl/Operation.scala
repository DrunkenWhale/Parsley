package com.parsley.dsl

object Operation {
    
    def declare(columns: SchemaColumn*):Seq[SchemaColumn] = columns
    
}
