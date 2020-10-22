package idv.lingerkptor.GoodsManager.core;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import idv.lingerkptor.GoodsManager.core.api.request.Request;

public class AnalyzeMultiPart implements Analyzable {
	public enum InputType {
		file, text;
	}

	@Override
	public Request analyze(HttpServletRequest req, Class<? extends Request> requestObjClass) {
		Request requestObj = null;
		try {
			requestObj = (Request) requestObjClass.getConstructor().newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
//		// 使用參數的名稱找對應的物件欄位
//		for (String paraName : req.getParameterMap().keySet()) {
//			try {
//				Field field = requestObj.getClass().getField(paraName);
//				field.setAccessible(true);// 開啟權限,即使private也可存取
//				field.set(requestObj, req.getParameter(paraName));
//			} catch (NoSuchFieldException e) {
//				// 找不到對應欄位
//				continue;
//			} catch (SecurityException e) {
//				e.printStackTrace();
//			} catch (IllegalArgumentException e) {
//				// 對應的欄位，型態不同（ex: Field的參數為int ,但傳入String）
//				e.printStackTrace();
//			} catch (IllegalAccessException e) {
//				// 權限錯誤
//				e.printStackTrace();
//			}
//		}

		try {
			for (Part part : req.getParts()) {
				String partName = part.getName();

				Field field = requestObj.getClass().getField(partName);
				field.setAccessible(true);
				String partType = part.getContentType();
				switch (InputType.valueOf(partType)) {
				case file:
					try {
						// 取得檔案名稱
						String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
						// 取得檔案的URI
						Method getUri = requestObj.getClass().getMethod("get" + partName);
						String uri = (String) getUri.invoke(requestObj);
						// 存檔start
						BufferedInputStream fileInput;
						fileInput = new BufferedInputStream(part.getInputStream(), 1024);

						String fileUri = uri + fileName;
						BufferedOutputStream fileOutput = new BufferedOutputStream(
								new FileOutputStream(new File(fileUri)), 1024);
						int buffContent = -1;
						while ((buffContent = fileInput.read()) != -1) {
							fileOutput.write(buffContent);
						}
						fileInput.close();
						fileOutput.close();
						// 存檔end
						field.set(requestObj, fileUri);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					}
					continue;
				case text:
					try {
						// 取的該欄位類別
						Class<?> type = field.getType();
						// 實體化該欄位物件
						Object object = type.getConstructor().newInstance();
						// 用該物件的ValueOf method (傳入參數為String)，轉換成該物件．
						// 例如 Integer.ValueOf("123") => 123 ,String.ValueOf("123456")=>"123456"
						Object getValue = type.getMethod("valueOf", String.class).invoke(object,
								req.getParameter(part.getName()));
						// 賦值給該欄位
						field.set(requestObj, getValue);
					} catch (IllegalArgumentException e) {
						// 參數錯誤
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// 權限不足
						e.printStackTrace();
					} catch (InstantiationException e) {
						// 實例化錯誤，在這裡會表示沒有無參數實體化的例外
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						// 沒有找到無參數建構式
						e.printStackTrace();
					}
					break;
				default:
					break;
				}
			}
		} catch (NoSuchFieldException e) {
			// 找不到對應欄位
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
		return requestObj;
	}
}
