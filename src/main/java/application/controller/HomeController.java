package application.controller;

import application.db.BoardDatabase;
import application.exception.FileNotFoundExceptionHandler;
import application.model.User;
import application.util.HttpRequest;
import framework.http.MediaType;
import framework.http.ResponseEntity;
import application.template.IndexTemplate;
import application.util.ResourceUtils;
import application.util.SessionManager;

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
