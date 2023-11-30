package dev.clcuenca.phase;

import dev.clcuenca.utilities.DirectedGraph;
import dev.clcuenca.utilities.SourceFile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
     * <p>Initializes the {@link Phase} to its' default state with the specified {@link Listener}.</p>
     *
     * @param listener The {@link Listener} to bind to the {@link Phase}.
     * @see Listener
     * @since 0.1.0
     */
    public GenerateCombinations(Listener listener) {
        super(listener);
    }

    @Override
    protected void executePhase() throws Error {

        System.out.println("Generating Combinations");

        final SourceFile sourceFile = this.getSourceFile();
        final DirectedGraph<String> directedGraph = sourceFile.getDirectedGraph();

        final Set<String> traces = new HashSet<>();

        if((MinimumDepth == -1) || (MaximumDepth == -1)) {

            directedGraph.treeCombinations((final List<String> combination) -> {

                final String combinationString = combination.toString();

                if(traces.add(combinationString))
                    System.out.println(combinationString);

            });

        } else {

            try {

                directedGraph.treeCombinations((final List<String> combination) -> {

                    final String combinationString = combination.toString();

                    if(traces.add(combinationString))
                        System.out.println(combinationString);

                }, MinimumDepth, MaximumDepth);

            } catch(final DirectedGraph.InvalidMaximumDepthException
                          | DirectedGraph.InvalidMinimumDepthException exception) {

                System.out.println(exception.getMessage());

            }

        }

    }

}
