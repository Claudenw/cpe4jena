package org.xenei.cpe.xml.transform.handlers.cpe;

import org.xenei.cpe.rdf.vocabulary.CPE;
import org.xenei.cpe.xml.transform.handlers.CPEHandlerBase;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * Processes Cpe Notes entries.
 *
 */
public class CpeNotes extends CPEHandlerBase {

	private CpeItem item;
	private String lang;

	/**
	 * Constructor.
	 * 
	 * @param item       The CpeItem this notes list is associated with.
	 * @param attributes the attributes for this notes list.
	 */
	public CpeNotes(CpeItem item, Attributes attributes) {
		super(item);
		this.item = item;
		this.lang = attributes.getValue("xml:lang");
		if (this.lang == null) {
			this.lang = "";
		}
	}

	@Override
	public String toString() {
		return String.format("%snotes on %s", CPE.uri, item);
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		String fqName = uri + localName;
		if (fqName.equals(CPE.note.getURI())) {
			push(new CpeNote(item, lang));
		} else {
			super.startElement(uri, localName, fqName, attributes);
		}

	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		String fqName = uri + localName;
		if (fqName.equals(CPE.uri + "notes")) {
			pop();
		} else {
			super.endElement(uri, localName, qName);
		}
	}

}
