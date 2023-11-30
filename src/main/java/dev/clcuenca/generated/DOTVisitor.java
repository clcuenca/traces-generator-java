// Generated from src/main/java/dev/clcuenca/generated/DOT.g4 by ANTLR 4.13.0
package dev.clcuenca.generated;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link DOTParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface DOTVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link DOTParser#graph}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGraph(DOTParser.GraphContext ctx);
	/**
	 * Visit a parse tree produced by {@link DOTParser#statementList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementList(DOTParser.StatementListContext ctx);
	/**
	 * Visit a parse tree produced by {@link DOTParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(DOTParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link DOTParser#attributeStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttributeStatement(DOTParser.AttributeStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link DOTParser#attributeList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttributeList(DOTParser.AttributeListContext ctx);
	/**
	 * Visit a parse tree produced by {@link DOTParser#aList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAList(DOTParser.AListContext ctx);
	/**
	 * Visit a parse tree produced by {@link DOTParser#edgeStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEdgeStatement(DOTParser.EdgeStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link DOTParser#edgeRHS}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEdgeRHS(DOTParser.EdgeRHSContext ctx);
	/**
	 * Visit a parse tree produced by {@link DOTParser#edgeOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEdgeOperator(DOTParser.EdgeOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link DOTParser#nodeStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNodeStatement(DOTParser.NodeStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link DOTParser#vertexId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVertexId(DOTParser.VertexIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link DOTParser#port}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPort(DOTParser.PortContext ctx);
	/**
	 * Visit a parse tree produced by {@link DOTParser#subgraph}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubgraph(DOTParser.SubgraphContext ctx);
	/**
	 * Visit a parse tree produced by {@link DOTParser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifier(DOTParser.IdentifierContext ctx);
}