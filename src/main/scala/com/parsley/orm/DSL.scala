package com.parsley.orm

import scala.reflect.ClassTag

object DSL {

    def declare(columnNameAttributeSeq: (String, Seq[Attribute])*): Seq[(String, Seq[Attribute])] = {
        columnNameAttributeSeq
    }
}
