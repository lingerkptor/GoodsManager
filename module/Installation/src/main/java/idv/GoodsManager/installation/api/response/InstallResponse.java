package idv.GoodsManager.installation.api.response;

import javax.servlet.http.HttpSession;

import idv.lingerkptor.GoodsManager.core.api.response.Response;

public class InstallResponse implements Response {

	@SuppressWarnings("unused")
	private boolean buildDBConfig = true;
	@SuppressWarnings("unused")
	private boolean testConnect = true;
	@SuppressWarnings("unused")
	private boolean createTable = true;

	private InstallResponse() {

	}

	public static InstallResponse buildDBConfigFault() {
		InstallResponse resp = new InstallResponse();
		resp.buildDBConfig = false;
		resp.testConnect = false;
		resp.createTable = false;
		return resp;
	}

	public static InstallResponse testConnectFault() {
		InstallResponse resp = new InstallResponse();
		resp.testConnect = false;
		resp.createTable = false;
		return resp;
	}

	public static InstallResponse createTableFault() {
		InstallResponse resp = new InstallResponse();
		resp.createTable = false;
		return resp;
	}

	public static InstallResponse createTableSucess() {
		return new InstallResponse();
	}

	@Override
	public void setAttribute(HttpSession session) {

	}

}
