package com.parsley.orm.curd.update

class UpdateOperation {

    private[parsley] val sqlString = new StringBuilder()

    override def toString: String = s"SET ${sqlString.result()}"

    def and(updateOperation: UpdateOperation): UpdateOperation = {
        val res = new UpdateOperation()
        res.sqlString.append(s"${updateOperation.sqlString.result}, ${this.sqlString.result()}")
        res
    }

}

object UpdateOperation {
    
    extension (self: String) {
        def ==>(x: Int | Double | String | Long | Boolean | Float | Char): UpdateOperation = {
            val res = new UpdateOperation
            res.sqlString.append(s"`$self`='${x.toString}'")
            res
        }
    }
    
}


