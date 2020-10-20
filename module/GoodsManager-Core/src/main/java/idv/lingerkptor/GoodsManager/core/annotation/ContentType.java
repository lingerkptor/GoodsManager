package idv.lingerkptor.GoodsManager.core.annotation;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;

import idv.lingerkptor.GoodsManager.core.Analyzable;
import idv.lingerkptor.GoodsManager.core.AnalyzeJson;
import idv.lingerkptor.GoodsManager.core.SendJson;
import idv.lingerkptor.GoodsManager.core.Sendable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;

/**
 * 請求型態 ex: application/json; http/text
 * 
 * @author lingerkptor
 *
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface ContentType {
	public enum RequestType {
		Json("application/json", AnalyzeJson.class);

		private String value = null;
		private Class<? extends Analyzable> analyzeObj = null;

		RequestType(String value, Class<? extends Analyzable> analyzable) {
			this.value = value;
			this.analyzeObj = analyzable;
		}

		public String getValue() {
			return value;
		}

		public Analyzable factory() {
			try {
				return analyzeObj.getConstructor().newInstance();
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
			return null;
		}
	};

	public enum ResponceType {
		Json("application/json", SendJson.class);

		private String value = null;

		private Class<? extends Sendable> sendObj = null;

		ResponceType(String value, Class<? extends Sendable> sendObj) {
			this.value = value;
			this.sendObj = sendObj;
		}

		public String getValue() {
			return value;
		}

		public Sendable getObject() {
			try {
				return sendObj.getConstructor().newInstance();
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
			return null;

		}
	}

	public RequestType reqType();

	public ResponceType respType();

}
