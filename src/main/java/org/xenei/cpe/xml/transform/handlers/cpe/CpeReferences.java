package org.xenei.cpe.xml.transform.handlers.cpe;

import org.xenei.cpe.rdf.vocabulary.CPE;
import org.xenei.cpe.xml.transform.handlers.CPEHandlerBase;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class CpeReferences extends CPEHandlerBase {

	private CpeItem item;

	public CpeReferences(CpeItem item) {
		super(item);
		this.item = item;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		String fqName = uri + localName;
		if (fqName.equals(CPE.reference.getURI())) {
			push(new CpeReference(item, attributes));
		} else {
			super.startElement(uri, localName, fqName, attributes);
		}

	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		String fqName = uri + localName;
		if (fqName.equals(CPE.uri + "references")) {
			pop();
		} else {
			super.endElement(uri, localName, qName);
		}
	}

}
