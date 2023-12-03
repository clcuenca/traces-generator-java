package dev.clcuenca.phase;

import dev.clcuenca.utilities.DirectedGraph;
import dev.clcuenca.utilities.SourceFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>{@link Phase} responsible for generating trace combinations from a specified {@link SourceFile}.
 * This {@link Phase} generates all traces lexicographically and writes the resultant {@link SourceFile} in the
 * same path as the original.</p>
 * @author Carlos L. Cuenca
 * @since 0.1.0
 * @see Phase
 * @see SourceFile
 */
public class GenerateCombinations extends Phase {

    /**
     * <p>The minimum depth to consider for every {@link DirectedGraph} combination.</p>
     * @since 1.0.0
     * @see DirectedGraph
     */
    public static int MinimumDepth = -1;

    /**
     * <p>The maximum depth to consider for every {@link DirectedGraph} combination.</p>
     * @since 1.0.0
     * @see DirectedGraph
     */
    public static int MaximumDepth = -1;

    /**
     * <p>Flag indicating if each unique generated trace should be output to the console.</p>
     * @since 0.1.0
     */
    public static boolean ShowGeneratedTraces = false;

    /**
     * <p>Initializes the {@link Phase} to its' default state with the specified {@link Listener}.</p>
     *
     * @param listener The {@link Listener} to bind to the {@link Phase}.
     * @see Listener
     * @since 0.1.0
     */
    public GenerateCombinations(final Listener listener) {
        super(listener);
    }

    /**
     * <p>Executes the {@link GenerateCombinations} {@link Phase} that generates all combinations from the current
     * {@link SourceFile}'s {@link DirectedGraph}.</p>
     * @since 0.1.0
     * @see Phase
     * @see Phase.Error
     * @see SourceFile
     */
    @Override
    protected void executePhase() {

        final SourceFile sourceFile = this.getSourceFile();
        final DirectedGraph<String> directedGraph = sourceFile.getDirectedGraph();

        GeneratorAssert.GeneratingCombinations.Assert(this, sourceFile);

        if((MinimumDepth == -1) || (MaximumDepth == -1)) {

            directedGraph.treeCombinations((final List<String> combination) -> {

                final String trace = String.join(" ", combination);

                if(sourceFile.addTrace(trace) && GenerateCombinations.ShowGeneratedTraces)
                    GeneratorAssert.Generated.Assert(this, sourceFile, trace);

            });

        } else {

            try {

                directedGraph.treeCombinations((final List<String> combination) -> {

                    final String trace = String.join(" ", combination);

                    if(sourceFile.addTrace(trace) && GenerateCombinations.ShowGeneratedTraces)
                        GeneratorAssert.Generated.Assert(this, sourceFile, trace);

                }, MinimumDepth, MaximumDepth);

            } catch(final DirectedGraph.InvalidMaximumDepthException
                          | DirectedGraph.InvalidMinimumDepthException exception) {

                GeneratorAssert.InvalidDepth.Assert(this, sourceFile);

            }

        }

        GeneratorAssert.GeneratedTotal.Assert(this, sourceFile, String.valueOf(sourceFile.getTraces().size()));

        try {

            sourceFile.write();

        } catch(final IOException ioException) {

            GeneratorAssert.FileWriteFailed.Assert(this, sourceFile);

        }

    }

}
