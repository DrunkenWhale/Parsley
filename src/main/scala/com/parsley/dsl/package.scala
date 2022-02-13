package com.parsley

import com.parsley.util.fakeInstance
import sourcecode.Text

import scala.reflect.ClassTag

package object dsl {
    /** ************** attribute value ****************** */

    val primaryKey = PrimaryKeyAttribute()
    val index = IndexAttribute()
    val autoIncrement = AutoIncrementAttribute()
    val nullable = NullableAttribute()
    val unique = UniqueAttribute()

    /** ********************************** */


    def on[T](table: Table[T])(operation: (T => Seq[ColumnBody]))(implicit clazzTag: ClassTag[T]): Unit = {
        operation(fakeInstance[T]).foreach(
            // if one column be declared again, attribute will be replaced with recent attribute
            x => table.columnMap.put(x.columnName, (x.columnType, x.attributes))
        )
    }

    def declare(columns: Text[ColumnExpressionResult]*): Seq[ColumnBody] =
        columns.map(x =>
            new ColumnBody(
                columnName = x.source.split("is")
                    .head.replace(".", " ")
                    .trim.split(" ").last,
                columnType = x.value.columnType,
                attributes = x.value.attributes,
            )
        )

    def table[T]()(implicit ClassTagT: ClassTag[T]): Table[T] = {
        new Table[T](ClassTagT.runtimeClass.getSimpleName)
    }


}
