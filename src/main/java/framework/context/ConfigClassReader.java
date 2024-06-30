package framework.context;

import framework.context.annotation.Bean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ConfigClassReader {
    private final ApplicationContext ac;

    public ConfigClassReader(ApplicationContext ac) {
        this.ac = ac;
    }

    public void read(Class<?> configClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Method[] methods = configClass.getMethods();
        for (Method method : methods) {
            if (method.getAnnotation(Bean.class) == null) { //@Bean annotation이 없으면 continue
                continue;
            }
            if (ac.containsBean(method.getName())) { //method 이름에 해당하는 bean이 이미 존재하면 continue
                continue;
            }
            method.invoke(configClass.getDeclaredConstructor(ApplicationContext.class).newInstance(ac));
        }
    }
}
