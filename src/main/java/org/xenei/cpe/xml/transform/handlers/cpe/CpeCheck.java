package org.xenei.cpe.xml.transform.handlers.cpe;

import java.util.UUID;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.RDF;
import org.xenei.cpe.rdf.vocabulary.CPE;
import org.xenei.cpe.rdf.vocabulary.XCPE;
import org.xenei.cpe.xml.transform.handlers.CPEHandlerBase;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * Processes Cpe Notes entries.
 *
 */
public class CpeCheck extends CPEHandlerBase {

	private Resource subject;
	private StringBuilder sb;

	/**
	 * Constructor.
	 * 
	 * @param item       The CpeItem this notes list is associated with.
	 * @param attributes the attributes for this notes list.
	 * @throws SAXException
	 */
	public CpeCheck(CpeItem item, Attributes attributes) throws SAXException {
		super(item);
		subject = ResourceFactory.createResource("url:uuid:" + UUID.randomUUID().toString());
		item.addTriple(CPE.check, subject);
		addTriple(subject, RDF.type, CPE.ChecktypeType);
		addRequiredURLAttribute(subject, attributes, "system", CPE.system);
		addOptionalURLAttribute(subject, attributes, "href", CPE.href);
		sb = new StringBuilder();
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		sb.append(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		String fqName = uri + localName;
		if (fqName.equals(CPE.check.getURI())) {
			addTriple(subject, XCPE.checkText, sb.toString());
			pop();
		} else {
			super.endElement(uri, localName, qName);
		}
	}

}
