package idv.lingerkptor.GoodsManager.core.annotation;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;

import idv.lingerkptor.GoodsManager.core.Analyzable;
import idv.lingerkptor.GoodsManager.core.AnalyzeJson;
import idv.lingerkptor.GoodsManager.core.AnalyzeMultiPart;
import idv.lingerkptor.GoodsManager.core.AnalyzeTextPlain;
import idv.lingerkptor.GoodsManager.core.SendJson;
import idv.lingerkptor.GoodsManager.core.Sendable;
import idv.lingerkptor.GoodsManager.core.SendFile;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;

/**
 * 請求型態 ex: application/json
 *
 * @author lingerkptor
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface ContentType {

	public enum RequestType {
		// 在這裡註冊
//		Json("application/json", AnalyzeJson.class),
//		MultiPart("multipart/form-data", AnalyzeMultiPart.class),
//		Text_Plain("text/plain", AnalyzeTextPlain.class);
		Json(AnalyzeJson.class), MultiPart(AnalyzeMultiPart.class),
		Text_Plain(AnalyzeTextPlain.class);

		/**
		 * 請求的關鍵字
		 */
//		private String key = null;
		/**
		 * 對應的分析法
		 */
		private Class<? extends Analyzable> analyzeObj = null;

		RequestType(Class<? extends Analyzable> analyzable) {
			this.analyzeObj = analyzable;
		}
//		
//		RequestType(String key, Class<? extends Analyzable> analyzable) {
//			this.key = key;
//			this.analyzeObj = analyzable;
//		}
//
//		public String getKey() {
//			return key;
//		}

		/**
		 * 建立請求的分析方法
		 *
		 * @param <T> 請求的主體類別
		 * @return 請求內容的分析法
		 */
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

	public enum ResponseType {
		// 在這裡註冊
		Json(SendJson.class)// JSON方式寄送
		, File(SendFile.class);// 檔案方式寄送

		private Class<? extends Sendable> sendObj = null;

		ResponseType(Class<? extends Sendable> sendObj) {
			this.sendObj = sendObj;
		}

		public Sendable factory() {
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

	public ResponseType respType();

}
