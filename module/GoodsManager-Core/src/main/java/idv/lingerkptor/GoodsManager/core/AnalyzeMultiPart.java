package idv.lingerkptor.GoodsManager.core;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import idv.lingerkptor.GoodsManager.core.DataAccess.ConfigReader;
import idv.lingerkptor.GoodsManager.core.api.request.Request;

public class AnalyzeMultiPart implements Analyzable {

	@Override
	public Request analyze(HttpServletRequest req, Class<? extends Request> requestObjClass) {
		Request requestObj = null;
		System.out.println("requestObj new Instance start");
		try {
			Constructor<? extends Request> constructor = (Constructor<? extends Request>) requestObjClass
					.getConstructor();
			requestObj = constructor.newInstance();
		} catch (NoSuchMethodException e) {// 找不到該Constructor
			e.printStackTrace();
		} catch (SecurityException e) {// 安全性例外
			e.printStackTrace();
		} catch (InstantiationException e) {// 如果宣告時，底層的class是抽象類別，就會拋出
			e.printStackTrace();
		} catch (IllegalAccessException e) {// 存取權限錯誤
			e.printStackTrace();
		} catch (IllegalArgumentException e) {// 參數錯誤
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// 如果Constructor內拋出異常沒有被捕捉到就跳到這裡(通常會發生在Method或Constructor
			e.printStackTrace();
		}
		System.out.println("requestObj new Instance end");

		try {
			for (Part part : req.getParts()) {
				String partName = part.getName();
				System.out.println("part:" + partName);
				try {
					Field field = requestObj.getClass().getDeclaredField(partName);
					field.setAccessible(true);
					if (part.getContentType() != null) {// 檔案處理
						field.set(requestObj, this.saveFile(part));//存檔
					} else {
						try {
							Class<?> fieldType = field.getType();
							Object fieldObj = null;

							switch (fieldType.getTypeName()) {// 原形基本型態
							case "int":
								field.setInt(requestObj,
										Integer.valueOf(req.getParameter(partName)));
								break;
							case "boolean":
								field.setBoolean(requestObj,
										Boolean.valueOf(req.getParameter(partName)));
								break;
							case "double":
								field.setDouble(requestObj,
										Double.valueOf(req.getParameter(partName)));
								break;
							case "float":
								field.setFloat(requestObj,
										Float.valueOf(req.getParameter(partName)));
								break;
							case "char":
								field.setInt(requestObj, req.getParameter(partName).charAt(0));
								break;
							case "short":
								field.setShort(requestObj,
										Short.valueOf(req.getParameter(partName)));
								break;
							case "byte":
								field.setInt(requestObj,
										Integer.valueOf(req.getParameter(partName)));
								break;
							case "long":
								field.setLong(requestObj, Long.valueOf(req.getParameter(partName)));
								break;
							default:
								/**
								 * 因為req.getParameter(partName)出來的都是String，而我們沒辦法確定Field的類別，
								 * 如果直接指定會有錯誤(ex:Field是Integer傳入String物件會出錯)
								 * 但是我們也沒辦法確定Field的類別，是Boolean、String、Integer...等等，
								 * 應該正確使用該類別的valueOf靜態方法（ex:
								 * String.valueOf(req.getParameter(partName))
								 * 、Interger.valueOf(req.getParameter(partName))...等等）
								 */
								if (fieldType != String.class) {
									try {
										Method valueOfMethod = fieldType
												.getDeclaredMethod("valueOf", String.class);
										fieldObj = valueOfMethod.invoke(null, // 因為是靜態方法所以第一個參數為null
												req.getParameter(partName));
										field.set(requestObj, fieldObj);
									} catch (NoSuchMethodException e) {// 沒找到對應的方法
										e.printStackTrace();
									} catch (InvocationTargetException e) {// 執行出錯
										e.printStackTrace();
									}
									break;
								} else {// String.valueOf(Object obj)所以不能使用reflection
									fieldObj = req.getParameter(partName);
									field.set(requestObj, fieldObj);
								}
							}
						} catch (IllegalArgumentException e) {// 參數錯誤
							e.printStackTrace();
						}
					}
					field.setAccessible(false);
				} catch (IllegalAccessException e) {// 存取權限出錯
					e.printStackTrace();
				} catch (NoSuchFieldException e) {// 找不到指定的Field
					e.printStackTrace();
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
		return requestObj;
	}

	private File saveFile(Part source) {
		File target = null;
		try {
			System.out.println("save file start.");
			// 取得檔案名稱
			String fileName = source.getSubmittedFileName();

			// 取得暫存資料夾路徑
			String filePath = ConfigReader.getTempDir();

			// 存檔start
			BufferedInputStream fileInput = // Source Stream
					new BufferedInputStream(source.getInputStream(), 1024);

			String fileAddr = filePath + fileName;
			target = new File(fileAddr);
			if (!target.getParentFile().exists())
				target.getParentFile().mkdirs();
			if (!target.exists())
				target.createNewFile();
			BufferedOutputStream fileOutput = // Target Stream
					new BufferedOutputStream(new FileOutputStream(target), 1024);
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
		return target;
	}
}
