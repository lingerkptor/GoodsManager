package test;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletContext;

public class Testbean {

	public String getUrl() throws MalformedURLException {
		URL url1 = new URL("/WEB-INF");
		ClassLoader cl=Testbean.class.getClassLoader();
		URL url = cl.getResource("sqlite-jdbc.jar");
		return url1.getPath();
	}
}
