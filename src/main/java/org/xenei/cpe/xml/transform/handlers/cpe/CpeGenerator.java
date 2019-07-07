package org.xenei.cpe.xml.transform.handlers.cpe;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.RDF;
import org.xenei.cpe.rdf.vocabulary.CPE;
import org.xenei.cpe.xml.transform.handlers.GenericElement;
import org.xenei.cpe.xml.transform.handlers.CPEHandlerBase;
import org.xenei.cpe.xml.transform.handlers.SubjectHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class CpeGenerator extends CPEHandlerBase implements SubjectHandler {

	Resource subject;

	public CpeGenerator(CpeList list) {
		super(list);
		subject = ResourceFactory.createResource(source().toExternalForm());
		addTriple(subject, RDF.type, CPE.generator);
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
		push(new GenericElement(this, uri, localName, attributes));
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		String fqName = uri + localName;
		if (CPE.generator.getURI().equals(fqName)) {
			pop();
		} else {
			super.endElement(uri, localName, qName);
		}
	}

}
