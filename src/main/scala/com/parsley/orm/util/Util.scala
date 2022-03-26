package com.parsley.orm.util

private[parsley] object Util {
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
   * @param x => must be a case class 
   * @tparam T x's type
   * @return a tuple seq,the first element in tuple is field name,and second element in tuple is field's value
   *         for example:
   *          case class Java(big:Boolean, boringLevel:Long)
   *          val java = Java(True, 114514)
   *          println(getNameValueSeqFromCaseClass(java))
   *          // print Vector((big,True),(boringLevel,114514))
   */
  def getNameValueSeqFromCaseClass[T <: Product](x: T): Seq[(String, Any)] = {
    val elementTuple = x.asInstanceOf[Tuple]
    val elementLength = elementTuple.productArity
    for (i <- 0 until elementLength) yield (elementTuple.productElementName(i), elementTuple.productElement(i))
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
