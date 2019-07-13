package org.xenei.cpe.rdf;

import org.apache.jena.datatypes.DatatypeFormatException;
import org.apache.jena.datatypes.RDFDatatype;
import org.apache.jena.datatypes.TypeMapper;
import org.apache.jena.graph.impl.AdhocDatatype;
import org.apache.jena.graph.impl.LiteralLabel;
import org.slf4j.LoggerFactory;
import org.xenei.cpe.rdf.vocabulary.EvidenceType;

import us.springett.parsers.cpe.values.Part;

public class EvidenceDatatype extends AdhocDatatype {

	public static final String URI = "java:" + EvidenceType.class.getName();
	public static final RDFDatatype evidenceDatatype = new EvidenceDatatype();

	/* register this data type */
	static {
		LoggerFactory.getLogger(PartDatatype.class).debug("Registering " + EvidenceDatatype.class.getCanonicalName());

		TypeMapper.getInstance().registerDatatype(evidenceDatatype);
	}

	/**
	 * Constructor.
	 */
	private EvidenceDatatype() {
		super(EvidenceType.class);
	}

	/**
	 * Use byTerm equality.
	 */
	@Override
	public boolean isEqual(LiteralLabel litLabel1, LiteralLabel litLabel2) {
		return isEqualByTerm(litLabel1, litLabel2);
	}

	/**
	 * Parse the lexical form of the value segment into the value segment.
	 *
	 * @param lexicalForm the lexical form for the value segment.
	 * @throws DatatypeFormatException if lexical is more than one character
	 */
	@Override
	public Object parse(final String lexicalForm) throws DatatypeFormatException {
		try {
			return EvidenceType.valueOf(lexicalForm);
		} catch (IllegalArgumentException e) {
			throw new DatatypeFormatException(lexicalForm, this, e.getMessage());
		}
	}

	/**
	 * Convert a value of this datatype out to lexical form.
	 */
	@Override
	public String unparse(final Object value) {
		if (value instanceof Part) {
			return ((EvidenceType) value).toString();
		}
		return super.unparse(value);
	}

	@Override
	public String toString() {
		return "Datatype[" + uri + (getJavaClass() == null ? "" : " -> " + getJavaClass()) + "]";
	}

}
