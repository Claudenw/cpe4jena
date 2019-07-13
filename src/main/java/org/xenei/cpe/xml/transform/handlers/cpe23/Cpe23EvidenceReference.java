package org.xenei.cpe.xml.transform.handlers.cpe23;

import java.util.UUID;

import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.vocabulary.VOID;
import org.xenei.cpe.rdf.vocabulary.CPE;
import org.xenei.cpe.rdf.vocabulary.CPE23;
import org.xenei.cpe.rdf.vocabulary.EvidenceType;
import org.xenei.cpe.rdf.vocabulary.XCPE;
import org.xenei.cpe.xml.transform.handlers.CPEHandlerBase;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * A CPE Reference processor.
 *
 */
public class Cpe23EvidenceReference extends CPEHandlerBase {

	private final StringBuilder sb;
	private final Resource subject;

	/**
	 * Constructor.
	 * @param item the CPE Item this reference is associated with.
	 * @param attributes the attributes for this reference.
	 * @throws SAXException if the reference does not have an "href" attribute.
	 */
	public Cpe23EvidenceReference(Cpe23ChangeDescription item, Attributes attributes) throws SAXException {
		super(item);
		this.subject = ResourceFactory.createResource( "urn:uuid:"+UUID.randomUUID().toString());
		
		addRequiredAttribute( subject, EvidenceType.class, attributes, "evidence", CPE23.evidenceType);
		item.addTriple(CPE23.evidenceReference, subject);
		sb = new StringBuilder();
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		sb.append(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		String fqName = uri + localName;
		if (CPE23.evidenceReference.getURI().equals(fqName)) {
			Resource evidence = ResourceFactory.createResource(sb.toString());
			
			addTriple(subject, XCPE.evidenceURL, evidence);
			pop();
		} else {
			super.endElement(uri, localName, qName);
		}
	}

}
