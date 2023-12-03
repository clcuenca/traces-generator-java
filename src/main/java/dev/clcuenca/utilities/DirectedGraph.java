package dev.clcuenca.utilities;

import java.util.*;

/**
 * <p>A directed graph representation with various utility methods.</p>
 * @param <Type> The type of the vertices contained in the {@link DirectedGraph}.
 * @since 1.0.0
 * @author Carlos L. Cuenca
 */
public class DirectedGraph<Type, LabelType> implements Cloneable {

    /**
     * <p>The adjacency {@link Map}ping that marks all adjacent vertices in the {@link DirectedGraph}.</p>
     * @since 1.0.0
     * @see Map
     */
    private final Map<Type, Map<Type, LabelType>> adjacencyMatrix;

    /**
     * <p>Initializes the {@link DirectedGraph} to its' default state.</p>
     * @since 1.0.0
     */
    public DirectedGraph() {

        this.adjacencyMatrix = new HashMap<>();

    }

    /**
     * <p>Returns the {@link String} representation of the {@link DirectedGraph}.</p>
     * @return String representation of the {@link DirectedGraph}.
     * @since 1.0.0
     * @see String
     */
    @Override
    public final String toString() {

        final StringBuilder stringBuilder = new StringBuilder();

        this.adjacencyMatrix.forEach((final Type vertex, final Map<Type, LabelType> neighbors) -> {

            if(!neighbors.isEmpty()) {

                stringBuilder.append(vertex).append(" -> ");

                neighbors.forEach((final Type neighbor, final LabelType label) -> {

                    stringBuilder.append(neighbor)
                            .append(' ');

                });

                stringBuilder.append('\n');

            }

        });

        final String result = stringBuilder.toString();

        return !result.isBlank() ? result.substring(0, result.length() - 1) : result;

    }

    /**
     * <p>Returns a copy of the {@link DirectedGraph}.</p>
     * @return Copy of the {@link DirectedGraph}.
     * @since 1.0.0
     */
    @Override
    public DirectedGraph<Type, LabelType> clone() {

        final DirectedGraph<Type, LabelType> clone = new DirectedGraph<>();

        this.adjacencyMatrix.forEach((final Type vertex, final Map<Type, LabelType> adjacent) ->
                adjacent.forEach((final Type neighbor, final LabelType label) ->
                        clone.addEdge(vertex, neighbor, label)));

        return clone;

    }

    /**
     * <p>Aggregates the edge representation from &amp; to the specified vertices and returns a flag
     * indicating if the edge representation was not present in the {@link DirectedGraph}.</p>
     * @param from The origin vertex.
     * @param to The destination vertex.
     * @param label The label representing the edge.
     * @return Flag indicating if the edge representation was not present in the {@link DirectedGraph}.
     * @since 1.0.0
     */
    public final boolean addEdge(final Type from, final Type to, final LabelType label) {

        this.adjacencyMatrix.putIfAbsent(from, new HashMap<>());
        this.adjacencyMatrix.putIfAbsent(to, new HashMap<>());

        final boolean contained = this.adjacencyMatrix.get(from).containsKey(to);

        this.adjacencyMatrix.get(from).put(to, label);

        return contained;

    }

    /**
     * <p>Generates a {@link DirectedGraph} as a tree representation rooted at the specifed vertex and calls back the
     * each {@link DirectedGraph} combination.</p>
     * @param vertex The root of the current combination.
     * @param visited {@link Set} indicating the visited vertices.
     * @param directedGraph {@link DirectedGraph} combination for each recursive invocation.
     * @param combinationsCallback {@link CombinationsCallback} That gets invoked for each {@link DirectedGraph}
     *                                                         combination.
     * @since 1.0.0
     * @see Set
     * @see DirectedGraph
     * @see CombinationsCallback
     */
    @SuppressWarnings("unchecked")
    private void toTree(final Type vertex,
                        final Set<Type> visited,
                        final DirectedGraph<Type, LabelType> directedGraph,
                        final CombinationsCallback<Type, LabelType> combinationsCallback) {

        // Enumerate the specified vertices' neighbors
        final Type[] adjacentArray = (Type[]) this.adjacencyMatrix.get(vertex).keySet().toArray();

        // For each combination of length one to the amount of neighbors
        for(int combination = 1; combination <= adjacentArray.length; combination++) {

            // Initialize a handle to the snapshot of the set of visited vertices
            final Set<Type> visitedSnapshot = new HashSet<>(visited);

            // For each combination
            Algorithms.LexicographicCombinations(adjacentArray.length, combination, (final int[] indices) -> {

                // Initialize a handle to the unmutated graph
                final DirectedGraph<Type, LabelType> directedGraphCombination = directedGraph.clone();

                // Add the edges corresponding to the current combination
                for(final int index : indices) {

                    final Type adjacent = adjacentArray[index];

                    directedGraphCombination.addEdge(vertex, adjacent, this.adjacencyMatrix.get(vertex).get(adjacent));

                }

                // Callback the permutation
                combinationsCallback.invoke(directedGraphCombination);

                // Recur for each child in the combination
                for(final Type child : directedGraphCombination.adjacencyMatrix.get(vertex).keySet())
                    if(visitedSnapshot.add(child))
                        toTree(child, visitedSnapshot, directedGraphCombination, combinationsCallback);

            });

        }

    }

    /**
     * <p>Generates a {@link DirectedGraph} as a tree representation rooted at the specifed vertex and calls back the
     * each {@link DirectedGraph} combination.</p>
     * @param vertex The root of the current combination.
     * @param visited {@link Set} indicating the visited vertices.
     * @param directedGraph {@link DirectedGraph} combination for each recursive invocation.
     * @param traceCallback {@link CombinationsCallback} That gets invoked for each {@link DirectedGraph}
     *                                                         combination.
     * @since 1.0.0
     * @see Set
     * @see DirectedGraph
     * @see CombinationsCallback
     */
    @SuppressWarnings("unchecked")
    private void toTree(final Type vertex,
                        final Set<Type> visited,
                        final DirectedGraph<Type, LabelType> directedGraph,
                        final TraceCallback<LabelType> traceCallback,
                        final List<LabelType> trace) {

        // Enumerate the specified vertices' neighbors
        final Type[] adjacentArray = (Type[]) this.adjacencyMatrix.get(vertex).keySet().toArray();

        // For each combination of length one to the amount of neighbors
        for(int combination = 1; combination <= adjacentArray.length; combination++) {

            // Initialize a handle to the snapshot of the set of visited vertices
            final Set<Type> visitedSnapshot = new HashSet<>(visited);

            // For each combination
            Algorithms.LexicographicCombinations(adjacentArray.length, combination, (final int[] indices) -> {

                // Initialize a handle to the unmutated graph
                final DirectedGraph<Type, LabelType> directedGraphCombination = directedGraph.clone();

                // Add the edges corresponding to the current combination
                for(final int index : indices) {

                    final Type adjacent = adjacentArray[index];

                    directedGraphCombination.addEdge(vertex, adjacent, this.adjacencyMatrix.get(vertex).get(adjacent));

                }

                // Recur for each child in the combination
                for(final Type child : directedGraphCombination.adjacencyMatrix.get(vertex).keySet())
                    if(visitedSnapshot.add(child)) {

                        trace.add(this.adjacencyMatrix.get(vertex).get(child));

                        traceCallback.invoke(trace);

                        toTree(child, visitedSnapshot, directedGraphCombination, traceCallback, trace);

                        trace.remove(trace.size() - 1);

                    }

            });

        }

    }

    /**
     * <p>Generates a {@link DirectedGraph} as a tree representation rooted at the specifed vertex and calls back the
     * each {@link DirectedGraph} combination.</p>
     * @param vertex The root of the current combination.
     * @param visited {@link Set} indicating the visited vertices.
     * @param directedGraph {@link DirectedGraph} combination for each recursive invocation.
     * @param combinationsCallback {@link CombinationsCallback} That gets invoked for each {@link DirectedGraph}
     *                                                         combination.
     * @since 1.0.0
     * @see Set
     * @see DirectedGraph
     * @see CombinationsCallback
     */
    @SuppressWarnings("unchecked")
    private void toTree(final Type vertex,
                        final Set<Type> visited,
                        final DirectedGraph<Type, LabelType> directedGraph,
                        final CombinationsCallback<Type, LabelType> combinationsCallback,
                        final int minimumDepth,
                        final int maximumDepth,
                        final int depth) {

        if(depth == -1) return;

        // Enumerate the specified vertices' neighbors
        final Type[] adjacentArray = (Type[]) this.adjacencyMatrix.get(vertex).keySet().toArray();

        // For each combination of length one to the amount of neighbors
        for(int combination = 1; combination <= adjacentArray.length; combination++) {

            // Initialize a handle to the snapshot of the set of visited vertices
            final Set<Type> visitedSnapshot = new HashSet<>(visited);

            // For each combination
            Algorithms.LexicographicCombinations(adjacentArray.length, combination, (final int[] indices) -> {

                // Initialize a handle to the unmutated graph
                final DirectedGraph<Type, LabelType> directedGraphCombination = directedGraph.clone();

                // Add the edges corresponding to the current combination
                for(final int index : indices) {

                    final Type adjacent = adjacentArray[index];

                    directedGraphCombination.addEdge(vertex, adjacent, this.adjacencyMatrix.get(vertex).get(adjacent));

                }

                // Callback the combination if the depth is in range.
                if((depth >= minimumDepth) && (depth <= maximumDepth))
                    combinationsCallback.invoke(directedGraphCombination);

                // Recur for each child in the combination
                for(final Type child : directedGraphCombination.adjacencyMatrix.get(vertex).keySet())
                    if(visitedSnapshot.add(child))
                        toTree(child, visitedSnapshot, directedGraphCombination, combinationsCallback,
                                minimumDepth, maximumDepth, depth + 1);

            });

        }

    }

    /**
     * <p>Generates a {@link DirectedGraph} as a tree representation rooted at the specifed vertex and calls back the
     * each {@link DirectedGraph} combination.</p>
     * @param vertex The root of the current combination.
     * @param visited {@link Set} indicating the visited vertices.
     * @param directedGraph {@link DirectedGraph} combination for each recursive invocation.
     * @param traceCallback {@link TraceCallback} That gets invoked for each {@link List} combination.
     * @since 1.0.0
     * @see Set
     * @see DirectedGraph
     * @see CombinationsCallback
     */
    @SuppressWarnings("unchecked")
    private void toTree(final Type vertex,
                        final Set<Type> visited,
                        final DirectedGraph<Type, LabelType> directedGraph,
                        final TraceCallback<LabelType> traceCallback,
                        final List<LabelType> trace,
                        final int minimumDepth,
                        final int maximumDepth,
                        final int depth) {

        if((depth == -1) || (depth < minimumDepth) || (depth > maximumDepth)) return;

        // Enumerate the specified vertices' neighbors
        final Type[] adjacentArray = (Type[]) this.adjacencyMatrix.get(vertex).keySet().toArray();

        // For each combination of length one to the amount of neighbors
        for(int combination = 1; combination <= adjacentArray.length; combination++) {

            // Initialize a handle to the snapshot of the set of visited vertices
            final Set<Type> visitedSnapshot = new HashSet<>(visited);

            // For each combination
            Algorithms.LexicographicCombinations(adjacentArray.length, combination, (final int[] indices) -> {

                // Initialize a handle to the unmutated graph
                final DirectedGraph<Type, LabelType> directedGraphCombination = directedGraph.clone();

                // Add the edges corresponding to the current combination
                for(final int index : indices) {

                    final Type adjacent = adjacentArray[index];

                    directedGraphCombination.addEdge(vertex, adjacentArray[index],
                            this.adjacencyMatrix.get(vertex).get(adjacent));

                }

                // Recur for each child in the combination
                for(final Type child : directedGraphCombination.adjacencyMatrix.get(vertex).keySet())
                    if(visitedSnapshot.add(child)) {

                        trace.add(this.adjacencyMatrix.get(vertex).get(child));

                        traceCallback.invoke(trace);

                        toTree(child, visitedSnapshot, directedGraphCombination, traceCallback, trace,
                                minimumDepth, maximumDepth, depth + 1);

                        trace.remove(trace.size() - 1);

                    }

            });

        }

    }

    /**
     * <p>Generates all tree combinations as {@link DirectedGraph}s; &amp; passes each {@link DirectedGraph} via the
     * specified
     * {@link CombinationsCallback}.</p>
     * @param combinationsCallback The {@link CombinationsCallback} that receives each {@link DirectedGraph}
     *                             combination.
     * @since 1.0.0
     * @see CombinationsCallback
     */
    public final void treeCombinations(final CombinationsCallback<Type, LabelType> combinationsCallback) {

        for(final Type vertex : this.adjacencyMatrix.keySet()) {

            final Set<Type> visited = new HashSet<>();

            visited.add(vertex);

            this.toTree(vertex, visited, new DirectedGraph<>(), combinationsCallback);

        }

    }

    /**
     * <p>Generates all tree combinations as {@link DirectedGraph}s from the minimum depth to the maximum depth;
     * &amp; passes each {@link DirectedGraph} via the specified {@link CombinationsCallback}.</p>
     * @param combinationsCallback The {@link CombinationsCallback} that receives each {@link DirectedGraph} combination.
     * @param minimumDepth The integer value of the minimum tree depth.
     * @param maximumDepth The integer value of the maximum tree depth.
     * @since 1.0.0
     * @see CombinationsCallback
     */
    public final void treeCombinations(final CombinationsCallback<Type, LabelType> combinationsCallback,
                                       final int minimumDepth,
                                       final int maximumDepth)
            throws InvalidMaximumDepthException, InvalidMinimumDepthException {

        final int minimum = Math.min(minimumDepth, maximumDepth);
        final int maximum = Math.max(minimumDepth, maximumDepth);

        if(minimumDepth < 0) throw new InvalidMinimumDepthException(minimumDepth);
        if(maximumDepth < 0) throw new InvalidMaximumDepthException(maximumDepth);

        for(final Type vertex : this.adjacencyMatrix.keySet()) {

            final Set<Type> visited = new HashSet<>();

            visited.add(vertex);

            this.toTree(vertex, visited, new DirectedGraph<>(),
                    combinationsCallback, minimum, maximum, 1);

        }

    }

    /**
     * <p>Generates all tree combinations as {@link DirectedGraph}s; &amp; passes each {@link DirectedGraph} via the
     * specified
     * {@link CombinationsCallback}.</p>
     * @param traceCallback The {@link CombinationsCallback} that receives each {@link DirectedGraph}
     *                             combination.
     * @since 1.0.0
     * @see CombinationsCallback
     */
    public final void treeCombinations(final TraceCallback<LabelType> traceCallback) {

        for(final Type vertex : this.adjacencyMatrix.keySet()) {

            final Set<Type> visited = new HashSet<>();

            visited.add(vertex);

            this.toTree(vertex, visited, new DirectedGraph<>(), traceCallback, new ArrayList<>());

        }

    }

    /**
     * <p>Generates all tree combinations as {@link DirectedGraph}s from the minimum depth to the maximum depth;
     * &amp; passes each {@link DirectedGraph} via the specified {@link CombinationsCallback}.</p>
     * @param traceCallback The {@link TraceCallback} that receives each {@link DirectedGraph} combination.
     * @param minimumDepth The integer value of the minimum tree depth.
     * @param maximumDepth The integer value of the maximum tree depth.
     * @since 1.0.0
     * @see CombinationsCallback
     */
    public final void treeCombinations(final TraceCallback<LabelType> traceCallback,
                                       final int minimumDepth,
                                       final int maximumDepth)
            throws InvalidMaximumDepthException, InvalidMinimumDepthException {

        final int minimum = Math.min(minimumDepth, maximumDepth);
        final int maximum = Math.max(minimumDepth, maximumDepth);

        if(minimumDepth < 0) throw new InvalidMinimumDepthException(minimumDepth);
        if(maximumDepth < 0) throw new InvalidMaximumDepthException(maximumDepth);

        for(final Type vertex : this.adjacencyMatrix.keySet()) {

            final Set<Type> visited = new HashSet<>();

            visited.add(vertex);

            this.toTree(vertex, visited, new DirectedGraph<>(),
                    traceCallback, new ArrayList<>(), minimum, maximum, 1);

        }

    }

    /**
     * <p>Clears the {@link DirectedGraph}.</p>
     * @since 1.0.0
     */
    public final void clear() {

        this.adjacencyMatrix.forEach((final Type vertex, final Map<Type, LabelType> neighbors) -> neighbors.clear());

        this.adjacencyMatrix.clear();

    }

    /**
     * <p>Functional interface describing a function that receives a {@link DirectedGraph} combination.</p>
     * @param <T> The type contained in the {@link DirectedGraph} as vertices.
     * @since 1.0.0
     * @author Carlos L. Cuenca
     */
    @FunctionalInterface
    public interface CombinationsCallback <T, L> {

        void invoke(final DirectedGraph<T, L> directedGraph);

    }

    /**
     * <p>Functional interface describing a function that receives a trace {@link List} combination of the Label Type.
     * </p>
     * @param <L> The label type contained in the {@link DirectedGraph} as vertices.
     * @since 1.0.0
     * @author Carlos L. Cuenca
     */
    @FunctionalInterface
    public interface TraceCallback <L> {

        void invoke(final List<L> trace);

    }

    /**
     * <p>Exception that gets thrown when indicating an invalid maximum depth.</p>
     * @author Carlos L. Cuenca
     * @since 1.0.0
     */
    public final static class InvalidMaximumDepthException extends Exception {

        /**
         * <p>The message that gets emitted when an invalid maximum depth has been specified.</p>
         * @since 1.0.0
         * @see String
         */
        private static final String MESSAGE = "Invalid maximum depth: %s";

        /**
         * <p>The integer value that was indicated as the maximum depth.</p>
         * @since 1.0.0
         */
        private final int specifiedDepth;

        /**
         * <p>Initializes the {@link InvalidMaximumDepthException} to its' default state with the specified integer
         * maximum depth.</p>
         * @param specifiedDepth The integer value that was specified as out of bounds.
         * @since 1.0.0
         */
        public InvalidMaximumDepthException(final int specifiedDepth) {

            this.specifiedDepth = specifiedDepth;

        }

        /**
         * <p>Returns the {@link String} value of the formatted error message.</p>
         * @return {@link String} value of the formatted error message.
         * @since 1.0.0
         * @see String
         */
        @Override
        public String getMessage() {

            return String.format(MESSAGE, this.specifiedDepth);

        }

    }

    /**
     * <p>Exception that gets thrown when indicating an invalid minimum depth.</p>
     * @author Carlos L. Cuenca
     * @since 1.0.0
     */
    public final static class InvalidMinimumDepthException extends Exception {

        /**
         * <p>The message that gets emitted when an invalid minimum depth has been specified.</p>
         * @since 1.0.0
         * @see String
         */
        private static final String MESSAGE = "Invalid minimum depth: %s";

        /**
         * <p>The integer value that was indicated as the minimum depth.</p>
         * @since 1.0.0
         */
        private final int specifiedDepth;

        /**
         * <p>Initializes the {@link InvalidMinimumDepthException} to its' default state with the specified integer
         * minimum depth.</p>
         * @param specifiedDepth The integer value that was specified as out of bounds.
         * @since 1.0.0
         */
        public InvalidMinimumDepthException(final int specifiedDepth) {

            this.specifiedDepth = specifiedDepth;

        }

        /**
         * <p>Returns the {@link String} value of the formatted error message.</p>
         * @return {@link String} value of the formatted error message.
         * @since 1.0.0
         * @see String
         */
        @Override
        public String getMessage() {

            return String.format(MESSAGE, this.specifiedDepth);

        }

    }

}
