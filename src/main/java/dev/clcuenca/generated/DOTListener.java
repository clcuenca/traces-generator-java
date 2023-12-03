// Generated from src/main/java/dev/clcuenca/generated/DOT.g4 by ANTLR 4.13.0
package dev.clcuenca.generated;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link DOTParser}.
 */
public interface DOTListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link DOTParser#graph}.
	 * @param ctx the parse tree
	 */
	void enterGraph(DOTParser.GraphContext ctx);
	/**
	 * Exit a parse tree produced by {@link DOTParser#graph}.
	 * @param ctx the parse tree
	 */
	void exitGraph(DOTParser.GraphContext ctx);
	/**
	 * Enter a parse tree produced by {@link DOTParser#statementList}.
	 * @param ctx the parse tree
	 */
	void enterStatementList(DOTParser.StatementListContext ctx);
	/**
	 * Exit a parse tree produced by {@link DOTParser#statementList}.
	 * @param ctx the parse tree
	 */
	void exitStatementList(DOTParser.StatementListContext ctx);
	/**
	 * Enter a parse tree produced by {@link DOTParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(DOTParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link DOTParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(DOTParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link DOTParser#attributeStatement}.
	 * @param ctx the parse tree
	 */
	void enterAttributeStatement(DOTParser.AttributeStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link DOTParser#attributeStatement}.
	 * @param ctx the parse tree
	 */
	void exitAttributeStatement(DOTParser.AttributeStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link DOTParser#attributeList}.
	 * @param ctx the parse tree
	 */
	void enterAttributeList(DOTParser.AttributeListContext ctx);
	/**
	 * Exit a parse tree produced by {@link DOTParser#attributeList}.
	 * @param ctx the parse tree
	 */
	void exitAttributeList(DOTParser.AttributeListContext ctx);
	/**
	 * Enter a parse tree produced by {@link DOTParser#aList}.
	 * @param ctx the parse tree
	 */
	void enterAList(DOTParser.AListContext ctx);
	/**
	 * Exit a parse tree produced by {@link DOTParser#aList}.
	 * @param ctx the parse tree
	 */
	void exitAList(DOTParser.AListContext ctx);
	/**
	 * Enter a parse tree produced by {@link DOTParser#edgeStatement}.
	 * @param ctx the parse tree
	 */
	void enterEdgeStatement(DOTParser.EdgeStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link DOTParser#edgeStatement}.
	 * @param ctx the parse tree
	 */
	void exitEdgeStatement(DOTParser.EdgeStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link DOTParser#edgeRHS}.
	 * @param ctx the parse tree
	 */
	void enterEdgeRHS(DOTParser.EdgeRHSContext ctx);
	/**
	 * Exit a parse tree produced by {@link DOTParser#edgeRHS}.
	 * @param ctx the parse tree
	 */
	void exitEdgeRHS(DOTParser.EdgeRHSContext ctx);
	/**
	 * Enter a parse tree produced by {@link DOTParser#edgeOperator}.
	 * @param ctx the parse tree
	 */
	void enterEdgeOperator(DOTParser.EdgeOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link DOTParser#edgeOperator}.
	 * @param ctx the parse tree
	 */
	void exitEdgeOperator(DOTParser.EdgeOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link DOTParser#nodeStatement}.
	 * @param ctx the parse tree
	 */
	void enterNodeStatement(DOTParser.NodeStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link DOTParser#nodeStatement}.
	 * @param ctx the parse tree
	 */
	void exitNodeStatement(DOTParser.NodeStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link DOTParser#vertexId}.
	 * @param ctx the parse tree
	 */
	void enterVertexId(DOTParser.VertexIdContext ctx);
	/**
	 * Exit a parse tree produced by {@link DOTParser#vertexId}.
	 * @param ctx the parse tree
	 */
	void exitVertexId(DOTParser.VertexIdContext ctx);
	/**
	 * Enter a parse tree produced by {@link DOTParser#port}.
	 * @param ctx the parse tree
	 */
	void enterPort(DOTParser.PortContext ctx);
	/**
	 * Exit a parse tree produced by {@link DOTParser#port}.
	 * @param ctx the parse tree
	 */
	void exitPort(DOTParser.PortContext ctx);
	/**
	 * Enter a parse tree produced by {@link DOTParser#subgraph}.
	 * @param ctx the parse tree
	 */
	void enterSubgraph(DOTParser.SubgraphContext ctx);
	/**
	 * Exit a parse tree produced by {@link DOTParser#subgraph}.
	 * @param ctx the parse tree
	 */
	void exitSubgraph(DOTParser.SubgraphContext ctx);
	/**
	 * Enter a parse tree produced by {@link DOTParser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterIdentifier(DOTParser.IdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link DOTParser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitIdentifier(DOTParser.IdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link DOTParser#comments}.
	 * @param ctx the parse tree
	 */
	void enterComments(DOTParser.CommentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link DOTParser#comments}.
	 * @param ctx the parse tree
	 */
	void exitComments(DOTParser.CommentsContext ctx);
}