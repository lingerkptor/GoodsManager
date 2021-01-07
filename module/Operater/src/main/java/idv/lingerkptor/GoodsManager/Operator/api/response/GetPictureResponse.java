package idv.lingerkptor.GoodsManager.Operator.api.response;

import java.io.File;

import idv.lingerkptor.GoodsManager.core.api.response.Response;

public class GetPictureResponse implements Response {
	public enum Code {
		jpeg("image/jpeg") // JPEG
		,jpg("image/jpeg"), // jpg
		jpe("image/jpeg"), // jpe
		png("image/png"), // png
		gif("image/gif")// gif
		,;

		private String contentType;

		private Code(String type) {
			this.contentType = type;
		}

		public GetPictureResponse getResponse(File file) {
			GetPictureResponse response = new GetPictureResponse();
			response.type = contentType;
			response.file = file;
			return response;
		}

		public static Code getCode(String name) {
			for (Code code : Code.values()) {
				if (code.name().indexOf(name) >= 0)
					return code;
			}
			return null;

		}
	};

	@SuppressWarnings("unused")
	private String type;
	@SuppressWarnings("unused")
	private File file;

}
