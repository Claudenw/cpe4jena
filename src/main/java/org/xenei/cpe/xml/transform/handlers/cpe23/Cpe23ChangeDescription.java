package org.xenei.cpe.xml.transform.handlers.cpe23;

import java.util.UUID;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.RDF;
import org.xenei.cpe.rdf.vocabulary.CPE23;
import org.xenei.cpe.rdf.vocabulary.ChangeType;
import org.xenei.cpe.xml.transform.handlers.CPEHandlerBase;
import org.xenei.cpe.xml.transform.handlers.GenericElement;
import org.xenei.cpe.xml.transform.handlers.SubjectHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * Handles the CPE23 organization types element.
 *
 */
public class Cpe23ChangeDescription extends CPEHandlerBase implements SubjectHandler {
	private final Resource subject;

	/**
	 * Constructor.
	 * 
	 * @param item       the CPE23 Item this deprecation is associated with.
	 * @param attributes the attributes for this element.
	 * @throws SAXException if the attributes does not contain a date attribute.
	 */
	public Cpe23ChangeDescription(Cpe23ProvenanceRecord item, Attributes attributes) throws SAXException {
		super(item);
		subject = ResourceFactory.createResource("urn:uuid:" + UUID.randomUUID().toString());
		addTriple(subject, RDF.type, CPE23.ChangeDescriptionType);
		item.addTriple(CPE23.changeDescription, subject);
		addRequiredAttribute(subject, ChangeType.class, attributes, "change-type", CPE23.changeType);
		addOptionalAttribute(subject, attributes, "date", CPE23.date);
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		String fqName = uri + localName;
		if (fqName.equals(CPE23.evidenceReference.getURI())) {
			push(new Cpe23EvidenceReference(this, attributes));
		} else {
			// handles comments
			push(new GenericElement(this, uri, localName, attributes));
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		String fqName = uri + localName;
		if (fqName.equals(CPE23.changeDescription.getURI())) {
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
