package org.xenei.cpe.rdf.reasoner;

import org.apache.jena.assembler.Assembler;
import org.apache.jena.assembler.Mode;
import org.apache.jena.assembler.assemblers.AssemblerGroup;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.DatasetFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.sparql.core.DatasetGraph;
import org.apache.jena.sparql.core.assembler.AssemblerUtils;
import org.apache.jena.sparql.core.assembler.DatasetAssembler;
import org.apache.jena.sparql.core.assembler.DatasetAssemblerException;
import org.apache.jena.sparql.util.FmtUtils;
import org.apache.jena.sparql.util.MappingRegistry;
import org.apache.jena.sparql.util.graph.GraphUtils;

public class ReasonedDatasetAssembler extends DatasetAssembler {

	private static final String URI = "http://xenei.org/cpe/rdf/reasoner#";
	public static final Resource tDataset = ResourceFactory.createResource("http://xenei.org/cpe/rdf/reasoner#Dataset");
	public static final Property pDataset = ResourceFactory.createProperty("http://xenei.org/cpe/rdf/reasoner#dataset");
	private static boolean initialized;

	static {
		init();
	}

	/**
	 * Initialize the assembler. Registers the prefix "sec" with the uri
	 * http://apache.org/jena/permission/Assembler# and registers this assembler
	 * with the uri http://apache.org/jena/permission/Assembler#Model
	 */
	static synchronized public void init() {
		if (initialized)
			return;
		MappingRegistry.addPrefixMapping("cpe", URI);
		registerWith(Assembler.general);
		initialized = true;
	}

	/**
	 * Register this assembler in the assembler group.
	 * 
	 * @param group The assembler group to register with.
	 */
	static void registerWith(AssemblerGroup group) {
		if (group == null)
			group = Assembler.general;
		group.implementWith(tDataset, new ReasonedDatasetAssembler());
	}

	@Override
	public Dataset createDataset(Assembler a, Resource root, Mode mode) {

		// get the dataset we will wrap.
		Resource datasetR = GraphUtils.getResourceValue(root, pDataset);
		if (datasetR == null) {
			throw new DatasetAssemblerException(root, "Not a resource: " + FmtUtils.stringForRDFNode(pDataset));
		}
		Dataset dataset = (Dataset) a.open(datasetR);

		ReasonedGraphMaker graphMaker = new ReasonedGraphMaker(dataset);

		DatasetGraph dg = new SoftDatasetGraphMap(graphMaker);

		AssemblerUtils.setContext(root, dg.getContext());

		return DatasetFactory.wrap(dg);
	}
}
