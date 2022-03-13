package com.parsley.orm.curd.util

private[parsley] object CRUDUtil {
  /**
   * foreach cls's fields
   * get value from special field by field name
   *
   * @param cls  : class which you want to find fields value
   * @param name : field name
   * @return field's value, unknown type
   * @throws throw a exception when can't find field by name
   *
   * */
  def findFieldValueFromClassByName[F <: Product](cls: F, name: String): Any = {
    val size = cls.productArity
    for (i <- 0 to size) {
      if (cls.productElementName(i) == name) {
        return cls.productElement(i)
      }
    }
    throw new Exception(s"can't find $name field from $cls")
  }


  /**
   *
   * ManyToMany RelationShip need a extension relation table
   * give two tables' name which have relationship
   *
   * @return relationTableName
   *
   * */
  def getRelationTableName(table1Name: String, table2Name: String): String = {
    if (table1Name > table2Name) {
      s"${table1Name}_${table2Name}"
    } else {
      s"${table2Name}_${table1Name}"
    }
  }

}
