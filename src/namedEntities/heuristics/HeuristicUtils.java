package namedEntities.heuristics;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HeuristicUtils {
    public static List<String> getHeuristics() {
        List<Class<?>> implementingClasses = new ArrayList<>();
        List<String> heuristicNames = new ArrayList<>(); 
        Path path = Paths.get("bin/namedEntities/heuristics/");

        try {
            Files.walk(path)
                 .filter(Files::isRegularFile)
                 .forEach(file -> {
                        Class<?> clazz = loadClassFromFile(file);
                        if (clazz != null) {
                            implementingClasses.add(clazz);
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

        implementingClasses.forEach(clazz -> {
            try {
                Object object = clazz.getDeclaredConstructor().newInstance();
                Method ms = object.getClass().getMethod("getName");
                heuristicNames.add((String) ms.invoke(object));
                // These are most probably never going to happen
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        });

        return heuristicNames;
    }

    private static Class<?> loadClassFromFile(Path path) {
        
        String className = path.toString()
                          .replace(File.separatorChar, '.')
                          .replace("bin.", "")             
                          .replace(".class", ""); 

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Class<?> clazz = null;
        try {
            clazz = classLoader.loadClass(className);
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
            return null;
        }
        
        if (!Heuristics.class.isAssignableFrom(clazz) || clazz.getName().contains(".Heuristics")) {
            return null;
        }

        return clazz;
    }

}
