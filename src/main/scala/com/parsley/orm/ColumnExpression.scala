package com.parsley.orm

private class ColumnExpression(val name: String, val tpe: String, val attribute: Seq[Attribute]) {
    def generateSQLSentence(): String = {
        s"$name $tpe ${attribute.filterNot(x => x == Attribute.Indexed).mkString}"
    }

    def isIndexColumn(): Boolean = {
        attribute.contains(Attribute.Indexed)
    }
}

object ColumnExpression {

    def apply(name: String, tpe: String, attributes: Seq[Attribute]): ColumnExpression =
        new ColumnExpression(name, tpe, attributes)

    private def attributeMappingToSQL(attribute: Attribute): String = {
        attribute match {
            case Attribute.PrimaryKey => "PRIMARY KEY"
            case Attribute.Unique => "UNIQUE"
            case Attribute.AutoIncrement => "AUTO_INCREMENT"
            case Attribute.NotNull => "NOT NULL"
            case x => throw Exception(s"$x is not implement")
        }
    }

    private def typeMappingToSQL(tpe: String): String = {
        tpe match {
            case _=>""
        }
    }

}
