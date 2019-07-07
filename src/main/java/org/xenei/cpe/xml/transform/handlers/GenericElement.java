package org.xenei.cpe.xml.transform.handlers;

import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.ResourceFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class GenericElement extends CPEHandlerBase {

	private Property predicate;
	private SubjectHandler subject;
	private StringBuilder sb;
	private String lang;
	
	public GenericElement(SubjectHandler subject, String uri, String localName, Attributes attributes) {
		super( (CPEHandlerBase) subject);
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
			subject.addTriple( predicate, object);
			pop();
		}
		else {
			super.endElement(uri, localName,  qName );
		}
	}

}
