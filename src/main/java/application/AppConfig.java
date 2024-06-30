package application;

import application.controller.BoardController;
import application.controller.FrontController;
import application.controller.HomeController;
import application.controller.UserController;
import framework.context.ApplicationContext;
import framework.context.annotation.Bean;
import application.service.BoardService;
import application.service.UserService;

public class AppConfig {
    private final ApplicationContext ac;

    public AppConfig(ApplicationContext ac) {
        this.ac = ac;
    }

    /**
     * Controller
     */
    @Bean
    public void frontController() {
        FrontController.putController("homeController", homeController());
        FrontController.putController("userController", userController());
        FrontController.putController("boardController", boardController());
    }

    @Bean
    public HomeController homeController() {
        if (ac.containsBean("homeController")) {
            return ac.getBean("homeController", HomeController.class);
        } else {
            HomeController homeController = new HomeController();
            ac.putBean("homeController", homeController);
            return homeController;
        }
    }

    @Bean
    public UserController userController() {
        if (ac.containsBean("userController")) {
            return ac.getBean("userController", UserController.class);
        } else {
            UserController userController = new UserController(userService());
            ac.putBean("userController", userController);
            return userController;
        }
    }

    @Bean
    public BoardController boardController() {
        if (ac.containsBean("boardController")) {
            return ac.getBean("boardController", BoardController.class);
        } else {
            BoardController boardController = new BoardController(boardService());
            ac.putBean("boardController", boardController);
            return boardController;
        }
    }

    /**
     * Service
     */
    @Bean
    public BoardService boardService() {
        if (ac.containsBean("boardService")) {
            return ac.getBean("boardService", BoardService.class);
        } else {
            BoardService boardService = new BoardService();
            ac.putBean("boardService", boardService);
            return boardService;
        }
    }

    @Bean
    public UserService userService() {
        if (ac.containsBean("userService")) {
            return ac.getBean("userService", UserService.class);
        } else {
            UserService userService = new UserService();
            ac.putBean("userService", userService);
            return userService;
        }
    }
}
