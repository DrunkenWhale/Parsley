package com.parsley.orm

enum Attribute(SQL: String) {

    case PrimaryKey extends Attribute("PRIMARY KEY")
    case Indexed extends Attribute("INDEX")
    case Unique extends Attribute("UNIQUE")
    case AutoIncrement extends Attribute("AUTO INCREMENT")
    case NotNull extends Attribute("NOT NULL")
    //    case DefaultValue[T](x: T) extends Attribute
    // etc
    // :p
}