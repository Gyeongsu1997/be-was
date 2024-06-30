package webserver;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servlet.http.HttpServlet;
import servlet.http.HttpServletRequest;
import servlet.http.HttpServletResponse;
import webserver.http.Response;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final Socket connection;
    private final HttpServlet frontController;

    public RequestHandler(Socket connectionSocket, HttpServlet frontController) {
        this.connection = connectionSocket;
        this.frontController = frontController;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpServletRequest request = RequestParser.parse(in);
            HttpServletResponse response = new Response();

            Class<? extends HttpServlet> clazz = frontController.getClass();
            Method service = clazz.getDeclaredMethod("service", HttpServletRequest.class, HttpServletResponse.class);
            service.setAccessible(true);
            service.invoke(frontController, request, response);
        } catch (IOException | NoSuchMethodException e) {

        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
