package com.parsley.orm.column



object ImplConv {
    given Conversion[String, ColumnMeta] = new ColumnMeta(_)
}
