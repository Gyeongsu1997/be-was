package myapplication.controller;

import myapplication.db.BoardDatabase;
import myapplication.exception.FileNotFoundExceptionHandler;
import myapplication.model.User;
import myapplication.util.HttpRequest;
import myframework.http.MediaType;
import myframework.http.ResponseEntity;
import myapplication.template.IndexTemplate;
import myapplication.util.ResourceUtils;
import myapplication.util.SessionManager;

import java.io.FileNotFoundException;
import java.io.IOException;

public class HomeController implements Controller{

    public ResponseEntity<?> run(HttpRequest httpRequest) throws IOException {
        try {
            String path = httpRequest.getPath();
//            String lastPath = ResourceUtils.getLastPath(httpRequest.getPath());

            if ("/index.html".equals(path) || "/".equals(path))
                return home(httpRequest);

            byte[] body = ResourceUtils.getStaticResource(httpRequest.getPath());

            return ResponseEntity.ok()
                    .contentType(MediaType.getContentType(httpRequest))
                    .contentLength(body.length)
                    .body(body);
        } catch (FileNotFoundException e) {
            return FileNotFoundExceptionHandler.handle();
        }
    }

    private ResponseEntity<?> home(HttpRequest httpRequest) throws IOException {
        User loggedInUser = SessionManager.getLoggedInUser(httpRequest);

        byte[] body = IndexTemplate.render(loggedInUser, BoardDatabase.findAll(), httpRequest.getQueryMap());

        return ResponseEntity.ok()
                .contentType(MediaType.getContentType(httpRequest))
                .contentLength(body.length)
                .body(body);
    }
}
