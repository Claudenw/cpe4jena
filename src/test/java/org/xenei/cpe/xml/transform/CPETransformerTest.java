package org.xenei.cpe.xml.transform;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.jena.arq.querybuilder.SelectBuilder;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.DatasetFactory;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.apache.jena.riot.system.StreamRDF;
import org.apache.jena.riot.system.StreamRDFCountingBase;
import org.junit.Test;
import org.xenei.cpe.rdf.connection.RDFConnectionStream;
import org.xenei.cpe.rdf.vocabulary.CPE;
import org.xenei.cpe.rdf.vocabulary.CPE23;
import org.xenei.cpe.rdf.vocabulary.XCPE;
import org.xml.sax.SAXException;

public class CPETransformerTest {

	/**
	 * A test on a representative sample of the data.
	 * 
	 * @throws IOException                  on IO error
	 * @throws TransformerException         on transformer error
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	@Test
	public void sampleTest() throws IOException, TransformerException, ParserConfigurationException, SAXException {

		Dataset ds = DatasetFactory.create();
		RDFConnection conn = RDFConnectionFactory.connect(ds);

		URL xmlURL = CPETransformerTest.class.getResource("/CPE.xml");

		StreamRDF stream = new RDFConnectionStream(conn);

		stream.start();

		CPEHandler handler = new CPEHandler(stream, xmlURL);

		try (InputStream xmlInput = xmlURL.openStream()) {
			handler.load(xmlInput, xmlURL.toString());
		}
		stream.finish();

		Model model = ds.getNamedModel( xmlURL.toExternalForm() );
		model.setNsPrefixes(handler.getPrefixMapping());

		model.write(System.out, "TURTLE");
		System.out.println("end of model");

		Resource s = model.createResource("cpe:/a:10web:form_maker:1.6.3::~~~wordpress~~");
		Resource o = model.createResource("cpe:2.3:a:10web:form_maker:1.6.3:*:*:*:*:wordpress:*:*");

		// check cpe23-item / cpe:item
		assertTrue(s.getURI() + " is missing " + CPE23.cpe23Item.getURI(), s.hasProperty(CPE23.cpe23Item, o));
		assertTrue(o.getURI() + " is missing " + CPE.cpeItem.getURI(), o.hasProperty(CPE.cpeItem, s));

		// check reference
		Resource reference = model.createResource("https://wordpress.org/plugins/form-maker/#developers");
		assertTrue("Missing reference", s.hasProperty(CPE.reference, reference));
		assertTrue("Missing reference type", model.contains(reference, CPE.type, (RDFNode) null));

		// check deprecation
		s = model.createResource("cpe:2.3:a:3com:tippingpoint_ips:-:*:*:*:*:*:*:*");
		o = model.createResource("cpe:2.3:h:3com:tippingpoint_ips:-:*:*:*:*:*:*:*");
		assertTrue("missing deprecated-by", s.hasProperty(CPE23.deprecatedBy, o));
		assertTrue(s.getURI() + " missing deprecated reason", s.hasProperty(XCPE.deprecatedReason));

		assertTrue(s.getURI() + " missing deprecation date", s.hasProperty(CPE.deprecationDate));
		Statement stmt = s.getProperty(CPE.cpeItem);

		Resource item = stmt.getResource();
		assertTrue(item.getURI() + " missing deprecation_date ", item.hasProperty(CPE.deprecationDate));
		assertTrue(item.getURI() + " missing cpe23-item", item.hasProperty(CPE23.cpe23Item));
		assertTrue(o.getURI() + " missing depcreates", o.hasProperty(XCPE.deprecates, s));

		// check notes
		s = model.createResource("cpe:/h:hp:advancestack_10base-t_switching_hub_j3200a:-");
		stmt = s.getProperty(CPE.note);
		Literal l = stmt.getLiteral();
		assertEquals("en-US", l.getLanguage());
		assertEquals("Use product number and detail plain name", l.getString());

		// check titles
		s = model.createResource("cpe:/h:3com:tippingpoint_ips:-");
		Map<String, String> langs = new HashMap<String, String>();
		langs.put("ja-JP", "スリーコム TippingPoint IPS");
		langs.put("en-US", "3Com TippingPoint IPS");
		for (RDFNode title : model.listObjectsOfProperty(s, CPE.title).toList()) {
			assertTrue("Not a literal", title.isLiteral());
			String lang = title.asLiteral().getLanguage();
			assertTrue(lang + " not found in map", langs.containsKey(lang));
			assertEquals(title.asLiteral().getString(), langs.get(lang));
			langs.remove(lang);
		}
		assertTrue("Did not find all languages", langs.isEmpty());
	}

	@Test
	public void largeTest() throws IOException, TransformerException, ParserConfigurationException, SAXException {

		URL xmlURL = new URL("file:///home/claude/Downloads/official-cpe-dictionary_v2.3.xml");

		Dataset ds = DatasetFactory.create();
		RDFConnection conn = RDFConnectionFactory.connect(ds);

		StreamRDFCountingBase stream = new StreamRDFCountingBase(new RDFConnectionStream(conn));

		stream.start();
		CPEHandler handler = new CPEHandler(stream, xmlURL);

		try (InputStream xmlInput = xmlURL.openStream()) {
			handler.load(xmlInput, xmlURL.toString());
		}

		stream.finish();
		System.out.println(stream.count());
		System.out.println(ds.asDatasetGraph().getDefaultGraph().size());

		SelectBuilder sb = new SelectBuilder().addVar("?p").setDistinct(true).addWhere("?s", "?p", "?o");
		try (QueryExecution qe = conn.query(sb.build());) {
			ResultSet rs = qe.execSelect();
			rs.forEachRemaining(qs -> System.out.println(qs));
		}
	}
}
