package org.xenei.cpe.xml.transform.handlers.cpe23;

import org.xenei.cpe.rdf.vocabulary.CPE;
import org.xenei.cpe.rdf.vocabulary.CPE23;
import org.xenei.cpe.xml.transform.handlers.CPEHandlerBase;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class Cpe23Deprecation extends CPEHandlerBase {
	
	private Cpe23Item item;
	
	public Cpe23Deprecation( Cpe23Item item, Attributes attributes) throws SAXException
	{
		super( item );
		this.item = item;
		String date = attributes.getValue( "date" );
		if (date == null)
		{
			throw new SAXException( "deprecation must have date" );
		}
		item.addTriple( CPE.deprecationDate, date);
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		String fqName = uri+localName;
			
			if (fqName.equals( CPE23.deprecatedBy.getURI())) {
				push( new Cpe23DeprecatedBy( item, attributes ));
			}
			else {
				super.startElement(uri, localName, fqName, attributes);
			}

	} 

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		String fqName = uri+localName;
		if (fqName.equals( CPE23.uri+"deprecation" ))
		{
			pop();
		} else {
			super.endElement(uri, localName, qName);
		}
	}
	



}
