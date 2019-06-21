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



import org.apache.jena.datatypes.RDFDatatype ;
import org.apache.jena.datatypes.xsd.impl.RDFLangString ;
import org.apache.jena.datatypes.xsd.impl.RDFhtml ;
import org.apache.jena.datatypes.xsd.impl.XMLLiteralType ;
import org.apache.jena.graph.Node ;
import org.apache.jena.rdf.model.Property ;
import org.apache.jena.rdf.model.Resource ;
import org.apache.jena.rdf.model.ResourceFactory ;
import org.xenei.cpe.rdf.CPEDatatype;
import org.xenei.cpe.rdf.CPESegment;
import org.xenei.cpe.rdf.PartDatatype;

/**
    The standard RDF vocabulary.
*/

public class CPE{

    /**
     * The namespace of the vocabulary as a string
     */
    public static final String uri = "http://xenei.org/cpe/rdf#";

    /** returns the URI for this schema
        @return the URI for this schema
    */
    public static String getURI()
        { return uri; }

    protected static final Resource resource( String local )
        { return ResourceFactory.createResource( uri + local ); }

    public static final Property property( String local )
        { return ResourceFactory.createProperty( uri, local ); }

    public static final Resource CPE = resource( "Cpe" );
    public static final Resource Part = resource( "Part" );

    
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

    

    public static final RDFDatatype cpeDatatype = CPEDatatype.cpeDatatype;
    public static final RDFDatatype partDatatype = PartDatatype.partDatatype;
    
}
