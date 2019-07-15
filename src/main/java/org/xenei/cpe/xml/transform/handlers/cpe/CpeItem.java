package org.xenei.cpe.xml.transform.handlers.cpe;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.xenei.cpe.rdf.vocabulary.CPE;
import org.xenei.cpe.rdf.vocabulary.CPE23;
import org.xenei.cpe.xml.transform.handlers.GenericElement;
import org.xenei.cpe.xml.transform.handlers.CPEHandlerBase;
import org.xenei.cpe.xml.transform.handlers.SubjectHandler;
import org.xenei.cpe.xml.transform.handlers.cpe23.Cpe23Item;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * A CpeItem.
 *
 */
public class CpeItem extends CPEHandlerBase implements SubjectHandler {

	public static final String ELEMENT = CPE.uri + "cpe-item";

	private Resource subject;

	/**
	 * Constructor.
	 * 
	 * @param cpeList    The CpeList this is enclosed by
	 * @param attributes the attributes for this item.
	 * @throws SAXException on missing attributes.
	 */
	public CpeItem(CpeList cpeList, Attributes attributes) throws SAXException {
		super(cpeList);
		String name = attributes.getValue("name");
		if (name == null) {
			throw new SAXException(CpeItem.ELEMENT + " must have name attribute");
		}

		subject = addCPE(name);

		addTriple(subject, RDF.type, CPE.ItemType);

		addOptionalAttribute(subject, attributes, "deprecated", CPE.deprecated);
		addOptionalAttribute(subject, attributes, "deprecation_date", CPE.deprecationDate);

		String deprecatedBy = attributes.getValue("deprecated_by");
		if (deprecatedBy != null) {
			addTriple(subject, CPE.deprecatedBy, addCPE(deprecatedBy));
		}
	}

	@Override
	public Resource getSubject() {
		return subject;
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
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		String fqName = uri + localName;

		if (fqName.equals(CPE.references.getURI())) {
			push(new CpeReferences(this));
		} else if (fqName.equals(CPE23.cpe23Item.getURI())) {
			push(new Cpe23Item(this, attributes));
		} else if (fqName.equals(CPE.uri + "notes")) {
			push(new CpeNotes(this, attributes));
		} else if (fqName.equals(CPE.check.getURI())) {
			push(new CpeCheck(this, attributes));
		} else {
			push(new GenericElement(this, uri, localName, attributes));
		}

	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		String fqName = uri + localName;
		if (CpeItem.ELEMENT.equals(fqName)) {
			pop();
		} else {
			super.endElement(uri, localName, qName);
		}
	}

}
