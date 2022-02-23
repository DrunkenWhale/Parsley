package com.parsley.macroImpl

import scala.quoted.*

/**
 *
 * */
inline def instanceBySeq[T](inline seq: Seq[Any]): T = $ {
    instanceBySeqImpl[T]('seq)
}

private def instanceBySeqImpl[T](x: Expr[Seq[Any]])(using quotes: Quotes, typ: Type[T]): Expr[T] = {
    import quotes.reflect.*
    val tree = x.asTerm
    val typeList = tree match {
        case Inlined(_, _, Apply(_, args)) => args
    }
    val termList = typeList.head match {
        case Typed(Repeated(tl, _), _) => tl
    }
    Apply(
        Select(
            New(TypeTree.of[T]),
            TypeRepr.of[T].typeSymbol.primaryConstructor
        ),
        termList
    ).asExprOf[T]
}