package com.parsley.schema;

import com.parsley.schema.type.TypeBasic;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @param 
 *      true  => can be null
 *      false => can't be null   
*/
@TypeBasic
public @interface Column {
    public boolean nullable() default true;
}
