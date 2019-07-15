package org.xenei.cpe.xml.transform.handlers.cpe23;

import java.util.UUID;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.RDF;
import org.xenei.cpe.rdf.vocabulary.CPE23;
import org.xenei.cpe.xml.transform.handlers.CPEHandlerBase;
import org.xenei.cpe.xml.transform.handlers.SubjectHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * Handles the CPE23 deprecation element.
 *
 */
public class Cpe23Deprecation extends CPEHandlerBase implements SubjectHandler {

	private final Resource subject;

	/**
	 * Constructor.
	 * 
	 * @param item       the CPE23 Item this deprecation is associated with.
	 * @param attributes the attributes for this element.
	 * @throws SAXException if the attributes does not contain a date attribute.
	 */
	public Cpe23Deprecation(Cpe23Item item, Attributes attributes) throws SAXException {
		super(item);
		subject = ResourceFactory.createResource("urn:uuid:" + UUID.randomUUID().toString());
		item.addTriple(CPE23.deprecation, subject);
		addTriple(subject, RDF.type, CPE23.DeprecationType);
		addRequiredAttribute(subject, attributes, "date", CPE23.date);

	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		String fqName = uri + localName;

		if (fqName.equals(CPE23.deprecatedBy.getURI())) {
			push(new Cpe23DeprecatedBy(this, attributes));
		} else {
			super.startElement(uri, localName, fqName, attributes);
		}

	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		String fqName = uri + localName;
		if (fqName.equals(CPE23.deprecation.getURI())) {
			pop();
		} else {
			super.endElement(uri, localName, qName);
		}
	}

	@Override
	public void addTriple(Object p, Object o) {
		addTriple(subject, p, o);
	}

	@Override
	public Resource getSubject() {
		return subject;
	}

}
