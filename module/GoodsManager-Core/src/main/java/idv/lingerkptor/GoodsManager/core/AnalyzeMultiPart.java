package idv.lingerkptor.GoodsManager.core;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import idv.lingerkptor.GoodsManager.core.api.request.Request;

public class AnalyzeMultiPart implements Analyzable {
	public enum InputType {
		file, text, checkbox
	}

	@Override
	public Request analyze(HttpServletRequest req, Class<? extends Request> requestObjClass) {
		Request requestObj = null;
		System.out.println("requestObj new Instance start");
		try {
			Constructor<? extends Request> constructor = (Constructor<? extends Request>) requestObjClass
					.getConstructor();
			requestObj = constructor.newInstance();
		} catch (NoSuchMethodException e) {// 找不到該Constructor
			e.printStackTrace();
		} catch (SecurityException e) {// 安全性例外
			e.printStackTrace();
		} catch (InstantiationException e) {// 如果宣告時，底層的class是抽象類別，就會拋出
			e.printStackTrace();
		} catch (IllegalAccessException e) {// 存取權限錯誤
			e.printStackTrace();
		} catch (IllegalArgumentException e) {// 參數錯誤
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// 如果Constructor內拋出異常沒有被捕捉到就跳到這裡(通常會發生在Method或Constructor
			e.printStackTrace();
		}
		System.out.println("requestObj new Instance end");

		try {
			for (Part part : req.getParts()) {
				String partName = part.getName();
				System.out.println("part:" + partName);
				try {
					Method setAttr = requestObj.getClass().getMethod("setAttribute", String.class,
							Object.class);
					if (part.getContentType() != null) {
						setAttr.invoke(requestObj, partName, part);
					} else {
						setAttr.invoke(requestObj, partName, req.getParameter(partName));
					}
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}

			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
		return requestObj;
	}
}
