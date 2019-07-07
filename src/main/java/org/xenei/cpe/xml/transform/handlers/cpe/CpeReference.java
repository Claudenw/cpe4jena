package org.xenei.cpe.xml.transform.handlers.cpe;

import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.xenei.cpe.rdf.vocabulary.CPE;
import org.xenei.cpe.xml.transform.handlers.CPEHandlerBase;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class CpeReference extends CPEHandlerBase {

	private Resource subject;
	private StringBuilder sb;

	public CpeReference(CpeItem item, Attributes attributes) throws SAXException {
		super(item);
		String href = attributes.getValue("href");
		if (href == null) {
			throw new SAXException(CPE.reference.getURI() + " requires an href");
		}
		subject = ResourceFactory.createResource(href);
		item.addTriple(CPE.reference, subject);
		sb = new StringBuilder();
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		sb.append(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		String fqName = uri + localName;
		if (CPE.reference.getURI().equals(fqName)) {
			Literal object = ResourceFactory.createStringLiteral(sb.toString());
			addTriple(subject, CPE.type, object);
			pop();
		} else {
			super.endElement(uri, localName, qName);
		}
	}

}
