package idv.lingerkptor.GoodsManager.core;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class JsonToObj  {


	public static void formJson(HttpServletRequest req) throws ServletException, IOException {
		StringBuffer json = new StringBuffer();
		String line = null;
		BufferedReader reader = req.getReader();
		while ((line = reader.readLine()) != null) {
			json.append(line);
		}
		Gson gson = new Gson();
		try {
			System.out.println(json.toString());
			@SuppressWarnings({ "rawtypes", "unchecked" })
			Object obj = gson.fromJson(json.toString(), (Class) req.getAttribute("class"));
			req.setAttribute("obj", obj);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
	}

}
