package idv.lingerkptor.GoodsManager.core;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class AnalyzeJson implements Analyzable {

	@SuppressWarnings("unchecked")
	@Override
	public <T> T analyze(HttpServletRequest req, Class<T> requestObjClass) {
//		StringBuffer json = new StringBuffer();
//		String line = null;
//		BufferedReader reader;
//		try {
//			reader = req.getReader();
//			while ((line = reader.readLine()) != null) {
//				json.append(line);
//			}
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
		Gson gson = new Gson();
		try {
//			return (T) gson.fromJson(json, requestObjClass);
			return gson.<T>fromJson(req.getReader(), requestObjClass);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("object is null.");
		return null;
	}


}
