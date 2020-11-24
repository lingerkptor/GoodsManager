package idv.lingerkptor.GoodsManager.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import idv.lingerkptor.GoodsManager.core.Listener.MessageInit;
import idv.lingerkptor.GoodsManager.core.Message.Message;
import idv.lingerkptor.GoodsManager.core.api.request.Request;

public class AnalyzeJson implements Analyzable {

	@Override
	public Request analyze(HttpServletRequest req, Class<? extends Request> requestObjClass) {
		Gson gson = new Gson();
		try {
			return gson.<Request>fromJson(req.getReader(), requestObjClass);
		} catch (JsonSyntaxException e) {
			MessageInit.getMsgManager().deliverMessage(new Message(Message.Category.warn,
					"Json 語句不正確，原因為： " + e.getCause().getClass() + "：" + e.getCause().getMessage()));
			e.printStackTrace();
		} catch (Exception e) {
			MessageInit.getMsgManager().deliverMessage(new Message(Message.Category.warn,
					"其他例外，原因為： " + e.getCause().getClass() + "：" + e.getCause().getMessage()));
			e.printStackTrace();
		}
		return this.createRequestInstance(requestObjClass);
	}

	private Request createRequestInstance(Class<? extends Request> requestClass) {
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
		return requestObj;
	}

}
