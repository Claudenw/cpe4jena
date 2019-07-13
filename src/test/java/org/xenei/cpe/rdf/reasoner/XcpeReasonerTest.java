package org.xenei.cpe.rdf.reasoner;

import static org.junit.Assert.assertTrue;

import org.apache.jena.arq.querybuilder.UpdateBuilder;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.junit.Before;
import org.junit.Test;
import org.xenei.cpe.rdf.vocabulary.CPE;
import org.xenei.cpe.rdf.vocabulary.CPE23;
import org.xenei.cpe.rdf.vocabulary.XCPE;

public class XcpeReasonerTest {
	
	private Model data;
	
	@Before
	public void setup() {
		data = ModelFactory.createDefaultModel();
		data.setNsPrefix("cpe", CPE.uri);
		data.setNsPrefix("cpe23", CPE23.uri);
		data.setNsPrefix("xcpe", XCPE.uri);
		data.setNsPrefix("rdf", RDF.uri);
		data.setNsPrefix("rdfs", RDFS.uri);
		data.setNsPrefix("owl", OWL.NS);		
	}

	@Test
	public void testXcpeLanguage() {
		
		Resource r = data.createResource( "http://example.com/id");
		data.add( r, XCPE.language, ResourceFactory.createPlainLiteral("en-US" ));
		
		InfModel model = XcpeReasoner.createInfModel(data);
		r = model.createResource( "http://example.com/id" );
		assertTrue( "Did not add Item", r.hasProperty(  RDF.type, XCPE.Item));
		
	}

	@Test
	public void testDeprecation() {
		
		Resource r = data.createResource( "http://example.com/one");
		Resource r2 =  data.createResource( "http://example.com/two");
		
		data.add( r, XCPE.deprecates, r2);
		
		InfModel model = XcpeReasoner.createInfModel(data);
		r2 = model.createResource( "http://example.com/two" );
		
		assertTrue( "Did not add deprecated-by", r2.hasProperty(  XCPE.deprecatedBy, r));;
		
	}
}
