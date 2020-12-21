package idv.lingerkptor.GoodsManager.core.test.multipart;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import idv.lingerkptor.GoodsManager.core.api.request.Request;

/**
 * 
 * @author lingerkptor
 * 
 *         <form action="testupload" method="post" enctype=
 *         "multipart/form-data"> <input type="text" name="description"/>
 *         <input type="file" name="testFile"/>
 *         <input type="checkbox" name="testboolean"/> </form>
 *
 */
public class TestMultiPartRequest implements Request {

	private String description = null;
	private Map<String,File> fileMap = new HashMap<String,File>();
//	private File TestFile = null;
	private boolean testboolean = false;

	public String getTestFilePath() {
		return "C:\\GoodsManager\\";
	}



	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public File getTestFile() {
//		return TestFile;
		return this.fileMap.get("TestFile");
	}

	public boolean isTestboolean() {
		return testboolean;
	}

}
