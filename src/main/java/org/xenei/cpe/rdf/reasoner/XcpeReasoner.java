package org.xenei.cpe.rdf.reasoner;

import java.net.URL;
import java.util.List;

import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.xenei.cpe.rdf.vocabulary.CPE;
import org.xenei.cpe.rdf.vocabulary.CPE23;
import org.xenei.cpe.rdf.vocabulary.XCPE;

/**
 * Build a reasoner for the XCPE classes.
 *
 */
public class XcpeReasoner {

	private static Reasoner reasoner;

	public static InfModel createInfModel(Model data) {
		return ModelFactory.createInfModel(getReasoner(), data);
	}

	private static List<Rule> getRules() {
		URL rules = XcpeReasoner.class.getResource("./XCPE.rules");
		return Rule.rulesFromURL(rules.toExternalForm());
	}

	private synchronized static Reasoner getReasoner() {
		if (reasoner == null) {
			Model schema = ModelFactory.createUnion(CPE.getSchema(), CPE23.getSchema());
			schema = ModelFactory.createUnion(schema, XCPE.getSchema());
			GenericRuleReasoner ruleReasoner = new GenericRuleReasoner(getRules());
			ruleReasoner.setOWLTranslation(true);
			ruleReasoner.setTransitiveClosureCaching(true);
			ruleReasoner.setMode(GenericRuleReasoner.HYBRID);
			reasoner = ruleReasoner.bindSchema(schema);
		}
		return reasoner;
	}
}
