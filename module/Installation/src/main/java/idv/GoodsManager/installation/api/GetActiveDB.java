package idv.GoodsManager.installation.api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import idv.GoodsManager.installation.api.request.GetActiveDBRequest;
import idv.GoodsManager.installation.api.response.GetActiveDBResponse;
import idv.lingerkptor.GoodsManager.core.DataAccess.ConfigReader;
import idv.lingerkptor.GoodsManager.core.Listener.MessageInit;
import idv.lingerkptor.GoodsManager.core.Message.Message;
import idv.lingerkptor.GoodsManager.core.annotation.ContentType;
import idv.lingerkptor.GoodsManager.core.api.Service;
import idv.lingerkptor.GoodsManager.core.api.request.Request;
import idv.lingerkptor.GoodsManager.core.api.response.Response;

@WebServlet("/api/getActiveDB")
public class GetActiveDB extends Service {

	private static final long serialVersionUID = 188247525668921582L;

	public static List<DB> getActivedDBList()
			throws JsonIOException, JsonSyntaxException, FileNotFoundException, IOException {
		Gson gson = new Gson();
		Type type = new TypeToken<List<DB>>() {
		}.getType();
		List<DB> activedDBList = null;

		FileReader reader = null;
		try {
			reader = new FileReader(
					new File(ConfigReader.getConfigReader().getWebAddr() + "//sql//activeDB.json"));
			activedDBList = gson.fromJson(reader, type);
		} catch (FileNotFoundException e) {
			throw e;
		} catch (JsonIOException | JsonSyntaxException e) {
			try {
				reader.close();
			} catch (IOException e1) {
				throw e1;
			}
			throw e;
		}
		return activedDBList;
	}

	@Override
	@ContentType(reqType = ContentType.RequestType.Text_Plain, respType = ContentType.ResponseType.Json)
	public Response process(Request requestObj) {

		List<DB> activedDBList = null;

		try {
			activedDBList = GetActiveDB.getActivedDBList();
		} catch (FileNotFoundException e) {
			MessageInit.getMsgManager().deliverMessage(
					new Message(Message.Category.info, "除了預設的SQLite資料庫外，沒有其他的資料庫．"));
			return GetActiveDBResponse.readActivedDBListFail();
		} catch (JsonIOException | JsonSyntaxException e) {
			MessageInit.getMsgManager().deliverMessage(
					new Message(Message.Category.warn, "取得資料失敗．Message: " + e.getMessage()));
			return GetActiveDBResponse.readActivedDBListFail();
		} catch (IOException e) {
			e.printStackTrace();
			return GetActiveDBResponse.readActivedDBListFail();
		}
		return GetActiveDBResponse.sendActivedDBList(this.getActivedDBNameList(activedDBList));
	}

	private List<String> getActivedDBNameList(List<DB> activedDBList) {
		List<String> nameList = new LinkedList<String>();
		activedDBList.stream().forEach(db -> {
			nameList.add(db.getDBName());
		});
		return nameList;
	}

	@Override
	protected void configRequestClass() {
		this.requestClass = GetActiveDBRequest.class;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.operater(req, resp);
	}

}
