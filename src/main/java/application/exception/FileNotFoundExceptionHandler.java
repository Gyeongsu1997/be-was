package application.exception;

import application.util.ResourceUtils;
import framework.http.HttpStatus;
import framework.http.MediaType;
import framework.http.ResponseEntity;

import java.io.IOException;

public class FileNotFoundExceptionHandler {
    public static ResponseEntity<?> handle() throws IOException {
        String html = new String(ResourceUtils.getStaticResource("/error.html"));
        byte[] body = html.replace("<h1 id=\"code\"></h1>", "<h1 id=\"code\">404</h1>")
                .replace("<h2 id=\"phrase\"></h2>","<h2 id=\"phrase\">Page not found</h2>")
                .getBytes();

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.TEXT_HTML)
                .contentLength(body.length)
                .body(body);
    }
}
