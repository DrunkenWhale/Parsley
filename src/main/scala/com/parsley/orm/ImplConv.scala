package com.parsley.orm

object ImplConv {
    given convertToMeta: Conversion[String, ColumnMeta] = new ColumnMeta(_)
}
