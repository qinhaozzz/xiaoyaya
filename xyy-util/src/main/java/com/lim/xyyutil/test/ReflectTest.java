package com.lim.xyyutil.test;

import com.lim.xyyutil.annotation.AnimalName;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 1.反射
 * 2.注解
 * @author qinhao
 */
public class ReflectTest {

    @Test
    public void testReflect() {
        try {
            Class<?> c = Class.forName("com.lim.xyyutil.basebean.AnimalZoo");
            Object obj = c.newInstance();
            Field[] fields = c.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(AnimalName.class)) {
                    AnimalName animalName = field.getAnnotation(AnimalName.class);
                    field.set(obj, animalName.value());
                } else if (field.getType() == Date.class) {
                    field.set(obj, new Date());
                } else {
                    //
                }
            }
            Method toString = c.getMethod("toString");
            System.out.println(toString.invoke(obj));
        } catch (ClassNotFoundException e) {
            // Class.forName()
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // c.newInstance()
            e.printStackTrace();
        } catch (InstantiationException e) {
            // c.newInstance()
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // c.getMethod()
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // .invoke()
            e.printStackTrace();
        }

    }

}
