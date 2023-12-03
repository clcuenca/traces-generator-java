package dev.clcuenca.phase;

import dev.clcuenca.generated.DOTLexer;
import dev.clcuenca.generated.DOTParser;
import dev.clcuenca.generated.DOTVisitor;
import dev.clcuenca.utilities.DirectedGraph;
import dev.clcuenca.utilities.SourceFile;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.*;

import static org.antlr.v4.runtime.CharStreams.fromFileName;

/**
 * <p>Phase that opens and parses a DOT {@link SourceFile} and creates a {@link DirectedGraph}.</p>
 * @author Carlos L. Cuenca
 * @since 1.0.0
 * @see DirectedGraph
 * @see SourceFile
 */
public class ParseFile extends Phase implements DOTVisitor<DirectedGraph<String, String>> {

    /**
     * <p>The {@link DirectedGraph} that gets constructed from a DOT file.</p>
     * @since 1.0.0
     * @see DirectedGraph
     */
    private final DirectedGraph<String, String> directedGraph;

    /**
     * <p>The current {@link Map} of attributes constructed from a current statement.</p>
     * @since 0.1.0
     * @see Map
     * @see String
     */
    private final Map<String, Set<String>> currentAttributes;

    /**
     * <p>The root of the tree to be generated from the {@link DirectedGraph}.</p>
     * @since 0.1.0
     * @see String
     * @see DirectedGraph
     */
    private String root;

    /**
     * <p>Initializes the {@link Phase} to its' default state with the specified {@link Listener}.</p>
     *
     * @param listener The {@link Listener} to bind to the {@link Phase}.
     * @see Listener
     * @since 0.1.0
     */
    public ParseFile(final Listener listener) {
        super(listener);

        this.directedGraph = new DirectedGraph<>();
        this.currentAttributes = new HashMap<>();
        this.root = null;

    }

    /**
     * <p>Parses the current DOT file and converts the result into a {@link DirectedGraph}.</p>
     * @since 1.0.0
     * @see DirectedGraph
     */
    @Override
    protected void executePhase() {

        final SourceFile sourceFile = this.getSourceFile();

        ParserAssert.ParsingFile.Assert(this, sourceFile);

        try {

            final DOTLexer dotLexer = new DOTLexer(fromFileName(sourceFile.getPath()));
            final DOTParser dotParser = new DOTParser(new CommonTokenStream(dotLexer));

            dotParser.graph().accept(this);

        // Otherwise
        } catch(final Exception exception) {

            // Assert the Parser is valid
            ParserAssert.FileOpenFailure.Assert(this, sourceFile);

            // Initialize & throw the error
            ParserAssert.ParserFailure.Assert(this, sourceFile);

        }

        sourceFile.setDirectedGraph(this.directedGraph.clone());

        this.directedGraph.clear();

    }

    @Override
    public DirectedGraph<String, String> visitGraph(final DOTParser.GraphContext graphContext) {

        if(graphContext.statementList() != null)
            graphContext.statementList().accept(this);

        return this.directedGraph;

    }

    @Override
    public DirectedGraph<String, String> visitStatementList(final DOTParser.StatementListContext statementListContext) {

        if(statementListContext.statement() != null)
            statementListContext.statement().forEach((final DOTParser.StatementContext statementContext) ->
                    statementContext.accept(this));

        return this.directedGraph;

    }

    @Override
    public DirectedGraph<String, String> visitStatement(final DOTParser.StatementContext statementContext) {

        if(statementContext.edgeStatement() != null)
            statementContext.edgeStatement().accept(this);

        return this.directedGraph;

    }

    @Override
    public DirectedGraph<String, String> visitAttributeStatement(final DOTParser.AttributeStatementContext attributeStatementContext) {

        return this.directedGraph;

    }

    @Override
    public DirectedGraph<String, String> visitAttributeList(final DOTParser.AttributeListContext attributeListContext) {

        if(attributeListContext.aList() != null)
            attributeListContext.aList()
                    .forEach((final DOTParser.AListContext aListContext) -> aListContext.accept(this));

        return this.directedGraph;

    }

    @Override
    public DirectedGraph<String, String> visitAList(final DOTParser.AListContext aListContext) {

        if((aListContext.identifier() != null) && (!aListContext.identifier().isEmpty())) {

            final String key = aListContext.identifier().get(0).getText();
            final String value = aListContext.identifier().get(1).getText();

            this.currentAttributes.putIfAbsent(key, new LinkedHashSet<>());
            this.currentAttributes.get(key).add(value);

        }

        return this.directedGraph;

    }

    @Override
    public DirectedGraph<String, String> visitEdgeStatement(final DOTParser.EdgeStatementContext edgeStatementContext) {

        final String from = edgeStatementContext.vertexId().identifier().getText();
        final DOTParser.EdgeRHSContext edgeRHSContext = edgeStatementContext.edgeRHS();

        if(edgeStatementContext.attributeList() != null)
            edgeStatementContext.attributeList().accept(this);

        edgeRHSContext.vertexId().forEach((final DOTParser.VertexIdContext vertexIdContext) -> {

            final Set<String> attributes = this.currentAttributes.getOrDefault("label", Set.of());
            final String label = !attributes.isEmpty()
                    ? (String) attributes.toArray()[0]
                    : vertexIdContext.identifier().getText();
            final String to = vertexIdContext.identifier().getText();

            this.directedGraph.addEdge(from, to, label);

        });

        this.currentAttributes.forEach((final String attribute, final Set<String> values) -> values.clear());
        this.currentAttributes.clear();

        return this.directedGraph;

    }

    @Override
    public DirectedGraph<String, String> visitEdgeRHS(final DOTParser.EdgeRHSContext edgeRHSContext) {

        return this.directedGraph;

    }

    @Override
    public DirectedGraph<String, String> visitEdgeOperator(final DOTParser.EdgeOperatorContext edgeOperatorContext) {

        return this.directedGraph;

    }

    @Override
    public DirectedGraph<String, String> visitNodeStatement(final DOTParser.NodeStatementContext nodeStatementContext) {

        return this.directedGraph;

    }

    @Override
    public DirectedGraph<String, String> visitVertexId(final DOTParser.VertexIdContext vertexIdContext) {

        return this.directedGraph;

    }

    @Override
    public DirectedGraph<String, String> visitPort(final DOTParser.PortContext portContext) {

        return this.directedGraph;

    }

    @Override
    public DirectedGraph<String, String> visitSubgraph(final DOTParser.SubgraphContext subgraphContext) {

        return this.directedGraph;

    }

    @Override
    public DirectedGraph<String, String> visitIdentifier(final DOTParser.IdentifierContext identifierContext) {

        return this.directedGraph;

    }

    @Override
    public DirectedGraph<String, String> visit(final ParseTree parseTree) {

        return this.directedGraph;

    }

    @Override
    public DirectedGraph<String, String> visitChildren(final RuleNode ruleNode) {

        return this.directedGraph;

    }

    @Override
    public DirectedGraph<String, String> visitTerminal(final TerminalNode terminalNode) {

        return this.directedGraph;

    }

    @Override
    public DirectedGraph<String, String> visitErrorNode(final ErrorNode errorNode) {

        return this.directedGraph;

    }

}
