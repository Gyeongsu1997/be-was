package application.controller;

import application.util.HttpRequest;
import framework.http.ResponseEntity;

import java.io.IOException;

public interface Controller {
    ResponseEntity<?> run(HttpRequest httpRequest) throws IOException;
}
