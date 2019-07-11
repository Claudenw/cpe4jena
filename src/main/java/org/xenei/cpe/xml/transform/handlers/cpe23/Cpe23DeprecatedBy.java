package org.xenei.cpe.xml.transform.handlers.cpe23;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.RDF;
import org.xenei.cpe.rdf.vocabulary.CPE23;
import org.xenei.cpe.rdf.vocabulary.XCPE;
import org.xenei.cpe.xml.transform.handlers.CPEHandlerBase;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * Handles the deprecated-by element.
 *
 */
public class Cpe23DeprecatedBy extends CPEHandlerBase {
	
	/**
	 * Constructor.
	 * @param item the CPE23 item that this is associated with.
	 * @param attributes the attributes of this deprecated-by element.
	 * @throws SAXException if there is no name attribute.
	 */
	public Cpe23DeprecatedBy(Cpe23Item item, Attributes attributes) throws SAXException {
		super(item);
		String name = attributes.getValue("name");
		if (name == null)
		{
			throw new SAXException( CPE23.deprecatedBy.getURI()+" must have a name");
		}
		Resource subject = ResourceFactory.createResource( name );
		item.addTriple( CPE23.deprecatedBy, subject );
		addTriple( subject, RDF.type, XCPE.Cpe23Type);
		addTriple( subject, XCPE.deprecates, item.getSubject() );
		String type = attributes.getValue( "type" );
		if (type != null)
		{
			item.addTriple( XCPE.deprecatedReason, type);
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		String fqName = uri+localName;
		if (fqName.equals( CPE23.deprecatedBy.getURI() ))
		{
			pop();
		} else {
			super.endElement(uri, localName, qName);
		}
	}

}
