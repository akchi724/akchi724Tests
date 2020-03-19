package ru.liga.akchi724.TestsRun;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import ru.liga.akchi724.FontColor;
import ru.liga.akchi724.annatations.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CustomJUnit {
    public static void runTests(String pathClass) {
        List<Class<?>> classesList = getClassesFromPath(pathClass);
        if (classesList.size() == 0) System.out.println("В данном пакете нет ни одного класса");
        else classesList.forEach(CustomJUnit::startMethods);

    }

    private static void startMethods(Class<?> someClass) {
        try {
            takeMethods(getMethodsWithAnnotations(someClass, Test.class)
                    , getMethodsWithAnnotations(someClass, Before.class)
                    , getMethodsWithAnnotations(someClass, After.class)
                    , someClass.getConstructor().newInstance());
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private static void takeMethods(List<Method> testMethods, List<Method> beforeMethods, List<Method> afterMethods, Object object){
        try {

            beforeMethods.forEach(beforeMethod -> {
                try {
                    beforeMethod.invoke(object);
                } catch (IllegalAccessException | InvocationTargetException e) {

                }
            });
            testMethods.forEach(testMethod -> {
                FontColor.soutBlue("Запуск выполнения метода \""+testMethod.getName()+"\"");

                try {
                    testMethod.invoke(object);
                } catch (IllegalAccessException | InvocationTargetException e) {

                }
                System.out.println("\""+testMethod.getName()+"\" выполнен");
                System.out.println("");
            });
            afterMethods.forEach(afterMethod -> {
                try {
                    afterMethod.invoke(object);
                } catch (IllegalAccessException | InvocationTargetException e) {

                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<Method> getMethodsWithAnnotations(Class<?> someClass, Class an) {
        return Arrays.stream(someClass.getMethods())
                .filter(method -> method.getAnnotation(an) != null).collect(Collectors.toList());
    }

    private static List<Class<?>> getClassesFromPath(String pathClass) {
        return new Reflections(pathClass, new SubTypesScanner(false))
                .getAllTypes()
                .stream()
                .map(CustomJUnit::getClassForName)
                .collect(Collectors.toList());
    }

    private  static Class<?> getClassForName(String name)  {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e){
            return null;
        }

    }
}
