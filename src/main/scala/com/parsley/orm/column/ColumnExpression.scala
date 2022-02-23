package com.parsley.orm.column

import com.parsley.orm.column.Attribute

import scala.reflect.ClassTag

/**
 * `T` is column type
 * */
class ColumnExpression[T](val name: String,val attribute:Attribute*)(implicit classTag:ClassTag[T]) {
    
}

object ColumnExpression{
    
}
