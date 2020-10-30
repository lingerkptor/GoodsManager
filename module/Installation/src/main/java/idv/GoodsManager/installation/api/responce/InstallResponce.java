package idv.GoodsManager.installation.api.responce;

import javax.servlet.http.HttpSession;

import idv.lingerkptor.GoodsManager.core.api.responce.Responce;

public class InstallResponce implements Responce {

	private boolean buildDBConfig = true;

	private boolean testConnect = true;

	private boolean createTable = true;

	private InstallResponce() {

	}

	public static InstallResponce buildDBConfigFault() {
		InstallResponce resp = new InstallResponce();
		resp.buildDBConfig = false;
		resp.testConnect = false;
		resp.createTable = false;
		return resp;
	}

	public static InstallResponce testConnectFault() {
		InstallResponce resp = new InstallResponce();
		resp.testConnect = false;
		resp.createTable = false;
		return resp;
	}

	public static InstallResponce createTableFault() {
		InstallResponce resp = new InstallResponce();
		resp.createTable = false;
		return resp;
	}

	public static InstallResponce createTableSucess() {
		return new InstallResponce();
	}

	@Override
	public void setAttribute(HttpSession session) {

	}

}
