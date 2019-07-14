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
package org.xenei.cpe.rdf.vocabulary;


import java.net.URL;

import org.apache.jena.datatypes.RDFDatatype ;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property ;
import org.apache.jena.rdf.model.Resource ;
import org.xenei.cpe.rdf.CPEDatatype;
import org.xenei.cpe.rdf.PartDatatype;

/**
    The standard RDF vocabulary.
*/

public class XCPE {

    /**
     * The namespace of the vocabulary as a string
     */
    public static final String uri = "http://xenei.org/cpe/rdf#";
    private static Model model = ModelFactory.createDefaultModel();

    /** returns the URI for this schema
        @return the URI for this schema
    */
    public static String getURI()
        { return uri; }

    protected static final Resource resource( String local )
        { return model.createResource( uri + local ); }

    public static final Property property( String local )
        { return model.createProperty( uri, local ); }
    
	public static Model getSchema() {
		return model;
	}


    public static final Resource CpeGraph = resource( "CpeGraph");
    
    public static final Property part = property( "part");
    public static final Property vendor = property( "vendor" );
    public static final Property product = property( "product" );
    public static final Property version = property( "version" );
    public static final Property update = property( "update" );
    public static final Property edition = property( "edition" );
    public static final Property language = property( "language" );
    public static final Property swEdition = property( "swEdition" );
    public static final Property targetSw = property( "targetSw" );
    public static final Property targetHw = property( "targetHw" );
    public static final Property other = property( "other" );

    public static final Resource Organization = resource( "Organization" ); 
    public static final Resource Item = resource( "ItemType" );

    public static final Property deprecatedBy = property( "deprecated-by");
    public static final Property deprecates = property( "deprecates");
    public static final Property name = property( "name" );
    public static final Property systemId = property( "system-id" );
    public static final Property referenceURL = property( "referenceURL");
    public static final Property checkText = property( "checkText");
    
    public static final RDFDatatype cpeDatatype = CPEDatatype.cpeDatatype;
    public static final RDFDatatype partDatatype = PartDatatype.partDatatype;
    
    static {
		URL url = CPE.class.getResource("./XCPE.ttl");
		model.read( CPE.class.getResourceAsStream("XCPE.ttl"), url.toExternalForm(), "TURTLE");
	}
    
    
}
