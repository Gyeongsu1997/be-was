package application.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import framework.http.HttpStatus;
import framework.http.ResponseEntity;

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
