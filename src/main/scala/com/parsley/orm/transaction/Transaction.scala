package com.parsley.orm.transaction

import com.parsley.connect.DataBaseManager

private[parsley] class Transaction {

}

object Transaction {

  def apply(param: Any*): Unit = {
    transaction(param)
  }

  def transaction(param: Any*): Unit = {
    DataBaseManager.commit()
  }
}