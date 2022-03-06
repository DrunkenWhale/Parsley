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

}


