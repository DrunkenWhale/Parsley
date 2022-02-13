package com.parsley

import com.parsley.util.fakeInstance

import scala.reflect.ClassTag

package object dsl {
    /** ************** attribute value ****************** */

    val primaryKey = PrimaryKeyAttribute()
    val index = IndexAttribute()
    val autoIncrement = AutoIncrementAttribute()
    val nullable = NullableAttribute()
    val unique = UniqueAttribute()

    /** ********************************** */


    def on[T](operation:(T => Seq[ColumnBody]))(implicit clazzTag: ClassTag[T]): Unit = {
        println(operation(fakeInstance[T]))
    }
}
