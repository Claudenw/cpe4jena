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
import org.apache.jena.graph.NodeFactory;
import org.apache.jena.sparql.expr.NodeValue;
import org.junit.Test;

public class CPEValueTest {
	private static RDFDatatype dtype = TypeMapper.getInstance().getTypeByName( CPEDatatype.URI );
	private static final String URN = "cpe:2.3:h:vendor:product:version:update:edition:language:swEdition:targetSw:targetHw:other";
	private static CPEValue cpeValue = new CPEValue();
	

	
	@Test
	public void execURI() {
		NodeValue cpeURI = NodeValue.makeNode( NodeFactory.createURI(URN));
		for (CPESegment segment : CPESegment.values())
		{
			NodeValue seg = NodeValue.makeString( segment.name() );
			
			NodeValue nv = cpeValue.exec( cpeURI, seg );
			assertTrue( nv.isLiteral() );
			if (segment == CPESegment.part )
			{
				assertEquals( "h", nv.getString());
			} else {
				assertEquals( segment.name(), nv.getString());
			}
		}
	}

	@Test
	public void execLiteral() {
		NodeValue cpeLiteral = NodeValue.makeNode(URN, dtype );
		for (CPESegment segment : CPESegment.values())
		{
			NodeValue seg = NodeValue.makeString( segment.name() );
			
			NodeValue nv = cpeValue.exec( cpeLiteral, seg );
			assertTrue( nv.isLiteral() );
			if (segment == CPESegment.part )
			{
				assertEquals( "h", nv.getString());
			} else {
				assertEquals( segment.name(), nv.getString());
			}
		}
	}

}
