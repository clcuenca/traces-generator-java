package dev.clcuenca.utilities;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Collection of reflection utility functions.</p>
 * @author Carlos L. Cuenca
 * @since 0.1.0
 */
public class Reflection {

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

                // To create the instance
                constructor.setAccessible(true);

                // Instantiate the result
                result = constructor.newInstance(arguments);

                // Set it back
                constructor.setAccessible(false);

            } catch(final InstantiationException | IllegalAccessException | InvocationTargetException exception){

                break;

            }

        // Return the result
        return result;

    }

    /**
     * <p>Returns the {@link List} of {@link Method}s that are annotated with the specified annotation {@link Class}.</p>
     * @param clazz The {@link Class} object to check.
     * @param annotationTypeClass The annotation type {@link Class} to filter.
     * @return {@link List} of {@link Method}s that are annotated with the specified annotation {@link Class}.
     * @param <Type> Type variable corresponding the {@link Class} object.
     * @param <AnnotationType> Type variable corresponding to the annotation {@link Class} type.
     * @since 0.1.0
     * @see Class
     * @see List
     * @see Method
     */
    public static <Type, AnnotationType extends Annotation>
    List<Method> MethodsWithAnnotationOf(final Class<Type> clazz, final Class<AnnotationType> annotationTypeClass) {

        return Arrays.stream(clazz.getDeclaredMethods()).toList().stream()
                .filter(method -> method.isAnnotationPresent(annotationTypeClass))
                .collect(Collectors.toList());

    }

    /**
     * <p>Returns the {@link List} of all instances of the specified annotation {@link Class} declared in the specified
     * {@link Class}.</p>
     * @param clazz The {@link Class} to retrieve the {@link List} of {@link Annotation}s.
     * @param annotationTypeClass The annotation {@link Class} to retrieve.
     * @return {@link List} of all instances of the specified annotation {@link Class} declared in the specified.
     * @param <Type> Type variable corresponding the {@link Class} object.
     * @param <AnnotationType> Type variable corresponding to the annotation {@link Class} type.
     * @since 0.1.0
     * @see Annotation
     * @see Class
     * @see List
     */
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

}
