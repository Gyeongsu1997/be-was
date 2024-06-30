package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servlet.http.Cookie;
import servlet.http.HttpServletRequest;
import servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Request implements HttpServletRequest {
    private static final Logger log = LoggerFactory.getLogger(Request.class);
    private final String method;
    private final String protocol;
    private final String requestURI;
    private final String queryString;
    private final Map<String, String> headers = new HashMap<>();
    private final Map<String, Object> attribute = new HashMap<>();


    @Override
    public Cookie[] getCookies() {
        return new Cookie[0];
    }

    @Override
    public long getDateHeader(String var1) {
        return 0;
    }

    @Override
    public String getHeader(String var1) {
        if (!headers.containsKey(var1.toLowerCase())) {
            return null;
        }
        return headers.get(var1);
    }

    @Override
    public Enumeration<String> getHeaders(String var1) {
        return null;
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        return null;
    }

    @Override
    public String getMethod() {
        return this.method;
    }

    @Override
    public String getQueryString() {
        return this.queryString;
    }

    @Override
    public String getRemoteUser() {
        return null;
    }

    @Override
    public String getRequestURI() {
        return this.requestURI;
    }

    @Override
    public StringBuffer getRequestURL() {
        return null;
    }

    @Override
    public boolean authenticate(HttpServletResponse var1) throws IOException {
        return false;
    }

    @Override
    public Object getAttribute(String var1) {
        return this.attribute.get(var1);
    }

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public void setCharacterEncoding(String var1) throws UnsupportedEncodingException {

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
    public String getParameter(String var1) {
        return null;
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return null;
    }

    @Override
    public String[] getParameterValues(String var1) {
        return new String[0];
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return null;
    }

    @Override
    public String getProtocol() {
        return this.protocol;
    }

    @Override
    public String getServerName() {
        return this.headers.get("host").split(":")[0];
    }

    @Override
    public int getServerPort() {
        return Integer.parseInt(this.headers.get("host").split(":")[1]);
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
    public void setAttribute(String var1, Object var2) {
        this.attribute.put(var1, var2);
    }

    @Override
    public void removeAttribute(String var1) {
        this.attribute.remove(var1);
    }

//    @Override
//    public Locale getLocale() {
//        return new Locale.Builder()
//                .setLanguage(this.headers.get("accept-language").split(",")[0])
//        return null;
//    }

    @Override
    public RequestDispatcher getRequestDispatcher(String var1) {
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
    public DispatcherType getDispatcherType() {
        return null;
    }

    public static class RequestBuilder {
        private String method;
        private String protocol;
        private String requestURI;
        private String queryString;

        public RequestBuilder method(String method) {
            this.method = method;
            return this;
        }

        public RequestBuilder protocol(String protocol) {
            this.method = method;
            return this;
        }

        public RequestBuilder requestURI(String requestURI) {
            this.requestURI = requestURI;
            return this;
        }

        public RequestBuilder queryString(String queryString) {
            this.queryString = queryString;
            return this;
        }

        public Request build() {
            return new Request(method, protocol, requestURI, queryString);
        }
    }

    public static RequestBuilder builder() {
        return new RequestBuilder();
    }

    private Request(String method, String protocol, String requestURI, String queryString) {
        this.method = method;
        this.protocol = protocol;
        this.requestURI = requestURI;
        this.queryString = queryString;
    }
}
