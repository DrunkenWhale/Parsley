package com.parsley.v2

import java.sql.DriverManager

implicit class ConvertToTranscation[T](x:T) {
    def transcation() ={
        new Transaction(x)
    }
}

