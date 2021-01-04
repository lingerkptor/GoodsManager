
package idv.lingerkptor.GoodsManager.Operator.api;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import idv.lingerkptor.GoodsManager.Operator.DataAccess.UploadPictureDAO;
import idv.lingerkptor.GoodsManager.Operator.api.request.UploadPictureRequest;
import idv.lingerkptor.GoodsManager.Operator.api.response.UploadPictureResponse;
import idv.lingerkptor.GoodsManager.core.DataAccess.DAORuntimeException;
import idv.lingerkptor.GoodsManager.core.DataAccess.DataAccessCore;
import idv.lingerkptor.GoodsManager.core.DataAccess.PictureManager;
import idv.lingerkptor.GoodsManager.core.Listener.MessageInit;
import idv.lingerkptor.GoodsManager.core.Message.Message;
import idv.lingerkptor.GoodsManager.core.annotation.ContentType;
import idv.lingerkptor.GoodsManager.core.api.Service;
import idv.lingerkptor.GoodsManager.core.api.request.Request;
import idv.lingerkptor.GoodsManager.core.api.response.Response;

/**
 * 
 * 
 * @author lingerkptor
 *
 */
@MultipartConfig
@WebServlet("/api/UploadPicture")
public class UploadPicture extends Service {
	private static final long serialVersionUID = 2711035809341750751L;

	@Override
	@ContentType(reqType = ContentType.RequestType.MultiPart, respType = ContentType.ResponseType.Json)
	public Response process(Request requestObj) {
		UploadPictureRequest request = (UploadPictureRequest) requestObj;
		UploadPictureDAO dao = new UploadPictureDAO(request);
		try {
			DataAccessCore.getSQLTemplate().update(dao);
		} catch (SQLException e) {
			e.printStackTrace();
			return UploadPictureResponse.Code.SQLEXCEPTION.getResponse(null);
		} catch (DAORuntimeException e) {
			MessageInit.getMsgManager()
					.deliverMessage(new Message(Message.Category.warn, e.getMessage()));
			return ((UploadPictureResponse.Code) e.getCode()).getResponse(null);
		}

		return UploadPictureResponse.Code.SECCESS.getResponse(dao.getKeyList());
	}

	@Override
	protected void configRequestClass() {
		this.requestClass = UploadPictureRequest.class;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.operater(req, resp);
	}

}
