package org.xenei.cpe.xml.transform.handlers.cpe;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.sparql.core.Quad;
import org.apache.jena.vocabulary.RDF;
import org.xenei.cpe.rdf.vocabulary.CPE;
import org.xenei.cpe.xml.transform.handlers.GenericElement;
import org.xenei.cpe.xml.transform.handlers.CPEHandlerBase;
import org.xenei.cpe.xml.transform.handlers.SubjectHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * Class for generator element parsing.
 * 
 * When SubjectHandler.addTriple is called this method also adds the value to the 
 * the graphName() subject in the default graph.
 */
public class CpeGenerator extends CPEHandlerBase implements SubjectHandler {

	private Resource subject;

	/**
	 * Constructor.
	 * 
	 * @param list the handler for Cpelist that this generator element is contained in. 
	 */
	public CpeGenerator(CpeList list) {
		super(list);
		subject = ResourceFactory.createResource(source().toExternalForm());
		addTriple(subject, RDF.type, CPE.generator);
	}

	@Override
	public void addTriple(Object p, Object o) {
		addTriple(subject, p, o);
		addQuad( Quad.defaultGraphNodeGenerated, graphName(), p, o);
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
