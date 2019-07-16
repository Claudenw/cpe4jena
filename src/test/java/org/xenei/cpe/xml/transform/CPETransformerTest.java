package org.xenei.cpe.xml.transform;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.jena.arq.querybuilder.SelectBuilder;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.DatasetFactory;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.apache.jena.riot.system.StreamRDFCountingBase;
import org.junit.Test;
import org.junit.Ignore;
import org.xenei.cpe.rdf.connection.RDFConnectionStream;
import org.xml.sax.SAXException;

@Ignore
public class CPETransformerTest {

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

	@Test
	public void fusekiTest() throws IOException, TransformerException, ParserConfigurationException, SAXException {

		URL xmlURL = new URL("file:///home/claude/Downloads/official-cpe-dictionary_v2.3.xml");

		RDFConnection conn = RDFConnectionFactory.connectFuseki("http://localhost:3030/CPE");
//		Dataset ds = DatasetFactory.create();
//		RDFConnection conn = RDFConnectionFactory.connect(ds);

		StreamRDFCountingBase stream = new StreamRDFCountingBase(new RDFConnectionStream(conn));

		stream.start();
		CPEHandler handler = new CPEHandler(stream, xmlURL);

		try (InputStream xmlInput = xmlURL.openStream()) {
			handler.load(xmlInput, xmlURL.toString());
		}

		stream.finish();
		System.out.println(stream.count());
		// System.out.println(ds.asDatasetGraph().getDefaultGraph().size());

		SelectBuilder sb = new SelectBuilder().addVar("?p").setDistinct(true).addWhere("?s", "?p", "?o");
		try (QueryExecution qe = conn.query(sb.build());) {
			ResultSet rs = qe.execSelect();
			rs.forEachRemaining(qs -> System.out.println(qs));
		}
	}
}
