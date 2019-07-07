package org.xenei.cpe.xml.transform.handlers.cpe;

import org.xenei.cpe.rdf.vocabulary.CPE;
import org.xenei.cpe.xml.transform.handlers.CPEHandlerBase;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class CpeList extends CPEHandlerBase {

	public CpeList(CPEHandlerBase handlerBase, Attributes attributes) {
		super(handlerBase);
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		String fqName = uri + localName;

		if (fqName.equals(CPE.cpeItem.getURI())) {
			push(new CpeItem(this, attributes));
		} else if (fqName.equals(CPE.generator.getURI())) {
			push(new CpeGenerator(this));
		} else {
			super.startElement(uri, localName, fqName, attributes);
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		String fqName = uri + localName;
		if (fqName.equals(CPE.cpeList.getURI())) {
			pop();
		}
	}

}
