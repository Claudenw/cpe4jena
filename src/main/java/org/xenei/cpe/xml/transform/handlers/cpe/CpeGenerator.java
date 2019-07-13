package org.xenei.cpe.xml.transform.handlers.cpe;

import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
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

	public static final String ELEMENT = CPE.uri+"generator";

	private Resource subject;

	/**
	 * Constructor.
	 * 
	 * @param list the handler for Cpelist that this generator element is contained in. 
	 */
	public CpeGenerator(CpeList list) {
		super(list);
		subject = ResourceFactory.createResource(source().toExternalForm());
		addTriple(subject, RDF.type, CPE.Generator);
		addQuad( Quad.defaultGraphNodeGenerated, subject, RDF.type, CPE.Generator);
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
		push(new GeneratorGenericElement(this, uri, localName, attributes));
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		String fqName = uri + localName;
		if (CpeGenerator.ELEMENT.equals(fqName)) {
			pop();
		} else {
			super.endElement(uri, localName, qName);
		}
	}
	
	private class GeneratorGenericElement extends CPEHandlerBase {
		
		private GenericElement named;
		private Property predicate;
		private Resource subject;
		private StringBuilder sb;
		private String lang;
		
		
		public GeneratorGenericElement(SubjectHandler subject, String uri, String localName, Attributes attributes) {
			super( (CPEHandlerBase) subject);
			named = new GenericElement( subject, uri, localName, attributes);			
			this.subject = subject.getSubject();
			this.sb = new StringBuilder();
			this.predicate = ResourceFactory.createProperty(uri, localName);
			this.lang = attributes.getValue( "xml:lang");
		}

		
		
		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			sb.append( ch, start, length );
			named.characters(ch, start, length);
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			String fqName = uri+localName;
			if (predicate.getURI().equals( fqName ))
			{
				Literal object = ResourceFactory.createLangLiteral(sb.toString(), lang==null?"":lang);
				addQuad( Quad.defaultGraphNodeGenerated, subject, predicate, object);
				/* 
				 * named will pop the stack so push it on
				 */
				push( named );				
				named.endElement(uri, localName, fqName);
				pop();
			}
			else {
				super.endElement(uri, localName,  qName );
			}
		}

	}

}
