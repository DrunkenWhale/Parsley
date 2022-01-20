package com.parsley.v2

implicit class ConvertToTranscation(x:AnyRef) {
    def transcation() ={
        new Transcation(x)
    }
}
