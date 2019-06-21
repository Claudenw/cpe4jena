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

import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.DC_11;
import org.xenei.cpe.rdf.vocabulary.CPE;

import us.springett.parsers.cpe.Cpe;
import us.springett.parsers.cpe.CpeParser;
import us.springett.parsers.cpe.exceptions.CpeParsingException;

public class CPEFactory {
	
	public static Resource buildResource( String cpeURN ) throws CpeParsingException
	{
		return buildResource( CpeParser.parse( cpeURN ) );
	}
	
	public static Resource buildResource( Cpe cpe ) {
		Model model = ModelFactory.createDefaultModel();
		Resource result = model.createResource( cpe.toString(), CPE.CPE );		
		for (CPESegment segment : CPESegment.values())
		{
			model.add( segment.property(), DC_11.description,  segment.description() );
			if (segment == CPESegment.part)
			{
				result.addLiteral( segment.property(), cpe.getPart() );
			} else {
				String value = segment.extractSegment(cpe);
				if (StringUtils.isNotBlank(value)) {
					result.addLiteral( segment.property(), value);
				}
			}
		}
		return result;
	}

}
