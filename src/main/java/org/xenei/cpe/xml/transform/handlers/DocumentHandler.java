package org.xenei.cpe.xml.transform.handlers;

import org.xenei.cpe.xml.transform.CPEHandler;
import org.xenei.cpe.xml.transform.handlers.cpe.CpeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * Class to handle the Start Document event.
 *
 */
public class DocumentHandler extends CPEHandlerBase {

	/**
	 * Constructor.
	 * 
	 * @param cpeHandler the CPE handler that has received the start document event.
	 */
	public DocumentHandler(CPEHandler cpeHandler) {
		super(cpeHandler);
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		String fqName = uri + localName;
		if (fqName.equals(CpeList.ELEMENT)) {
			push(new CpeList(this, attributes));
		} else {
			super.startElement(uri, localName, qName, attributes);
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		throw new SAXException("Unexpected end Element " + uri + localName);
	}

	@Override
	public void endDocument() throws SAXException {
		pop();
	}

}
