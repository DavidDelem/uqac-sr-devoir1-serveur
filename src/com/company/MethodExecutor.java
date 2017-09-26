package com.company;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * La classe MethodExecutor permet d'exectuer par reflection
 * des méthodes d'une classe compilée après le lancement du programme
 *
 * @author  David Delemotte
 * @version 1.0
 * @since   2017-09-12
 */

public class MethodExecutor {

    private String className;
    private String pathTmp;
    private Object object;

    public MethodExecutor(String className, String pathTmp) {
        this.className = className;
        this.pathTmp = pathTmp;
    }

    public MethodExecutor(String className, Object object) {
        this.className = className;
        this.object = object;
    }

    /**
     * Execute une méthode par reflection
     *
     * @param  methodName  le nom de la méthode à executer
     * @param  methodParams les valeurs des paramètres pour cette méthode
     * @return la valeur retournée par la méthode
     */

    public Object executeMethod(String methodName, String[] methodParams) {

        Object result = null;
        Object[] realParams = new Object[methodParams.length];
        Class<?> typeParams[] = new Class[methodParams.length];

        try {
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { new File(pathTmp).toURI().toURL() });

            Class cls = Class.forName(this.className, true, classLoader);
            Object _instance = cls.newInstance();

            /* Etape préparatoire pour la récupération de la méthode à appeler      */
            /* Il est nécessaire de déterminer la liste des types de ses paramètres */
            Method m[] = cls.getMethods();

            for (Method method : m) {
                if (method.getName().equals(methodName) && method.getParameterCount() == methodParams.length) {
                    for (int i = 0; i < method.getParameterTypes().length; i++) {
                        Class<?> paramClass = method.getParameterTypes()[i];
                        if (paramClass == String.class) {
                            realParams[i] = methodParams[i];
                            typeParams[i] = String.class;
                        } else if (paramClass == Integer.class) {
                            realParams[i] = Integer.parseInt(methodParams[i]);
                            typeParams[i] = Integer.class;
                        }
                    }
                }
            }

            /* Récupération de la méthode demandée par l'utilisateur */
            Method method = cls.getMethod(methodName, typeParams);

            /* Appel de la méthode par reflection */
            result = method.invoke(_instance, realParams);

        } catch (InvocationTargetException x) {
            Throwable cause = x.getCause();
            System.err.format("Erreur lors de l'invocation de la fonction", cause.getMessage());
        } catch(IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception x) {
            x.printStackTrace();
        }

        return result;
    }

    public Object executeMethod2(String methodName, String[] methodParams) {
        Object result = null;
//        Object[] realParams = new Object[methodParams.length];
//        Class<?> typeParams[] = new Class[methodParams.length];
//
//        try {
//
//            Class cls = Class.forName("com.company." + this.className);
//            Object _instance = object;
//
//            /* Etape préparatoire pour la récupération de la méthode à appeler      */
//            /* Il est nécessaire de déterminer la liste des types de ses paramètres */
//            Method m[] = cls.getMethods();
//
//            for (Method method : m) {
//                if (method.getName().equals(methodName) && method.getParameterCount() == methodParams.length) {
//                    for (int i = 0; i < method.getParameterTypes().length; i++) {
//                        Class<?> paramClass = method.getParameterTypes()[i];
//                        if (paramClass == String.class) {
//                            realParams[i] = methodParams[i];
//                            typeParams[i] = String.class;
//                        } else if (paramClass == Integer.class) {
//                            realParams[i] = Integer.parseInt(methodParams[i]);
//                            typeParams[i] = Integer.class;
//                        }
//                    }
//                }
//            }
//
//            /* Récupération de la méthode demandée par l'utilisateur */
//            Method method = cls.getMethod(methodName, typeParams);
//
//            /* Appel de la méthode par reflection */
//            result = method.invoke(_instance, realParams);
//
//        } catch (InvocationTargetException x) {
//            Throwable cause = x.getCause();
//            System.err.format("Erreur lors de l'invocation de la fonction", cause.getMessage());
//        } catch(IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (Exception x) {
//            x.printStackTrace();
//        }

        return result;
    }
}
