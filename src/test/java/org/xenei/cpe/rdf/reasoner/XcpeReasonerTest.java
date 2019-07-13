package org.xenei.cpe.rdf.reasoner;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.apache.jena.arq.querybuilder.UpdateBuilder;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.reasoner.rulesys.Rule;
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
	public void testXcpePart() {
		
		Resource r = data.createResource( "http://example.com/id");
		data.add( r, XCPE.part, "x");
		
		InfModel model = XcpeReasoner.createInfModel(data);
		r = model.createResource( "http://example.com/id" );
		assertTrue( "Did not add Item", r.hasProperty(  RDF.type, XCPE.Item));
		
	}
	
	@Test
	public void testXcpeVendor() {
		
		Resource r = data.createResource( "http://example.com/id");
		data.add( r, XCPE.vendor, "x");
		
		InfModel model = XcpeReasoner.createInfModel(data);
		r = model.createResource( "http://example.com/id" );
		assertTrue( "Did not add Item", r.hasProperty(  RDF.type, XCPE.Item));
		
	}
	
	@Test
	public void testXcpeProduct() {
		
		Resource r = data.createResource( "http://example.com/id");
		data.add( r, XCPE.product, "x");
		
		InfModel model = XcpeReasoner.createInfModel(data);
		r = model.createResource( "http://example.com/id" );
		assertTrue( "Did not add Item", r.hasProperty(  RDF.type, XCPE.Item));
		
	}
	
	@Test
	public void testXcpeVersion() {
		
		Resource r = data.createResource( "http://example.com/id");
		data.add( r, XCPE.version, "x");
		
		InfModel model = XcpeReasoner.createInfModel(data);
		r = model.createResource( "http://example.com/id" );
		assertTrue( "Did not add Item", r.hasProperty(  RDF.type, XCPE.Item));
		
	}

	@Test
	public void testXcpeUpdate() {
		
		Resource r = data.createResource( "http://example.com/id");
		data.add( r, XCPE.update, "x");
		
		InfModel model = XcpeReasoner.createInfModel(data);
		r = model.createResource( "http://example.com/id" );
		assertTrue( "Did not add Item", r.hasProperty(  RDF.type, XCPE.Item));
		
	}
	@Test
	public void testXcpeEdition() {
		
		Resource r = data.createResource( "http://example.com/id");
		data.add( r, XCPE.edition, "x");
		
		InfModel model = XcpeReasoner.createInfModel(data);
		r = model.createResource( "http://example.com/id" );
		assertTrue( "Did not add Item", r.hasProperty(  RDF.type, XCPE.Item));
		
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
	public void testXcpeSwEdition() {
		
		Resource r = data.createResource( "http://example.com/id");
		data.add( r, XCPE.swEdition, "x");
		
		InfModel model = XcpeReasoner.createInfModel(data);
		r = model.createResource( "http://example.com/id" );
		assertTrue( "Did not add Item", r.hasProperty(  RDF.type, XCPE.Item));
		
	}

	@Test
	public void testXcpeTargetSw() {
		
		Resource r = data.createResource( "http://example.com/id");
		data.add( r, XCPE.targetSw, "x");
		
		InfModel model = XcpeReasoner.createInfModel(data);
		r = model.createResource( "http://example.com/id" );
		assertTrue( "Did not add Item", r.hasProperty(  RDF.type, XCPE.Item));
		
	}
	
	@Test
	public void testXcpeTargetHw() {
		
		Resource r = data.createResource( "http://example.com/id");
		data.add( r, XCPE.targetHw, "x");
		
		InfModel model = XcpeReasoner.createInfModel(data);
		r = model.createResource( "http://example.com/id" );
		assertTrue( "Did not add Item", r.hasProperty(  RDF.type, XCPE.Item));
		
	}
	
	@Test
	public void testXcpeOther() {
		
		Resource r = data.createResource( "http://example.com/id");
		data.add( r, XCPE.other, "x");
		
		InfModel model = XcpeReasoner.createInfModel(data);
		r = model.createResource( "http://example.com/id" );
		assertTrue( "Did not add Item", r.hasProperty(  RDF.type, XCPE.Item));
		
	}

	@Test
	public void testXcpeName() {
		
		Resource r = data.createResource( "http://example.com/id");
		data.add( r, XCPE.name, "x");
		
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
	
	@Test
	public void testDeprecatedBy() {
		
		Resource r = data.createResource( "http://example.com/one");
		Resource r2 =  data.createResource( "http://example.com/two");
		
		data.add( r, XCPE.deprecatedBy, r2);
		
		InfModel model = XcpeReasoner.createInfModel(data);
		r2 = model.createResource( "http://example.com/two" );
		
		assertTrue( "Did not add deprecated-by", r2.hasProperty(  XCPE.deprecates, r));;
		
	}
	@Test
	public void testCpeDeprecation() {
		Resource r = data.createResource( "http://example.com/one");
		Resource r2 =  data.createResource( "http://example.com/two");
		
		data.add( r, CPE.deprecatedBy, r2);
		
		InfModel model = XcpeReasoner.createInfModel(data);
		r = model.createResource( "http://example.com/one");
		r2 = model.createResource( "http://example.com/two" );
		
		assertTrue( r.hasProperty( XCPE.deprecatedBy, r2 ));
		assertTrue( r2.hasProperty( XCPE.deprecates, r));
		
	}
	
	@Test
	public void testCpe23Deprecation() {
		Resource r = data.createResource( "http://example.com/one");
		Resource r2 =  data.createResource( "http://example.com/two");
		
		Resource t = data.createResource();
		data.add( r, CPE23.deprecation, t );
		Resource t2 = data.createResource();
		data.add( t, CPE23.deprecatedBy, t2 );
		data.add( t2, CPE23.cpe23Item, r2 );
		
		InfModel model = XcpeReasoner.createInfModel(data);
		r = model.createResource( "http://example.com/one");
		r2 = model.createResource( "http://example.com/two" );
		
		assertTrue( "Did not add deprecated-by", r.hasProperty(  XCPE.deprecatedBy, r2));
		assertTrue( "Did not add deprecates", r2.hasProperty( XCPE.deprecates, r));
		
	}
	
	@Test
	public void testCpeName() {
		
		Resource r = data.createResource( "http://example.com/id");
		data.add( r, CPE.name, "cpe:/a:10web:form_maker:1.6.3::~~~wordpress~~");
		
		InfModel model = XcpeReasoner.createInfModel(data);
		r = model.createResource( "http://example.com/id" );
		assertTrue( "Did not add CpeItem", r.hasProperty(  RDF.type, CPE.ItemType));

		assertTrue( "Did not add XCPE Item", r.hasProperty(  RDF.type, XCPE.Item));

		
	}
	
	@Test
	public void testCpe23Name() {
		
		Resource r = data.createResource( "http://example.com/id");
		data.add( r, CPE23.name, "cpe:2.3:a:10web:form_maker:1.6.3:*:*:*:*:wordpress:*:*");;
		
		InfModel model = XcpeReasoner.createInfModel(data);
		r = model.createResource( "http://example.com/id" );
		assertTrue( "Did not add Cpe23Item", r.hasProperty(  RDF.type, CPE23.ItemType));

		assertTrue( "Did not add XCPE Item", r.hasProperty(  RDF.type, XCPE.Item));

		
	}
}
