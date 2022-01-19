package com.parsley.schema;

public @interface CaseClassToSchema {
    public String schemaName() default "";
}
