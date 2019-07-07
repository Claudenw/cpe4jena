package org.xenei.cpe.xml.transform;

import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xenei.cpe.rdf.vocabulary.CPE;
import org.xenei.cpe.rdf.vocabulary.XCPE;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * CPE Transformer class. <b> This class is used to transform a given XML file
 * (containing CPEs) using a defined XSL file to a consumable rdf format. </b>
 */
public class CPETransformer {

	private static final Logger LOG = LoggerFactory.getLogger(CPETransformer.class);

	public static final String DATA_URL = "https://nvd.nist.gov/feeds/xml/cpe/dictionary/official-cpe-dictionary_v2.3.xml.gz";

	public static void load(CPEHandler handler) throws ParserConfigurationException, SAXException, IOException {
		URL xmlURL = new URL(DATA_URL);
		
		try (InputStream xmlInput = new GzipCompressorInputStream(new BufferedInputStream(xmlURL.openStream()))) {
			load(xmlInput, DATA_URL, handler);
		}
	}

	public static void load(InputStream xmlInput, String base, CPEHandler cpeHandler)
			throws ParserConfigurationException, SAXException, IOException {
         
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(true);

		SAXParser parser = factory.newSAXParser();
		XMLReader xmlreader = parser.getXMLReader();
		xmlreader.setContentHandler(cpeHandler);
		cpeHandler.startPrefixMapping("cpe",  CPE.uri);
		cpeHandler.startPrefixMapping("xcpe", XCPE.uri);
		xmlreader.parse(new InputSource(xmlInput));
	}

}
