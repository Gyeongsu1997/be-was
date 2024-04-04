package myapplication.service;

import myapplication.db.UserDatabase;
import myapplication.exception.UserException;
import myapplication.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import myapplication.util.SessionManager;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {
    private final UserService userService = new UserService();

    @BeforeEach
    void beforeEach() {
        UserDatabase.clearAll();
    }

    @Test
    @DisplayName("회원가입: 성공")
    void createUserSuccess() {
        //Given
        String userId = "gyeongsu";
        String password = "1234";
        String name = "gyeongsu";
        String email = "gyeongsu@gmail.com";

        //When
        String id = userService.create(userId, password, name, email);

        //Then
        User user = UserDatabase.findUserById(id);

        assertEquals(userId, user.getUserId());
        assertEquals(password, user.getPassword());
        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());
    }

    @Test
    @DisplayName("회원가입: 매개변수로 null이 들어왔을 때")
    void createUserWhenNull() {
        //Given
        String userId = "gyeongsu";
        String password = "1234";
        String name = "gyeongsu";
        String email = null;

        //When
        UserException e = assertThrows(UserException.class,
                () -> userService.create(userId, password, name, email));


        //Then
        assertEquals(UserException.NULL_EMAIL, e.getMessage());
    }

    @Test
    @DisplayName("회원가입: 중복 아이디")
    void createUserDuplicateUserId() {
        //Given
        String userId = "gyeongsu";
        String password = "1234";
        String name = "gyeongsu";
        String email = "gyeongsu@gmail.com";
        userService.create(userId, password, name, email);

        //When
        UserException e = assertThrows(UserException.class,
                () -> userService.create(userId, "5678", "호날두", "ronaldo@gmail.com"));


        //Then
        assertEquals(UserException.DUPLICATE_ID, e.getMessage());
    }

    @Test
    @DisplayName("회원가입: 중복 이메일")
    void createUserDuplicateEmail() {
        //Given
        String userId = "gyeongsu";
        String password = "1234";
        String name = "gyeongsu";
        String email = "gyeongsu@gmail.com";
        userService.create(userId, password, name, email);

        //When
        UserException e = assertThrows(UserException.class,
                () -> userService.create("ronaldo", "5678", "호날두", "gyeongsu@gmail.com"));


        //Then
        assertEquals(UserException.DUPLICATE_EMAIL, e.getMessage());
    }

    @Test
    @DisplayName("로그인: 성공")
    void loginSuccess() {
        //Given
        String userId = "gyeongsu";
        String password = "1234";
        String name = "gyeongsu";
        String email = "gyeongsu@gmail.com";
        userService.create(userId, password, name, email);

        //When
        String sessionId = userService.login(userId, password);

        //Then
        User user = SessionManager.getUserBySessionId(sessionId);

        assertEquals(userId, user.getUserId());
        assertEquals(password, user.getPassword());
        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());
    }

    @Test
    @DisplayName("로그인: 존재하지 않는 아이디")
    void loginWithUserIdNotExist() {
        //Given

        //When
        String sessionId = userService.login("gyeongsu", "1234");

        //Then
        assertNull(sessionId);
    }

    @Test
    @DisplayName("로그인: 비밀번호 오류")
    void loginNotCorrectPassword() {
        //Given
        String userId = "gyeongsu";
        String password = "1234";
        String name = "gyeongsu";
        String email = "gyeongsu@gmail.com";
        userService.create(userId, password, name, email);

        //When
        String sessionId = userService.login("gyeongsu", "12345");

        //Then
        assertNull(sessionId);
    }

    @Test
    @DisplayName("회원 정보 수정: 성공")
    void updateUserSuccess() {
        //Given
        String userId = "gyeongsu";
        String password = "1234";
        String name = "gyeongsu";
        String email = "gyeongsu@gmail.com";
        userService.create(userId, password, name, email);

        String newPassword = "5678";
        String newName = "호날두";
        String newEmail = "ronaldo@gmail.com";

        //When
        userService.update(userId, newPassword, newName, newEmail);

        //Then
        User user = UserDatabase.findUserById(userId);

        assertEquals(userId, user.getUserId());
        assertEquals(newPassword, user.getPassword());
        assertEquals(newName, user.getName());
        assertEquals(newEmail, user.getEmail());
    }

    @Test
    @DisplayName("회원 정보 수정: 매개변수로 null이 들어 왔을 때")
    void updateUserWhenNull() {
        //Given
        String userId = "gyeongsu";
        String password = "1234";
        String name = "gyeongsu";
        String email = "gyeongsu@gmail.com";
        userService.create(userId, password, name, email);

        //When
        UserException e = assertThrows(UserException.class,
                () -> userService.update(userId, "5678", null, email));


        //Then
        assertEquals(UserException.NULL_NAME, e.getMessage());
    }

    @Test
    @DisplayName("회원 정보 수정: 중복 이메일")
    void updateUserDuplicateEmail() {
        //Given
        userService.create("gyeongsu", "1234", "gyeongsu", "gyeongsu@gmail.com");

        String userId = "ronaldo";
        String password = "1234";
        String name = "호날두";
        String email = "ronaldo@hyundai.com";
        userService.create(userId, password, name, email);

        //When
        UserException e = assertThrows(UserException.class,
                () -> userService.update(userId, password, "gyeongsu", "gyeongsu@gmail.com"));


        //Then
        assertEquals(UserException.DUPLICATE_EMAIL, e.getMessage());
    }
}
