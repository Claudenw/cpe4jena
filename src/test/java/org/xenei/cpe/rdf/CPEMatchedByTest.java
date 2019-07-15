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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.jena.graph.NodeFactory;
import org.apache.jena.sparql.expr.NodeValue;
import org.junit.Test;

import us.springett.parsers.cpe.exceptions.CpeParsingException;

public class CPEMatchedByTest {
	private static final String URN = "cpe:2.3:h:vendor:product:version:update:edition:language:swEdition:targetSw:targetHw:other";
	private static final String URN2 = "cpe:2.3:a:vendor:product:version:update:edition:language:swEdition:targetSw:targetHw:other";
	private static final String URNWild = "cpe:2.3:*:vendor:product:version:update:edition:language:swEdition:targetSw:targetHw:other";
	private static CPEMatchedBy cpeMatchedBy = new CPEMatchedBy();

	public CPEMatchedByTest() throws CpeParsingException {

	}

	@Test
	public void matchSameTest() {
		NodeValue cpe1Node = NodeValue.makeNode(NodeFactory.createURI(URN));
		NodeValue cpe2Node = NodeValue.makeNode(NodeFactory.createURI(URN));
		NodeValue result = cpeMatchedBy.exec(cpe1Node, cpe2Node);
		assertTrue(result.getBoolean());
	}

	@Test
	public void matchDifferentTest() {
		NodeValue cpe1Node = NodeValue.makeNode(NodeFactory.createURI(URN));
		NodeValue cpe2Node = NodeValue.makeNode(NodeFactory.createURI(URN2));
		NodeValue result = cpeMatchedBy.exec(cpe1Node, cpe2Node);
		assertFalse(result.getBoolean());
	}

	@Test
	public void matchRightWildcardTest() {
		NodeValue cpe1Node = NodeValue.makeNode(NodeFactory.createURI(URN));
		NodeValue cpe2Node = NodeValue.makeNode(NodeFactory.createURI(URNWild));
		NodeValue result = cpeMatchedBy.exec(cpe1Node, cpe2Node);
		assertTrue(result.getBoolean());
	}

	@Test
	public void matchLeftWildcardTest() {
		NodeValue cpe1Node = NodeValue.makeNode(NodeFactory.createURI(URNWild));
		NodeValue cpe2Node = NodeValue.makeNode(NodeFactory.createURI(URN));
		NodeValue result = cpeMatchedBy.exec(cpe1Node, cpe2Node);
		assertFalse(result.getBoolean());
	}

}
