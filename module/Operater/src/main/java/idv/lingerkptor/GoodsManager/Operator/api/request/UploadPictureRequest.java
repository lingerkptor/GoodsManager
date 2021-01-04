package idv.lingerkptor.GoodsManager.Operator.api.request;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import idv.lingerkptor.GoodsManager.core.api.request.Request;

public class UploadPictureRequest implements Request {

	private Map<String, File> fileMap = new HashMap<String, File>();

	public Collection<File> getPictureFile() {
		return fileMap.values();
	}

}
