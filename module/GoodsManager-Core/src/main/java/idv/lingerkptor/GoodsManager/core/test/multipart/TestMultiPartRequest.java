package idv.lingerkptor.GoodsManager.core.test.multipart;

import java.io.File;

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
	private File TestFile = null;
	private boolean testboolean = false;

	public String getTestFilePath() {
		return "C:\\GoodsManager\\";
	}

	@Override
	public void setAttribute(HttpSession session) {

	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public File getTestFile() {
		return TestFile;
	}

	public boolean isTestboolean() {
		return testboolean;
	}

}
