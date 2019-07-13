package org.xenei.cpe.rdf;

import org.apache.jena.datatypes.DatatypeFormatException;
import org.apache.jena.datatypes.RDFDatatype;
import org.apache.jena.datatypes.TypeMapper;
import org.apache.jena.graph.impl.AdhocDatatype;
import org.apache.jena.graph.impl.LiteralLabel;
import org.slf4j.LoggerFactory;
import org.xenei.cpe.rdf.vocabulary.ChangeType;

import us.springett.parsers.cpe.values.Part;

public class ChangeDatatype extends AdhocDatatype {

	public static final String URI = "java:" + ChangeType.class.getName();
	public static final RDFDatatype changeDatatype = new ChangeDatatype();

	/* register this data type */
	static {
		LoggerFactory.getLogger(PartDatatype.class).debug("Registering " + ChangeDatatype.class.getCanonicalName());

		TypeMapper.getInstance().registerDatatype(changeDatatype);
	}

	/**
	 * Constructor.
	 */
	private ChangeDatatype() {
		super(ChangeType.class);
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
			return ChangeType.valueOf(lexicalForm);
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
			return ((ChangeType) value).toString();
		}
		return super.unparse(value);
	}

	@Override
	public String toString() {
		return "Datatype[" + uri + (getJavaClass() == null ? "" : " -> " + getJavaClass()) + "]";
	}

}
