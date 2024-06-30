package application.controller;

import application.model.Post;
import application.model.User;
import application.exception.*;
import application.util.HttpRequest;
import framework.http.*;
import application.service.BoardService;
import application.util.ResourceUtils;
import application.util.SessionManager;
import framework.util.StringUtils;
import application.template.board.ShowTemplate;
import application.template.board.UpdateTemplate;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.Map;

public class BoardController implements Controller {
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    public ResponseEntity<?> run(HttpRequest httpRequest) throws IOException {
        try {
            String path = httpRequest.getPath();
            String lastPath = ResourceUtils.getLastPath(httpRequest.getPath());

            ResponseEntity<?> responseEntity = null;
            if (lastPath.equals("write.html")) {
                responseEntity = writeForm(httpRequest);
            }
            if (lastPath.equals("write")) {
                if (httpRequest.getMethod() != HttpMethod.POST)
                    throw new WebException(HttpStatus.METHOD_NOT_ALLOWED);
                responseEntity = write(httpRequest);
            }
            if (lastPath.equals("comment")) {
                if (httpRequest.getMethod() != HttpMethod.POST)
                    throw new WebException(HttpStatus.METHOD_NOT_ALLOWED);
                responseEntity = comment(httpRequest);
            }
            if (StringUtils.isMatched(path, "/board/(\\d+)/delete/(\\d+)")) {
                String[] tokens = path.split("/");
                Long postId = Long.parseLong(tokens[2]);
                Long commentId = Long.parseLong(tokens[4]);

                if (httpRequest.getMethod() != HttpMethod.POST)
                    throw new WebException(HttpStatus.METHOD_NOT_ALLOWED);
                return deleteComment(postId, commentId);
            }
            if (StringUtils.isMatched(path, "/board/.+/(\\d+)")) {
                String[] tokens = path.split("/");
                Long postId = Long.parseLong(tokens[tokens.length - 1]);

                String middlePath = ResourceUtils.getMiddlePath(path);
                if ("show".equals(middlePath)) {
                    responseEntity = show(httpRequest, postId);
                }
                if ("delete".equals(middlePath)) {
                    if (httpRequest.getMethod() != HttpMethod.POST)
                        throw new WebException(HttpStatus.METHOD_NOT_ALLOWED);
                    responseEntity = delete(httpRequest, postId);
                }
                if ("update".equals(middlePath)) {
                    if (httpRequest.getMethod() == HttpMethod.GET)
                        responseEntity = updateForm(httpRequest, postId);
                    if (httpRequest.getMethod() == HttpMethod.POST)
                        responseEntity = update(httpRequest, postId);
                }
            }
            return responseEntity;
        } catch (WebException e) {
            return WebExceptionHandler.handle(e);
        } catch (FileNotFoundException e) {
            return FileNotFoundExceptionHandler.handle();
        }
    }

    private ResponseEntity<?> writeForm(HttpRequest httpRequest) throws IOException {
        boolean isLoggedIn = SessionManager.isLoggedIn(httpRequest);

        if (!isLoggedIn) {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create("/user/login.html"))
                    .build();
        }

        User loggedInUser = SessionManager.getLoggedInUser(httpRequest);

        String username = "<li><a role=\"button\">{username}</a></li>";
        username = username.replace("{username}", loggedInUser.getName());

        String html = new String(ResourceUtils.getStaticResource(httpRequest.getPath()));
        byte[] body = html.replace("<li id=\"username\"></li>", username)
                .getBytes();

        return ResponseEntity.ok()
                .contentType(MediaType.getContentType(httpRequest))
                .contentLength(body.length)
                .body(body);
    }

    private ResponseEntity<?> write(HttpRequest httpRequest) throws IOException {
        Map<String, String> query = httpRequest.getBodyParams();

        User writer = SessionManager.getLoggedInUser(httpRequest);
        String title = query.get("title");
        String contents = query.get("contents");

        try {
            boardService.write(writer, title, contents);

            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create("/index.html"))
                    .build();
        } catch (PostException e) {
            return PostExceptionHandler.handle(writer, e);
        }
    }

    private ResponseEntity<?> show(HttpRequest httpRequest, Long postId) throws IOException {
        boolean isLoggedIn = SessionManager.isLoggedIn(httpRequest);

        if (!isLoggedIn) {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create("/user/login.html"))
                    .build();
        }

        User loggedInUser = SessionManager.getLoggedInUser(httpRequest);

        byte[] body = ShowTemplate.render(loggedInUser, boardService.getPostById(postId));

        return ResponseEntity.ok()
                .contentType(MediaType.getContentType(httpRequest))
                .contentLength(body.length)
                .body(body);
    }

    private ResponseEntity<?> comment(HttpRequest httpRequest) {
        Map<String, String> query = httpRequest.getBodyParams();

        User writer = SessionManager.getLoggedInUser(httpRequest);
        String postId = query.get("postId");
        String body = query.get("body");

        boardService.comment(Long.parseLong(postId), writer, body);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("/board/show/" + postId))
                .build();
    }

    private ResponseEntity<?> delete(HttpRequest httpRequest, Long postId) {
        User loggedInUser = SessionManager.getLoggedInUser(httpRequest);

        boardService.delete(postId, loggedInUser);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("/index.html"))
                .build();
    }

    private ResponseEntity<?> updateForm(HttpRequest httpRequest, Long postId) throws IOException {
        User loggedInUser = SessionManager.getLoggedInUser(httpRequest);
        Post post = boardService.getPostById(postId);

        if (loggedInUser == null || !loggedInUser.equals(post.getWriter()))
            throw new WebException(HttpStatus.UNAUTHORIZED);

        byte[] body = UpdateTemplate.render(loggedInUser, post);

        return ResponseEntity.ok()
                .contentType(MediaType.getContentType(httpRequest))
                .contentLength(body.length)
                .body(body);
    }

    private ResponseEntity<?> update(HttpRequest httpRequest, Long postId) throws IOException {
        Map<String, String> query = httpRequest.getBodyParams();

        String title = query.get("title");
        String contents = query.get("contents");

        boardService.update(postId, title, contents);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("/index.html"))
                .build();
    }

    private ResponseEntity<?> deleteComment(Long postId, Long commentId) {
        boardService.deleteComment(postId, commentId);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("/board/show/" + postId))
                .build();
    }
}
