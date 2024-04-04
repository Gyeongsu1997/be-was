package myapplication.webserver;

import java.io.*;
import java.net.Socket;

import myapplication.controller.FrontController;
import myapplication.util.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = HttpRequest.parse(in);
            FrontController.route(httpRequest, out);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
