package webserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servlet.http.HttpServlet;

public class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;
    private final HttpServlet frontController;

    public WebServer(HttpServlet frontController) {
        this.frontController = frontController;
    }

    public void run(String[] args) {
        int port = 0;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }

        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            ExecutorService threadPool = new ThreadPoolExecutor(25, 200, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                threadPool.submit(new RequestHandler(connection, frontController));
            }
        } catch (IOException e) {

        }
    }
}
