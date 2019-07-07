package org.xenei.cpe.xml.transform.handlers.cpe;

import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.ResourceFactory;
import org.xenei.cpe.rdf.vocabulary.CPE;
import org.xenei.cpe.xml.transform.handlers.CPEHandlerBase;
import org.xml.sax.SAXException;

public class CpeNote extends CPEHandlerBase {

	private CpeItem item;
	private String lang;
	private StringBuilder sb;

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
