//package com.parsley.orm
//
//enum Attribute(val SQL: String) {
//
//    case PrimaryKey extends Attribute("PRIMARY KEY")
//    case Indexed extends Attribute("INDEX")
//    case Unique extends Attribute("UNIQUE")
//    case AutoIncrement extends Attribute("AUTO INCREMENT")
//    case NotNull extends Attribute("NOT NULL")
//    //    case DefaultValue[T](x: T) extends Attribute
//    // etc
//    // :p
//
//    // design wrong ,we don't always need SQL
//    case ManyToOne[T <: Product](mainTable: Table[T]) extends Attribute(s"114514")
//}
//
//class s extends Attribute("") {
//    
//}