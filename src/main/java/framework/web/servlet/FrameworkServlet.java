package framework.web.servlet;

import servlet.http.HttpServlet;
import servlet.http.HttpServletRequest;
import servlet.http.HttpServletResponse;

import java.util.Set;

public abstract class FrameworkServlet extends HttpServlet {
    private static final Set<String> HTTP_SERVLET_METHODS =
            Set.of("DELETE", "GET", "POST", "PUT");

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (HTTP_SERVLET_METHODS.contains(request.getMethod())) {
            super.service(request, response);
        } else {
            processRequest(request, response);
        }
    }

    @Override
    protected final void doGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        processRequest(request, response);
    }

    @Override
    protected final void doPost(HttpServletRequest request, HttpServletResponse response) throws Exception {
        processRequest(request, response);
    }

    @Override
    protected final void doPut(HttpServletRequest request, HttpServletResponse response) throws Exception {
        processRequest(request, response);
    }

    @Override
    protected final void doDelete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        processRequest(request, response);
    }

    protected final void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        doService(request, response);
    }

    protected abstract void doService(HttpServletRequest request, HttpServletResponse response)
            throws Exception;
}
