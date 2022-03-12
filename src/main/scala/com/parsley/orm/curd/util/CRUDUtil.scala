package com.parsley.orm.curd.util

private[parsley] object CRUDUtil {
  /**
   * foreach cls's fields
   * get value from special field by field name
   *
   * @param: cls : class which you want to find fields value
   * @param: name: field name
   * @return: field's value, unknown type
   * @throws: throw a exception when can't find field by name
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
}
