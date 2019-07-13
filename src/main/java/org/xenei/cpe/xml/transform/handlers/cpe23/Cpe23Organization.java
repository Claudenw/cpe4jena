package org.xenei.cpe.xml.transform.handlers.cpe23;

import java.util.UUID;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.RDF;
import org.xenei.cpe.rdf.vocabulary.CPE23;
import org.xenei.cpe.xml.transform.handlers.CPEHandlerBase;
import org.xenei.cpe.xml.transform.handlers.GenericElement;
import org.xenei.cpe.xml.transform.handlers.SubjectHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * Handles the CPE23 organization types element.
 *
 */
public class Cpe23Organization extends CPEHandlerBase implements SubjectHandler {	
	private final Resource subject;

	
	/**
	 * Constructor.
	 * @param item the CPE23 Item this deprecation is associated with.
	 * @param predicate the predicate describing this organization type.
	 * @param attributes the attributes for this element.
	 * @throws SAXException if the attributes does not contain a date attribute.
	 */
	public Cpe23Organization( Cpe23ProvenanceRecord item, Property predicate, Attributes attributes) throws SAXException
	{
		super( item );
		String systemId = attributes.getValue( "system-id");
		if (systemId == null) {
			throw new SAXException( predicate.getURI()+" subjects must have a system-id attribute." );
		}
		subject = ResourceFactory.createResource( systemId );
		addTriple( subject, RDF.type, CPE23.OrganizationType );
		item.addTriple( predicate, subject );
		addRequiredAttribute( subject, attributes, "name", CPE23.name );
		addOptionalAttribute( subject, attributes, "date", CPE23.date );
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		push( new GenericElement( this, uri, localName, attributes ) );
	} 
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		String fqName = uri+localName;
		if (fqName.equals( CPE23.provenanceRecord.getURI() ))
		{
			pop();
		} else {
			super.endElement(uri, localName, qName);
		}
	}

	@Override
	public void addTriple(Object p, Object o) {
		addTriple( subject, p, o );
	}

	@Override
	public Resource getSubject() {
		return subject;
	}

}
