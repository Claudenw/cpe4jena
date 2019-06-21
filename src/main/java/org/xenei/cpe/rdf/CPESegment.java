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

import us.springett.parsers.cpe.Cpe;

import org.apache.jena.rdf.model.Property;
import org.xenei.cpe.rdf.vocabulary.CPE;

public enum CPESegment {
	part( "the type of entry: application, operating system, or hardware", CPE.part),
	vendor( "the vendor of the CPE entry", CPE.vendor),
    product( "the product of the CPE entry", CPE.product),
    version( "the version of the CPE entry", CPE.version),
    update( "the update of the CPE entry", CPE.update),
    edition( "the edition of the CPE entry", CPE.edition),
    language( "the language of the CPE entry", CPE.language),
    swEdition( "the swEdition of the CPE entry", CPE.swEdition),
    targetSw( "the targetSw of the CPE entry", CPE.targetSw),
    targetHw( "the targetHw of the CPE entry", CPE.targetHw),
    other( "the other of the CPE entry", CPE.other);
    
    private String desc;
    private Property prop;
    
    CPESegment( String desc, Property prop )
    {
		this.desc = desc;
		this.prop = prop;
    }

    public String description() {
    	return desc;
    }
    
    public Property property() {
    	return prop;
    }
    
    public String extractSegment( Cpe cpe )
    {
    	switch( this ) {
    		case part:
    			return cpe.getPart().getAbbreviation();
    		case vendor:
    			return cpe.getVendor();
    		case product:
    			return cpe.getProduct();
    		case version:
    			return cpe.getVersion();
    		case update:
    			return cpe.getUpdate();
    		case edition:
    			return cpe.getEdition();
    		case language:
    			return cpe.getLanguage();
    		case swEdition:
    			return cpe.getSwEdition();
    		case targetSw:
    			return cpe.getTargetSw();
    		case targetHw:
    			return cpe.getTargetHw();
    		case other:
    			return cpe.getOther();
    	}
    	throw new IllegalStateException( "Unknown enum value "+this);
    }
    
    public String extractWellFormedSegment( Cpe cpe )
    {
    	switch( this ) {
    		case part:
    			return cpe.getPart().getAbbreviation();
    		case vendor:
    			return cpe.getWellFormedVendor();
    		case product:
    			return cpe.getWellFormedProduct();
    		case version:
    			return cpe.getWellFormedVersion();
    		case update:
    			return cpe.getWellFormedUpdate();
    		case edition:
    			return cpe.getWellFormedEdition();
    		case language:
    			return cpe.getWellFormedLanguage();
    		case swEdition:
    			return cpe.getWellFormedSwEdition();
    		case targetSw:
    			return cpe.getWellFormedTargetSw();
    		case targetHw:
    			return cpe.getWellFormedTargetHw();
    		case other:
    			return cpe.getWellFormedOther();
    	}
    	throw new IllegalStateException( "Unknown enum value "+this);
    }

}
