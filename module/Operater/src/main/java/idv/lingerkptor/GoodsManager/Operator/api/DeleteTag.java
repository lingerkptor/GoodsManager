package idv.lingerkptor.GoodsManager.Operator.api;

import idv.lingerkptor.GoodsManager.Operator.DataAccess.DeleteTagDAO;
import idv.lingerkptor.GoodsManager.Operator.api.request.DeleteTagRequest;
import idv.lingerkptor.GoodsManager.Operator.api.response.DeleteTagResponse;
import idv.lingerkptor.GoodsManager.core.DataAccess.DAORuntimeException;
import idv.lingerkptor.GoodsManager.core.DataAccess.DataAccessCore;
import idv.lingerkptor.GoodsManager.core.Listener.MessageInit;
import idv.lingerkptor.GoodsManager.core.Message.Message;
import idv.lingerkptor.GoodsManager.core.annotation.ContentType;
import idv.lingerkptor.GoodsManager.core.api.Service;
import idv.lingerkptor.GoodsManager.core.api.request.Request;
import idv.lingerkptor.GoodsManager.core.api.response.Response;
import idv.lingerkptor.util.DBOperator.DataAccessTemplate;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@WebServlet("/api/DeleteTag")
public class DeleteTag extends Service {
    @Override
    @ContentType(reqType = ContentType.RequestType.Json, respType = ContentType.ResponseType.Json)
    public Response process(Request request) {
        DeleteTagRequest deleteTagRequest = (DeleteTagRequest) request;
        if (deleteTagRequest.getTagName().equals(""))
            return DeleteTagResponse.TAGNAMEISEMPTY;
        DataAccessTemplate template = DataAccessCore.getSQLTemplate();
        try {
            template.update(new DeleteTagDAO(deleteTagRequest));
        } catch (SQLException e) {
            return DeleteTagResponse.SQLEXCEPTION;
        } catch (DAORuntimeException e) {
            MessageInit.getMsgManager().deliverMessage(new Message(Message.Category.warn, e.getMessage()));
            return (DeleteTagResponse) e.getCode();
        }
        return null;
    }

    @Override
    protected void configRequestClass() {
        this.requestClass = DeleteTagRequest.class;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        this.operater(request, response);
    }
}
