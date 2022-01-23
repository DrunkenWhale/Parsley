package com.parsley.v2.`type`


/**
 * use this class in case class
 * will generator a column with TEXT when
 * create table in database
 * */

class Text(private val string: String) {
    override def toString(): String = string
}

object Text {
    def apply(string: String): Text = {
        new Text(string)
    }
}
