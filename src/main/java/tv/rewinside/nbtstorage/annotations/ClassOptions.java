package tv.rewinside.nbtstorage.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ClassOptions {

	public FilterType filterType() default FilterType.NONE;
	
	public enum FilterType {
		NONE,
		INCLUDE,
		EXCLUDE;
	}
	
}
