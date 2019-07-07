package org.xenei.cpe.xml.transform.handlers.cpe23;


import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.RDF;
import org.xenei.cpe.rdf.vocabulary.CPE;
import org.xenei.cpe.rdf.vocabulary.CPE23;
import org.xenei.cpe.rdf.vocabulary.XCPE;
import org.xenei.cpe.xml.transform.handlers.GenericElement;
import org.xenei.cpe.xml.transform.handlers.CPEHandlerBase;
import org.xenei.cpe.xml.transform.handlers.SubjectHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import us.springett.parsers.cpe.CpeParser;
import us.springett.parsers.cpe.exceptions.CpeParsingException;

public class Cpe23Item extends CPEHandlerBase implements SubjectHandler {

	private Resource subject; 
	public Cpe23Item(SubjectHandler item, Attributes attributes) throws SAXException {
		super((CPEHandlerBase)item);
		String name = attributes.getValue("name");
		if (name == null)
		{
			throw new SAXException( CPE.cpeItem.getURI()+" must have name attribute" );
		}
		subject = ResourceFactory.createResource( name );
		try {
			addTriple( subject, CPE.name, CpeParser.parse( name ));
		} catch (CpeParsingException e) {
			throw new SAXException( "Error parsing "+name, e);
		}
		addTriple( subject, RDF.type, XCPE.cpe23Type);
		item.addTriple( CPE23.cpe23Item, subject);
		addTriple( subject, CPE.cpeItem, item.getSubject());
	}

	@Override
	public String toString()
	{
		return subject.getURI();
	}
	
	@Override
	public void addTriple( Object p, Object o)
	{
		addTriple( subject, p, o );
	}
	
	@Override
	public Resource getSubject() {
		return subject;
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		String fqName = uri+localName;
			
			if (fqName.equals( CPE23.uri+"deprecation")) {
				push( new Cpe23Deprecation( this, attributes ));
			}
			else {
				push( new GenericElement( this, uri, localName, attributes ) );
			}

	} 

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		String fqName = uri+localName;
		if (CPE23.cpe23Item.getURI().equals( fqName ))
		{
			pop();
		} else {
			super.endElement(uri, localName, qName);
		}
	}
	

	/*
	 * 
	<cpe-23:cpe23-item name="cpe:2.3:a:3com:tippingpoint_ips:-:*:*:*:*:*:*:*">
    <cpe-23:deprecation date="2010-12-28T12:35:59.023-05:00">
      <cpe-23:deprecated-by name="cpe:2.3:h:3com:tippingpoint_ips:-:*:*:*:*:*:*:*" type="NAME_CORRECTION"/>
    </cpe-23:deprecation>
  </cpe-23:cpe23-item>

	 */
}
