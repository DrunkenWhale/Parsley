package com.parsley.dsl

sealed trait ColumnAttribute {
}

protected sealed case class PrimaryKeyAttribute()
    extends ColumnAttribute

protected sealed case class AutoIncrementAttribute()
    extends ColumnAttribute

protected sealed case class IndexAttribute()
    extends ColumnAttribute

protected sealed case class UniqueAttribute()
    extends ColumnAttribute

protected sealed case class NotNullAttribute()
    extends ColumnAttribute

protected object ColumnAttribute {

    def attributeMappingToSQL(attribute: ColumnAttribute): String = {

        attribute match {
            case PrimaryKeyAttribute() => "PRIMARY KEY"
            case IndexAttribute() => "INDEX" // program will never match this  
            case NotNullAttribute() => "NOT NULL"
            case UniqueAttribute() => "UNIQUE"
            case AutoIncrementAttribute() => "AUTO_INCREMENT"
        }

    }

}