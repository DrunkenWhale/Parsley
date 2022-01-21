package com.parsley.v2

import java.lang.reflect.Constructor

object InvokeCaseClass {
    def getCaseClassWithInstance(obj:AnyRef,parameter:Seq[AnyRef]): Product ={
        val constructor: Constructor[_] = obj.getClass.getConstructors.head
        parameter.size match {
            case 1 => constructor.newInstance(parameter(0))
        }
    }
}
