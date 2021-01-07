package idv.lingerkptor.GoodsManager.Operator.api;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import idv.lingerkptor.GoodsManager.Operator.api.request.GetPictureRequest;
import idv.lingerkptor.GoodsManager.Operator.api.response.GetPictureResponse;
import idv.lingerkptor.GoodsManager.core.DataAccess.PictureManager;
import idv.lingerkptor.GoodsManager.core.annotation.ContentType;
import idv.lingerkptor.GoodsManager.core.api.Service;
import idv.lingerkptor.GoodsManager.core.api.request.Request;
import idv.lingerkptor.GoodsManager.core.api.response.Response;

@WebServlet("/api/img")
public class GetPicture extends Service {
	private static final long serialVersionUID = 5205966325613765463L;

	@Override
	@ContentType(reqType = ContentType.RequestType.Text_Plain, respType = ContentType.ResponseType.File)
	public Response process(Request requestObj) {
		GetPictureRequest request = (GetPictureRequest) requestObj;
		File pic = PictureManager.getPictureManager().getPicture(request.getKey());
		if (pic.exists()) {
			String[] name = pic.getName().split("\\.");
			GetPictureResponse.Code code = // 副檔名去找
					GetPictureResponse.Code.getCode(name[name.length - 1]);
			return code.getResponse(pic);
		}
		return null;
	}

	@Override
	protected void configRequestClass() {
		this.requestClass = GetPictureRequest.class;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.operater(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.operater(req, resp);
	}

}
