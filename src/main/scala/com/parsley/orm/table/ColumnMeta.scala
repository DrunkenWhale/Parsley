package com.parsley.orm.table

protected class ColumnMeta(val name: String) {

}

private object ColumnMeta {

    extension (self: ColumnMeta) {
        @scala.annotation.targetName("is")
        def ==>(attributes: Attribute*): Unit = {

        }
    }

}
