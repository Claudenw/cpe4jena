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

import org.apache.jena.atlas.lib.Lib;
import org.apache.jena.sparql.expr.ExprEvalException;
import org.apache.jena.sparql.expr.NodeValue;
import org.apache.jena.sparql.function.FunctionBase2;

import us.springett.parsers.cpe.Cpe;

public class CPEWellFormedValue extends FunctionBase2 {

	@Override
	public NodeValue exec(NodeValue cpeNode, NodeValue segmentNode) {
	
		Cpe cpe  = CPENodeParser.parse(this, cpeNode );
		
		if (segmentNode.isLiteral())
		{
			CPESegment segment = CPESegment.valueOf( segmentNode.getString() );
			String result = segment.extractWellFormedSegment( cpe );
			return NodeValue.makeString( result );
		} else {
			throw new ExprEvalException(Lib.className(this)+": segmentNode not a Literal "+segmentNode.asString());
		}
	}

}
