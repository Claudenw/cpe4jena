package org.xenei.cpe.xml.transform;

import java.net.URL;
import java.util.Stack;

import org.apache.jena.arq.querybuilder.AbstractQueryBuilder;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.Triple;
import org.apache.jena.riot.system.StreamRDF;
import org.apache.jena.shared.PrefixMapping;
import org.apache.jena.shared.impl.PrefixMappingImpl;
import org.xenei.cpe.xml.transform.handlers.DocumentHandler;
import org.xenei.cpe.xml.transform.handlers.CPEHandlerBase;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class CPEHandler implements ContentHandler {
	
	private StreamRDF streamRDF;
	private Stack<CPEHandlerBase> stack;
	private PrefixMapping pMapping;
	private Locator locator;
	private final URL source;
	
	public CPEHandler( StreamRDF streamRDF, URL source ) {
		this.streamRDF = streamRDF;
		this.stack = new Stack<CPEHandlerBase>();
		this.pMapping = new PrefixMappingImpl();
		this.source = source;
	}
	
	public PrefixMapping getPrefixMapping() {
		return pMapping;
	}
	
	public URL source()
	{
		return source;
	}

	public void push( CPEHandlerBase handler  )
	{
		this.stack.push( handler );
	}
	
	public CPEHandlerBase pop() {
		return this.stack.pop();
	}
	
	@Override
	public void endDocument() throws SAXException {
		this.stack.peek().endDocument();
		if ( ! stack.isEmpty())
		{
			throw new SAXException( "Handlers left on Stack");
		}
	}

	@Override
	public void startDocument() throws SAXException {
		this.stack.push( new DocumentHandler( this ) );	
	}

	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		this.stack.peek().startElement(  uri,  localName,  qName,  attributes );
	}

	@Override
	public void startPrefixMapping(String prefix, String iri) throws SAXException {
		pMapping.setNsPrefix(prefix, iri);
		streamRDF.prefix(prefix, iri);
	}
	
	@Override
	public void endPrefixMapping(String prefix) throws SAXException {
		//pMapping.removeNsPrefix(prefix);
	}

	public void addTriple( Object s, Object p, Object o )
	{		
		Node sn = AbstractQueryBuilder.makeNode(s, pMapping);
		Node pn = AbstractQueryBuilder.makeNode(p, pMapping);
		Node on = AbstractQueryBuilder.makeNode(o, pMapping);
		streamRDF.triple( new Triple( sn, pn, on ));
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
		this.stack.peek().ignorableWhitespace( ch, start, length );
	}

	@Override
	public void processingInstruction(String target, String data) throws SAXException {
		this.stack.peek().processingInstruction(target,  data);
	}

	@Override
	public final void setDocumentLocator(Locator locator) {
		this.locator = locator;
	}
	
	public final Locator getDocumentLocator()
	{
		return locator;
	}

	@Override
	public void skippedEntity(String name) throws SAXException {
		this.stack.peek().skippedEntity( name );
	}
}
