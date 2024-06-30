package servlet.http;

import java.io.IOException;
import java.text.MessageFormat;

public class HttpServlet {
    private static final String METHOD_DELETE = "DELETE";
    private static final String METHOD_GET = "GET";
    private static final String METHOD_POST = "POST";
    private static final String METHOD_PUT = "PUT";

    protected void service(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        String method = req.getMethod();

        if (method.equals(METHOD_GET)) {
            doGet(req, resp);

        } else if (method.equals(METHOD_POST)) {
            doPost(req, resp);

        } else if (method.equals(METHOD_PUT)) {
            doPut(req, resp);

        } else if (method.equals(METHOD_DELETE)) {
            doDelete(req, resp);

        } else {
            String errMsg = "http.method_not_implemented";
            Object[] errArgs = new Object[1];
            errArgs[0] = method;
            errMsg = MessageFormat.format(errMsg, errArgs);

            resp.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, errMsg);
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String msg = "http.method_get_not_supported";
        sendMethodNotAllowed(req, resp, msg);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String msg = "http.method_post_not_supported";
        sendMethodNotAllowed(req, resp, msg);
    }

    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String msg = "http.method_put_not_supported";
        sendMethodNotAllowed(req, resp, msg);
    }

    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String msg = "http.method_delete_not_supported";
        sendMethodNotAllowed(req, resp, msg);
    }

    private void sendMethodNotAllowed(HttpServletRequest req, HttpServletResponse resp, String msg) throws IOException {
        String protocol = req.getProtocol();
        if (protocol.length() == 0 || protocol.endsWith("0.9") || protocol.endsWith("1.0")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, msg);
        } else {
            resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, msg);
        }
    }
}
