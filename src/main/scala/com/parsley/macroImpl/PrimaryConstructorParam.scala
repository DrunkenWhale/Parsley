package com.parsley.macroImpl

import scala.quoted.*
import scala.reflect.ClassTag

/**
 * 
 * get a map, its element is (`field name`->`field type(scala type)`)
 * 
 * */
inline def primaryConstructorParamMap[T]: Map[String, String] = $ {
    primaryConstructorParamMapImpl[T]
}

private def primaryConstructorParamMapImpl[T](using quotes: Quotes, typed: Type[T]): Expr[Map[String, String]] = {
    import quotes.reflect.*
    Expr(TypeTree.of[T].symbol.primaryConstructor.paramSymss.head.map(x => x.tree match {
        case ValDef(name, tp, _) => (name, tp.tpe.show)
        case x => throw new Exception(s"Illegal Constructor => $x")
    }).toMap)

}