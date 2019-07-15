package org.xenei.cpe.xml.transform.handlers.cpe;

import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.ResourceFactory;
import org.xenei.cpe.rdf.vocabulary.CPE;
import org.xenei.cpe.xml.transform.handlers.CPEHandlerBase;
import org.xml.sax.SAXException;

/**
 * Processes a Cpe Note element.
 *
 */
public class CpeNote extends CPEHandlerBase {

	private CpeItem item;
	private String lang;
	private StringBuilder sb;

	/**
	 * Constructor.
	 * 
	 * @param item the Item that this not is associated with.
	 * @param lang the language tag for this note (may not be null, may be an empty
	 *             string).
	 */
	public CpeNote(CpeItem item, String lang) {
		super(item);
		this.item = item;
		this.lang = lang;
		this.sb = new StringBuilder();
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		sb.append(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		String fqName = uri + localName;
		if (CPE.note.getURI().equals(fqName)) {
			Literal object = ResourceFactory.createLangLiteral(sb.toString(), lang);
			item.addTriple(CPE.note, object);
			pop();
		} else {
			super.endElement(uri, localName, qName);
		}
	}

}
