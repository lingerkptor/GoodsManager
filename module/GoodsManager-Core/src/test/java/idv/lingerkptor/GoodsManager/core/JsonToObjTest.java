package idv.lingerkptor.GoodsManager.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.Part;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JsonToObjTest {
	private static HttpServletRequest req; // 測試用的req，只複寫有用到的部分．其他都空的．

	@Test
	public void test() {
		req.setAttribute("class", TestObject.class);
		try {
			JsonToObj.formJson(req);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Object obj = req.getAttribute("obj");
		Assert.assertEquals(TestObject.class.getSimpleName(), obj.getClass().getSimpleName());

		Assert.assertEquals("Test Name", ((TestObject) obj).name);
		Assert.assertEquals("Test Content", ((TestObject) obj).content);
	}

	public class TestObject {
		private String name;
		private String content;
	}

	@BeforeClass
	public static void buildTestHttpServletRequest() {
		URL testResourceFolder = JsonToObjTest.class.getResource("./" + "test.json");// 測試用的資源檔

		req = new HttpServletRequest() { // 建立一個假的請求，只Override需要的方法
			Map<String, Object> attribute = new HashMap<String, Object>();

			@Override
			public BufferedReader getReader() throws IOException {
				File testFile = null;
				try {
					testFile = new File(testResourceFolder.toURI());// 讀取測試用的檔案
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
				return new BufferedReader(new FileReader(testFile));
			}

			@Override
			public Object getAttribute(String name) {
				return attribute.get(name);
			}

			@Override
			public void setAttribute(String name, Object o) {
				this.attribute.put(name, o);
			}

			/**
			 * 下面的都用不到，所以不用再看了．
			 */
			@Override
			public Enumeration<String> getAttributeNames() {
				return null;
			}

			@Override
			public String getCharacterEncoding() {

				return null;
			}

			@Override
			public void setCharacterEncoding(String env) throws UnsupportedEncodingException {

			}

			@Override
			public int getContentLength() {

				return 0;
			}

			@Override
			public long getContentLengthLong() {

				return 0;
			}

			@Override
			public String getContentType() {

				return null;
			}

			@Override
			public ServletInputStream getInputStream() throws IOException {

				return null;
			}

			@Override
			public String getParameter(String name) {

				return null;
			}

			@Override
			public Enumeration<String> getParameterNames() {

				return null;
			}

			@Override
			public String[] getParameterValues(String name) {
				return null;
			}

			@Override
			public Map<String, String[]> getParameterMap() {
				return null;
			}

			@Override
			public String getProtocol() {
				return null;
			}

			@Override
			public String getScheme() {
				return null;
			}

			@Override
			public String getServerName() {
				return null;
			}

			@Override
			public int getServerPort() {
				return 0;
			}

			@Override
			public String getRemoteAddr() {
				return null;
			}

			@Override
			public String getRemoteHost() {
				return null;
			}

			@Override
			public void removeAttribute(String name) {

			}

			@Override
			public Locale getLocale() {
				return null;
			}

			@Override
			public Enumeration<Locale> getLocales() {
				return null;
			}

			@Override
			public boolean isSecure() {
				return false;
			}

			@Override
			public RequestDispatcher getRequestDispatcher(String path) {
				return null;
			}

			@Override
			public String getRealPath(String path) {
				return null;
			}

			@Override
			public int getRemotePort() {
				return 0;
			}

			@Override
			public String getLocalName() {
				return null;
			}

			@Override
			public String getLocalAddr() {
				return null;
			}

			@Override
			public int getLocalPort() {
				return 0;
			}

			@Override
			public ServletContext getServletContext() {
				return null;
			}

			@Override
			public AsyncContext startAsync() throws IllegalStateException {
				return null;
			}

			@Override
			public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse)
					throws IllegalStateException {
				return null;
			}

			@Override
			public boolean isAsyncStarted() {
				return false;
			}

			@Override
			public boolean isAsyncSupported() {
				return false;
			}

			@Override
			public AsyncContext getAsyncContext() {
				return null;
			}

			@Override
			public DispatcherType getDispatcherType() {
				return null;
			}

			@Override
			public String getAuthType() {
				return null;
			}

			@Override
			public Cookie[] getCookies() {
				return null;
			}

			@Override
			public long getDateHeader(String name) {
				return 0;
			}

			@Override
			public String getHeader(String name) {
				return null;
			}

			@Override
			public Enumeration<String> getHeaders(String name) {
				return null;
			}

			@Override
			public Enumeration<String> getHeaderNames() {
				return null;
			}

			@Override
			public int getIntHeader(String name) {
				return 0;
			}

			@Override
			public String getMethod() {

				return null;
			}

			@Override
			public String getPathInfo() {
				return null;
			}

			@Override
			public String getPathTranslated() {

				return null;
			}

			@Override
			public String getContextPath() {
				return null;
			}

			@Override
			public String getQueryString() {
				return null;
			}

			@Override
			public String getRemoteUser() {
				return null;
			}

			@Override
			public boolean isUserInRole(String role) {
				return false;
			}

			@Override
			public Principal getUserPrincipal() {
				return null;
			}

			@Override
			public String getRequestedSessionId() {
				return null;
			}

			@Override
			public String getRequestURI() {
				return null;
			}

			@Override
			public StringBuffer getRequestURL() {
				return null;
			}

			@Override
			public String getServletPath() {
				return null;
			}

			@Override
			public HttpSession getSession(boolean create) {
				return null;
			}

			@Override
			public HttpSession getSession() {
				return null;
			}

			@Override
			public String changeSessionId() {
				return null;
			}

			@Override
			public boolean isRequestedSessionIdValid() {
				return false;
			}

			@Override
			public boolean isRequestedSessionIdFromCookie() {
				return false;
			}

			@Override
			public boolean isRequestedSessionIdFromURL() {
				return false;
			}

			@Override
			public boolean isRequestedSessionIdFromUrl() {
				return false;
			}

			@Override
			public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
				return false;
			}

			@Override
			public void login(String username, String password) throws ServletException {

			}

			@Override
			public void logout() throws ServletException {

			}

			@Override
			public Collection<Part> getParts() throws IOException, ServletException {
				return null;
			}

			@Override
			public Part getPart(String name) throws IOException, ServletException {
				return null;
			}

			@Override
			public <T extends HttpUpgradeHandler> T upgrade(Class<T> handlerClass)
					throws IOException, ServletException {
				return null;
			}

		};
	}

}
