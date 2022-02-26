package com.parsley.macroImpl

import scala.quoted.*

inline def instanceFieldValue[T](inline x: T) :List[Any]= $ {
    instanceFieldValueImpl('x)
}

private def instanceFieldValueImpl[T](x: Expr[T])(using quotes: Quotes, tpe: Type[T]):Expr[List[Any]] = {
    import quotes.reflect.*
    val literalList = x.asTerm match {
        case Inlined(_,_, apply) => apply match {
            case Apply(_,list) => list
        }
    }
    Expr(literalList.map(x=>x.asInstanceOf[Literal].constant.value))
}
