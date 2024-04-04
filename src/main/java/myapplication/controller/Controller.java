package myapplication.controller;

import myapplication.util.HttpRequest;
import myframework.http.ResponseEntity;

import java.io.IOException;

public interface Controller {
    ResponseEntity<?> run(HttpRequest httpRequest) throws IOException;
}
