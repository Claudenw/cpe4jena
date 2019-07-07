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
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.DC;
import org.apache.jena.vocabulary.DC_11;
import org.apache.jena.vocabulary.RDF;
import org.xenei.cpe.rdf.vocabulary.XCPE;

import us.springett.parsers.cpe.Cpe;
import us.springett.parsers.cpe.CpeParser;
import us.springett.parsers.cpe.exceptions.CpeParsingException;

/**
 * Factory class to build RDF objects.
 *
 */
public class CPEFactory {
	
	/**
	 * Build an RDF resource with model that contains all the properties defined in the URN.
	 * 
	 * For a simple resource without model use asResource()
	 * @param cpeURN the URN to create a resource with model from.
	 * @return the Resource with model
	 * @throws CpeParsingException if the URN is not a valid CPE.
	 */
	public static Resource buildResource( String cpeURN ) throws CpeParsingException
	{
		return buildResource( CpeParser.parse( cpeURN ) );
	}
	
	/**
	 * Build an RDF resource with model that contains all the properties defined in the Cpe.
	 * 
	 * For a simple resource without a model use asResource()
	 * @param cpe the CPE to process.
	 * @return the RDF Resource with model.
	 */
	public static Resource buildResource( Cpe cpe ) {
		Model model = ModelFactory.createDefaultModel();
		Resource result = model.createResource( cpe.toString(), XCPE.CPE );		
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

	/**
	 * Build an RDF resource without a model
	 * 
	 * For an RDF resource with a model use buildResource()
	 * @param cpeURN the URN to create a resource with model from.
	 * @return the Resource without a model.
	 * @throws CpeParsingException if the URN is not a valid CPE.
	 */
	public static Resource asResource(String cpeURN )  throws CpeParsingException
    {
		return asResource( CpeParser.parse( cpeURN ) );
    }
	
	/**
	 * Build an RDF resource without a model.
	 * 
	 * For an RDF resource with a model use buildResource()
	 * @param cpe the CPE to process.
	 * @return the RDF Resource with model.
	 */
	public static Resource asResource(Cpe cpe )  throws CpeParsingException
    {
		return ResourceFactory.createResource( cpe.toString() );
    }

	/**
	 * Create a model that contains all the schema information for the CPE schema.
	 * @return a model containing the CPE schema statements.
	 */
	public static Model getSchema() {
		Model model = ModelFactory.createDefaultModel();
		for (CPESegment segment : CPESegment.values())
		{
			model.add( segment.property(), DC_11.description,  segment.description() );
			model.add( segment.property(), RDF.type, RDF.Property );
		}
		model.add( XCPE.CPE, DC.description, "epresentation of a Common Platform Enumeration (CPE)" );
		return model;
	}
	
}
