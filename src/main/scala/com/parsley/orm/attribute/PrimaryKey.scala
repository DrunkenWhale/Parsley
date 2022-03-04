package com.parsley.orm.attribute

class PrimaryKey extends Attribute {
    
    override private[parsley] val code = 1

    override private[parsley] def sql: String = "PRIMARY KEY"
    
}
