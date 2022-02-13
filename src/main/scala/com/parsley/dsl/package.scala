package com.parsley

import sourcecode.Text

package object dsl {
    /** ************** attribute value ****************** */

    val primaryKey = PrimaryKeyAttribute()
    val index = IndexAttribute()
    val autoIncrement = AutoIncrementAttribute()
    val nullable = NullableAttribute()
    val unique = UniqueAttribute()

    /** ********************************** */

   
    def on(): Unit = {

    }
}
