package com.parsley.orm.table

import com.parsley.orm.table.Attribute

import scala.reflect.ClassTag

/**
 * `T` is column type
 * */
class ColumnExpression[T](val name: String,val attribute:Attribute*)(implicit classTag:ClassTag[T]) {
    
}

object ColumnExpression{
    
}
