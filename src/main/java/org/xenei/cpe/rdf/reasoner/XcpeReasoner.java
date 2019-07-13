package org.xenei.cpe.rdf.reasoner;

import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.RDFSRuleReasonerFactory;
import org.xenei.cpe.rdf.vocabulary.CPE;
import org.xenei.cpe.rdf.vocabulary.CPE23;
import org.xenei.cpe.rdf.vocabulary.XCPE;

public class XcpeReasoner {

	public static InfModel createInfModel( Model data )
	{
		Model schema = ModelFactory.createUnion( CPE.getSchema(), CPE23.getSchema());
		schema = ModelFactory.createUnion( schema, XCPE.getSchema());
		Reasoner reasoner = RDFSRuleReasonerFactory.theInstance().create(null);
		Reasoner boundReasoner = reasoner.bindSchema( schema );
		return ModelFactory.createInfModel( boundReasoner, data );
	}
}
