package ru.liga.akchi724.TestsRun;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import ru.liga.akchi724.FontColor;
import ru.liga.akchi724.annatations.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CustomJUnit {
    public  void runTests(String pathClass) {
        List<Class<?>> classesList = getClassesFromPath(pathClass);
        if (classesList.size() == 0)
            System.out.println("В данном пакете нет ни одного класса");
        else classesList.forEach(x->startMethods(x));

    }

    private  void startMethods(Class<?> someClass) {
        try {
            invokeMethods(getMethodsWithAnnotations(someClass, Test.class)
                    , getOnlyOneMethodWithAnnotation(someClass, Before.class)
                    , getOnlyOneMethodWithAnnotation(someClass, After.class)
                    , someClass.getConstructor().newInstance());
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void invokeMethods(List<Method> testMethods, Method beforeMethod, Method afterMethod, Object object){
        try {

                try {
                    beforeMethod.invoke(object);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }

            testMethods.forEach(testMethod -> {
                FontColor.soutBlue("Запуск выполнения метода \""+testMethod.getName()+"\"");
                try {
                    testMethod.invoke(object);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                System.out.println("\""+testMethod.getName()+"\" выполнен");
                System.out.println("");
            });

                try {
                    afterMethod.invoke(object);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Method> getMethodsWithAnnotations(Class<?> someClass, Class an) {
        return Arrays.stream(someClass.getMethods())
                .filter(method -> method.getAnnotation(an) != null).collect(Collectors.toList());
    }
    private Method getOnlyOneMethodWithAnnotation(Class<?> someClass,Class an){
        List<Method> methods= new ArrayList<>();
        methods=Arrays.stream(someClass.getMethods()).filter(method -> method.getAnnotation(an)!=null).collect(Collectors.toList());
        if (methods.size()>1)
            FontColor.soutRed("Используйте только один метод с аннотацией @"+an.getSimpleName());
        return methods.get(0);
    }

    private List<Class<?>> getClassesFromPath(String pathClass) {
        return new Reflections(pathClass, new SubTypesScanner(false))
                .getAllTypes()
                .stream()
                .map(x->getClassForName(x))
                .collect(Collectors.toList());
    }

    private Class<?> getClassForName(String name)  {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e){
            return null;
        }

    }
}
