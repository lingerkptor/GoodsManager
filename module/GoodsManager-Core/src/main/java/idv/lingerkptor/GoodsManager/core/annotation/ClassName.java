package idv.lingerkptor.GoodsManager.core.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface ClassName {
	public String reqObjClass();
}
