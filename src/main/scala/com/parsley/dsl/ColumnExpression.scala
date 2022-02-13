package com.parsley.dsl

import scala.reflect.ClassTag
import scala.collection.mutable
import com.parsley.dsl.ColumnAttribute
import com.parsley.dsl.ColumnExpressionResult

implicit class ColumnExpression[T](xs: T)(implicit ClassTagT: ClassTag[T]) {

    protected val typeName = ClassTagT.runtimeClass.getSimpleName
    protected val attributes: mutable.Seq[ColumnAttribute] = mutable.Seq[ColumnAttribute]()

    def is(attributes: ColumnAttribute*): ColumnExpressionResult = {
        return ColumnExpressionResult(typeName, attributes)
    }

}

private object ColumnExpression {
    
}

protected case class ColumnExpressionResult(columnType: String, attributes: Seq[ColumnAttribute])