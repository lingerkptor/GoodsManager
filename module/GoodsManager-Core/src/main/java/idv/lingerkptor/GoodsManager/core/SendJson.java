package idv.lingerkptor.GoodsManager.core;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class SendJson implements Sendable {

	@Override
	public void send(Object sendObj, HttpServletResponse resp) {
		resp.setContentType("application/json;charset=UTF-8");
		Gson gson = new Gson();
		String json = gson.toJson(sendObj);
		try {
			resp.getWriter().println(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
