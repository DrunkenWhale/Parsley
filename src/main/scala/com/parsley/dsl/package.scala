package com.parsley

package object dsl {
    /**************** attribute value *******************/

    val primaryKey = PrimaryKeyAttribute()
    val index = IndexAttribute()
    val autoIncrement = AutoIncrementAttribute()
    val nullable = NullableAttribute()
    val unique = UniqueAttribute()

    /*************************************/

    def is():Unit={

    }

    def on(): Unit ={

    }
}
