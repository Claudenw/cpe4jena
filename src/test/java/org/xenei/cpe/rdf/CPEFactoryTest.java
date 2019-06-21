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

import org.apache.jena.rdf.model.Resource;
import org.junit.Test;

import us.springett.parsers.cpe.exceptions.CpeParsingException;
import us.springett.parsers.cpe.values.Part;

public class CPEFactoryTest {
	
	private static final String URN = "cpe:2.3:h:vendor:product:version:update:edition:language:swEdition:targetSw:targetHw:other";
	
	@Test
	public void getResourceTest() throws CpeParsingException {
		
		Resource result = CPEFactory.buildResource(URN);
		result.getModel().write( System.out, "TURTLE" );
		for (CPESegment segment : CPESegment.values())
		{
			
			if (segment == CPESegment.part)
			{				
				assertEquals( Part.HARDWARE_DEVICE, result.getProperty( segment.property() ).getLiteral().getValue());
				assertEquals( Part.HARDWARE_DEVICE.name(), result.getProperty( segment.property() ).getString());
				
			}
			else {
				assertEquals( segment.name(), result.getProperty( segment.property() ).getString());
			}
		}
	}

}
