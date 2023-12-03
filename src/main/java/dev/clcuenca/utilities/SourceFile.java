package dev.clcuenca.utilities;

import dev.clcuenca.phase.Phase;

import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * <p>Encapsulates a ProcessJ source file that containing structures produced by compilation {@link Phase}s &
 * keeps track of which compilation {@link Phase}s have successfully operated on the contents.</p>
 * @see Phase
 * @author Carlos L. Cuenca
 * @version 1.0.0
 * @since 0.1.0
 */
public class SourceFile {

    /**
     * <p>The {@link Set} of {@link Phase} class objects the {@link SourceFile} has been processed through.</p>
     * @since 0.1.0
     * @see Set
     */
    private final Set<Class<? extends Phase>> completedPhases;

    /**
     * <p>The {@link File} object representing the ProcessJ source file.</p>
     * @since 0.1.0
     * @see File
     */
    private final File file;

    /**
     * <p>The {@link Set} of {@link String} traces.</p>
     * @since 0.1.0
     * @see Set
     * @see String
     */
    private final Set<String> traces;

    /**
     * <p>The class object corresponding to the most recent {@link Phase} that operated or validated the
     * {@link SourceFile}.</p>
     * @since 0.1.0
     * @see Class
     * @see Phase
     */
    private Class<? extends Phase> lastCompletedPhase;

    /**
     * <p>Initially the result of the parsing phase. This instance gets transformed as it propagates through
     * the toolchain.</p>
     * @since 0.1.0
     * @see DirectedGraph
     */
    private DirectedGraph<String, String> directedGraph;


    /// ------------
    /// Constructors

    /**
     * <p>Initializes the {@link SourceFile} to its' default state.</p>
     * @param inputPath The {@link String} value of the input path corresponding with the processj source file.
     * @since 0.1.0
     */
    public SourceFile(final String inputPath) {

        this.completedPhases = new LinkedHashSet<>();
        this.file = new File(inputPath);
        this.traces = new LinkedHashSet<>();
        this.directedGraph = null;
        this.lastCompletedPhase = null;

    }

    /**
     * <p>Returns the {@link SourceFile} contents as a {@link String} value.</p>
     * @return The {@link SourceFile} contents as a {@link String} value.
     * @since 0.1.0
     * @see String
     */
    @Override
    public String toString() {

        // Initialize a handle to the result
        String result = "";

        // Attempt to
        try {

            // Initialize the FileReader and StringBuilder
            final BufferedReader fileReader = new BufferedReader(new FileReader(this.file.getPath()));
            final StringBuilder stringBuilder = new StringBuilder();

            // Initialize a handle to the line and separator
            String separator = System.getProperty("line.separator");
            String line;

            // Append the contents
            while((line = fileReader.readLine()) != null)
                stringBuilder.append(line).append(separator);

            // Remove the last separator
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);

            // Update the result
            result = stringBuilder.toString();

        } catch(final IOException ioException) { /* Empty */ }

        // Return the result
        return result;

    }

    /**
     * <p>Updates {@link SourceFile#lastCompletedPhase} with the class object corresponding to the specified
     * {@link Phase}.</p>
     * @param phase The {@link Phase} that has processed the {@link SourceFile}.
     * @since 0.1.0
     */
    public final void setCompletedPhase(final Phase phase) {

        // If the Phase is not null, attempt to add the Phase's class object to the set of completed Phases
        if((phase != null) && this.completedPhases.add(phase.getClass())) {

            // And if not already completed by the Phase, update the latest completed phase
            this.lastCompletedPhase = phase.getClass();

        }

    }

    /**
     * <p>Mutates the current state of the {@link SourceFile}'s {@link DirectedGraph}.</p>
     * @param directedGraph The {@link DirectedGraph} to set.
     * @since 0.1.0
     */
    public final void setDirectedGraph(final DirectedGraph<String, String> directedGraph) {

        if(directedGraph != null) this.directedGraph = directedGraph;

    }

    /**
     * <p>Aggregates the {@link String} trace to the {@link Set} of traces.</p>
     * @param trace The {@link String} value of the trace to aggregate.
     * @since 0.1.0
     * @see String
     */
    public final boolean addTrace(final String trace) {

        return this.traces.add(trace);

    }

    /**
     * <p>Returns the {@link Set} of traces corresponding to the {@link SourceFile}.</p>
     * @return {@link Set} of traces corresponding to the {@link SourceFile}.
     * @since 0.1.0
     */
    public final Set<String> getTraces() {

        return this.traces;

    }

    /**
     * <p>Writes the {@link Set} of {@link String} traces contained in the {@link SourceFile} to file.</p>
     * @throws IOException If the file write failed.
     * @since 0.1.0
     * @see Set
     * @see String
     */
    public final void write() throws IOException {

        final String path = this.getPath() + ".traces";
        final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path));

        for(final String trace : this.traces)
            bufferedWriter.write(trace + '\n');

        bufferedWriter.close();

    }

    /**
     * <p>Returns a flag indicating if the specified {@link Phase} has processed the {@link SourceFile}.</p>
     * @param phase The {@link Phase} to check for completion
     * @return Flag indicating if the specified {@link Phase} has processed the {@link SourceFile}.
     * @since 0.1.0
     */
    public final boolean hasBeenCompletedBy(final Phase phase) {

        return (phase != null) && this.completedPhases.contains(phase.getClass());

    }

    /**
     * <p>Returns the path corresponding to the {@link SourceFile}.</p>
     * @return {@link String} value of the path corresponding to the {@link SourceFile}.
     * @since 0.1.0
     */
    public final String getPath() {

        return this.file.getPath();

    }

    /**
     * <p>Retrieves the {@link DirectedGraph} corresponding with the {@link SourceFile}.</p>
     * @return {@link DirectedGraph}
     * @since 0.1.0
     */
    public final DirectedGraph<String, String> getDirectedGraph() {

        return this.directedGraph;

    }

    /**
     * <p>Returns the class object of the most recent {@link Phase} that operated or validated the {@link SourceFile}
     * or null if the {@link SourceFile} has not been processed by any {@link Phase}.</p>
     * @return The class object of the most recent {@link Phase} that operated or validated the {@link SourceFile}
     * or null.
     * @see Phase
     * @since 0.1.0
     */
    public final Class<? extends Phase> getLastCompletedPhase() {

        return this.lastCompletedPhase;

    }

}
