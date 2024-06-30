package framework.web.servlet;

import java.util.Locale;

public interface ViewResolver {
    View reolveViewName(String viewName, Locale locale) throws Exception;
}
