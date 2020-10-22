package idv.GoodsManager.installation.api.responce;

import idv.lingerkptor.GoodsManager.core.api.responce.Responce;

public class InstallResponce implements Responce {
	@SuppressWarnings("unused")
	private boolean buildDBConfig = true;
	@SuppressWarnings("unused")
	private boolean testConnect = true;
	@SuppressWarnings("unused")
	private boolean createTable = true;

	private InstallResponce() {

	}

	public static InstallResponce buildDBConfigFault() {
		InstallResponce resp = new InstallResponce();
		resp.buildDBConfig = false;
		return resp;
	}

	public static InstallResponce testConnectFault() {
		InstallResponce resp = new InstallResponce();
		resp.testConnect = false;
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

}