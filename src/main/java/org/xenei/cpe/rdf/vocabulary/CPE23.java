package org.xenei.cpe.rdf.vocabulary;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

public class CPE23 {

	public static final String uri = "http://scap.nist.gov/schema/cpe-extension/2.3";
	
	protected static final Resource resource( String local )
    { return ResourceFactory.createResource( uri + local ); }

	public static final Property property( String local )
    { return ResourceFactory.createProperty( uri, local ); }
	
	public static final Property cpe23Item = property( "cpe23-item");	
	public static final Property deprecatedBy = property( "deprecated-by" );
 }
