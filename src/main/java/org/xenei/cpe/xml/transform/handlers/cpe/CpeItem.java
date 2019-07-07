package org.xenei.cpe.xml.transform.handlers.cpe;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.RDF;
import org.xenei.cpe.rdf.vocabulary.CPE;
import org.xenei.cpe.rdf.vocabulary.CPE23;
import org.xenei.cpe.rdf.vocabulary.XCPE;
import org.xenei.cpe.xml.transform.handlers.GenericElement;
import org.xenei.cpe.xml.transform.handlers.CPEHandlerBase;
import org.xenei.cpe.xml.transform.handlers.SubjectHandler;
import org.xenei.cpe.xml.transform.handlers.cpe23.Cpe23Item;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import us.springett.parsers.cpe.CpeParser;
import us.springett.parsers.cpe.exceptions.CpeParsingException;

public class CpeItem extends CPEHandlerBase implements SubjectHandler {

	private Resource subject;

	public CpeItem(CpeList cpeList, Attributes attributes) throws SAXException {
		super(cpeList);
		String name = attributes.getValue("name");
		if (name == null) {
			throw new SAXException(CPE.cpeItem.getURI() + " must have name attribute");
		}
		subject = ResourceFactory.createResource(name);
		try {
			addTriple(subject, CPE.name, CpeParser.parse(name));
		} catch (CpeParsingException e) {
			throw new SAXException("Error parsing " + name, e);
		}
		addTriple(subject, RDF.type, XCPE.cpeItemType);

		if (attributes.getValue("deprecated") != null) {
			addTriple(subject, CPE.deprecationDate, attributes.getValue("deprecation_date"));
		}
	}

	@Override
	public Resource getSubject() {
		return subject;
	}

	@Override
	public String toString() {
		return subject.getURI();
	}

	@Override
	public void addTriple(Object p, Object o) {
		addTriple(subject, p, o);
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		String fqName = uri + localName;

		if (fqName.equals(CPE.uri + "references")) {
			push(new CpeReferences(this));
		}

		else if (fqName.equals(CPE23.cpe23Item.getURI())) {
			push(new Cpe23Item(this, attributes));
		} else if (fqName.equals(CPE.uri + "notes")) {
			push(new CpeNotes(this, attributes));
		} else {
			push(new GenericElement(this, uri, localName, attributes));
		}

//			<xsl:template match="cpe-23:deprecation" mode="reference">
//			   <cpe:deprecation_date><xsl:value-of select="@date"/></cpe:deprecation_date>
//			   <xsl:apply-templates select="cpe-23:deprecated-by" mode="reference"/>
//			</xsl:template>
//			
//			<xsl:apply-templates select="cpe-23:cpe23-item" mode="reference" />
//			<xsl:apply-templates />
//		</rdf:Description>
//		
//        <xsl:apply-templates select="cpe-23:cpe23-item" mode="definition" />
//        <xsl:apply-templates select="cpe:references/cpe:reference" mode="definition" />
//	</xsl:template>
//		
//		throw new SAXException( "Unknown Element "+fqName);
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		String fqName = uri + localName;
		if (CPE.cpeItem.getURI().equals(fqName)) {
			pop();
		} else {
			super.endElement(uri, localName, qName);
		}
	}

}
