package myapplication.exception;

import myapplication.util.ResourceUtils;
import myframework.http.HttpStatus;
import myframework.http.MediaType;
import myframework.http.ResponseEntity;

import java.io.IOException;

public class WebExceptionHandler {
    private static final String CODE = "<h1 id=\"code\">{}</h1>";
    private static final String MESSAGE = "<h2 id=\"phrase\">{}</h2>";

    public static ResponseEntity<?> handle(WebException e) throws IOException {
        String html = new String(ResourceUtils.getStaticResource("/error.html"));
        byte[] body = html.replace("<h1 id=\"code\"></h1>", CODE.replace("{}", Integer.toString(e.getStatus())))
                .replace("<h2 id=\"phrase\"></h2>",MESSAGE.replace("{}", e.getMessage()))
                .getBytes();

        return ResponseEntity.status(HttpStatus.resolve(e.getStatus()))
                .contentType(MediaType.TEXT_HTML)
                .contentLength(body.length)
                .body(body);
    }
}

