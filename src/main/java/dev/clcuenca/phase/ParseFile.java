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

import static org.antlr.v4.runtime.CharStreams.fromFileName;

/**
 * <p>Phase that opens and parses a DOT {@link SourceFile} and creates a {@link DirectedGraph}.</p>
 * @author Carlos L. Cuenca
 * @since 1.0.0
 * @see DirectedGraph
 * @see SourceFile
 */
public class ParseFile extends Phase implements DOTVisitor<DirectedGraph<String>> {

    /**
     * <p>The {@link DirectedGraph} that gets constructed from a DOT file.</p>
     * @since 1.0.0
     * @see DirectedGraph
     */
    final DirectedGraph<String> directedGraph;

    /**
     * <p>Initializes the {@link Phase} to its' default state with the specified {@link Listener}.</p>
     *
     * @param listener The {@link Listener} to bind to the {@link Phase}.
     * @see Listener
     * @since 0.1.0
     */
    public ParseFile(Listener listener) {
        super(listener);

        this.directedGraph = new DirectedGraph<>();

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
    public DirectedGraph<String> visitGraph(final DOTParser.GraphContext graphContext) {

        return graphContext.statementList().accept(this);

    }

    @Override
    public DirectedGraph<String> visitStatementList(final DOTParser.StatementListContext statementListContext) {

        statementListContext.statement().forEach(
                (final DOTParser.StatementContext statementContext) -> statementContext.accept(this));

        return this.directedGraph;

    }

    @Override
    public DirectedGraph<String> visitStatement(final DOTParser.StatementContext statementContext) {

        if(statementContext.edgeStatement() != null)
            statementContext.edgeStatement().accept(this);

        return this.directedGraph;

    }

    @Override
    public DirectedGraph<String> visitAttributeStatement(final DOTParser.AttributeStatementContext attributeStatementContext) {

        return this.directedGraph;

    }

    @Override
    public DirectedGraph<String> visitAttributeList(final DOTParser.AttributeListContext attributeListContext) {

        return this.directedGraph;

    }

    @Override
    public DirectedGraph<String> visitAList(final DOTParser.AListContext aListContext) {

        return this.directedGraph;

    }

    @Override
    public DirectedGraph<String> visitEdgeStatement(final DOTParser.EdgeStatementContext edgeStatementContext) {

        final String from = edgeStatementContext.vertexId().identifier().getText();
        final DOTParser.EdgeRHSContext edgeRHSContext = edgeStatementContext.edgeRHS();

        edgeRHSContext.vertexId().forEach((final DOTParser.VertexIdContext vertexIdContext) -> {

            this.directedGraph.addEdge(from, vertexIdContext.identifier().getText());

        });

        return this.directedGraph;

    }

    @Override
    public DirectedGraph<String> visitEdgeRHS(final DOTParser.EdgeRHSContext edgeRHSContext) {

        return this.directedGraph;

    }

    @Override
    public DirectedGraph<String> visitEdgeOperator(final DOTParser.EdgeOperatorContext edgeOperatorContext) {

        return this.directedGraph;

    }

    @Override
    public DirectedGraph<String> visitNodeStatement(final DOTParser.NodeStatementContext nodeStatementContext) {

        return this.directedGraph;

    }

    @Override
    public DirectedGraph<String> visitVertexId(final DOTParser.VertexIdContext vertexIdContext) {

        return this.directedGraph;

    }

    @Override
    public DirectedGraph<String> visitPort(final DOTParser.PortContext portContext) {

        return this.directedGraph;

    }

    @Override
    public DirectedGraph<String> visitSubgraph(final DOTParser.SubgraphContext subgraphContext) {

        return this.directedGraph;

    }

    @Override
    public DirectedGraph<String> visitIdentifier(final DOTParser.IdentifierContext identifierContext) {

        return this.directedGraph;

    }

    @Override
    public DirectedGraph<String> visit(final ParseTree parseTree) {

        return this.directedGraph;

    }

    @Override
    public DirectedGraph<String> visitChildren(final RuleNode ruleNode) {

        return this.directedGraph;

    }

    @Override
    public DirectedGraph<String> visitTerminal(final TerminalNode terminalNode) {

        return this.directedGraph;

    }

    @Override
    public DirectedGraph<String> visitErrorNode(final ErrorNode errorNode) {

        return this.directedGraph;

    }

}
