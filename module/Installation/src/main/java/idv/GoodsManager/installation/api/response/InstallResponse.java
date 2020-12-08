package idv.GoodsManager.installation.api.response;

import javax.servlet.http.HttpSession;

import idv.lingerkptor.GoodsManager.core.api.response.Response;

public class InstallResponse implements Response {
	public enum ERRORCODE {
		GoodsTable, ClassesTable, PictureTable, TagsTable, GoodsTagsTable,
		SQLERROR;
	};

	@SuppressWarnings("unused")
	private boolean buildDBConfig = true;
	@SuppressWarnings("unused")
	private boolean testConnect = true;
	@SuppressWarnings("unused")
	private boolean SQLReading = true;
	@SuppressWarnings("unused")
	private String code = "";
	@SuppressWarnings("unused")
	private boolean importMainConfig = true;

	private InstallResponse() {

	}

	public static InstallResponse buildDBConfigFault() {
		InstallResponse resp = new InstallResponse();
		resp.buildDBConfig = false;
		resp.testConnect = false;
		resp.SQLReading = false;
		resp.importMainConfig = false;
		return resp;
	}

	public static InstallResponse testConnectFault() {
		InstallResponse resp = new InstallResponse();
		resp.testConnect = false;
		resp.SQLReading = false;
		resp.importMainConfig = false;
		return resp;
	}

	public static Response readSQLFileFault() {
		InstallResponse resp = new InstallResponse();
		resp.SQLReading = false;
		resp.importMainConfig = false;
		return resp;
	}

	public static InstallResponse createTableFault(String code) {
		InstallResponse resp = new InstallResponse();
		resp.code = code;
		resp.importMainConfig = false;
		return resp;
	}

	public static Response importMainConfigFault() {
		InstallResponse resp = new InstallResponse();
		resp.importMainConfig = false;
		return resp;
	}

	public static InstallResponse createTableSucess() {
		return new InstallResponse();
	}

	@Override
	public void setAttribute(HttpSession session) {

	}


}
