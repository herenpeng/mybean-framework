package com.mybeanframework.http;

import org.mybeanframework.common.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.net.Socket;
import java.security.Principal;
import java.util.*;

/**
 * @author herenpeng
 * @since 2021-05-29 23:10
 */
public class Request implements HttpServletRequest {

    private String method;

    private String uri;

    private String version;

    private Map<String, String> requestHeads = new HashMap<>(8);
    private Map<String, String> requestParams = new HashMap<>(8);

    public Request(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        String requestLine = in.readLine();
        // 第一个是请求行
        requestLineResolver(requestLine);

        // 请求头
        String requestHead;
        while (StringUtils.isNotEmpty(requestHead = in.readLine())) {
            requestHeadResolver(requestHead);
        }

        // 如果是 POST 方法，则解析请求体
        String requestBody = null;
        if (StringUtils.equalsIgnoreCase(Method.POST, getMethod())) {
            char[] chars = new char[this.getContentLength()];
            int len = in.read(chars, 0, this.getContentLength());
            if (len > 0) {
                requestBody = new String(chars);
            }
        }
        requestParamsResolver(requestBody);

    }

    public static final int REQUEST_LINE_ARR_LENGTH = 3;

    private void requestLineResolver(String requestLine) {
        String[] requestAttributes = requestLine.split(" ");
        if (requestAttributes.length == REQUEST_LINE_ARR_LENGTH) {
            this.method = requestAttributes[0];
            this.uri = requestAttributes[1];
            this.version = requestAttributes[2];
        }
    }

    public static final int REQUEST_HEAD_ARR_LENGTH = 2;

    private void requestHeadResolver(String requestHead) {
        String[] requestHeadAttributes = requestHead.split(": ");
        if (requestHeadAttributes.length == REQUEST_HEAD_ARR_LENGTH) {
            requestHeads.put(requestHeadAttributes[0], requestHeadAttributes[1]);
        }
    }

    /**
     * 解析参数信息
     *
     * @param requestBody
     */
    private void requestParamsResolver(String requestBody) {
        String paramsStr = uri.substring(uri.indexOf("?") + 1);
        String[] paramArr = StringUtils.split(paramsStr, "&");
        for (String param : paramArr) {
            String[] arr = StringUtils.split(param, "=");
            if (arr.length != 2) {
                continue;
            }
            requestParams.put(arr[0], arr[1]);
        }
    }

    @Override
    public String getAuthType() {
        return null;
    }

    @Override
    public Cookie[] getCookies() {
        return new Cookie[0];
    }

    @Override
    public long getDateHeader(String name) {
        return 0;
    }

    @Override
    public String getHeader(String name) {
        String header = this.requestHeads.get(name);
        if (StringUtils.isEmpty(header)) {
            header = this.requestHeads.get(name.toUpperCase());
        }
        if (StringUtils.isEmpty(header)) {
            header = this.requestHeads.get(name.toLowerCase());
        }
        return header;
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
        return this.method;
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
        return this.uri;
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
    public <T extends HttpUpgradeHandler> T upgrade(Class<T> handlerClass) throws IOException, ServletException {
        return null;
    }

    @Override
    public Object getAttribute(String name) {
        return null;
    }

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
        return Integer.parseInt(getHeader("Content-Length"));
    }

    @Override
    public long getContentLengthLong() {
        return Long.parseLong(getHeader("Content-Length"));
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
        return requestParams.get(name);
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return null;
    }

    @Override
    public String[] getParameterValues(String name) {
        return new String[0];
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
    public BufferedReader getReader() throws IOException {
        return null;
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
    public void setAttribute(String name, Object o) {

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
    public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
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
}
