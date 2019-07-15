package org.xenei.cpe.rdf.reasoner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.DatasetFactory;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.sparql.core.DatasetGraphMap;
import org.apache.jena.vocabulary.RDF;
import org.junit.Before;
import org.junit.Test;
import org.xenei.cpe.rdf.vocabulary.XCPE;

public class ReasonedGraphMakerTest {

	public Dataset baseDataset;
	public DatasetGraphMap dataset;

	@Before
	public void setup() {
		baseDataset = DatasetFactory.create();
		dataset = new DatasetGraphMap(new ReasonedGraphMaker(baseDataset));
	}

	@Test
	public void testXcpeVendor() {

		Resource g = ResourceFactory.createResource("http://example.com/graph");
		Resource s = ResourceFactory.createResource("http://example.com/subject");
		Literal o = ResourceFactory.createPlainLiteral("x");
		dataset.add(g.asNode(), s.asNode(), XCPE.vendor.asNode(), o.asNode());

		assertTrue(dataset.find(g.asNode(), s.asNode(), XCPE.vendor.asNode(), o.asNode()).hasNext());
		assertTrue(dataset.find(g.asNode(), s.asNode(), RDF.type.asNode(), XCPE.Item.asNode()).hasNext());

		Model m = baseDataset.getNamedModel(g.getURI());

		assertTrue(m.contains(s, XCPE.vendor, o));
		assertFalse(m.contains(s, RDF.type, XCPE.Item));

	}

}
