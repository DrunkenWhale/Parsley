package com.parsley.orm

import java.lang.reflect.Constructor
import scala.reflect.ClassTag

private[parsley] object DataToInstance {

    def instanceFromFakeParamSeq[T <: Product](implicit classTag: ClassTag[T]): T = {
        val construtor = classTag.runtimeClass.getConstructors.head
        val parameterSeq: Seq[Any] = construtor.getParameterTypes.map(x => x.getSimpleName match {
            case "int" => 0
            case "long" => 0L
            case "float" => 0.0
            case "double" => 0.0
            case "boolean" => true
            case "String" => ""
            case "char" => '0'
            case x => null
        })
        getInstanceWithParamSeq(construtor, parameterSeq).asInstanceOf[T]
    }

    def instanceFromParamSeq[T <: Product](parameterSeq: Seq[Any])(implicit classTag: ClassTag[T]): T = {
        val constructor = classTag.runtimeClass.getConstructors.head
        getInstanceWithParamSeq(constructor, parameterSeq).asInstanceOf[T]
    }

    private def getInstanceWithParamSeq(constructor: Constructor[_], parameterSeq: Seq[Any]) = {
        parameterSeq.size match {
            case 0 => constructor.newInstance()
            case 1 => constructor.newInstance(parameterSeq(0))
            case 2 => constructor.newInstance(parameterSeq(0), parameterSeq(1))
            case 3 => constructor.newInstance(parameterSeq(0), parameterSeq(1), parameterSeq(2))
            case 4 => constructor.newInstance(parameterSeq(0), parameterSeq(1), parameterSeq(2), parameterSeq(3))
            case 5 => constructor.newInstance(parameterSeq(0), parameterSeq(1), parameterSeq(2), parameterSeq(3), parameterSeq(4))
            case 6 => constructor.newInstance(parameterSeq(0), parameterSeq(1), parameterSeq(2), parameterSeq(3), parameterSeq(4), parameterSeq(5))
            case 7 => constructor.newInstance(parameterSeq(0), parameterSeq(1), parameterSeq(2), parameterSeq(3), parameterSeq(4), parameterSeq(5), parameterSeq(6))
            case 8 => constructor.newInstance(parameterSeq(0), parameterSeq(1), parameterSeq(2), parameterSeq(3), parameterSeq(4), parameterSeq(5), parameterSeq(6), parameterSeq(7))
            case 9 => constructor.newInstance(parameterSeq(0), parameterSeq(1), parameterSeq(2), parameterSeq(3), parameterSeq(4), parameterSeq(5), parameterSeq(6), parameterSeq(7), parameterSeq(8))
            case 10 => constructor.newInstance(parameterSeq(0), parameterSeq(1), parameterSeq(2), parameterSeq(3), parameterSeq(4), parameterSeq(5), parameterSeq(6), parameterSeq(7), parameterSeq(8), parameterSeq(9))
            case 11 => constructor.newInstance(parameterSeq(0), parameterSeq(1), parameterSeq(2), parameterSeq(3), parameterSeq(4), parameterSeq(5), parameterSeq(6), parameterSeq(7), parameterSeq(8), parameterSeq(9), parameterSeq(10))
            case 12 => constructor.newInstance(parameterSeq(0), parameterSeq(1), parameterSeq(2), parameterSeq(3), parameterSeq(4), parameterSeq(5), parameterSeq(6), parameterSeq(7), parameterSeq(8), parameterSeq(9), parameterSeq(10), parameterSeq(11))
            case 13 => constructor.newInstance(parameterSeq(0), parameterSeq(1), parameterSeq(2), parameterSeq(3), parameterSeq(4), parameterSeq(5), parameterSeq(6), parameterSeq(7), parameterSeq(8), parameterSeq(9), parameterSeq(10), parameterSeq(11), parameterSeq(12))
            case 14 => constructor.newInstance(parameterSeq(0), parameterSeq(1), parameterSeq(2), parameterSeq(3), parameterSeq(4), parameterSeq(5), parameterSeq(6), parameterSeq(7), parameterSeq(8), parameterSeq(9), parameterSeq(10), parameterSeq(11), parameterSeq(12), parameterSeq(13))
            case 15 => constructor.newInstance(parameterSeq(0), parameterSeq(1), parameterSeq(2), parameterSeq(3), parameterSeq(4), parameterSeq(5), parameterSeq(6), parameterSeq(7), parameterSeq(8), parameterSeq(9), parameterSeq(10), parameterSeq(11), parameterSeq(12), parameterSeq(13), parameterSeq(14))
            case 16 => constructor.newInstance(parameterSeq(0), parameterSeq(1), parameterSeq(2), parameterSeq(3), parameterSeq(4), parameterSeq(5), parameterSeq(6), parameterSeq(7), parameterSeq(8), parameterSeq(9), parameterSeq(10), parameterSeq(11), parameterSeq(12), parameterSeq(13), parameterSeq(14), parameterSeq(15))
            case 17 => constructor.newInstance(parameterSeq(0), parameterSeq(1), parameterSeq(2), parameterSeq(3), parameterSeq(4), parameterSeq(5), parameterSeq(6), parameterSeq(7), parameterSeq(8), parameterSeq(9), parameterSeq(10), parameterSeq(11), parameterSeq(12), parameterSeq(13), parameterSeq(14), parameterSeq(15), parameterSeq(16))
            case 18 => constructor.newInstance(parameterSeq(0), parameterSeq(1), parameterSeq(2), parameterSeq(3), parameterSeq(4), parameterSeq(5), parameterSeq(6), parameterSeq(7), parameterSeq(8), parameterSeq(9), parameterSeq(10), parameterSeq(11), parameterSeq(12), parameterSeq(13), parameterSeq(14), parameterSeq(15), parameterSeq(16), parameterSeq(17))
            case 19 => constructor.newInstance(parameterSeq(0), parameterSeq(1), parameterSeq(2), parameterSeq(3), parameterSeq(4), parameterSeq(5), parameterSeq(6), parameterSeq(7), parameterSeq(8), parameterSeq(9), parameterSeq(10), parameterSeq(11), parameterSeq(12), parameterSeq(13), parameterSeq(14), parameterSeq(15), parameterSeq(16), parameterSeq(17), parameterSeq(18))
            case 20 => constructor.newInstance(parameterSeq(0), parameterSeq(1), parameterSeq(2), parameterSeq(3), parameterSeq(4), parameterSeq(5), parameterSeq(6), parameterSeq(7), parameterSeq(8), parameterSeq(9), parameterSeq(10), parameterSeq(11), parameterSeq(12), parameterSeq(13), parameterSeq(14), parameterSeq(15), parameterSeq(16), parameterSeq(17), parameterSeq(18), parameterSeq(19))
            case 21 => constructor.newInstance(parameterSeq(0), parameterSeq(1), parameterSeq(2), parameterSeq(3), parameterSeq(4), parameterSeq(5), parameterSeq(6), parameterSeq(7), parameterSeq(8), parameterSeq(9), parameterSeq(10), parameterSeq(11), parameterSeq(12), parameterSeq(13), parameterSeq(14), parameterSeq(15), parameterSeq(16), parameterSeq(17), parameterSeq(18), parameterSeq(19), parameterSeq(20))
            case 22 => constructor.newInstance(parameterSeq(0), parameterSeq(1), parameterSeq(2), parameterSeq(3), parameterSeq(4), parameterSeq(5), parameterSeq(6), parameterSeq(7), parameterSeq(8), parameterSeq(9), parameterSeq(10), parameterSeq(11), parameterSeq(12), parameterSeq(13), parameterSeq(14), parameterSeq(15), parameterSeq(16), parameterSeq(17), parameterSeq(18), parameterSeq(19), parameterSeq(20), parameterSeq(21))
            case _ => throw Exception("Illeagl Arguments")
        }
    }

}
