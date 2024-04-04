package myframework.context;

import myapplication.AppConfig;
import myapplication.controller.Controller;
import myframework.beans.factory.NoSuchBeanDefinitionException;
import myapplication.controller.BoardController;
import myapplication.controller.UserController;
import myapplication.model.User;
import myframework.beans.factory.NoUniqueBeanDefinitionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.lang.reflect.InvocationTargetException;
import java.util.Map;

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
    void getBeanSuccess() {
        //when
        Object bean1 = ac.getBean("userController"); //이름으로만 조회
        UserController bean2 = ac.getBean("userController", UserController.class); //이름 + 타입으로 조회
        UserController bean3 = ac.getBean(UserController.class); //타입으로만 조회

        //then
        assertThat(bean1).isInstanceOf(UserController.class);
        assertEquals(bean1, bean2);
        assertEquals(bean2, bean3);
    }

    @Test
    @DisplayName("name에 해당하는 bean이 존재하지 않을 때")
    void getBeanWithNameNotExist() {
        assertThrows(NoSuchBeanDefinitionException.class, () -> ac.getBean("notExistController"));
    }

    @Test
    @DisplayName("name으로 조회한 bean의 타입이 requiredType과 다를 때")
    void getBeanTypeMismatched() {
        assertThrows(NoSuchBeanDefinitionException.class, () -> ac.getBean("userController", BoardController.class));
    }

    @Test
    @DisplayName("requiredType에 해당하는 bean이 존재하지 않을 때")
    void getBeanWithTypeNotExist() {
        assertThrows(NoSuchBeanDefinitionException.class, () -> ac.getBean(User.class));
    }

    @Test
    @DisplayName("requiredType에 해당하는 bean이 여러개 존재할 때")
    void getBeanWithTypeDuplicate() {
        assertThrows(NoUniqueBeanDefinitionException.class, () -> ac.getBean(Controller.class));
    }

    @Test
    void getBeansOfType() {
        //when
        Map<String, Controller> beansOfType = ac.getBeansOfType(Controller.class);

        //then
        System.out.println("beansOfType.size() = " + beansOfType.size());
        for (String name : beansOfType.keySet()) {
            System.out.println(name);
            assertThat(beansOfType.get(name)).isInstanceOf(Controller.class);
        }
    }

    @Test
    void containsBeanTest() {
        assertThat(ac.containsBean("userController")).isTrue();
        assertThat(ac.containsBean("notExistController")).isFalse();
    }
}
