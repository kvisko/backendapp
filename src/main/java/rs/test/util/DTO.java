package rs.test.util;

import java.lang.annotation.RetentionPolicy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(java.lang.annotation.ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface DTO {
	
	Class value();

}
