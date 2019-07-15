package org.xenei.cpe.xml.transform.handlers.cpe;

import java.util.UUID;

import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.RDF;
import org.xenei.cpe.rdf.vocabulary.CPE;
import org.xenei.cpe.rdf.vocabulary.XCPE;
import org.xenei.cpe.xml.transform.handlers.CPEHandlerBase;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * A CPE Reference processor.
 *
 */
public class CpeReference extends CPEHandlerBase {

	private Resource subject;
	private StringBuilder sb;

	/**
	 * Constructor.
	 * 
	 * @param item       the CPE Item this reference is associated with.
	 * @param attributes the attributes for this reference.
	 * @throws SAXException if the reference does not have an "href" attribute.
	 */
	public CpeReference(CpeItem item, Attributes attributes) throws SAXException {
		super(item);
		subject = ResourceFactory.createResource("urn:uuid:" + UUID.randomUUID().toString());
		addTriple(subject, RDF.type, CPE.ReferenceType);
		item.addTriple(CPE.references, subject);

		String href = attributes.getValue("href");
		if (href == null) {
			throw new SAXException(CPE.reference.getURI() + " requires an href");
		}
		addTriple(subject, XCPE.referenceURL, ResourceFactory.createResource(href));
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
			addTriple(subject, CPE.reference, object);
			pop();
		} else {
			super.endElement(uri, localName, qName);
		}
	}

}
