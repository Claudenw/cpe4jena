package org.xenei.cpe.xml.transform.handlers.cpe23;

import java.util.UUID;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.RDF;
import org.xenei.cpe.rdf.vocabulary.CPE23;
import org.xenei.cpe.xml.transform.handlers.CPEHandlerBase;
import org.xenei.cpe.xml.transform.handlers.SubjectHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * Handles the CPE23 deprecation element.
 *
 */
public class Cpe23ProvenanceRecord extends CPEHandlerBase implements SubjectHandler {

	private final Resource subject;
	
	/**
	 * Constructor.
	 * @param item the CPE23 Item this deprecation is associated with. 
	 * @param attributes the attributes for this element.
	 * @throws SAXException if the attributes does not contain a date attribute.
	 */
	public Cpe23ProvenanceRecord( Cpe23Item item, Attributes attributes) throws SAXException
	{
		super( item );
		subject = ResourceFactory.createResource( "urn:uuid:"+ UUID.randomUUID().toString());
		addTriple( subject, RDF.type, CPE23.ProvenanceRecordType);
		item.addTriple( CPE23.provenanceRecord, subject );
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		String fqName = uri+localName;
			
		if (fqName.equals( CPE23.submitter.getURI())) {
			push( new Cpe23Organization( this, CPE23.submitter, attributes ));
		}
		else if (fqName.equals( CPE23.authority.getURI())) {
			push( new Cpe23Organization( this, CPE23.submitter, attributes ));
		} else if (fqName.equals( CPE23.changeDescription.getURI())) {
			push( new Cpe23ChangeDescription( this, attributes ));
		}
		else {
			super.startElement(uri, localName, fqName, attributes);
		}

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
