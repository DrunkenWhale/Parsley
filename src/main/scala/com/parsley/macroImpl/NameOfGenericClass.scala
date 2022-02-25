package com.parsley.macroImpl

import scala.quoted.*

inline def nameOf[T]: String = $ {
    nameOfImpl[T]
}

private def nameOfImpl[T](using quotes: Quotes, tpe: Type[T]): Expr[String] = {
    import quotes.reflect.*
    Expr(TypeTree.of[T].symbol.name)
}
