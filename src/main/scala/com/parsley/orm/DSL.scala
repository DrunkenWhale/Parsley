package com.parsley.orm

import scala.reflect.ClassTag

object DSL {
    def table[T <: Product](implicit classTag: ClassTag[T]) = Table.apply[T]
}
