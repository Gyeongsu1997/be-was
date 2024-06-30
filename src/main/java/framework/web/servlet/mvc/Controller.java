package framework.web.servlet.mvc;

import framework.web.servlet.ModelAndView;
import servlet.http.HttpServletRequest;
import servlet.http.HttpServletResponse;

@FunctionalInterface
public interface Controller {
    ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception;
}

