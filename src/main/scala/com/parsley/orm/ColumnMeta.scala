package com.parsley.orm

class ColumnMeta(val name: String) {
    def ==>(attributes: Attribute*): (String, Seq[Attribute]) = {
        (this.name, attributes)
    }
}

object ColumnMeta {
}
