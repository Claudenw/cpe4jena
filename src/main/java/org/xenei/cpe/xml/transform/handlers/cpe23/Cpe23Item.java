package org.xenei.cpe.xml.transform.handlers.cpe23;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.xenei.cpe.rdf.vocabulary.CPE;
import org.xenei.cpe.rdf.vocabulary.CPE23;
import org.xenei.cpe.xml.transform.handlers.GenericElement;
import org.xenei.cpe.xml.transform.handlers.CPEHandlerBase;
import org.xenei.cpe.xml.transform.handlers.SubjectHandler;
import org.xenei.cpe.xml.transform.handlers.cpe.CpeItem;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * Handler for a CPE23 element.
 *
 */
public class Cpe23Item extends CPEHandlerBase implements SubjectHandler {

	private Resource subject;

	/**
	 * Constructor.
	 * 
	 * @param item       the subject handler this CPE23 item is part of.
	 * @param attributes the attributes of this item.
	 * @throws SAXException if the attributes do not contain a name that is of a
	 *                      proper cpe23 format.
	 */
	public Cpe23Item(SubjectHandler item, Attributes attributes) throws SAXException {
		super((CPEHandlerBase) item);
		String name = attributes.getValue("name");
		if (name == null) {
			throw new SAXException(CpeItem.ELEMENT + " must have name attribute");
		}

		subject = addCPE(name);
		addTriple(subject, RDF.type, CPE23.ItemType);
		item.addTriple(CPE23.cpe23Item, subject);
		addTriple(subject, CPE.cpeItem, item.getSubject());
	}

	@Override
	public String toString() {
		return subject.getURI();
	}

	@Override
	public void addTriple(Object p, Object o) {
		addTriple(subject, p, o);
	}

	@Override
	public Resource getSubject() {
		return subject;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		String fqName = uri + localName;
		if (CPE23.provenanceRecord.getURI().equals(fqName)) {
			push(new Cpe23ProvenanceRecord(this, attributes));
		} else if (CPE23.deprecation.getURI().equals(fqName)) {
			push(new Cpe23Deprecation(this, attributes));
		} else {
			push(new GenericElement(this, uri, localName, attributes));
		}

	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		String fqName = uri + localName;
		if (CPE23.cpe23Item.getURI().equals(fqName)) {
			pop();
		} else {
			super.endElement(uri, localName, qName);
		}
	}
}
