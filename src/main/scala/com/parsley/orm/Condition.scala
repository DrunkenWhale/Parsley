package com.parsley.orm

/**
 * default query all columns
 * */
class Condition {

    private[parsley] val sqlString = new StringBuilder()

    override def toString: String = {
        if (sqlString.isEmpty) {
            ""
        } else {
            s" WHERE ${sqlString.result()}"
        }
    }

    def and(condition: Condition): Condition = {
        val res = new Condition()
        res.sqlString.append(s"${condition.sqlString.result} AND ${this.sqlString.result()}")
        res
    }

    def or(condition: Condition): Condition = {
        val res = new Condition()
        res.sqlString.append(s"${condition.sqlString.result} OR ${this.sqlString.result()}")
        res
    }

    def limit(columnNumber: Int): Condition = {
        val res = new Condition()
        res.sqlString.append(s"${this.sqlString.result()} LIMIT $columnNumber")
        res
    }

}

object Condition {
    extension (self: String) {
        def ===(x: Int | Double | String | Long | Boolean | Float | Char): Condition = {
            val res = new Condition()
            res.sqlString.append(s"`$self`='${x.toString}'")
            res
        }
    }
}