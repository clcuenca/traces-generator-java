package dev.clcuenca.utilities;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Reflection {

    /**
     * <p>Returns a flag indicating if the specified constructor parameter types match the specified arguments
     * in length & type.</p>
     * @param constructorParameters The constructor parameters to check against
     * @param arguments The arguments to check against
     * @return Boolean flag indicating if the specified constructor parameter types match the specified argument types.
     */
    public static boolean ParameterTypesMatch(final Class<?>[] constructorParameters, final Object... arguments) {

        // Initialize the preliminary result
        boolean doMatch = ((constructorParameters != null) && (arguments != null))
                && arguments.length == constructorParameters.length;

        // Iterate through each type & check while the argument lists match
        int index = 0; while((doMatch) && (index < constructorParameters.length)) {
            doMatch = constructorParameters[index]
                    .getTypeName()
                    .equals(arguments[index++].getClass().getTypeName());
        }

        // Return the result
        return doMatch;

    }

    /**
     * <p>Instantiates a new instance of the specified class with the specified constructor arguments, if the
     * specified class contains a constructor declaration that matches the variadic arguments. This method
     * will return an instance with the first Constructor that matches the arguments.</p>
     * @param clazz The class to instantiate
     * @param arguments The arguments to give to the constructor
     * @return Type instance
     * @param <Type> The Type representing the class to instantiate
     */
    @SuppressWarnings("unchecked")
    public static <Type> Type NewInstanceOf(final Class<Type> clazz, final Object... arguments) {

        // Initialize the result
        Type result = null;

        // Retrieve the class's constructors
        final Constructor<Type>[] constructors = (Constructor<Type>[]) clazz.getDeclaredConstructors();

        // Iterate through each of the available constructors
        for(final Constructor<Type> constructor: constructors)

            try {

                // Instantiate the result
                result = constructor.newInstance(arguments);

            } catch(final InstantiationException | IllegalAccessException | InvocationTargetException exception){

                break;

            }

        // Return the result
        return result;

    }

    public static <Type> boolean DoesDeclareField(final Type instance, final String fieldName) {

        boolean doesDeclareField = true;

        try {

            instance.getClass().getDeclaredField(fieldName);

        } catch(final NoSuchFieldException noSuchFieldException) {

            doesDeclareField = false;

        }

        return doesDeclareField;

    }

    public static <Type> Object GetFieldValue(final Type instance, final String fieldName) {

        // Initialize the result
        Object result;

        try {

            // Retrieve the field
            final Field field = instance.getClass().getDeclaredField(fieldName);

            // Set accessible
            field.setAccessible(true);

            // Update the result
            result = field.get(instance);

        } catch(final NoSuchFieldException | IllegalAccessException exception) {

            result = null;

        }

        // Return the field instance
        return result;

    }

    public static <Type> void SetFieldValueOf(final Type instance, final String fieldName, final Object value) {

        try {

            // Retrieve the field
            final Field field = instance.getClass().getDeclaredField(fieldName);

            // Set accessible
            field.setAccessible(true);

            // Set the value
            field.set(instance, value);

        } catch(final NoSuchFieldException | IllegalAccessException exception) {

            // Ignore

        }

    }

    public static <Type, AnnotationType extends Annotation>
    List<Method> MethodsWithAnnotationOf(final Class<Type> clazz, final Class<AnnotationType> annotationTypeClass) {

        return Arrays.stream(clazz.getDeclaredMethods()).toList().stream()
                .filter(method -> method.isAnnotationPresent(annotationTypeClass))
                .collect(Collectors.toList());

    }

    public static <Type, AnnotationType extends Annotation>
    List<Annotation> DeclaredMethodAnnotations(final Class<Type> clazz, final Class<AnnotationType> annotationTypeClass) {

        // Initialize a handle to the result
        final List<Annotation> annotations = new ArrayList<>();

        // Assert the Class Object and Annotation Class Object are valid
        if(clazz != null && annotationTypeClass != null) {

            // Aggregate the Annotations to the list
            Arrays.stream(clazz.getDeclaredMethods()).toList().stream()
                    .filter(method -> method.isAnnotationPresent(annotationTypeClass))
                    .forEach(method -> annotations.addAll(Arrays.stream(method.getDeclaredAnnotations()).toList()));

        }

        // Return the result
        return annotations;

    }

    public static <Type, Check> boolean AreEqual(final Type instance, final Class<Check> check) {

        // Initialize the preliminary result
        boolean areEqual = (instance != null) && (check != null);

        // If the preliminary result is valid
        if(areEqual) {

            // Initialize a handle to the class names
            final String instanceName   = instance.getClass().getName();
            final String checkName      = check.getName();

            // Update the result
            areEqual = instanceName.equals(checkName);

        }

        // Return the Result
        return areEqual;

    }


}