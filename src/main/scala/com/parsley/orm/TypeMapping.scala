package com.parsley.orm

protected object TypeMapping {
  def scalaTypeMappingToSQLType(tpe: String): String =
    tpe match {
      case "int" => "INTEGER"
      case "long" => "BIGINT"
      case "float" => "FLOAT"
      case "double" => "DOUBLE"
      case "boolean" => "BOOLEAN"
      case "String" => "CHAR(128)"
      case "char" => "CHAR(1)"
      case x => throw Exception(s" type: $x not be implement ")
    }
}
