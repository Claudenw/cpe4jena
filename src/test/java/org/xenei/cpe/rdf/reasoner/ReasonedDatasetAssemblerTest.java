package org.xenei.cpe.rdf.reasoner;

import static org.junit.Assert.assertTrue;

import java.net.URL;

import org.junit.Assert;
import org.apache.jena.assembler.Assembler;
import org.apache.jena.query.Dataset;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.sparql.core.DatasetGraph;
import org.apache.jena.vocabulary.RDF;
import org.junit.Before;
import org.junit.Test;
import org.xenei.cpe.rdf.vocabulary.XCPE;

public class ReasonedDatasetAssemblerTest {
	private Assembler assembler;
	private Model model;

	public ReasonedDatasetAssemblerTest() {
		assembler = Assembler.general;
	}

	@Before
	public void setUp() throws Exception {
		model = ModelFactory.createDefaultModel();
		URL url = ReasonedDatasetAssemblerTest.class.getClassLoader().getResource("./AssemblerTest.ttl");
		model.read(url.toURI().toString(), "TURTLE");
	}

	@Test
	public void testCreation() throws Exception {

		Resource r = model.createResource("http://xenei.org/cpe/rdf/reasoner/test#dataset");
		Object o = assembler.open(r);
		Assert.assertTrue(o instanceof Dataset);
		DatasetGraph dsg = ((Dataset) o).asDatasetGraph();
		Assert.assertTrue(dsg instanceof SoftDatasetGraphMap);

	}

	@Test
	public void testReadWrite() {

		Resource g = ResourceFactory.createResource("http://example.com/graph");
		Resource s = ResourceFactory.createResource("http://example.com/subject");
		Literal o = ResourceFactory.createPlainLiteral("x");

		Resource r = model.createResource("http://xenei.org/cpe/rdf/reasoner/test#dataset");
		Dataset dataset = (Dataset) assembler.open(r);
		Model tModel = dataset.getNamedModel(g.getURI());

		tModel.add(s, XCPE.vendor, o);

		assertTrue(tModel.contains(s, XCPE.vendor, o));
		assertTrue(tModel.contains(s, RDF.type, XCPE.Item));

	}

}
