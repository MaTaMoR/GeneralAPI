package me.matamor.generalapi.api.reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class SafeMethod<T> implements MethodAccessor<T> {

    private Method method;
    private Class<?>[] parameterTypes;
    private boolean isStatic = false;

    public SafeMethod(Method method) {
        if (method == null) {
            throw new IllegalArgumentException("Can not construct using a null Method");
        }

        this.method = method;
        this.parameterTypes = this.method.getParameterTypes();
        this.isStatic = Modifier.isStatic(this.method.getModifiers());
    }

    public SafeMethod(Class<?> clazz, String name, Class<?>... parameterTypes) {
        this.parameterTypes = parameterTypes;

        try {
            method = clazz.getDeclaredMethod(name, parameterTypes);
            if(method != null) {
                method.setAccessible(true);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return method.getName();
    }

    public boolean isOverridedIn(Class<?> type) {
        try {
            Method m = type.getDeclaredMethod(method.getName(), method.getParameterTypes());
            return m.getDeclaringClass() != method.getDeclaringClass();
        } catch (Throwable t) {
            return false;
        }
    }

    @Override
    public boolean isValid() {
        return this.method != null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T invoke(Object instance, Object... args) {
        if (this.method != null) {
            if (!this.isStatic && instance == null) {
                throw new IllegalArgumentException("Non-static methods require a valid instance passed in - the instance was null");
            }
            if (args.length != parameterTypes.length) {
                throw new IllegalArgumentException("Illegal amount of arguments - check method signature");
            }
            try {
                return (T) this.method.invoke(instance, args);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (IllegalArgumentException e) {
                // First find a more understandable message for this
                if (args.length == parameterTypes.length) {
                    for (int i = 0; i < parameterTypes.length; i++) {
                        Object arg = args[i];
                        if (parameterTypes[i].isPrimitive() && arg == null) {
                            throw new IllegalArgumentException("Passed in null for primitive type parameter #" + i);
                        } else if (arg != null && !parameterTypes[i].isAssignableFrom(arg.getClass())) {
                            throw new IllegalArgumentException("Passed in wrong type for parameter #" + i + " (" + parameterTypes[i].getName() + " expected)");
                        }
                    }
                }

                throw e;
            }
        }
        return null;
    }
}

