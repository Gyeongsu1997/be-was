package myapplication.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import myframework.http.HttpStatus;
import myframework.http.ResponseEntity;

import java.io.IOException;

public class FileNotFoundExceptionHandlerTest {
    @Test
    void handle() throws IOException {
        //given

        //when
        ResponseEntity<?> responseEntity = FileNotFoundExceptionHandler.handle();

        //then
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
