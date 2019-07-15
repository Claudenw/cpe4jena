package org.xenei.cpe.rdf.reasoner;

import org.apache.jena.graph.Graph;
import org.apache.jena.graph.Node;
import org.apache.jena.query.Dataset;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.sparql.core.DatasetGraphFactory.GraphMaker;
import org.apache.jena.sparql.core.Quad;

public class ReasonedGraphMaker implements GraphMaker {

	Dataset wrapped;

	public ReasonedGraphMaker(Dataset dataset) {
		this.wrapped = dataset;
	}

	@Override
	public Graph create(Node name) {
		String uri = name == null ? Quad.defaultGraphIRI.getURI() : name.getURI();
		if (!wrapped.containsNamedModel(uri)) {
			wrapped.addNamedModel(uri, ModelFactory.createDefaultModel());
		}
		InfModel model = XcpeReasoner.createInfModel(wrapped.getNamedModel(uri));
		return model.getGraph();
	}

}
