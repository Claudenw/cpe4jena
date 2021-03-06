package org.xenei.cpe.xml.transform.handlers.cpe23;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.RDF;
import org.xenei.cpe.rdf.vocabulary.CPE23;
import org.xenei.cpe.rdf.vocabulary.EvidenceType;
import org.xenei.cpe.xml.transform.handlers.CPEHandlerBase;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * A CPE Reference processor.
 *
 */
public class Cpe23EvidenceReference extends CPEHandlerBase {

	private final StringBuilder sb;
	private final Cpe23ChangeDescription item;

	/**
	 * Constructor.
	 * 
	 * @param item       the CPE Item this reference is associated with.
	 * @param attributes the attributes for this reference.
	 * @throws SAXException if the reference does not have an "href" attribute.
	 */
	public Cpe23EvidenceReference(Cpe23ChangeDescription item, Attributes attributes) throws SAXException {
		super(item);
		this.item = item;
		addRequiredAttribute(item.getSubject(), EvidenceType.class, attributes, "evidence", CPE23.evidence);
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
			item.addTriple(CPE23.evidenceReference, evidence);
			addTriple(evidence, RDF.type, CPE23.EvidenceReferenceType);
			pop();
		} else {
			super.endElement(uri, localName, qName);
		}
	}

}
