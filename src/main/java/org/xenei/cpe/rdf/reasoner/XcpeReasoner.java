package org.xenei.cpe.rdf.reasoner;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.RDFSRuleReasonerFactory;
import org.apache.jena.reasoner.rulesys.Rule;
import org.xenei.cpe.rdf.vocabulary.CPE;
import org.xenei.cpe.rdf.vocabulary.CPE23;
import org.xenei.cpe.rdf.vocabulary.XCPE;

public class XcpeReasoner {

	public static InfModel createInfModel( Model data )
	{
		Model schema = ModelFactory.createUnion( CPE.getSchema(), CPE23.getSchema());
		schema = ModelFactory.createUnion( schema, XCPE.getSchema());
		GenericRuleReasoner ruleReasoner = new GenericRuleReasoner( getRules() );
		ruleReasoner.setOWLTranslation(true);
		ruleReasoner.setTransitiveClosureCaching(true);
		ruleReasoner.setMode( GenericRuleReasoner.HYBRID);

		Reasoner boundReasoner = ruleReasoner.bindSchema( schema );
		return ModelFactory.createInfModel( boundReasoner, data );
	}
	
	private static List<Rule> getRules() {
		URL rules = XcpeReasoner.class.getResource("./XCPE.rules");
		return Rule.rulesFromURL( rules.toExternalForm() );
	}
}
