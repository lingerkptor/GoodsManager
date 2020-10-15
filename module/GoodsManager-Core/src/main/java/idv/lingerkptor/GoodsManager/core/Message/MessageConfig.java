package idv.lingerkptor.GoodsManager.core.Message;

public class MessageConfig {

	private String location;

	@SuppressWarnings("unused")
	private MessageConfig() {

	}

	public MessageConfig(String location) {
		this.location = location;
	}

	public String getLocation() {
		return location;
	}

}
