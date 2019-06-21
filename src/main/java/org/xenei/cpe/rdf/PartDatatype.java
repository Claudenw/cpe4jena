/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.xenei.cpe.rdf;

import org.apache.jena.datatypes.DatatypeFormatException;
import org.apache.jena.datatypes.RDFDatatype;
import org.apache.jena.datatypes.TypeMapper;
import org.apache.jena.graph.impl.AdhocDatatype;
import org.apache.jena.graph.impl.LiteralLabel;
import org.slf4j.LoggerFactory;

import us.springett.parsers.cpe.values.Part;


public class PartDatatype extends AdhocDatatype {
	
	public static final String URI = "java:" + Part.class.getName();
    public static final RDFDatatype partDatatype = new PartDatatype();


    /* register this data type */
    static
    {
        LoggerFactory.getLogger( PartDatatype.class ).debug( "Registering " + PartDatatype.class
                .getCanonicalName() );

        TypeMapper.getInstance().registerDatatype( partDatatype );
    }

    /**
     * Constructor.
     */
    private PartDatatype() {
        super( Part.class );
    }
    
    /**
     * Use byTerm equality.
     */
    @Override
    public boolean isEqual(LiteralLabel litLabel1, LiteralLabel litLabel2) {
        return isEqualByTerm( litLabel1, litLabel2 );
    }
    
    /**
     * Parse the lexical form of the value segment into the value segment.
     *
     * @param lexicalForm
     *            the lexical form for the value segment.
     * @throws DatatypeFormatException
     *             if lexical is more than one character
     */
    @Override
    public Object parse(final String lexicalForm)  throws DatatypeFormatException {
        try {
			return Part.valueOf(lexicalForm);
		} catch (IllegalArgumentException e) {
			throw new DatatypeFormatException(lexicalForm, this, e.getMessage());
		}
    }

    /**
     * Convert a value of this datatype out to lexical form.
     */
    @Override
    public String unparse(final Object value) {
        if (value instanceof Part)
        {
        	return ((Part)value).toString();
        }
        return super.unparse( value );
    }

    @Override
    public String toString() {
        return "Datatype[" + uri + (getJavaClass() == null ? "" : " -> " + getJavaClass()) + "]";
    }


}
