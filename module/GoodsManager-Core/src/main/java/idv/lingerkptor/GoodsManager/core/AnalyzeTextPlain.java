package idv.lingerkptor.GoodsManager.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;

import idv.lingerkptor.GoodsManager.core.api.request.Request;

public class AnalyzeTextPlain implements Analyzable {

	@Override
	public Request analyze(HttpServletRequest req, Class<? extends Request> requestClass) {
		Request requestObj = null;
		try {
			Constructor<? extends Request> constructor = (Constructor<? extends Request>) requestClass
					.getConstructor();
			requestObj = constructor.newInstance();
		} catch (NoSuchMethodException e) {// 找不到該Constructor
			e.printStackTrace();
			return requestObj;
		} catch (SecurityException e) {// 安全性例外
			e.printStackTrace();
			return requestObj;
		} catch (InstantiationException e) {// 如果宣告時，底層的class是抽象類別或是沒有相對應的建構式，就會拋出
			e.printStackTrace();
			return requestObj;
		} catch (IllegalAccessException e) {// 存取權限錯誤
			e.printStackTrace();
			return requestObj;
		} catch (IllegalArgumentException e) {// 參數錯誤
			e.printStackTrace();
			return requestObj;
		} catch (InvocationTargetException e) {
			// 如果Constructor內拋出異常沒有被捕捉到就跳到這裡(通常會發生在Method或Constructor
			e.printStackTrace();
			return requestObj;
		}
		Enumeration<String> paraNames = req.getParameterNames();
		String name = null;
		try {
			while ((name = paraNames.nextElement()) != null) {
				Field field = requestObj.getClass().getDeclaredField(name);
				String value = req.getParameter(name);
				try {
					Class<?> fieldType = field.getType();
					Object fieldObj = null;

					switch (fieldType.getTypeName()) {// 原形基本型態
					case "int":
						field.setInt(requestObj, Integer.valueOf(value));
						break;
					case "boolean":
						field.setBoolean(requestObj, Boolean.valueOf(value));
						break;
					case "double":
						field.setDouble(requestObj, Double.valueOf(value));
						break;
					case "float":
						field.setFloat(requestObj, Float.valueOf(value));
						break;
					case "char":
						field.setInt(requestObj, value.charAt(0));
						break;
					case "short":
						field.setShort(requestObj, Short.valueOf(value));
						break;
					case "byte":
						field.setInt(requestObj, Integer.valueOf(value));
						break;
					case "long":
						field.setLong(requestObj, Long.valueOf(value));
						break;
					default:
						/**
						 * 因為req.getParameter(name)出來的都是String，而我們沒辦法確定Field的類別，
						 * 如果直接指定會有錯誤(ex:Field是Integer傳入String物件會出錯)
						 * 但是我們也沒辦法確定Field的類別，是Boolean、String、Integer...等等，
						 * 應該正確使用該類別的valueOf靜態方法（ex: String.valueOf(req.getParameter(name))
						 * 、Interger.valueOf(req.getParameter(name))...等等）
						 */
						if (fieldType != String.class) {
							try {
								Method valueOfMethod = fieldType.getDeclaredMethod("valueOf",
										String.class);
								fieldObj = valueOfMethod.invoke(null, // 因為是靜態方法所以第一個參數為null
										value);
								field.set(requestObj, fieldObj);
							} catch (NoSuchMethodException e) {// 沒找到對應的方法
								e.printStackTrace();
							} catch (InvocationTargetException e) {// 執行出錯
								e.printStackTrace();
							}
							break;
						} else {// String.valueOf(Object obj)所以不能使用reflection
							field.set(requestObj, value);
						}
					}
				} catch (IllegalArgumentException e) {// 參數錯誤
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		} catch (NoSuchElementException e) {
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return requestObj;

	}

}
