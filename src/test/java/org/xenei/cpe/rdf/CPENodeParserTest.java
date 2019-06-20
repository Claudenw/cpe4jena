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

import org.apache.jena.datatypes.RDFDatatype;
import org.apache.jena.datatypes.TypeMapper;
import org.apache.jena.graph.NodeFactory;
import org.apache.jena.sparql.expr.NodeValue;
import org.apache.jena.sparql.function.FunctionBase0;
import org.junit.Test;

import us.springett.parsers.cpe.Cpe;
import us.springett.parsers.cpe.values.Part;

public class CPENodeParserTest {
	
	private MyFunction func = new MyFunction();
	private static final String URN = "cpe:2.3:h:vendor:product:version:update:edition:language:swEdition:targetSw:targetHw:other";
	
	private void verifyCpe( Cpe cpe )
	{
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
	public void parseStringTest() {
		NodeValue nv = NodeValue.makeString(URN);
		verifyCpe( CPENodeParser.parse( func, nv ) );
	}

	@Test
	public void parseLiteralTest() {
		RDFDatatype dtype = TypeMapper.getInstance().getTypeByName( CPEDatatype.URI ); 
		NodeValue nv = NodeValue.makeNode(URN, dtype );
		verifyCpe( CPENodeParser.parse( func, nv ) );
	}

	@Test
	public void parseNodeTest() {
		NodeValue nv = NodeValue.makeNode( NodeFactory.createURI(URN));
		verifyCpe( CPENodeParser.parse( func, nv ) );
	}
	
	private class MyFunction extends FunctionBase0 {

		@Override
		public NodeValue exec() {
			return NodeValue.makeString( "MyFunction" );
		}
		
	}

}
