package org.xenei.cpe.xml.transform;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.DatasetFactory;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.apache.jena.riot.system.StreamRDF;
import org.apache.jena.vocabulary.RDF;
import org.junit.Test;
import org.xenei.cpe.rdf.CPEDatatype;
import org.xenei.cpe.rdf.connection.RDFConnectionStream;
import org.xenei.cpe.rdf.vocabulary.CPE;
import org.xenei.cpe.rdf.vocabulary.CPE23;
import org.xenei.cpe.rdf.vocabulary.ChangeType;
import org.xenei.cpe.rdf.vocabulary.DeprecationType;
import org.xenei.cpe.rdf.vocabulary.EvidenceType;
import org.xenei.cpe.rdf.vocabulary.XCPE;
import org.xml.sax.SAXException;

import us.springett.parsers.cpe.values.Part;

public class CPETransformerTestOnSample {

	private Dataset ds;
	private Model model;
	private URL xmlURL;

	public CPETransformerTestOnSample() throws ParserConfigurationException, SAXException, IOException {
		ds = DatasetFactory.create();
		RDFConnection conn = RDFConnectionFactory.connect(ds);

		xmlURL = CPETransformerTestOnSample.class.getResource("/CPE.xml");

		StreamRDF stream = new RDFConnectionStream(conn);

		stream.start();

		CPEHandler handler = new CPEHandler(stream, xmlURL);

		try (InputStream xmlInput = xmlURL.openStream()) {
			handler.load(xmlInput, xmlURL.toString());
		}
		stream.finish();

		model = ds.getNamedModel(xmlURL.toExternalForm());
		model.setNsPrefixes(handler.getPrefixMapping());

		model.write(System.out, "TURTLE");
		System.out.println("end of model");

	}

	private void testGenerator(String modelName, Model model) {
		Resource gen = model.createResource(xmlURL.toExternalForm());
		assertTrue(modelName + " missing rdf:type", gen.hasProperty(RDF.type, CPE.Generator));
		assertTrue(modelName + " missing productName",
				gen.hasProperty(CPE.productName, "National Vulnerability Database (NVD)"));
		assertTrue(modelName + " missing product_version", gen.hasProperty(CPE.productVersion, "3.28"));
		assertTrue(modelName + " missing schema_version", gen.hasProperty(CPE.schemaVersion, "2.3"));
		assertTrue(modelName + " missing timestamp", gen.hasProperty(CPE.timestamp, "2019-07-04T03:50:27.441Z"));
	}

	@Test
	public void testGenerator() {
		testGenerator("source model", model);
		testGenerator("default model", ds.getDefaultModel());
	}

	@Test
	public void testDeprecation() {
		// check deprecation
		Resource s = model.createResource("cpe:/a:3com:tippingpoint_ips:-");
		Resource o = model.createResource("cpe:/h:3com:tippingpoint_ips:-");

		assertTrue("missing deprecated-by", s.hasProperty(CPE.deprecatedBy, o));
		assertTrue(s.getURI() + " missing deprecation date", s.hasProperty(CPE.deprecationDate));
		assertTrue(s.getURI() + " missing deprecated flag", s.hasProperty(CPE.deprecated));
	}

	@Test
	public void test23Deprecation() {
		Resource s = model.createResource("cpe:2.3:a:3com:tippingpoint_ips:-:*:*:*:*:*:*:*");
		Statement stmt = s.getProperty(CPE23.deprecation);
		assertNotNull("Missing deprecation", stmt);
		Resource deprecation = stmt.getResource();
		assertEquals(CPE23.DeprecationType, deprecation.getRequiredProperty(RDF.type).getResource());
		assertEquals("2010-12-28T12:35:59.023-05:00", deprecation.getRequiredProperty(CPE23.date).getString());

		Resource depInfo = deprecation.getRequiredProperty(CPE23.deprecatedBy).getResource();
		assertEquals(CPE23.DeprecationInfo, depInfo.getRequiredProperty(RDF.type).getResource());
		assertEquals(DeprecationType.NAME_CORRECTION.name(),
				depInfo.getRequiredProperty(CPE23.type).getLiteral().getLexicalForm());
		Resource depItem = model.createResource("cpe:2.3:h:3com:tippingpoint_ips:-:*:*:*:*:*:*:*");
		assertEquals(depItem, depInfo.getRequiredProperty(CPE23.cpe23Item).getResource());
	}

	@Test
	public void test23Provenance() {
		Resource s = model.createResource("cpe:2.3:h:3com:tippingpoint_ips:-:*:*:*:*:*:*:*");
		Statement stmt = s.getRequiredProperty(CPE23.provenanceRecord);
		Resource provenance = stmt.getResource();
		assertTrue("missing provenance record type", provenance.hasProperty(RDF.type, CPE23.ProvenanceRecordType));

		// check submitter
		stmt = provenance.getRequiredProperty(CPE23.submitter);
		Resource submitter = stmt.getResource();
		assertTrue("missing submitter date", submitter.hasLiteral(CPE23.date, "2010-12-28T12:35:59.023-05:00"));
		assertTrue("submitter missing organization type", submitter.hasProperty(RDF.type, CPE23.OrganizationType));
		stmt = submitter.getRequiredProperty(CPE23.systemId);
		Resource org = stmt.getResource();
		assertEquals("Wrong url for submitter org", ResourceFactory.createResource("http://example.com/submitter"),
				org);
		assertTrue("missing submitter org name", org.hasLiteral(CPE23.name, "submitter"));
		assertTrue("missing submitter org description",
				org.hasLiteral(CPE23.description, "The submitter organization"));
		assertTrue("submitter org missing organization type", org.hasProperty(RDF.type, XCPE.Organization));

		// check authority
		stmt = provenance.getRequiredProperty(CPE23.authority);
		Resource authority = stmt.getResource();
		assertTrue("missing authority date", authority.hasLiteral(CPE23.date, "2010-12-28T12:35:59.023-05:00"));
		assertTrue("authority missing organization type", authority.hasProperty(RDF.type, CPE23.OrganizationType));
		stmt = authority.getRequiredProperty(CPE23.systemId);
		org = stmt.getResource();
		assertEquals("Wrong url for authority org", ResourceFactory.createResource("http://example.com/authority"),
				org);
		assertTrue("missing submitter org name", org.hasLiteral(CPE23.name, "authority"));
		assertTrue("missing submitter org description",
				org.hasLiteral(CPE23.description, "The authority organization"));
		assertTrue("submitter org missing organization type", org.hasProperty(RDF.type, XCPE.Organization));

		// check change-description
		stmt = provenance.getRequiredProperty(CPE23.changeDescription);
		Resource change = stmt.getResource();
		assertEquals("wrong change type", ChangeType.AUTHORITY_CHANGE,
				change.getRequiredProperty(CPE23.changeType).getLiteral().getValue());
		assertTrue("wrong change date", change.hasLiteral(CPE23.date, "2010-12-28T12:35:59.023-05:00"));
		assertEquals("missing change evidence", EvidenceType.CURATOR_UPDATE,
				change.getRequiredProperty(CPE23.evidence).getLiteral().getValue());
		Resource url = model.createResource("http://example.com/reference");
		assertTrue("missing change evidence reference", change.hasProperty(CPE23.evidenceReference, url));
		assertTrue("missing change comments",
				change.hasProperty(CPE23.comments, "The change description comment text"));

		assertTrue("change evidence reference missing Evidence Reference Type",
				url.hasProperty(RDF.type, CPE23.EvidenceReferenceType));
	}

	@Test
	public void testCheck() {
		Resource s = model.createResource("cpe:/h:hp:advancestack_10base-t_switching_hub_j3200a:-");
		assertTrue("missing check", s.hasProperty(CPE.check));
		Statement stmt = s.getProperty(CPE.check);
		Resource check = stmt.getResource();
		assertTrue("missing check type", check.hasProperty(RDF.type, CPE.ChecktypeType));
		Resource url = ResourceFactory.createResource("http://example.com/OVAL");
		assertTrue("missing system", check.hasProperty(CPE.system, url));
		url = ResourceFactory.createResource("http://example.com/external+file");
		assertTrue("missing href", check.hasProperty(CPE.href, url));
		assertTrue("missing value", check.hasProperty(XCPE.checkText, "The Test Identifier"));
	}

	@Test
	public void testItem() {
		String nameStr = "cpe:/a:10web:form_maker:1.6.3::~~~wordpress~~";
		String cpe23Str = "cpe:2.3:a:10web:form_maker:1.6.3:*:*:*:*:wordpress:*:*";
		Resource item = model.createResource(nameStr);
		assertTrue("Missing type", item.hasProperty(RDF.type, CPE.ItemType));
		Literal lit = ResourceFactory.createTypedLiteral(nameStr, CPEDatatype.cpeDatatype);
		assertTrue("missing CPE name", item.hasProperty(CPE.name, lit));
		assertTrue("missinc XCPE name", item.hasProperty(XCPE.name, lit));
		lit = ResourceFactory.createLangLiteral("10web Form Maker 1.6.3 for WordPress", "en-US");
		assertTrue("missing title", item.hasProperty(CPE.title, lit));
		Resource item23 = model.createResource(cpe23Str);
		assertTrue("missing cpe23 item", item.hasProperty(CPE23.cpe23Item, item23));
		lit = ResourceFactory.createTypedLiteral(Part.APPLICATION);
		assertTrue("Missing xcpe:part", item.hasProperty(XCPE.part, lit));
		assertTrue("Missing xcpe:product", item.hasProperty(XCPE.product, "form_maker"));
		assertTrue("Missing xcpe:targetSw", item.hasProperty(XCPE.targetSw, "wordpress"));
		assertTrue("Missing xcpe:vendor", item.hasProperty(XCPE.vendor, "10web"));
		assertTrue("Missing xcpe:version", item.hasProperty(XCPE.version, "1.6.3"));
	}

	@Test
	public void testReference() throws IOException, TransformerException, ParserConfigurationException, SAXException {

		Resource s = model.createResource("cpe:/a:10web:form_maker:1.6.3::~~~wordpress~~");
		Statement stmt = s.getRequiredProperty(CPE.references);
		Resource ref = stmt.getResource();
		assertTrue("missing ReferencesType", ref.hasProperty(RDF.type, CPE.ReferenceType));
		assertTrue("wrong reference", ref.hasProperty(CPE.reference, "Change Log"));
		Resource url = ResourceFactory.createResource("https://wordpress.org/plugins/form-maker/#developers");
		assertEquals("wrong url", url, ref.getRequiredProperty(XCPE.referenceURL).getResource());
	}

	@Test
	public void testTitles() {
		// check titles
		Resource s = model.createResource("cpe:/h:3com:tippingpoint_ips:-");
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
	public void testNotes() {
		// check notes
		Resource s = model.createResource("cpe:/h:hp:advancestack_10base-t_switching_hub_j3200a:-");
		Statement stmt = s.getProperty(CPE.note);
		Literal l = stmt.getLiteral();
		assertEquals("en-US", l.getLanguage());
		assertEquals("Use product number and detail plain name", l.getString());

	}

}
