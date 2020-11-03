package idv.GoodsManager.installation.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import idv.GoodsManager.installation.api.request.UploadDBFilesRequest;
import idv.GoodsManager.installation.api.responce.UploadDBFilesResponce;
import idv.lingerkptor.GoodsManager.core.annotation.ContentType;
import idv.lingerkptor.GoodsManager.core.api.Service;
import idv.lingerkptor.GoodsManager.core.api.request.Request;
import idv.lingerkptor.GoodsManager.core.api.responce.Responce;

@SuppressWarnings("serial")
@WebServlet("/api/UploadDBFiles")
public class UploadDBFiles extends Service {

	@Override
	@ContentType(reqType = ContentType.RequestType.MultiPart, respType = ContentType.ResponceType.Json)
	public Responce process(Request requestObj) {
		UploadDBFilesRequest request = (UploadDBFilesRequest) requestObj;
		if (request.getDBName() == null || request.getJDBC() == null || request.getSQLZIP() == null)
			return UploadDBFilesResponce.uploadFailure();

		return UploadDBFilesResponce.uploadSusscess(request.getDBName(),
				request.getJDBC().getName(), request.getSQLZIP().getName());
	}

	@Override
	protected void configRequestClass() {
		this.requestClass = UploadDBFilesRequest.class;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.operater(req, resp);
	}

}
