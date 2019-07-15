package org.xenei.cpe.xml.transform.handlers.cpe23;

import java.util.UUID;

import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.RDF;
import org.xenei.cpe.rdf.CPEMatchDatatype;
import org.xenei.cpe.rdf.vocabulary.CPE23;
import org.xenei.cpe.rdf.vocabulary.DeprecationType;
import org.xenei.cpe.rdf.vocabulary.XCPE;
import org.xenei.cpe.xml.transform.handlers.CPEHandlerBase;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * Handles the deprecated-by element.
 *
 */
public class Cpe23DeprecatedBy extends CPEHandlerBase {

	private final Resource subject;

	/**
	 * Constructor.
	 * 
	 * @param item       the CPE23 item that this is associated with.
	 * @param attributes the attributes of this deprecated-by element.
	 * @throws SAXException if there is no name attribute.
	 */
	public Cpe23DeprecatedBy(Cpe23Deprecation item, Attributes attributes) throws SAXException {
		super(item);
		subject = ResourceFactory.createResource("urn:uuid:" + UUID.randomUUID().toString());
		addTriple(subject, RDF.type, CPE23.DeprecationInfo);
		item.addTriple(CPE23.deprecatedBy, subject);
		addRequiredAttribute(subject, DeprecationType.class, attributes, "type", CPE23.type);
		String name = attributes.getValue("name");
		if (name != null) {
			Resource matchCPE = addCPE(name);
			Literal lit = ResourceFactory.createTypedLiteral(name, CPEMatchDatatype.cpeMatchDatatype);
			addTriple(subject, CPE23.name, lit);
			addTriple(subject, CPE23.cpe23Item, matchCPE);
			addTriple(matchCPE, XCPE.deprecates, item.getSubject());
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		String fqName = uri + localName;
		if (fqName.equals(CPE23.deprecatedBy.getURI())) {
			pop();
		} else {
			super.endElement(uri, localName, qName);
		}
	}

}
