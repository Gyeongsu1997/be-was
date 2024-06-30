package webserver;

import framework.http.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servlet.http.HttpServletRequest;
import webserver.http.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class RequestParser {
    private static final Logger log = LoggerFactory.getLogger(RequestParser.class);

    public static HttpServletRequest parse(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        String line = br.readLine();
        log.debug(line);
        String[] tokens = line.split(" ");
        if (tokens.length != 3) {
            log.error("HTTP Request Message has not valid form");
            throw new IllegalStateException("HTTP Request Message has not valid form");
        }
        String method = tokens[0];
        String[] target = tokens[1].split("\\?");
        String requestURI = target[0];
        String queryString = null;
        if (target.length == 2) {
            queryString = target[1];
        }
        String protocol = tokens[2];

        /**
         * parse http headers
         */
        Map<String, String> headers = new HashMap<>();
        while (!(line = br.readLine()).isEmpty()) {
            tokens = line.split(":");
            headers.put(tokens[0].toLowerCase(), tokens[1].trim());
        }

        /**
         * parse http body
         */
        char[] body = null;
        if (headers.containsKey("content-length")) {
            body = new char[Integer.parseInt(headers.get("content-length")) / 2 + 1];
            br.read(body);
        }

        String accept = MediaType.ALL_VALUE;
        String cookie = null;
        String contentLength = null;
        String contentType = null;

        return Request.builder()
                .method(method)
                .protocol(protocol)
                .requestURI(requestURI)
                .queryString(queryString)
                .build();
    }

    private static void parseQueryString(String[] target) {

    }
}
