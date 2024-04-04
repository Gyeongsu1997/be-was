package myapplication;

import myframework.context.MyApplicationContext;
import myapplication.webserver.WebServer;

public class Application {
    public static void main(String[] args) throws Exception {
        MyApplicationContext ac = new MyApplicationContext(AppConfig.class);
        new WebServer().run(args);
    }
}

