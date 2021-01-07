package idv.lingerkptor.GoodsManager.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;

import javax.servlet.http.HttpServletResponse;

import idv.lingerkptor.GoodsManager.core.api.response.Response;

public class SendFile implements Sendable {

	@Override
	public void send(Response obj, HttpServletResponse resp) {
		FileInputStream in = null;
		OutputStream out = null;
		try {
			out = resp.getOutputStream();
			for (Field field : obj.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				if (field.getName().equals("type")) {
					resp.setContentType(field.get(obj).toString());
				} else {
					if (field.get(obj) instanceof File) {
						File file = (File) field.get(obj);

						in = new FileInputStream(file);
						byte[] data = new byte[1024];
						while (in.read(data) > 0) {
							out.write(data);
						}
					}
				}
				field.setAccessible(false);
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null)
					in.close();
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
