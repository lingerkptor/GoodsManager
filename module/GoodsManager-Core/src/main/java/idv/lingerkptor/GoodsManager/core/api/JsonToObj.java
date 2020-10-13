package idv.lingerkptor.GoodsManager.core.api;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/JsonToObj")
public class JsonToObj extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7283377271529964002L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		StringBuffer json = new StringBuffer();
		String line = null;
		BufferedReader reader = req.getReader();
		while ((line = reader.readLine()) != null) {
			json.append(line);
		}
		Gson gson = new Gson();
		Object obj = gson.fromJson(json.toString(), req.getAttribute("class").getClass());
		req.setAttribute("obj", obj);
	}

}
