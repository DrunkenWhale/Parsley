package com.parsley

trait SqlSchema{
    val schemaName:String
    val primaryKey:String
}


//object schema extends SqlSchema{
//    override val schemaName: String = "114514"
//    override val primaryKey: String = "id"
//}