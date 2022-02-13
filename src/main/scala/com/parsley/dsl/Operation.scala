package com.parsley.dsl

import sourcecode.Text
import com.parsley.dsl.ColumnExpressionResult

object Operation {

    def declare(columns: Text[ColumnExpressionResult]*): Seq[ColumnBody] = {
        columns.map(x =>
            new ColumnBody(
                columnName = x.source,
                columnType = x.value.columnType,
                attributes = x.value.attributes,
            )
        )
    }

}
