package com.parsley.orm.attribute

class AutoIncrement extends Attribute {
    override private[parsley] val code = 2
    override def sql: String = "AUTO INCREMENT"
}
