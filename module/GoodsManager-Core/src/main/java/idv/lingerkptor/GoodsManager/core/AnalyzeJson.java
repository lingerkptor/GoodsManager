package idv.lingerkptor.GoodsManager.core;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import idv.lingerkptor.GoodsManager.core.api.request.Request;

public class AnalyzeJson implements Analyzable {

	@Override
	public Request analyze(HttpServletRequest req, Class<? extends Request> requestObjClass) {
		Gson gson = new Gson();
		try {
			return gson.<Request>fromJson(req.getReader(), requestObjClass);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("object is null.");
		return null;
	}

}
