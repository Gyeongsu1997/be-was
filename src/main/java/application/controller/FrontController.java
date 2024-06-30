package application.controller;

import application.util.HttpRequest;
import application.util.HttpResponse;
import framework.http.ResponseEntity;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FrontController {
    private static final Map<String, Controller> controllers = new ConcurrentHashMap<>();

    public static void route(HttpRequest httpRequest, OutputStream out) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        String path = httpRequest.getPath();

        ResponseEntity<?> responseEntity = null;
        if (path.startsWith("/user")) {
            responseEntity = controllers.get("userController").run(httpRequest);
        }
        if (path.startsWith("/board")) {
            responseEntity = controllers.get("boardController").run(httpRequest);
        }

        if (responseEntity == null)
            responseEntity = controllers.get("homeController").run(httpRequest);

        HttpResponse.send(dos, responseEntity);
    }

    public static void putController(String name, Controller controller) {
        controllers.put(name, controller);
    }
}
