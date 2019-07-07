package org.xenei.cpe.xml.transform.handlers;

import org.xenei.cpe.rdf.vocabulary.CPE;
import org.xenei.cpe.xml.transform.CPEHandler;
import org.xenei.cpe.xml.transform.handlers.cpe.CpeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class DocumentHandler extends CPEHandlerBase {

	public DocumentHandler(CPEHandler cpeHandler) {
		super(cpeHandler);
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		String fqName = uri+localName;
		if (fqName.equals( CPE.cpeList.getURI()))
		{
			push( new CpeList( this, attributes ));
		} else {
			super.startElement( uri, localName, qName, attributes);
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		throw new SAXException( "Unexpected end Element "+uri+localName);
	}

	@Override
	public void endDocument() throws SAXException {
		pop();
	}

	

}
