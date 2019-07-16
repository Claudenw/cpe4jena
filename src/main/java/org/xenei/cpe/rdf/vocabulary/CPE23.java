package org.xenei.cpe.rdf.vocabulary;

import java.net.URL;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

public class CPE23 {

	public static final String uri = "http://scap.nist.gov/schema/cpe-extension/2.3";

	private static final Model model = ModelFactory.createDefaultModel();

	protected static final Resource resource(String local) {
		return model.createResource(uri + local);
	}

	public static final Property property(String local) {
		return model.createProperty(uri, local);
	}

	public static Model getSchema() {
		return model;
	}

	public static final Resource ItemType = resource("ItemType");
	public static final Property cpe23Item = property("cpe23-item");
	public static final Property provenanceRecord = property("provenance-record");
	public static final Property deprecation = property("deprecation");
	public static final Property name = property("name");

	public static final Resource ProvenanceRecordType = resource("provenanceRecordType");
	public static final Property submitter = property("submitter");
	public static final Property authority = property("authority");
	public static final Property changeDescription = property("change-description");

	public static final Resource ChangeDescriptionType = resource("changeDescriptionType");
	public static final Property evidenceReference = property("evidence-reference");
	public static final Property evidence = property("evidence");
	public static final Property comments = property("comments");
	public static final Property changeType = property("change-type");
	public static final Property date = property("date");

	public static final Resource DeprecationType = resource("deprecationType");
	public static final Property deprecatedBy = property("deprecated-by");
	// date included as well

	public static final Resource DeprecationInfo = resource("deprecationInfoType");
	public static final Property type = property("type");
	// cpe23Item included as well

	public static final Resource OrganizationType = resource("organizationType");
	public static final Property description = property("description");
	public static final Property systemId = property("system-id");
	// name included as well
	// date included as well

	public static final Resource EvidenceReferenceType = resource("evidenceReferenceType");

	static {
		URL url = CPE23.class.getResource( "./CPE23.ttl" );
		model.read(CPE23.class.getResourceAsStream( "./CPE23.ttl" ), url.toExternalForm(), "TURTLE");
	}
}
