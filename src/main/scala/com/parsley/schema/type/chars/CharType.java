package com.parsley.schema.type.chars;

import com.parsley.schema.type.TypeBasic;

@TypeBasic
public @interface CharType {
    int size() default 255;
}
