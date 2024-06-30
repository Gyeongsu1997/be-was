package framework.boot;

import application.AppConfig;
import framework.context.ApplicationContext;
import framework.web.servlet.DispatcherServlet;
import webserver.WebServer;

public class SpringApplication {
    public static ApplicationContext run(Class<?> primarySource, String... args) throws Exception {
        return run(new Class<?>[]{primarySource}, args);
    }

    public static ApplicationContext run(Class<?>[] primarySources, String[] args) throws Exception {
        return new SpringApplication().run(args);
    }

    public ApplicationContext run(String... args) throws Exception {
        ApplicationContext context = createApplicationContext();
        new WebServer(new DispatcherServlet(context)).run(args);
        return context;
    }

    private ApplicationContext createApplicationContext() throws Exception {
        return new ApplicationContext(AppConfig.class);
    }
}
