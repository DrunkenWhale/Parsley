package com.parsley.orm

object ImplConv {
    given Conversion[String, ColumnMeta] = new ColumnMeta(_)
}
