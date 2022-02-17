package com.parsley

import java.lang.reflect.Constructor
import scala.reflect.ClassTag

package object util {

    // for the interest syntax "xxx.xxx is xxx"
    // I create this method
    // although I know this is not the best method
    def fakeInstance[T](implicit clazz: ClassTag[T]): T = {
        val construct = clazz.runtimeClass.getDeclaredConstructors.head
        val paramSeq = construct.getParameterTypes.map(x => injectUnmeaningfulParamWithType(x.getSimpleName)).toSeq
        getInstanceWithParamSeq(construct, paramSeq).asInstanceOf[T]
    }

    def realInstance[T](paramSeq:Seq[Any])(implicit clazz: ClassTag[T]): T = {
        val construct = clazz.runtimeClass.getDeclaredConstructors.head
        getInstanceWithParamSeq(construct,paramSeq).asInstanceOf[T]
    }

    private def injectUnmeaningfulParamWithType(typeName: String) = typeName match {
        case "int" => 0
        case "long" => 0L
        case "float" => 0.0
        case "double" => 0.0
        case "boolean" => true
        case "String" => ""
        case "char" => '0'
        case x => null
        //        case x => throw Exception(s" type: $x not be implement ")
    }

    private def getInstanceWithParamSeq(constructor: Constructor[_], parameter: Seq[Any]) = {
        parameter.size match {
            case 0 => constructor.newInstance()
            case 1 => constructor.newInstance(parameter(0))
            case 2 => constructor.newInstance(parameter(0), parameter(1))
            case 3 => constructor.newInstance(parameter(0), parameter(1), parameter(2))
            case 4 => constructor.newInstance(parameter(0), parameter(1), parameter(2), parameter(3))
            case 5 => constructor.newInstance(parameter(0), parameter(1), parameter(2), parameter(3), parameter(4))
            case 6 => constructor.newInstance(parameter(0), parameter(1), parameter(2), parameter(3), parameter(4), parameter(5))
            case 7 => constructor.newInstance(parameter(0), parameter(1), parameter(2), parameter(3), parameter(4), parameter(5), parameter(6))
            case 8 => constructor.newInstance(parameter(0), parameter(1), parameter(2), parameter(3), parameter(4), parameter(5), parameter(6), parameter(7))
            case 9 => constructor.newInstance(parameter(0), parameter(1), parameter(2), parameter(3), parameter(4), parameter(5), parameter(6), parameter(7), parameter(8))
            case 10 => constructor.newInstance(parameter(0), parameter(1), parameter(2), parameter(3), parameter(4), parameter(5), parameter(6), parameter(7), parameter(8), parameter(9))
            case 11 => constructor.newInstance(parameter(0), parameter(1), parameter(2), parameter(3), parameter(4), parameter(5), parameter(6), parameter(7), parameter(8), parameter(9), parameter(10))
            case 12 => constructor.newInstance(parameter(0), parameter(1), parameter(2), parameter(3), parameter(4), parameter(5), parameter(6), parameter(7), parameter(8), parameter(9), parameter(10), parameter(11))
            case 13 => constructor.newInstance(parameter(0), parameter(1), parameter(2), parameter(3), parameter(4), parameter(5), parameter(6), parameter(7), parameter(8), parameter(9), parameter(10), parameter(11), parameter(12))
            case 14 => constructor.newInstance(parameter(0), parameter(1), parameter(2), parameter(3), parameter(4), parameter(5), parameter(6), parameter(7), parameter(8), parameter(9), parameter(10), parameter(11), parameter(12), parameter(13))
            case 15 => constructor.newInstance(parameter(0), parameter(1), parameter(2), parameter(3), parameter(4), parameter(5), parameter(6), parameter(7), parameter(8), parameter(9), parameter(10), parameter(11), parameter(12), parameter(13), parameter(14))
            case 16 => constructor.newInstance(parameter(0), parameter(1), parameter(2), parameter(3), parameter(4), parameter(5), parameter(6), parameter(7), parameter(8), parameter(9), parameter(10), parameter(11), parameter(12), parameter(13), parameter(14), parameter(15))
            case 17 => constructor.newInstance(parameter(0), parameter(1), parameter(2), parameter(3), parameter(4), parameter(5), parameter(6), parameter(7), parameter(8), parameter(9), parameter(10), parameter(11), parameter(12), parameter(13), parameter(14), parameter(15), parameter(16))
            case 18 => constructor.newInstance(parameter(0), parameter(1), parameter(2), parameter(3), parameter(4), parameter(5), parameter(6), parameter(7), parameter(8), parameter(9), parameter(10), parameter(11), parameter(12), parameter(13), parameter(14), parameter(15), parameter(16), parameter(17))
            case 19 => constructor.newInstance(parameter(0), parameter(1), parameter(2), parameter(3), parameter(4), parameter(5), parameter(6), parameter(7), parameter(8), parameter(9), parameter(10), parameter(11), parameter(12), parameter(13), parameter(14), parameter(15), parameter(16), parameter(17), parameter(18))
            case 20 => constructor.newInstance(parameter(0), parameter(1), parameter(2), parameter(3), parameter(4), parameter(5), parameter(6), parameter(7), parameter(8), parameter(9), parameter(10), parameter(11), parameter(12), parameter(13), parameter(14), parameter(15), parameter(16), parameter(17), parameter(18), parameter(19))
            case 21 => constructor.newInstance(parameter(0), parameter(1), parameter(2), parameter(3), parameter(4), parameter(5), parameter(6), parameter(7), parameter(8), parameter(9), parameter(10), parameter(11), parameter(12), parameter(13), parameter(14), parameter(15), parameter(16), parameter(17), parameter(18), parameter(19), parameter(20))
            case 22 => constructor.newInstance(parameter(0), parameter(1), parameter(2), parameter(3), parameter(4), parameter(5), parameter(6), parameter(7), parameter(8), parameter(9), parameter(10), parameter(11), parameter(12), parameter(13), parameter(14), parameter(15), parameter(16), parameter(17), parameter(18), parameter(19), parameter(20), parameter(21))
            case _ => throw Exception("Illeagl Arguments")
        }
    }
}
