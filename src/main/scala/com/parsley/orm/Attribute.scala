package com.parsley.orm

enum Attribute {
    
    case PrimaryKey extends Attribute
    case Indexed extends Attribute
    case Unique extends Attribute
    case AutoIncrement extends Attribute
    case NotNull extends Attribute
//    case DefaultValue[T](x: T) extends Attribute
    // etc
    // :p
}