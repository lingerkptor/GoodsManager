package idv.lingerkptor.GoodsManager.core.test.multipart;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

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

	public void setAttribute(String attributeName, Object value) {
		switch (attributeName) {
		case "description":
			this.description = (String) value;
			break;
		case "TestFile":
			Part part = (Part) value;
			try {
				System.out.println("save file start.");
				// 取得檔案名稱
				String fileName = part.getSubmittedFileName();
				// 取得檔案的路徑

				String filePath = this.getTestFilePath();
				// 存檔start
				BufferedInputStream fileInput;
				fileInput = new BufferedInputStream(part.getInputStream(), 1024);

				String fileAddr = filePath + fileName;
				this.TestFile = new File(fileAddr);
				BufferedOutputStream fileOutput = new BufferedOutputStream(
						new FileOutputStream(TestFile), 1024);
				int buffContent = -1;
				while ((buffContent = fileInput.read()) != -1) {
					fileOutput.write(buffContent);
				}
				fileOutput.flush();
				fileOutput.close();
				// 存檔end
			} catch (SecurityException e) {// 安全性例外
				e.printStackTrace();
			} catch (IllegalArgumentException e) {// 參數錯誤
				e.printStackTrace();
			} catch (IOException e) {// IO存取錯誤
				e.printStackTrace();
			}
			System.out.println("save file end.");
			break;
		case "testboolean":
			this.testboolean = Boolean.valueOf((String) value);
			break;
		default:

			break;
		}
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
