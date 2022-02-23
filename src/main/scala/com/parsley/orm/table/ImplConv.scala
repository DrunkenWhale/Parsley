package com.parsley.orm.table



object ImplConv {
    given Conversion[String, ColumnMeta] = new ColumnMeta(_)
}
