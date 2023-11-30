package dev.clcuenca;

import dev.clcuenca.phase.GenerateCombinations;
import dev.clcuenca.phase.ParseFile;
import dev.clcuenca.phase.Phase;
import dev.clcuenca.utilities.Files;
import dev.clcuenca.utilities.SourceFile;
import dev.clcuenca.utilities.Strings;

import java.io.PrintStream;
import java.lang.annotation.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static dev.clcuenca.utilities.Files.RetrieveMatchingFilesListFrom;
import static dev.clcuenca.utilities.Reflection.*;

public class TracesGenerator extends Phase.Listener {

    /**
     * <p>Maintains the set of {@link SourceFile}s that have been opened.</p>
     * @since 1.0.0
     * @see Map
     * @see String
     * @see SourceFile
     * @see HashMap
     */
    private final static Map<String, SourceFile> Opened = new HashMap<>();

    /**
     * <p>Contains a mapping of the {@link Phase}s executed by the tracesGenerator to the corresponding {@link Phase.Listener}
     * that will handle the {@link Phase.Message} callbacks. This {@link Map} is used to initialize a set of
     * {@link Phase} instances that will be executed by the {@link TracesGenerator}</p>
     * @see Phase
     * @see Phase.Message
     * @see Phase.Listener
     * @see Map
     * @see TracesGenerator
     */
    private final static Map<Class<? extends Phase>, Phase> ActivePhases = new HashMap<>();

    /**
     * <p>Contains a mapping of the next {@link Phase} executed by the {@link TracesGenerator} from the corresponding previous
     * {@link Phase}.</p>
     * @see Phase
     * @see Phase.Message
     * @see Phase.Listener
     * @see Map
     */
    private final static Map<Class<? extends Phase>, Class<? extends Phase>> NextPhaseOf = Map.of(
            ParseFile.class, GenerateCombinations.class
    );

    /**
     * <p>The current TracesGenerator version.</p>
     * @since 1.0.0
     */
    private final static String Version = "1.0.0";

    /**
     * <p>{@link String} value of the basic {@link TracesGenerator} usage.</p>
     * @since 1.0.0
     */
    private final static String Usage = "Usage: 'traces [<option>] <files>";

    /**
     * <p>Regex pattern that corresponds to a TracesGenerator source file.</p>
     * @since 1.0.0
     */
    private final static String TracesGeneratorSourceFileRegex = ".*\\.([dD][oO][tT])";

    /**
     * <p>The standard library relative include directory.</p>
     * @since 1.0.0
     */
    private final static String IncludeDirectory = "include";

    /**
     * <p>Flag that indicates if the {@link TracesGenerator} should output colored messages.</p>
     * @since 1.0.0
     */
    private static boolean ShowColor = false;

    /**
     * <p>Flag that indicates if the {@link TracesGenerator} should emit informative, warning, and error messages.</p>
     * @since 1.0.0
     */
    private static boolean ShowMessage = false;

    /**
     * <p>{@link Set} containing the {@link String} paths the {@link TracesGenerator} will look in for source files.</p>
     * @since 1.0.0
     * @see Set
     */
    private final static Set<String> IncludePaths = new LinkedHashSet<>();

    /**
     * <p>The set of files specified in the command line to be compiled.</p>
     * @since 1.0.0
     * @see Set
     */
    private final static Set<String> FileSet = new LinkedHashSet<>();

    /**
     * <p>The standard {@link PrintStream} that handles reporting informative messages.</p>
     * @since 1.0.0
     * @see PrintStream
     */
    private final static PrintStream InfoStream = System.out;

    /**
     * <p>The standard {@link PrintStream} that handles reporting warning messages.</p>
     * @since 1.0.0
     * @see PrintStream
     */
    private final static PrintStream WarningStream = System.out;

    /**
     * <p>The standard {@link PrintStream} that handles reporting error messages.</p>
     * @since 1.0.0
     * @see PrintStream
     */
    private final static PrintStream ErrorStream = System.err;

    /**
     * <p>Reports the specified informative {@link String} using {@link TracesGenerator#InfoStream}.</p>
     * @param message The {@link String} message to report.
     * @since 1.0.0
     * @see PrintStream
     * @see String
     * @see TracesGenerator#InfoStream
     */
    public static void Info(final String message) {

        if(TracesGenerator.ShowMessage && (message != null))
            InfoStream.println("[info] " + message);

    }

    /**
     * <p>Reports the specified warning {@link String} using {@link TracesGenerator#WarningStream}.</p>
     * @param message The {@link String} message to report.
     * @since 1.0.0
     * @see PrintStream
     * @see String
     * @see TracesGenerator#WarningStream
     */
    public static void Warn(final String message) {

        if(TracesGenerator.ShowMessage && (message != null))
            WarningStream.println("[warning] " + message);

    }

    /**
     * <p>Reports the specified error {@link String} using {@link TracesGenerator#ErrorStream}.</p>
     * @param message The {@link String} message to report.
     * @since 1.0.0
     * @see PrintStream
     * @see String
     * @see TracesGenerator#ErrorStream
     */
    public static void Error(final String message) {

        if(message != null)
            ErrorStream.println("[error] " + message);

    }

    /**
     * <p>Prints the {@link TracesGenerator#Usage} {@link String}.</p>
     * @since 1.0.0
     */
    private static void PrintUsage() {

        InfoStream.println(TracesGenerator.Usage);

    }

    /**
     * Returns a flag indicating if the {@link String} value is a valid argument.
     * @param string The {@link String} argument to validate.
     * @return Flag indicating if the {@link String} value is a valid argument.
     * @since 1.0.0
     */
    private static boolean IsValidArgument(final String string) {

        return (string != null) && !string.isBlank() && string.startsWith("-");

    }

    /**
     * <p>Attempts the invoke the specified {@link Method} that has been checked to be annotated as a
     * {@link TracesGeneratorOption} & returns a flag indicating if the execution should terminate.</p>
     * @param tracesGeneratorOption The {@link Method} corresponding to a tracesGenerator option, annotated as
     *                              {@link TracesGeneratorOption}.
     * @param argument The {@link String} value of the argument.
     * @param value The {@link Object} instance containing the argument's parameter values, if any
     * @return A flag indicating if the execution should terminate.
     * @since 1.0.0
     */
    private static boolean Invoked(final Method tracesGeneratorOption, final String argument, final Object value) {

        boolean shouldTerminate = true;

        // Assert the TracesGeneratorOption is valid and attempt to
        if(tracesGeneratorOption != null) try {

            // Initialize a handle to the result of invoking the TracesGenerator Option
            final Object result = (tracesGeneratorOption.getParameterCount() > 0)
                    ? tracesGeneratorOption.invoke(null, value) : tracesGeneratorOption.invoke(null);

            // Update the terminate flag
            shouldTerminate = (result != null) && ((Boolean) result);

            // Otherwise, if the invocation failed,
        } catch(final InvocationTargetException | IllegalAccessException exception) {

            // Report the failure
            TracesGenerator.Info("Argument '" + argument + "' failed.");

            // Otherwise, report the invalid argument
        } else TracesGenerator.Info("Invalid argument: '" + argument + "'.");

        // Return the terminate flag
        return shouldTerminate;

    }

    /**
     * <p>Extracts the specified {@link String} array (arguments) and invokes the corresponding {@link Method} marked
     * as a {@link TracesGeneratorOption}.</p>
     * @param inputArguments The {@link String} array to extract.
     * @return Flag indicating if the execution that invoked this method should terminate.
     * @since 1.0.0
     */
    @SuppressWarnings("unchecked")
    private static boolean SetEnvironment(final String[] inputArguments) {

        // Initialize a handle to the terminate flag; @0: -include, @1: path
        boolean shouldTerminate = false;

        // Retrieve the tracesGenerator options mapping & parse the arguments
        final Map<String, Method> tracesGeneratorOptions = RetrieveTracesGeneratorOptions();
        final Map<String, Object> arguments = ParseArguments(inputArguments);

        // Set the FileSet
        TracesGenerator.FileSet.addAll(Collections.checkedList((List<String>)
                arguments.getOrDefault("files", FileSet), String.class));

        // Set the Include Paths
        TracesGenerator.IncludePaths.addAll(List.of("src/main/resources/dot_files", ""));

        // Iterate through the specified arguments
        for(final Map.Entry<String, Object> argument: arguments.entrySet()) {

            // Initialize a handle to the String value of the argument
            final String key = argument.getKey();

            // Retrieve the flag indicating if the execution should terminate from the invoked tracesGenerator option
            shouldTerminate |= tracesGeneratorOptions.containsKey(key)
                    && Invoked(tracesGeneratorOptions.get(key), key, argument.getValue());

        }

        // Return the flag indicating if the execution should terminate
        return shouldTerminate;

    }

    /**
     * <p>Extracts the parameters from the specified {@link String} array into the specified {@link List} starting
     * at the specified index.</p>
     * @param parameters The {@link String} array composed of parameters to extract.
     * @param result The {@link List} that will contain the results.
     * @param startIndex The integral index to start.
     * @return ending index.
     * @since 1.0.0
     */
    private static int ParseParameters(final String[] parameters, final List<String> result, final int startIndex) {

        // Iterate while the arguments are valid, or the index is in range
        int index = startIndex; while(index < parameters.length) {

            // Initialize a handle to the parameter
            final String parameter = parameters[index++];

            // Assert the parameter is not empty & does not begin with a '-'
            if(ValidParameter(parameter).isBlank()) break;

            // Aggregate the parameter
            result.add(parameter);

        }

        // Return the latest index
        return index;

    }

    /**
     * Returns a flag indicating if the {@link String} value is a valid parameter.
     * @param string The {@link String} parameter to validate.
     * @return Flag indicating if the {@link String} value is a valid parameter.
     * @since 1.0.0
     */
    private static String ValidParameter(final String string) {

        return ((string != null) && !string.startsWith("-")) ? string.trim() : "" ;

    }

    /**
     * <p>Returns the next {@link Phase} with the specified {@link Phase.Listener} corresponding with the
     * {@link SourceFile}'s most recently completed {@link Phase}.</p>
     * @param sourceFile The {@link SourceFile} to retrieve the next {@link Phase} for.
     * @param phaseListener The {@link Phase.Listener} bound to the corresponding {@link Phase}.
     * @return The next {@link Phase} corresponding with the {@link SourceFile}'s most recently completed {@link Phase}.
     * @see Phase
     * @see SourceFile
     * @since 0.1.0
     */
    public static Phase NextPhaseFor(final SourceFile sourceFile, final Phase.Listener phaseListener) {

        // Initialize a handle to the Source File's next Phase & the result
        final Class<? extends Phase> nextPhase = sourceFile.getLastCompletedPhase() != null
                ? NextPhaseOf.get(sourceFile.getLastCompletedPhase())
                : ParseFile.class;

        final Phase result;

        if(nextPhase != null) {

            // Set the result to the next phase
            result = ActivePhases.getOrDefault(nextPhase, NewInstanceOf(nextPhase, phaseListener));

            // And assert the Mapping contains the instance
            ActivePhases.putIfAbsent(nextPhase, result);

        } else result = null;

        // Return the result
        return result;

    }

    /**
     * <p>Instantiates & opens the {@link SourceFile} corresponding to the specified {@link String} file path, executes
     * the initial compilation {@link Phase}, & aggregates the file to the {@link TracesGenerator#Opened} mapping &
     * {@link TracesGenerator#FileSet}.
     * @param filePath {@link String} file path corresponding to the file to open & aggregate into the compilation
     *                               queue.
     * @return Opened & minimally process {@link SourceFile}.
     * @since 0.1.0
     */
    private static SourceFile SourceFileAt(final String filePath, final Phase.Listener phaseListener) throws Phase.Error {

        // Attempt to retrieve the TracesGenerator Source file
        SourceFile sourceFile = Opened.get(filePath);

        // If the SourceFile at the specified path has not been opened
        if(sourceFile == null) {

            // Initialize a new TracesGeneratorSourceFile with the first path.
            sourceFile = new SourceFile(filePath);

            // Execute the initial Compilation
            NextPhaseFor(sourceFile, phaseListener).execute(sourceFile);

            // Mark it as opened
            TracesGenerator.Opened.put(filePath, sourceFile);

            // Aggregate it to the file set
            TracesGenerator.FileSet.add(filePath);

        }

        // Return the result
        return sourceFile;

    }

    /**
     * <p>Instantiates & opens the {@link SourceFile} corresponding to the specified {@link String} file path, executes
     * the initial compilation {@link Phase}, & aggregates the file to the {@link TracesGenerator#Opened} mapping &
     * {@link TracesGenerator#FileSet}.
     * @param filePath {@link String} file path corresponding to the file to open & aggregate into the compilation
     *                               queue.
     * @return Opened & minimally process {@link SourceFile}.
     * @since 0.1.0
     */
    private static List<SourceFile> SourceFilesAt(final String filePath, final Phase.Listener phaseListener) throws Phase.Error {

        // Initialize a handle to the resultant SourceFile List & the matching file paths.
        final List<String> files = RetrieveMatchingFilesListFrom(filePath, IncludePaths, TracesGeneratorSourceFileRegex);
        final List<SourceFile> sourceFiles = new ArrayList<>();

        // Iterate through each file path & Open the file
        for(final String filepath: files)
            sourceFiles.add(SourceFileAt(filepath, phaseListener));

        // Return the result
        return sourceFiles;

    }

    /**
     * <p>Extracts the {@link String} arguments & their corresponding {@link String} parameters into a {@link Map}.</p>
     * @param arguments The {@link String} array containing the arguments to extract.
     * @return {@link Map} containing the argument-parameter mapping.
     * @since 1.0.0
     */
    private static Map<String, Object> ParseArguments(final String[] arguments) {

        // Initialize a handle to the result & the file list
        final Map<String, Object> result = new HashMap<>();
        final List<String> files = new ArrayList<>();

        // Iterate through the arguments
        int index = 0; while(index < arguments.length) {

            // Assert the specified argument is valid
            if(IsValidArgument(arguments[index])) {

                // Initialize a handle to the argument & parameter
                final String argument = arguments[index++].replaceFirst("(-)+", "");
                final String parameter = ((index < arguments.length)) ? arguments[index] : "";

                // Assert the argument was not a nativelib specification
                if(argument.equals("nativelib") || argument.equals("userlib")) {

                    final String currentValue = (String) result.getOrDefault("install", "");

                    result.put("install", ((!currentValue.isBlank() && !currentValue.endsWith(":"))
                            ? ":" : "") +  ValidParameter(parameter));

                } else result.put(argument, ValidParameter(parameter));

                // Increment the index
                if(!parameter.startsWith("-")) index++;

                // Otherwise, we must have encountered files, aggregate them
            } else index = ParseParameters(arguments, files, index);

        }

        // Set the files
        result.put("files", files);

        // Return the result
        return result;

    }

    /**
     * <p>Retrieves a {@link Map} containing all the {@link TracesGenerator}'s {@link Method}s that are annotated as
     * {@link TracesGeneratorOption} mapped to the corresponding {@link String} value of {@link TracesGeneratorOption#name()}.</p>
     * @return {@link Map} containing all the {@link TracesGenerator}'s {@link Method}s that are annotated as
     * {@link TracesGeneratorOption} mapped to the corresponding {@link String} value of {@link TracesGeneratorOption#name()}.
     * @since 1.0.0
     */
    private static Map<String, Method> RetrieveTracesGeneratorOptions() {

        // Initialize a handle to the result & retrieve all the Methods annotated with TracesGeneratorOption
        final Map<String, Method> result = new HashMap<>();
        final List<Method> tracesGeneratorOptions
                = MethodsWithAnnotationOf(TracesGenerator.class, TracesGeneratorOption.class);

        // Iterate through the TracesGeneratorOptions
        for(final Method tracesGeneratorOption: tracesGeneratorOptions) {

            // Initialize a handle to the TracesGeneratorOption
            final TracesGeneratorOption annotation = tracesGeneratorOption.getDeclaredAnnotation(TracesGeneratorOption.class);

            // Map the name to the Method
            result.put(annotation.name(), tracesGeneratorOption);

        }

        // Return the result
        return result;

    }

    /**
     * <p>{@link TracesGeneratorOption} that enables the {@link TracesGenerator#ShowColor} flag.</p>
     * @return Flag indicating the execution should not terminate.
     * @since 1.0.0
     */
    @TracesGeneratorOption(name = "showColor", description = "Use color on terminals that support ansi escape codes.")
    private static boolean EnableColor() {

        // Update the Showing Color flag
        TracesGenerator.ShowColor = true;

        // Indicate the execution should not terminate
        return false;

    }

    /**
     * <p>{@link TracesGeneratorOption} that enables the {@link TracesGenerator#ShowMessage} flag.</p>
     * @return Flag indicating the execution should not terminate.
     * @since 1.0.0
     */
    @TracesGeneratorOption(name = "showMessage", description = "Show all info, error, & warning messages when available.")
    private static boolean EnableLogging() {

        // Update the show message flag
        TracesGenerator.ShowMessage = true;

        // Indicate the execution should not terminate
        return false;

    }

    /**
     * <p>{@link TracesGeneratorOption} that overwrites the {@link TracesGenerator#IncludePaths} with the path(s) specified by the
     * user.</p>
     * @return Flag indicating the execution should not terminate.
     * @since 1.0.0
     */
    @TracesGeneratorOption(name = "include", description = "Override the default include directories.")
    private static boolean OverwriteIncludePaths(final String includePaths) {

        // Clear the include paths
        TracesGenerator.IncludePaths.clear();

        // Add each specified path
        TracesGenerator.IncludePaths.addAll(List.of(includePaths.split(":")));

        // Return a flag indicating the execution should not terminate
        return false;

    }

    /**
     * <p>{@link TracesGeneratorOption} that retrieves all {@link Method}s annotated with {@link TracesGeneratorOption} & prints
     * the specified metadata.</p>
     * @return Flag indicating the execution should terminate.
     * @since 1.0.0
     */
    @TracesGeneratorOption(name = "help", description = "Show this help message.")
    private static boolean PrintHelp() {

        // Retrieve the set of annotations
        final List<Annotation> tracesGeneratorOptions =
                DeclaredMethodAnnotations(TracesGenerator.class, TracesGeneratorOption.class);

        int argumentMaxLength = 0;
        final Map<String, String> arguments = new HashMap<>();

        // Iterate through each annotation
        for(final Annotation annotation: tracesGeneratorOptions) {

            // Initialize a handle to the TracesGeneratorOption
            final TracesGeneratorOption tracesGeneratorOption = (TracesGeneratorOption) annotation;

            // Insert the name & description
            arguments.put(tracesGeneratorOption.name(), tracesGeneratorOption.description());

            // Compute the maximum lengths
            argumentMaxLength = Math.max(argumentMaxLength, tracesGeneratorOption.name().length());

        }

        // Initialize the StringBuilder
        final StringBuilder stringBuilder = new StringBuilder();

        // Construct the formatted table
        for(final Map.Entry<String, String> entry : arguments.entrySet())
            stringBuilder.append(String.format("-%s%s %s",
                    entry.getKey(),
                    Strings.SpacesOf(argumentMaxLength - entry.getKey().length()),
                    entry.getValue()))
                    .append('\n');

        final String table = stringBuilder.toString();

        InfoStream.println(table.substring(0, !table.isEmpty() ? table.length() - 1 : 0));

        // Return a flag indicating the execution should terminate
        return true;

    }

    /**
     * <p>{@link TracesGeneratorOption} that prints the current {@link TracesGenerator#Version} {@link String}.</p>
     * @return Flag indicating the execution should terminate.
     * @since 1.0.0
     */
    @TracesGeneratorOption(name = "version", description = "Print version information and exit.")
    private static boolean PrintVersion() {

        InfoStream.println("TracesGenerator Version: " + Version);

        // Return a flag indicating the execution should terminate
        return true;

    }

    /**
     * <p>{@link TracesGeneratorOption} that sets the minimum depth of trees to consider.</p>
     * @param minimumDepth The minimum depth value for each tree.
     * @return Flag indicating if the program should terminate.
     */
    @TracesGeneratorOption(name = "min", description = "Sets the minimum depth to consider.")
    private static boolean SetMinimum(final String minimumDepth) {

        GenerateCombinations.MinimumDepth = Integer.parseInt(minimumDepth);

        // Return a flag indicating the execution should terminate
        return false;

    }

    /**
     * <p>{@link TracesGeneratorOption} that sets the minimum depth of trees to consider.</p>
     * @param maximumDepth The maximum depth value for each tree.
     * @return Flag indicating if the program should terminate.
     */
    @TracesGeneratorOption(name = "max", description = "Sets the maximum depth to consider.")
    private static boolean SetMaximum(final String maximumDepth) {

        GenerateCombinations.MaximumDepth = Integer.parseInt(maximumDepth);

        // Return a flag indicating the execution should terminate
        return false;

    }

    /**
     * <p>Execution entry point. Sets the {@link TracesGenerator} environment via {@link TracesGenerator#SetEnvironment(String[])}
     * with the specified arguments corresponding to the {@link Method}s annotated as {@link TracesGeneratorOption}. If an
     * argument that indicates the execution should terminate was specified, the compilation will not continue.
     * Otherwise, a {@link TracesGenerator} is instantiated & each {@link Phase} executed on each {@link SourceFile} that
     * was specified or subsequently imported.</p>
     * @param arguments The {@link String} array containing the command-line arguments specified by the user.
     * @since 1.0.0
     */
    public static void main(final String[] arguments) throws Phase.Error {

        // Assert the arguments are valid
        if(!SetEnvironment(arguments)) {

            // Initialize an instance of the tracesGenerator
            final TracesGenerator tracesGenerator = new TracesGenerator();

            // For each Source File
            for(final String filepath: TracesGenerator.IncludePaths) {

                // Initialize the corresponding handle TODO: Handle clashes?
                final List<SourceFile> sourceFiles = SourceFilesAt(filepath, tracesGenerator);

                for(final SourceFile sourceFile : sourceFiles) {

                    // Execute each remaining Phase
                    Phase phase;
                    while ((phase = NextPhaseFor(sourceFile, tracesGenerator)) != null)
                        phase.execute(sourceFile); // TODO: Check Errors here

                }

            }

            // TODO: Write to File with working directory and extension in utf-8 encoding

        }

    }

    /**
     * <p>Annotation that is used an an indicator for a {@link Method} that potentially updates a {@link TracesGenerator}
     * variable or reports pertinent {@link TracesGenerator} information.</p>
     * @see TracesGenerator
     * @author Carlos L. Cuenca
     * @since 1.0.0
     * @version 1.0.0
     */
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    private @interface TracesGeneratorOption {

        String name() default "none";
        String description() default "none";
        String value() default "";

    }

}