package org.xenei.cpe.xml.transform;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.jena.arq.querybuilder.AbstractQueryBuilder;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.NodeFactory;
import org.apache.jena.riot.system.StreamRDF;
import org.apache.jena.shared.PrefixMapping;
import org.apache.jena.shared.impl.PrefixMappingImpl;
import org.apache.jena.sparql.core.Quad;
import org.xenei.cpe.xml.transform.handlers.DocumentHandler;
import org.xenei.cpe.rdf.vocabulary.CPE;
import org.xenei.cpe.rdf.vocabulary.XCPE;
import org.xenei.cpe.xml.transform.handlers.CPEHandlerBase;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * A content handler to parse CPE xml files.
 * 
 * Uses a SAX parser.
 * 
 * Works by pushing and popping CPEHandlerBase instance on and off a stack. Most
 * ContentHandler events are delegated to the CPEHandlerBase at the top of the
 * stack.
 *
 */
public class CPEHandler implements ContentHandler {

	/**
	 * The default CPE XML URL.
	 */
	public static final String DATA_URL = "https://nvd.nist.gov/feeds/xml/cpe/dictionary/official-cpe-dictionary_v2.3.xml.gz";

	private StreamRDF streamRDF;
	private Stack<CPEHandlerBase> stack;
	private PrefixMapping pMapping;
	private Locator locator;
	private final URL source;
	private final Node graphName;

	/**
	 * Constructor.
	 * 
	 * @param streamRDF The stream to write the results to.
	 * @param source    the Source of the XML.
	 */
	public CPEHandler(StreamRDF streamRDF, URL source) {
		this.streamRDF = streamRDF;
		this.stack = new Stack<CPEHandlerBase>();
		this.pMapping = new PrefixMappingImpl();
		this.source = source;
		this.graphName = NodeFactory.createURI(source.toExternalForm());
	}

	/**
	 * Get the prefix mapping for the transform.
	 * 
	 * @return the prefix mapping.
	 */
	public PrefixMapping getPrefixMapping() {
		return pMapping;
	}

	public Node graphName() {
		return graphName;
	}

	/**
	 * Get the URL for the source.
	 * 
	 * @return the URL for the source.
	 */
	public URL source() {
		return source;
	}

	/**
	 * Push a CPEHandlerBase onto the stack.
	 * 
	 * @param handler the handler to push.
	 */
	public void push(CPEHandlerBase handler) {
		this.stack.push(handler);
	}

	/**
	 * Pop the CPEHandlerBase from the stack.
	 * 
	 * @return the handler that was popped.
	 */
	public CPEHandlerBase pop() {
		return this.stack.pop();
	}

	@Override
	public void endDocument() throws SAXException {
		this.stack.peek().endDocument();
		if (!stack.isEmpty()) {
			throw new SAXException("Handlers left on Stack");
		}
	}

	@Override
	public void startDocument() throws SAXException {
		this.stack.push(new DocumentHandler(this));
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		this.stack.peek().startElement(uri, localName, qName, attributes);
	}

	@Override
	public void startPrefixMapping(String prefix, String iri) throws SAXException {
		pMapping.setNsPrefix(prefix, iri);
		streamRDF.prefix(prefix, iri);
	}

	@Override
	public void endPrefixMapping(String prefix) throws SAXException {
		// pMapping.removeNsPrefix(prefix);
	}

	/**
	 * Add a triple to the output stream. The triple will be in the graph named by
	 * the URL
	 * 
	 * @param s the subject of the triple.
	 * @param p the predicate of the triple.
	 * @param o the object of the triple.
	 */
	public void addTriple(Object s, Object p, Object o) {
		Node sn = AbstractQueryBuilder.makeNode(s, pMapping);
		Node pn = AbstractQueryBuilder.makeNode(p, pMapping);
		Node on = AbstractQueryBuilder.makeNode(o, pMapping);
		streamRDF.quad(new Quad(graphName, sn, pn, on));
	}

	/**
	 * Add a quad to the output stream.
	 * 
	 * @param g the graph of the quad.
	 * @param s the subject of the quad.
	 * @param p the predicate of the quad.
	 * @param o the object of the quad.
	 */
	public void addQuad(Object g, Object s, Object p, Object o) {
		Node gn = AbstractQueryBuilder.makeNode(g, pMapping);
		Node sn = AbstractQueryBuilder.makeNode(s, pMapping);
		Node pn = AbstractQueryBuilder.makeNode(p, pMapping);
		Node on = AbstractQueryBuilder.makeNode(o, pMapping);
		streamRDF.quad(new Quad(gn, sn, pn, on));
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		this.stack.peek().characters(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		this.stack.peek().endElement(uri, localName, qName);
	}

	@Override
	public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
		this.stack.peek().ignorableWhitespace(ch, start, length);
	}

	@Override
	public void processingInstruction(String target, String data) throws SAXException {
		this.stack.peek().processingInstruction(target, data);
	}

	@Override
	public final void setDocumentLocator(Locator locator) {
		this.locator = locator;
	}

	/**
	 * Get the locator as set by the SAX parser.
	 * 
	 * @return the Locator.
	 */
	public final Locator getDocumentLocator() {
		return locator;
	}

	@Override
	public void skippedEntity(String name) throws SAXException {
		this.stack.peek().skippedEntity(name);
	}

	/**
	 * Load the xmlinput stream using the defined base.
	 * 
	 * @param xmlInput the xml input file.
	 * @param base     the base URL as a string.
	 * @throws ParserConfigurationException on parser configuration error.
	 * @throws SAXException                 on sax parsing error.
	 * @throws IOException                  on IO error.
	 */
	public void load(InputStream xmlInput, String base) throws ParserConfigurationException, SAXException, IOException {

		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(true);

		SAXParser parser = factory.newSAXParser();
		XMLReader xmlreader = parser.getXMLReader();
		xmlreader.setContentHandler(this);
		startPrefixMapping("cpe", CPE.uri);
		startPrefixMapping("xcpe", XCPE.uri);
		xmlreader.parse(new InputSource(xmlInput));
	}

	/**
	 * load the default CPE XML file.
	 * 
	 * @throws ParserConfigurationException on parser configuration error.
	 * @throws SAXException                 on sax parsing error.
	 * @throws IOException                  on IO error.
	 */
	public void load() throws ParserConfigurationException, SAXException, IOException {
		URL xmlURL = new URL(CPEHandler.DATA_URL);

		try (InputStream xmlInput = new GzipCompressorInputStream(new BufferedInputStream(xmlURL.openStream()))) {
			load(xmlInput, CPEHandler.DATA_URL);
		}
	}
}
