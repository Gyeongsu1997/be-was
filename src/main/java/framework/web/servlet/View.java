package framework.web.servlet;

import servlet.http.HttpServletRequest;
import servlet.http.HttpServletResponse;

import java.util.Map;

public interface View {
    void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response)
            throws Exception;
}
