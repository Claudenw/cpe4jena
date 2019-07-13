package org.xenei.cpe.rdf.vocabulary;

import java.net.URL;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

/**
 * Defines the CPE vocabulary in the http://cpe.mitre.org/dictionary/2.0 namespace
 *
 */
public class CPE {
	
	public static final String uri = "http://cpe.mitre.org/dictionary/2.0";
	private static Model model = ModelFactory.createDefaultModel();
	
	protected static final Resource resource( String local )
    { return model.createResource( uri + local ); }

	public static final Property property( String local )
    { return model.createProperty( uri, local ); }

	public static Model getSchema() {
		return model;
	}
 
	public static final Resource Generator = resource( "GeneratorType");
	
	public static final Property productName = property( "product_name");
	public static final Property productVersion = property( "product_version");
	public static final Property schemaVersion = property( "schema_version" );
	public static final Property timestamp = property( "timestamp" );
	
	public static final Resource ItemType = resource( "ItemType");

	public static final Property cpeItem = property( "cpe-item" );
	public static final Property title = property( "title" );
	public static final Property note = property( "note" );
	public static final Property references = property( "references" );
	public static final Property check = property( "check");
	public static final Property name = property( "name" );
	public static final Property deprecated = property( "deprecated");
	public static final Property deprecatedBy = property( "deprecated_by" );
	public static final Property deprecationDate = property("deprecation_date");

	public static final Resource ReferenceType = resource( "ReferencesType");
	public static final Property reference = property( "reference" );
	
	public static final Resource ChecktypeType = resource( "CheckType");
	public static final Property system = property( "system" );
	public static final Property href = property( "href" );

	static {
		URL url = CPE.class.getResource("./CPE.ttl");
		model.read( CPE.class.getResourceAsStream("CPE.ttl"), url.toExternalForm(), "TURTLE");
	}


}
