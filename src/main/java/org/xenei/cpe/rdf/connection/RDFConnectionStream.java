package org.xenei.cpe.rdf.connection;

import org.apache.jena.graph.Triple;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.DatasetFactory;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.riot.system.StreamRDF;
import org.apache.jena.sparql.core.Quad;

public class RDFConnectionStream implements StreamRDF {

	public static final int DEFAULT_BUFFER_SIZE = 1000;
	private final RDFConnection connection;
	private final Dataset dataset;
	private final int bufferSize;
	private int count;

	public RDFConnectionStream(RDFConnection connection) {
		this(connection, DEFAULT_BUFFER_SIZE);
	}

	public RDFConnectionStream(RDFConnection connection, int bufferSize) {
		this.connection = connection;
		this.dataset = DatasetFactory.create();
		this.bufferSize = bufferSize;
		this.count = 0;
	}

	@Override
	public void start() {
		// do nothing
	}

	@Override
	public void triple(Triple triple) {
		dataset.asDatasetGraph().add(Quad.defaultGraphNodeGenerated, triple.getSubject(), triple.getPredicate(),
				triple.getObject());
		checkFlush();
	}

	@Override
	public void quad(Quad quad) {
		dataset.asDatasetGraph().add(quad);
		checkFlush();

	}

	@Override
	public void base(String base) {
		// do nothing
	}

	@Override
	public void prefix(String prefix, String iri) {
		dataset.getDefaultModel().setNsPrefix(prefix, iri);
		dataset.listNames().forEachRemaining(name -> dataset.getNamedModel(name).setNsPrefix(prefix, iri));
	}

	@Override
	public void finish() {
		flush();
	}

	private void checkFlush() {

		if (++count >= bufferSize) {
			flush();
		}

	}

	private void flush() {
		if (count > 0) {
			try {
				connection.begin(ReadWrite.WRITE);
				connection.loadDataset(dataset);
				dataset.asDatasetGraph().clear();
				count = 0;
			} finally {
				connection.commit();
			}
		}

	}

}
