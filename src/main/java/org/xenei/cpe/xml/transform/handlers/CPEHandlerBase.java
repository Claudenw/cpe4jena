package org.xenei.cpe.xml.transform.handlers;

import java.net.URL;

import org.xenei.cpe.xml.transform.CPEHandler;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public abstract class CPEHandlerBase implements ContentHandler {
	
	public static final String cpe="http://cpe.mitre.org/dictionary/2.0";
	public static final String scapCore3="http://scap.nist.gov/schema/scap-core/0.3";
	public static final String cpe23="http://scap.nist.gov/schema/cpe-extension/2.3";
	public static final String config="http://scap.nist.gov/schema/configuration/0.1";
	public static final String scapCore1="http://scap.nist.gov/schema/scap-core/0.1";
	public static final String meta="http://scap.nist.gov/schema/cpe-dictionary-metadata/0.2";

	
	private CPEHandler cpeHandler;
	
	
	public CPEHandlerBase( CPEHandler cpeHandler ) {
		this.cpeHandler = cpeHandler;
	}
	
	public CPEHandlerBase( CPEHandlerBase otherHandler ) {
		this.cpeHandler = otherHandler.cpeHandler;
	}
	
	protected final Locator getDocumentLocator()
	{
		return cpeHandler.getDocumentLocator();
	}
	
	protected final URL source() {
		return cpeHandler.source();
	}
	
	protected final CPEHandlerBase pop() throws SAXException
	{
		CPEHandlerBase me = cpeHandler.pop();
		if (me != this)
		{
			throw new SAXException( "expected "+this+" got "+me);
		}
		return me;
	}
	
	protected void push( CPEHandlerBase handler )
	{
		cpeHandler.push( handler );
	}
	
	protected final void addTriple( Object s, Object p, Object o)
	{
		cpeHandler.addTriple( s, p, o);
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
