package org.xenei.cpe.rdf.vocabulary;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

public class CPE {

	public static final String uri = "http://cpe.mitre.org/dictionary/2.0";
	
	protected static final Resource resource( String local )
    { return ResourceFactory.createResource( uri + local ); }

	public static final Property property( String local )
    { return ResourceFactory.createProperty( uri, local ); }

	public static final Resource cpeList = resource( "cpe-list");

	public static final Resource generator = resource( "generator");
	
	public static final Property cpeItem = property( "cpe-item");
	public static final Property title = property( "title" );
	public static final Property reference = property( "reference" );
	public static final Property type = property( "type" );
	public static final Property name = property( "name" );
	public static final Property note = property( "note" );
	public static final Property deprecationDate = property("deprecation_date");
		
	
	

}
