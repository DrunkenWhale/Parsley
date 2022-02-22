package com.parsley.macroImpl

import scala.quoted.*

inline def instanceBySeq[T](seq: Seq[Any]) = $ {instanceBySeqImpl[T](seq)}


private def instanceBySeqImpl[T](seqExpr: Seq[Any])(using quotes: Quotes, typ: Type[T]) = {
    import quotes.reflect.*
    val seq: Seq[?] = seqExpr
    Apply(
        Select(
            New(TypeTree.of[T]),
            TypeRepr.of[T].typeSymbol.primaryConstructor
        ),
        seq.map(x=>Expr(x).asTerm.tree)
    ).asExprOf[T]
}