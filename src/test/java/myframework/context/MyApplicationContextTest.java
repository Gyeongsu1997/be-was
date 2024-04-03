package myframework.context;

import myapplication.AppConfig;
import myframework.beans.factory.NoSuchBeanDefinitionException;
import myapplication.controller.BoardController;
import myapplication.controller.UserController;
import myapplication.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class MyApplicationContextTest {
    private MyApplicationContext ac;

    @BeforeEach
    void beforeEach() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        ac = new MyApplicationContext(AppConfig.class);
    }

    @Test
    void putBean() {
        //given
        String name = "user";
        User bean = new User("gyeongsu", "1234", "경수", "gyeongsu@gmail.com");

        //when
        ac.putBean(name, bean);

        //then
        Object foundBean = ac.getBean(name);
        assertThat(foundBean).isInstanceOf(User.class);
        assertEquals(foundBean, bean);
    }

    @Test
    @DisplayName("name에 해당하는 bean이 존재할 때")
    void getBeanSuccess() {
        Object bean = ac.getBean("userController");
        assertThat(bean).isInstanceOf(UserController.class);
    }

    @Test
    @DisplayName("name에 해당하는 bean이 존재하지 않을 때")
    void getBeanWithNameNotExist() {
        assertThrows(NoSuchBeanDefinitionException.class, () -> ac.getBean("notExistController"));
    }

    @Test
    @DisplayName("bean의 타입이 requiredType과 다를 때")
    void getBeanTypeMismatched() {
        assertThrows(NoSuchBeanDefinitionException.class, () -> ac.getBean("userController", BoardController.class));
    }

    @Test
    @DisplayName("requiredType에 해당하는 bean이 존재하지 않을 때")
    void getBeanWithTypeNotExist() {
        assertThrows(NoSuchBeanDefinitionException.class, () -> ac.getBean(User.class));
    }
}
