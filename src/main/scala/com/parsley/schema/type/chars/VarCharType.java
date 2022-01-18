package com.parsley.schema.type.chars;

import com.parsley.schema.type.TypeBasic;

@TypeBasic
public @interface VarCharType {
    int size() default 65535;
}
