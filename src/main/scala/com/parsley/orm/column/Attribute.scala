package com.parsley.orm.column

enum Attribute(protected val code: Int) {
    
    case PrimaryKey extends Attribute(0)
    case Indexed extends Attribute(1)
    case Unique extends Attribute(2)
    case AutoIncrement extends Attribute(3)
    case NotNull extends Attribute(4)
    case DefaultValue[T](x: T) extends Attribute(5)
    // etc
    // :p
}