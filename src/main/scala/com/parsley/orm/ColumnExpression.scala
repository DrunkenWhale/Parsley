package com.parsley.orm

private class ColumnExpression(val name: String, val tpe: String, val attribute: Seq[Attribute]) {

    def generateSQLSentence(): String = {
        s"$name $tpe " +
            s"${
                attribute
                    .filterNot(x => x == Attribute.Indexed)
                    .map(x => ColumnExpression.attributeMappingToSQL(x)).mkString(" ")
            }"
    }

    def isIndexColumn(): Boolean = {
        attribute.contains(Attribute.Indexed)
    }

    override def toString: String = {
        s"ColumnExpression($name, $tpe, $attribute)"
    }

}

protected object ColumnExpression {

    def apply(name: String, tpe: String, attributes: Seq[Attribute]): ColumnExpression =
        new ColumnExpression(name, typeMappingToSQL(tpe), attributes)

    private def attributeMappingToSQL(attribute: Attribute): String = {
        attribute match {
            case Attribute.PrimaryKey => "PRIMARY KEY"
            case Attribute.Unique => "UNIQUE"
            case Attribute.AutoIncrement => "AUTO_INCREMENT"
            case Attribute.NotNull => "NOT NULL"
            case x => throw Exception(s"$x is not implement")
        }
    }

    /**
     * mapping scala type to sql tpye
     *
     * @param: tpe => support these scala type and they will be mapping like this:
     *
     *         scala.Int               =>   INT
     *         scala.Double            =>   DOUBLE
     *         scala.Long              =>   BIGINT
     *         scala.Float             =>   FLOAT
     *         scala.Predef.String     =>   CHAR(128)
     *         scala.Boolean           =>   BOOLEAN
     *         java.sql.Date           =>   DATE
     *         java.sql.Time           =>   TIME
     *         java.sql.Timestamp      =>   TIMESTAMP
     * */
    private def typeMappingToSQL(tpe: String): String = {
        tpe match {
            case "scala.Int" => "INT"
            case "scala.Double" => "DOUBLE"
            case "scala.Long" => "BIGINT"
            case "scala.Float" => "FLOAT"
            case "scala.Predef.String" => "CHAR(128)"
            case "scala.Boolean" => "BOOLEAN"
            case "java.sql.Date" => "DATE"
            case "java.sql.Time" => "TIME"
            case "java.sql.Timestamp" => "TIMESTAMP"
            case x => throw Exception(s"$x")
        }
    }

}
