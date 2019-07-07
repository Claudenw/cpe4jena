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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.jena.datatypes.RDFDatatype;
import org.apache.jena.datatypes.TypeMapper;
import org.junit.Test;

import us.springett.parsers.cpe.Cpe;
import us.springett.parsers.cpe.exceptions.CpeValidationException;
import us.springett.parsers.cpe.values.Part;

public class CPEDataTypeTest {
	private RDFDatatype dtype = TypeMapper.getInstance().getTypeByName( CPEDatatype.URI );
	
	private static final String URN = "cpe:2.3:h:vendor:product:version:update:edition:language:swEdition:targetSw:targetHw:other";
	
	@Test
	public void parseTest() {
		Object o = dtype.parse( URN );
		assertTrue( o instanceof Cpe );
		Cpe cpe = (Cpe) o;
		assertEquals( Part.HARDWARE_DEVICE, cpe.getPart());
		assertEquals( "vendor", cpe.getVendor());
		assertEquals( "product", cpe.getProduct());
		assertEquals( "version", cpe.getVersion());
		assertEquals( "update", cpe.getUpdate());
		assertEquals( "edition", cpe.getEdition());
		assertEquals( "language", cpe.getLanguage());
		assertEquals( "swEdition", cpe.getSwEdition());
		assertEquals( "targetSw", cpe.getTargetSw());
		assertEquals( "targetHw", cpe.getTargetHw());
		assertEquals( "other", cpe.getOther());
	}
	
	@Test
	public void unparseTest() throws CpeValidationException {
		Cpe cpe = new Cpe( Part.HARDWARE_DEVICE, "vendor", "product", "version",
	            "update", "edition", "language", "swEdition",
	            "targetSw", "targetHw", "other");
		assertEquals( URN, dtype.unparse( cpe ) );
	}
}
