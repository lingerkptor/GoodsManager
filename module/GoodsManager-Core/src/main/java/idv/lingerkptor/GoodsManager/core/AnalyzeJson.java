package idv.lingerkptor.GoodsManager.core;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class AnalyzeJson implements Analyzable {

	@Override
	public Object analyze(HttpServletRequest req) {
		StringBuffer json = new StringBuffer();
		String line = null;
		BufferedReader reader;
		try {
			reader = req.getReader();
			while ((line = reader.readLine()) != null) {
				json.append(line);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Gson gson = new Gson();
		try {
			@SuppressWarnings({ "unchecked" })
			Object obj = gson.fromJson(json.toString(), (Class<Object>) req.getAttribute("reqclass"));
			return obj;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}

}
