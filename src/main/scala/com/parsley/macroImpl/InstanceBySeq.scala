package com.parsley.macroImpl

import scala.quoted.*

/**
 * get a `T` type Instance
 *
 * @version: 0.3
 * @param: seq => params in seq,its order is the same with class `T` 's Primary Constructor
 *         only need values, and don't need any types describe about them
 * @author:Pigeon377
 * @example:
 *
 * case class Student(name:String,age:Int,gender:Boolean)
 *
 * println(instanceBySeq[Student]("野兽前辈",114514,false))
 *
 * // print in console: Student("野兽前辈",114514,false)
 *
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