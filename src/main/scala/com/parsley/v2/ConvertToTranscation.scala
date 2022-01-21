package com.parsley.v2

import java.sql.DriverManager

implicit class ConvertToTranscation(x:AnyRef) {
    def transcation() ={
        new Transcation(x)
    }
}

