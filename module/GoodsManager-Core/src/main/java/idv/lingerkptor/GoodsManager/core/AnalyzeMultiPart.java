package idv.lingerkptor.GoodsManager.core;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

public class AnalyzeMultiPart<T> implements Analyzable<T> {
	public enum InputType {
		file, text;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T analyze(HttpServletRequest req, Class<?> requestObjClass) {
		T requestObj = null;
		try {
			requestObj = (T) requestObjClass.getConstructor().newInstance();
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
		for (String para : req.getParameterMap().keySet()) {
			try {
				Field field = requestObj.getClass().getField(para);

			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}

		try {
			for (Part part : req.getParts()) {
				String partName = part.getName();

				Field field = requestObj.getClass().getField(partName);
				field.setAccessible(true);
				String partType = part.getContentType();
				switch (InputType.valueOf(partType)) {
				case file:
					try {
						String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
						Method getUri = requestObj.getClass().getMethod("get" + partName);

						String uri = (String) getUri.invoke(requestObj);

						BufferedInputStream fileInput;
						fileInput = new BufferedInputStream(part.getInputStream(), 1024);

						String fileUri = uri + fileName;
						BufferedOutputStream fileOutput = new BufferedOutputStream(
								new FileOutputStream(new File(fileUri)));
						int buffContent = -1;
						while ((buffContent = fileInput.read()) != -1) {
							fileOutput.write(buffContent);
						}
						fileInput.close();
						fileOutput.close();
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
						field.set(requestObj, req.getParameter(part.getName()));
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
					break;
				default:
					break;
				}
			}
		} catch (NoSuchFieldException e) {
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
