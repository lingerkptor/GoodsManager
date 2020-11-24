package idv.lingerkptor.GoodsManager.core.test.multipart;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import idv.lingerkptor.GoodsManager.core.annotation.ContentType;
import idv.lingerkptor.GoodsManager.core.annotation.ContentType.RequestType;
import idv.lingerkptor.GoodsManager.core.annotation.ContentType.ResponseType;
import idv.lingerkptor.GoodsManager.core.api.Service;
import idv.lingerkptor.GoodsManager.core.api.request.Request;
import idv.lingerkptor.GoodsManager.core.api.response.Response;

@MultipartConfig
@WebServlet("/api/testMultiPart")
public class TestMultipart extends Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1537960356654410381L;

	@Override
	@ContentType(reqType = RequestType.MultiPart, respType = ResponseType.Json)
	public Response process(Request requestObj) {
		TestMultiPartRequest request = (TestMultiPartRequest) requestObj;
		System.out.println(request.getDescription());
		try {
			if (request.getTestFile().exists()) {
				System.out.println("file OK!");
			} else
				return TestMultiPartResponse.FAILURE();
		} catch (Exception e) {
			return TestMultiPartResponse.FAILURE();
		}
		System.out.println("boolean = >" + request.isTestboolean());
		return TestMultiPartResponse.SUCESS();
	}

	@Override
	protected void configRequestClass() {
		this.requestClass = TestMultiPartRequest.class;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("post start");
		this.operater(req, resp);
		System.out.println("post end");
	}

}
