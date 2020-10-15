package idv.lingerkptor.GoodsManager.core.annotation;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;

/**
 * HTTP METHOD
 * 
 * @author lingerkptor
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface ClassName {
	public String value();
}
