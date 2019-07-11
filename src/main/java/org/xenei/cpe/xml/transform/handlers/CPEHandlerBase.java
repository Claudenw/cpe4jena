package org.xenei.cpe.xml.transform.handlers;

import java.net.URL;

import org.apache.jena.graph.Node;
import org.xenei.cpe.xml.transform.CPEHandler;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

/**
 * The abstract class that underlies all CPEHandler stack entries.
 *
 */
public abstract class CPEHandlerBase implements ContentHandler {
	
	
//	public static final String scapCore3="http://scap.nist.gov/schema/scap-core/0.3";
//	public static final String config="http://scap.nist.gov/schema/configuration/0.1";
//	public static final String scapCore1="http://scap.nist.gov/schema/scap-core/0.1";
//	public static final String meta="http://scap.nist.gov/schema/cpe-dictionary-metadata/0.2";

	/**
	 * The handler that this base is associated with.
	 */
	private CPEHandler cpeHandler;
	
	/**
	 * Constructor.
	 * @param cpeHandler the handler this base is associated with.
	 */
	public CPEHandlerBase( CPEHandler cpeHandler ) {
		this.cpeHandler = cpeHandler;
	}
	
	/**
	 * Constructor.
	 * 
	 * This constructor is called when a CPEHandlerBase needs to construct another
	 * CPEHandlerBase instance.
	 * 
	 * @param parent the handler that is the parent of this one.
	 */
	public CPEHandlerBase( CPEHandlerBase parent ) {
		this.cpeHandler = parent.cpeHandler;
	}
	
	/**
	 * Get the current locator from the SAX parser.
	 * @return the SAX locator
	 */
	protected final Locator getDocumentLocator()
	{
		return cpeHandler.getDocumentLocator();
	}
	
	/**
	 * Get the URL for the XML source.
	 * @return  the source URL.
	 */
	protected final URL source() {
		return cpeHandler.source();
	}
	
	/**
	 * Pop this handlr off the stack.
	 * @return this handler.
	 * @throws SAXException if the handler popped off the stack is not this one.
	 */
	protected final CPEHandlerBase pop() throws SAXException
	{
		CPEHandlerBase me = cpeHandler.pop();
		if (me != this)
		{
			throw new SAXException( "expected "+this+" got "+me);
		}
		return me;
	}
	
	/**
	 * Push a handler onto the stack.  The new handler will begin receiving SAX events.
	 * @param newHandler the new handler to put on the stack.
	 */
	protected void push( CPEHandlerBase newHandler )
	{
		cpeHandler.push( newHandler );
	}
	
	/**
	 * Add a triple to the default graph
	 * @param s the subject for the triple
	 * @param p the object for the triple.
	 * @param o the predicate for the triple.
	 */
	protected final void addTriple( Object s, Object p, Object o)
	{
		cpeHandler.addTriple( s, p, o);
	}
	
	/**
	 * Add a quad to the output stream.
	 * @param g the graph of the quad.
	 * @param s the subject of the quad.
	 * @param p the predicate of the quad.
	 * @param o the object of the quad.
	 */
	protected void addQuad( Object g, Object s, Object p, Object o )
	{		
		cpeHandler.addQuad( g, s, p, o);
	}

	protected Node graphName() {
		return cpeHandler.graphName();
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		// ignore them
	}

	@Override
	public void endDocument() throws SAXException {
		throw new SAXException( "Unexpected end document");
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		throw new SAXException( "Unexpected end element: "+uri+localName+" from "+this );
	}

	@Override
	public final void endPrefixMapping(String prefix) throws SAXException {
		cpeHandler.endPrefixMapping(prefix);
	}

	@Override
	public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
		// do nothing.
	}

	@Override
	public void processingInstruction(String target, String data) throws SAXException {
		// do nothing.
	}

	@Override
	public void setDocumentLocator(Locator locator) {
		cpeHandler.setDocumentLocator(locator);
	}

	@Override
	public void skippedEntity(String name) throws SAXException {
		// do nothing
		
	}

	@Override
	public void startDocument() throws SAXException {
		throw new SAXException( "Unexpected start document");
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
		throw new SAXException( "Unexpected start element: "+uri+localName+ " from "+this);
	}

	@Override
	public final void startPrefixMapping(String prefix, String uri) throws SAXException {
		cpeHandler.startPrefixMapping(prefix, uri);
	}

}
