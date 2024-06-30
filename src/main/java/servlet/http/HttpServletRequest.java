package servlet.http;

import servlet.ServletRequest;

import java.io.IOException;
import java.security.Principal;
import java.util.Enumeration;

public interface HttpServletRequest extends ServletRequest {
    Cookie[] getCookies();

    long getDateHeader(String var1);

    String getHeader(String var1);

    Enumeration<String> getHeaders(String var1);

    Enumeration<String> getHeaderNames();

    String getMethod();

    String getQueryString();

    String getRemoteUser();

    String getRequestURI();

    StringBuffer getRequestURL();

}
