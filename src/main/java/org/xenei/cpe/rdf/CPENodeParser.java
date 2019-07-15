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
import org.apache.jena.graph.Node;
import org.apache.jena.sparql.expr.ExprEvalException;
import org.apache.jena.sparql.expr.NodeValue;
import org.apache.jena.sparql.function.Function;

import us.springett.parsers.cpe.Cpe;
import us.springett.parsers.cpe.CpeParser;
import us.springett.parsers.cpe.exceptions.CpeParsingException;

public class CPENodeParser {

	public static Cpe parse(Function function, NodeValue cpeNode) {
		Node n = cpeNode.asNode();
		if (n.isLiteral()) {
			try {
				return CpeParser.parse(n.getLiteralLexicalForm());
			} catch (CpeParsingException e) {
				throw new ExprEvalException(
						Lib.className(function) + ": cpeNode not in CPE format " + n.getLiteralLexicalForm());
			}
		} else if (n.isURI()) {
			try {
				return CpeParser.parse(n.getURI());
			} catch (CpeParsingException e) {
				throw new ExprEvalException(Lib.className(function) + ": cpeNode not in CPE format " + n.getURI());
			}
		} else {
			throw new ExprEvalException(
					Lib.className(function) + ": cpeNode not a Literal or URI " + cpeNode.asString());
		}
	}

}
