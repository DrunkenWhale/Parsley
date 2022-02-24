package com.parsley.macroImpl

import scala.quoted.*

inline def primaryConstructorParamList[T]: List[(String, String)] = $ {
    primaryConstructorParamListImpl[T]
}

private def primaryConstructorParamListImpl[T](using quotes: Quotes, typed: Type[T]): Expr[List[(String, String)]] = {
    import quotes.reflect.*
    val constructorList = TypeTree.of[T].symbol.primaryConstructor.paramSymss
    if (constructorList.isEmpty) {
        Expr(List[(String, String)]())
        // macro will throw exception when use generic type
        // use this method to avoid compile error
        // emm,may be it's about safety? just like rust(yikes , don't hit me!)
    } else {
        Expr(constructorList.head.map(x => x.tree match {
            case ValDef(name, tp, _) => (name, tp.tpe.show)
            case x => throw new Exception(s"Illegal Constructor => $x")
        }))
    }
}