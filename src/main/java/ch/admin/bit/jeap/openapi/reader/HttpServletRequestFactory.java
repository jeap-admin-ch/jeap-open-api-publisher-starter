package ch.admin.bit.jeap.openapi.reader;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.experimental.UtilityClass;

import java.io.BufferedReader;
import java.security.Principal;
import java.util.*;

@UtilityClass
public class HttpServletRequestFactory {

    private static final String DEFAULT_URI = "http://localhost:8080/api-docs";

    /**
     * Create a fake http request in order to retrieve the OpenAPI spec.
     * @return HttpServletRequest without content except the request URI.
     */
    public static HttpServletRequest getHttpServletRequest() {
        return new HttpServletRequest() {
            @Override
            public Object getAttribute(String s) {
                return null;
            }

            @Override
            public Enumeration<String> getAttributeNames() {
                return null;
            }

            @Override
            public String getCharacterEncoding() {
                return "";
            }

            @Override
            public void setCharacterEncoding(String s) {
                // no-op
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
                return "";
            }

            @Override
            public ServletInputStream getInputStream() {
                return null;
            }

            @Override
            public String getParameter(String s) {
                return "";
            }

            @Override
            public Enumeration<String> getParameterNames() {
                return null;
            }

            @Override
            public String[] getParameterValues(String s) {
                return new String[0];
            }

            @Override
            public Map<String, String[]> getParameterMap() {
                return Map.of();
            }

            @Override
            public String getProtocol() {
                return "http";
            }

            @Override
            public String getScheme() {
                return "HTTP/1.1";
            }

            @Override
            public String getServerName() {
                return "localhost";
            }

            @Override
            public int getServerPort() {
                return 8080;
            }

            @Override
            public BufferedReader getReader() {
                return null;
            }

            @Override
            public String getRemoteAddr() {
                return "";
            }

            @Override
            public String getRemoteHost() {
                return "";
            }

            @Override
            public void setAttribute(String s, Object o) {
                // no-op
            }

            @Override
            public void removeAttribute(String s) {
                // no-op
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
            public RequestDispatcher getRequestDispatcher(String s) {
                return null;
            }

            @Override
            public int getRemotePort() {
                return 0;
            }

            @Override
            public String getLocalName() {
                return "";
            }

            @Override
            public String getLocalAddr() {
                return "";
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

            @Override
            public String getRequestId() {
                return "";
            }

            @Override
            public String getProtocolRequestId() {
                return "";
            }

            @Override
            public ServletConnection getServletConnection() {
                return null;
            }

            @Override
            public String getAuthType() {
                return "";
            }

            @Override
            public Cookie[] getCookies() {
                return new Cookie[0];
            }

            @Override
            public long getDateHeader(String s) {
                return 0;
            }

            @Override
            public String getHeader(String s) {
                return "";
            }

            @Override
            public Enumeration<String> getHeaders(String s) {
                return null;
            }

            @Override
            public Enumeration<String> getHeaderNames() {
                return null;
            }

            @Override
            public int getIntHeader(String s) {
                return 0;
            }

            @Override
            public String getMethod() {
                return "";
            }

            @Override
            public String getPathInfo() {
                return "";
            }

            @Override
            public String getPathTranslated() {
                return "";
            }

            @Override
            public String getContextPath() {
                return "";
            }

            @Override
            public String getQueryString() {
                return "";
            }

            @Override
            public String getRemoteUser() {
                return "";
            }

            @Override
            public boolean isUserInRole(String s) {
                return false;
            }

            @Override
            public Principal getUserPrincipal() {
                return null;
            }

            @Override
            public String getRequestedSessionId() {
                return "";
            }

            @Override
            public String getRequestURI() {
                return DEFAULT_URI;
            }

            @Override
            public StringBuffer getRequestURL() {
                return new StringBuffer(DEFAULT_URI);
            }

            @Override
            public String getServletPath() {
                return "";
            }

            @Override
            public HttpSession getSession(boolean b) {
                return null;
            }

            @Override
            public HttpSession getSession() {
                return null;
            }

            @Override
            public String changeSessionId() {
                return "";
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
            public boolean authenticate(HttpServletResponse httpServletResponse) {
                return false;
            }

            @Override
            public void login(String s, String s1) {
                // no-op
            }

            @Override
            public void logout(){
                // no-op
            }

            @Override
            public Collection<Part> getParts() {
                return List.of();
            }

            @Override
            public Part getPart(String s) {
                return null;
            }

            @Override
            public <T extends HttpUpgradeHandler> T upgrade(Class<T> aClass) {
                return null;
            }

        };
    }
}
