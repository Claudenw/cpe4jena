package org.xenei.cpe.xml.transform.handlers;

import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * A generic element in the XML document.  The generic element in the XML document contains text.
 * This class will capture the text and create a property to add it to the subject handler.
 * The only attribute processed is the xml:lang attribute.   
 */
public class GenericElement extends CPEHandlerBase {

	private Property predicate;
	private Resource subject;
	private StringBuilder sb;
	private String lang;
	
	/**
	 * Constructor.
	 * 
	 * Only the xml:lang attribute is processed.  All others are ignored.
	 * 
	 * @param subject the subject handler to add the property to.
	 * @param uri the URI for this generic element.
	 * @param localName the local name for this generic element.
	 * @param attributes the attributes for this generic element.
	 */
	public GenericElement(SubjectHandler subject, String uri, String localName, Attributes attributes) {
		super( (CPEHandlerBase) subject);
		this.subject = subject.getSubject();
		this.sb = new StringBuilder();
		this.predicate = ResourceFactory.createProperty(uri, localName);
		this.lang = attributes.getValue( "xml:lang");
	}

	/**
	 * Constructor.
	 * 
	 * Only the xml:lang attribute is processed.  All others are ignored.
	 * 
	 * @param baseHandler the baseHandler for the queue.
	 * @param subject the Resource to add the property to.
	 * @param uri the URI for this generic element.
	 * @param localName the local name for this generic element.
	 * @param attributes the attributes for this generic element.
	 */
	public GenericElement(CPEHandlerBase baseHandler, Resource subject, String uri, String localName, Attributes attributes) {
		super( baseHandler );
		this.subject = subject;
		this.sb = new StringBuilder();
		this.predicate = ResourceFactory.createProperty(uri, localName);
		this.lang = attributes.getValue( "xml:lang");
	}


	@Override
	public String toString()
	{
		return String.format( "%s from %s", predicate.getURI(), subject.toString() );
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		sb.append( ch, start, length );
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		String fqName = uri+localName;
		if (predicate.getURI().equals( fqName ))
		{
			Literal object = ResourceFactory.createLangLiteral(sb.toString(), lang==null?"":lang);
			addTriple( subject, predicate, object);
			pop();
		}
		else {
			super.endElement(uri, localName,  qName );
		}
	}

}
