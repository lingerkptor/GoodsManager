package idv.lingerkptor.GoodsManager.core.DataAccess;

public class DAORuntimeException extends RuntimeException {
	private static final long serialVersionUID = 5672291721463033784L;

	private String code = "";

	@SuppressWarnings("unused")
	private DAORuntimeException() {
	}

	private DAORuntimeException(String message) {
		super(message);
	}

	public DAORuntimeException(String message, String code) {
		this(message);
		this.code = code;
	}

	/**
	 * 自定義狀態碼
	 * 
	 * @return 狀態碼
	 */
	public String getCode() {
		return code;
	}
}
