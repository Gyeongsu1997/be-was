package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

public interface ServletRequest {
    Object getAttribute(String var1);

    String getCharacterEncoding();

    void setCharacterEncoding(String var1) throws UnsupportedEncodingException;

    int getContentLength();

    long getContentLengthLong();

    String getContentType();

    ServletInputStream getInputStream() throws IOException;

    String getParameter(String var1);

    Enumeration<String> getParameterNames();

    String[] getParameterValues(String var1);

    Map<String, String[]> getParameterMap();

    String getProtocol();

    String getServerName();

    int getServerPort();

    BufferedReader getReader() throws IOException;

    String getRemoteAddr();

    String getRemoteHost();

    void setAttribute(String var1, Object var2);

    void removeAttribute(String var1);

    Locale getLocale();

    RequestDispatcher getRequestDispatcher(String var1);

    int getRemotePort();

    String getLocalName();

    String getLocalAddr();

    int getLocalPort();

    DispatcherType getDispatcherType();
}
