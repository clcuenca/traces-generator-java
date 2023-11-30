// Generated from src/main/java/dev/clcuenca/generated/DOT.g4 by ANTLR 4.13.0
package dev.clcuenca.generated;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class DOTParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.0", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, STRICT=11, GRAPH=12, DIGRAPH=13, NODE=14, EDGE=15, SUBGRAPH=16, 
		NUMBER=17, STRING=18, ID=19, HTML_STRING=20, COMMENT=21, LINE_COMMENT=22, 
		PREPROC=23, WS=24;
	public static final int
		RULE_graph = 0, RULE_statementList = 1, RULE_statement = 2, RULE_attributeStatement = 3, 
		RULE_attributeList = 4, RULE_aList = 5, RULE_edgeStatement = 6, RULE_edgeRHS = 7, 
		RULE_edgeOperator = 8, RULE_nodeStatement = 9, RULE_vertexId = 10, RULE_port = 11, 
		RULE_subgraph = 12, RULE_identifier = 13;
	private static String[] makeRuleNames() {
		return new String[] {
			"graph", "statementList", "statement", "attributeStatement", "attributeList", 
			"aList", "edgeStatement", "edgeRHS", "edgeOperator", "nodeStatement", 
			"vertexId", "port", "subgraph", "identifier"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'{'", "'}'", "';'", "'='", "'['", "']'", "','", "'->'", "'--'", 
			"':'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, "STRICT", 
			"GRAPH", "DIGRAPH", "NODE", "EDGE", "SUBGRAPH", "NUMBER", "STRING", "ID", 
			"HTML_STRING", "COMMENT", "LINE_COMMENT", "PREPROC", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "DOT.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public DOTParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class GraphContext extends ParserRuleContext {
		public StatementListContext statementList() {
			return getRuleContext(StatementListContext.class,0);
		}
		public TerminalNode EOF() { return getToken(DOTParser.EOF, 0); }
		public TerminalNode GRAPH() { return getToken(DOTParser.GRAPH, 0); }
		public TerminalNode DIGRAPH() { return getToken(DOTParser.DIGRAPH, 0); }
		public TerminalNode STRICT() { return getToken(DOTParser.STRICT, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public GraphContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_graph; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DOTListener ) ((DOTListener)listener).enterGraph(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DOTListener ) ((DOTListener)listener).exitGraph(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DOTVisitor ) return ((DOTVisitor<? extends T>)visitor).visitGraph(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GraphContext graph() throws RecognitionException {
		GraphContext _localctx = new GraphContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_graph);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(29);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==STRICT) {
				{
				setState(28);
				match(STRICT);
				}
			}

			setState(31);
			_la = _input.LA(1);
			if ( !(_la==GRAPH || _la==DIGRAPH) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(33);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1966080L) != 0)) {
				{
				setState(32);
				identifier();
				}
			}

			setState(35);
			match(T__0);
			setState(36);
			statementList();
			setState(37);
			match(T__1);
			setState(38);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StatementListContext extends ParserRuleContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public StatementListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statementList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DOTListener ) ((DOTListener)listener).enterStatementList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DOTListener ) ((DOTListener)listener).exitStatementList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DOTVisitor ) return ((DOTVisitor<? extends T>)visitor).visitStatementList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementListContext statementList() throws RecognitionException {
		StatementListContext _localctx = new StatementListContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_statementList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(46);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2084866L) != 0)) {
				{
				{
				setState(40);
				statement();
				setState(42);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__2) {
					{
					setState(41);
					match(T__2);
					}
				}

				}
				}
				setState(48);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StatementContext extends ParserRuleContext {
		public NodeStatementContext nodeStatement() {
			return getRuleContext(NodeStatementContext.class,0);
		}
		public EdgeStatementContext edgeStatement() {
			return getRuleContext(EdgeStatementContext.class,0);
		}
		public AttributeStatementContext attributeStatement() {
			return getRuleContext(AttributeStatementContext.class,0);
		}
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		public SubgraphContext subgraph() {
			return getRuleContext(SubgraphContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DOTListener ) ((DOTListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DOTListener ) ((DOTListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DOTVisitor ) return ((DOTVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_statement);
		try {
			setState(57);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(49);
				nodeStatement();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(50);
				edgeStatement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(51);
				attributeStatement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(52);
				identifier();
				setState(53);
				match(T__3);
				setState(54);
				identifier();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(56);
				subgraph();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AttributeStatementContext extends ParserRuleContext {
		public AttributeListContext attributeList() {
			return getRuleContext(AttributeListContext.class,0);
		}
		public TerminalNode GRAPH() { return getToken(DOTParser.GRAPH, 0); }
		public TerminalNode NODE() { return getToken(DOTParser.NODE, 0); }
		public TerminalNode EDGE() { return getToken(DOTParser.EDGE, 0); }
		public AttributeStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attributeStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DOTListener ) ((DOTListener)listener).enterAttributeStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DOTListener ) ((DOTListener)listener).exitAttributeStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DOTVisitor ) return ((DOTVisitor<? extends T>)visitor).visitAttributeStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AttributeStatementContext attributeStatement() throws RecognitionException {
		AttributeStatementContext _localctx = new AttributeStatementContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_attributeStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(59);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 53248L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(60);
			attributeList();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AttributeListContext extends ParserRuleContext {
		public List<AListContext> aList() {
			return getRuleContexts(AListContext.class);
		}
		public AListContext aList(int i) {
			return getRuleContext(AListContext.class,i);
		}
		public AttributeListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attributeList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DOTListener ) ((DOTListener)listener).enterAttributeList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DOTListener ) ((DOTListener)listener).exitAttributeList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DOTVisitor ) return ((DOTVisitor<? extends T>)visitor).visitAttributeList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AttributeListContext attributeList() throws RecognitionException {
		AttributeListContext _localctx = new AttributeListContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_attributeList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(67); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(62);
				match(T__4);
				setState(64);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1966080L) != 0)) {
					{
					setState(63);
					aList();
					}
				}

				setState(66);
				match(T__5);
				}
				}
				setState(69); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__4 );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AListContext extends ParserRuleContext {
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		public AListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_aList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DOTListener ) ((DOTListener)listener).enterAList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DOTListener ) ((DOTListener)listener).exitAList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DOTVisitor ) return ((DOTVisitor<? extends T>)visitor).visitAList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AListContext aList() throws RecognitionException {
		AListContext _localctx = new AListContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_aList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(79); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(71);
				identifier();
				setState(74);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__3) {
					{
					setState(72);
					match(T__3);
					setState(73);
					identifier();
					}
				}

				setState(77);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__6) {
					{
					setState(76);
					match(T__6);
					}
				}

				}
				}
				setState(81); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & 1966080L) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class EdgeStatementContext extends ParserRuleContext {
		public EdgeRHSContext edgeRHS() {
			return getRuleContext(EdgeRHSContext.class,0);
		}
		public VertexIdContext vertexId() {
			return getRuleContext(VertexIdContext.class,0);
		}
		public SubgraphContext subgraph() {
			return getRuleContext(SubgraphContext.class,0);
		}
		public AttributeListContext attributeList() {
			return getRuleContext(AttributeListContext.class,0);
		}
		public EdgeStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_edgeStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DOTListener ) ((DOTListener)listener).enterEdgeStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DOTListener ) ((DOTListener)listener).exitEdgeStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DOTVisitor ) return ((DOTVisitor<? extends T>)visitor).visitEdgeStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EdgeStatementContext edgeStatement() throws RecognitionException {
		EdgeStatementContext _localctx = new EdgeStatementContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_edgeStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(85);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NUMBER:
			case STRING:
			case ID:
			case HTML_STRING:
				{
				setState(83);
				vertexId();
				}
				break;
			case T__0:
			case SUBGRAPH:
				{
				setState(84);
				subgraph();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(87);
			edgeRHS();
			setState(89);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__4) {
				{
				setState(88);
				attributeList();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class EdgeRHSContext extends ParserRuleContext {
		public List<EdgeOperatorContext> edgeOperator() {
			return getRuleContexts(EdgeOperatorContext.class);
		}
		public EdgeOperatorContext edgeOperator(int i) {
			return getRuleContext(EdgeOperatorContext.class,i);
		}
		public List<VertexIdContext> vertexId() {
			return getRuleContexts(VertexIdContext.class);
		}
		public VertexIdContext vertexId(int i) {
			return getRuleContext(VertexIdContext.class,i);
		}
		public List<SubgraphContext> subgraph() {
			return getRuleContexts(SubgraphContext.class);
		}
		public SubgraphContext subgraph(int i) {
			return getRuleContext(SubgraphContext.class,i);
		}
		public EdgeRHSContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_edgeRHS; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DOTListener ) ((DOTListener)listener).enterEdgeRHS(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DOTListener ) ((DOTListener)listener).exitEdgeRHS(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DOTVisitor ) return ((DOTVisitor<? extends T>)visitor).visitEdgeRHS(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EdgeRHSContext edgeRHS() throws RecognitionException {
		EdgeRHSContext _localctx = new EdgeRHSContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_edgeRHS);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(96); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(91);
				edgeOperator();
				setState(94);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case NUMBER:
				case STRING:
				case ID:
				case HTML_STRING:
					{
					setState(92);
					vertexId();
					}
					break;
				case T__0:
				case SUBGRAPH:
					{
					setState(93);
					subgraph();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				}
				setState(98); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__7 || _la==T__8 );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class EdgeOperatorContext extends ParserRuleContext {
		public EdgeOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_edgeOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DOTListener ) ((DOTListener)listener).enterEdgeOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DOTListener ) ((DOTListener)listener).exitEdgeOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DOTVisitor ) return ((DOTVisitor<? extends T>)visitor).visitEdgeOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EdgeOperatorContext edgeOperator() throws RecognitionException {
		EdgeOperatorContext _localctx = new EdgeOperatorContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_edgeOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(100);
			_la = _input.LA(1);
			if ( !(_la==T__7 || _la==T__8) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class NodeStatementContext extends ParserRuleContext {
		public VertexIdContext vertexId() {
			return getRuleContext(VertexIdContext.class,0);
		}
		public AttributeListContext attributeList() {
			return getRuleContext(AttributeListContext.class,0);
		}
		public NodeStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nodeStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DOTListener ) ((DOTListener)listener).enterNodeStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DOTListener ) ((DOTListener)listener).exitNodeStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DOTVisitor ) return ((DOTVisitor<? extends T>)visitor).visitNodeStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NodeStatementContext nodeStatement() throws RecognitionException {
		NodeStatementContext _localctx = new NodeStatementContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_nodeStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(102);
			vertexId();
			setState(104);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__4) {
				{
				setState(103);
				attributeList();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VertexIdContext extends ParserRuleContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public PortContext port() {
			return getRuleContext(PortContext.class,0);
		}
		public VertexIdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_vertexId; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DOTListener ) ((DOTListener)listener).enterVertexId(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DOTListener ) ((DOTListener)listener).exitVertexId(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DOTVisitor ) return ((DOTVisitor<? extends T>)visitor).visitVertexId(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VertexIdContext vertexId() throws RecognitionException {
		VertexIdContext _localctx = new VertexIdContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_vertexId);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(106);
			identifier();
			setState(108);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__9) {
				{
				setState(107);
				port();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PortContext extends ParserRuleContext {
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		public PortContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_port; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DOTListener ) ((DOTListener)listener).enterPort(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DOTListener ) ((DOTListener)listener).exitPort(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DOTVisitor ) return ((DOTVisitor<? extends T>)visitor).visitPort(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PortContext port() throws RecognitionException {
		PortContext _localctx = new PortContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_port);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(110);
			match(T__9);
			setState(111);
			identifier();
			setState(114);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__9) {
				{
				setState(112);
				match(T__9);
				setState(113);
				identifier();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SubgraphContext extends ParserRuleContext {
		public StatementListContext statementList() {
			return getRuleContext(StatementListContext.class,0);
		}
		public TerminalNode SUBGRAPH() { return getToken(DOTParser.SUBGRAPH, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public SubgraphContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subgraph; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DOTListener ) ((DOTListener)listener).enterSubgraph(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DOTListener ) ((DOTListener)listener).exitSubgraph(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DOTVisitor ) return ((DOTVisitor<? extends T>)visitor).visitSubgraph(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SubgraphContext subgraph() throws RecognitionException {
		SubgraphContext _localctx = new SubgraphContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_subgraph);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(120);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SUBGRAPH) {
				{
				setState(116);
				match(SUBGRAPH);
				setState(118);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1966080L) != 0)) {
					{
					setState(117);
					identifier();
					}
				}

				}
			}

			setState(122);
			match(T__0);
			setState(123);
			statementList();
			setState(124);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IdentifierContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(DOTParser.ID, 0); }
		public TerminalNode STRING() { return getToken(DOTParser.STRING, 0); }
		public TerminalNode HTML_STRING() { return getToken(DOTParser.HTML_STRING, 0); }
		public TerminalNode NUMBER() { return getToken(DOTParser.NUMBER, 0); }
		public IdentifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_identifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DOTListener ) ((DOTListener)listener).enterIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DOTListener ) ((DOTListener)listener).exitIdentifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DOTVisitor ) return ((DOTVisitor<? extends T>)visitor).visitIdentifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdentifierContext identifier() throws RecognitionException {
		IdentifierContext _localctx = new IdentifierContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_identifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(126);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 1966080L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001\u0018\u0081\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001"+
		"\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004"+
		"\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007"+
		"\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b"+
		"\u0002\f\u0007\f\u0002\r\u0007\r\u0001\u0000\u0003\u0000\u001e\b\u0000"+
		"\u0001\u0000\u0001\u0000\u0003\u0000\"\b\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0003\u0001"+
		"+\b\u0001\u0005\u0001-\b\u0001\n\u0001\f\u00010\t\u0001\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0003\u0002:\b\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0004\u0001\u0004\u0003\u0004A\b\u0004\u0001\u0004\u0004\u0004D\b\u0004"+
		"\u000b\u0004\f\u0004E\u0001\u0005\u0001\u0005\u0001\u0005\u0003\u0005"+
		"K\b\u0005\u0001\u0005\u0003\u0005N\b\u0005\u0004\u0005P\b\u0005\u000b"+
		"\u0005\f\u0005Q\u0001\u0006\u0001\u0006\u0003\u0006V\b\u0006\u0001\u0006"+
		"\u0001\u0006\u0003\u0006Z\b\u0006\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0003\u0007_\b\u0007\u0004\u0007a\b\u0007\u000b\u0007\f\u0007b\u0001"+
		"\b\u0001\b\u0001\t\u0001\t\u0003\ti\b\t\u0001\n\u0001\n\u0003\nm\b\n\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0003\u000bs\b\u000b\u0001"+
		"\f\u0001\f\u0003\fw\b\f\u0003\fy\b\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\r\u0001\r\u0001\r\u0000\u0000\u000e\u0000\u0002\u0004\u0006\b\n\f\u000e"+
		"\u0010\u0012\u0014\u0016\u0018\u001a\u0000\u0004\u0001\u0000\f\r\u0002"+
		"\u0000\f\f\u000e\u000f\u0001\u0000\b\t\u0001\u0000\u0011\u0014\u0088\u0000"+
		"\u001d\u0001\u0000\u0000\u0000\u0002.\u0001\u0000\u0000\u0000\u00049\u0001"+
		"\u0000\u0000\u0000\u0006;\u0001\u0000\u0000\u0000\bC\u0001\u0000\u0000"+
		"\u0000\nO\u0001\u0000\u0000\u0000\fU\u0001\u0000\u0000\u0000\u000e`\u0001"+
		"\u0000\u0000\u0000\u0010d\u0001\u0000\u0000\u0000\u0012f\u0001\u0000\u0000"+
		"\u0000\u0014j\u0001\u0000\u0000\u0000\u0016n\u0001\u0000\u0000\u0000\u0018"+
		"x\u0001\u0000\u0000\u0000\u001a~\u0001\u0000\u0000\u0000\u001c\u001e\u0005"+
		"\u000b\u0000\u0000\u001d\u001c\u0001\u0000\u0000\u0000\u001d\u001e\u0001"+
		"\u0000\u0000\u0000\u001e\u001f\u0001\u0000\u0000\u0000\u001f!\u0007\u0000"+
		"\u0000\u0000 \"\u0003\u001a\r\u0000! \u0001\u0000\u0000\u0000!\"\u0001"+
		"\u0000\u0000\u0000\"#\u0001\u0000\u0000\u0000#$\u0005\u0001\u0000\u0000"+
		"$%\u0003\u0002\u0001\u0000%&\u0005\u0002\u0000\u0000&\'\u0005\u0000\u0000"+
		"\u0001\'\u0001\u0001\u0000\u0000\u0000(*\u0003\u0004\u0002\u0000)+\u0005"+
		"\u0003\u0000\u0000*)\u0001\u0000\u0000\u0000*+\u0001\u0000\u0000\u0000"+
		"+-\u0001\u0000\u0000\u0000,(\u0001\u0000\u0000\u0000-0\u0001\u0000\u0000"+
		"\u0000.,\u0001\u0000\u0000\u0000./\u0001\u0000\u0000\u0000/\u0003\u0001"+
		"\u0000\u0000\u00000.\u0001\u0000\u0000\u00001:\u0003\u0012\t\u00002:\u0003"+
		"\f\u0006\u00003:\u0003\u0006\u0003\u000045\u0003\u001a\r\u000056\u0005"+
		"\u0004\u0000\u000067\u0003\u001a\r\u00007:\u0001\u0000\u0000\u00008:\u0003"+
		"\u0018\f\u000091\u0001\u0000\u0000\u000092\u0001\u0000\u0000\u000093\u0001"+
		"\u0000\u0000\u000094\u0001\u0000\u0000\u000098\u0001\u0000\u0000\u0000"+
		":\u0005\u0001\u0000\u0000\u0000;<\u0007\u0001\u0000\u0000<=\u0003\b\u0004"+
		"\u0000=\u0007\u0001\u0000\u0000\u0000>@\u0005\u0005\u0000\u0000?A\u0003"+
		"\n\u0005\u0000@?\u0001\u0000\u0000\u0000@A\u0001\u0000\u0000\u0000AB\u0001"+
		"\u0000\u0000\u0000BD\u0005\u0006\u0000\u0000C>\u0001\u0000\u0000\u0000"+
		"DE\u0001\u0000\u0000\u0000EC\u0001\u0000\u0000\u0000EF\u0001\u0000\u0000"+
		"\u0000F\t\u0001\u0000\u0000\u0000GJ\u0003\u001a\r\u0000HI\u0005\u0004"+
		"\u0000\u0000IK\u0003\u001a\r\u0000JH\u0001\u0000\u0000\u0000JK\u0001\u0000"+
		"\u0000\u0000KM\u0001\u0000\u0000\u0000LN\u0005\u0007\u0000\u0000ML\u0001"+
		"\u0000\u0000\u0000MN\u0001\u0000\u0000\u0000NP\u0001\u0000\u0000\u0000"+
		"OG\u0001\u0000\u0000\u0000PQ\u0001\u0000\u0000\u0000QO\u0001\u0000\u0000"+
		"\u0000QR\u0001\u0000\u0000\u0000R\u000b\u0001\u0000\u0000\u0000SV\u0003"+
		"\u0014\n\u0000TV\u0003\u0018\f\u0000US\u0001\u0000\u0000\u0000UT\u0001"+
		"\u0000\u0000\u0000VW\u0001\u0000\u0000\u0000WY\u0003\u000e\u0007\u0000"+
		"XZ\u0003\b\u0004\u0000YX\u0001\u0000\u0000\u0000YZ\u0001\u0000\u0000\u0000"+
		"Z\r\u0001\u0000\u0000\u0000[^\u0003\u0010\b\u0000\\_\u0003\u0014\n\u0000"+
		"]_\u0003\u0018\f\u0000^\\\u0001\u0000\u0000\u0000^]\u0001\u0000\u0000"+
		"\u0000_a\u0001\u0000\u0000\u0000`[\u0001\u0000\u0000\u0000ab\u0001\u0000"+
		"\u0000\u0000b`\u0001\u0000\u0000\u0000bc\u0001\u0000\u0000\u0000c\u000f"+
		"\u0001\u0000\u0000\u0000de\u0007\u0002\u0000\u0000e\u0011\u0001\u0000"+
		"\u0000\u0000fh\u0003\u0014\n\u0000gi\u0003\b\u0004\u0000hg\u0001\u0000"+
		"\u0000\u0000hi\u0001\u0000\u0000\u0000i\u0013\u0001\u0000\u0000\u0000"+
		"jl\u0003\u001a\r\u0000km\u0003\u0016\u000b\u0000lk\u0001\u0000\u0000\u0000"+
		"lm\u0001\u0000\u0000\u0000m\u0015\u0001\u0000\u0000\u0000no\u0005\n\u0000"+
		"\u0000or\u0003\u001a\r\u0000pq\u0005\n\u0000\u0000qs\u0003\u001a\r\u0000"+
		"rp\u0001\u0000\u0000\u0000rs\u0001\u0000\u0000\u0000s\u0017\u0001\u0000"+
		"\u0000\u0000tv\u0005\u0010\u0000\u0000uw\u0003\u001a\r\u0000vu\u0001\u0000"+
		"\u0000\u0000vw\u0001\u0000\u0000\u0000wy\u0001\u0000\u0000\u0000xt\u0001"+
		"\u0000\u0000\u0000xy\u0001\u0000\u0000\u0000yz\u0001\u0000\u0000\u0000"+
		"z{\u0005\u0001\u0000\u0000{|\u0003\u0002\u0001\u0000|}\u0005\u0002\u0000"+
		"\u0000}\u0019\u0001\u0000\u0000\u0000~\u007f\u0007\u0003\u0000\u0000\u007f"+
		"\u001b\u0001\u0000\u0000\u0000\u0013\u001d!*.9@EJMQUY^bhlrvx";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}